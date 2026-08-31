import repository.ConexaoJava;
import repository.ConexaoRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CraftingController {

    private static boolean ativo = true;

    public static void main(String[] args) {
        ConexaoRepository conexao = ConexaoJava.obterInstancia();

        try {
            Connection con = conexao.conectar();
            if (con == null) {
                System.out.println("Erro de conexão");
                return;
            }
            System.out.println("conectado com sucesso");

            while (ativo) {
                processarInstrucoesPendentes(con);
                Thread.sleep(3000);
            }

            con.close();

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void processarInstrucoesPendentes(Connection con) {
        String instructionSql = "SELECT * FROM JavaInstructions WHERE processado = false";

        try {
            PreparedStatement pstmt = con.prepareStatement(instructionSql);
            ResultSet rs = pstmt.executeQuery();
            boolean encontrouRegistros = false;

            while (rs.next()) {
                encontrouRegistros = true;
                int idJavaInstruction = rs.getInt("idJavaInstructions");
                System.out.println("Nova instrução encontrada! ID: " + idJavaInstruction);

                String tipo = rs.getString("tipo");
                switch (tipo.toUpperCase()) {
                    case "CREATE":
                        processarCreate(con, rs, idJavaInstruction);
                        break;
                    case "END":
                        processarEnd(con, idJavaInstruction);
                        break;
                    default:
                        break;
                }
            }

            if (!encontrouRegistros) {
                System.out.println("Nenhum registro encontrado");
            }

        } catch (Exception e) {
            System.out.println("Erro durante a consulta na tabela:");
            e.printStackTrace();
        }
    }

    private static void processarCreate(Connection con, ResultSet rs, int idJavaInstruction) throws Exception {
        String metodo = rs.getString("instruction");
        int idUsuario = rs.getInt("idUsuario");
        int idTemplate = rs.getInt("idTemplate");

        List<Material> materiais = buscarMateriaisDoTemplate(con, idTemplate);
        Crafting sistemaCrafting = criarSistemaCrafting(con, metodo, idUsuario);

        if (sistemaCrafting == null) {
            return;
        }

        TemplateEquipamento template = buscarTemplate(con, idTemplate);
        if (template == null) {
            System.out.println("Template não encontrado");
            return;
        }

        Equipamento equipamentoCriado = sistemaCrafting.executarCraft(template, materiais);
        Inventario.salvarEquipamento(equipamentoCriado, con, idUsuario);

        marcarComoProcessada(con, idJavaInstruction);
        System.out.println("Equipamento criado com sucesso");
    }

    private static void processarEnd(Connection con, int idJavaInstruction) {
        marcarComoProcessada(con, idJavaInstruction);
        ativo = false;
    }

    private static List<Material> buscarMateriaisDoTemplate(Connection con, int idTemplate) throws Exception {
        List<Material> materiais = new ArrayList<>();

        String materiaisSql = "SELECT \n" +
                "    m.idMaterial,\n" +
                "    m.nome,\n" +
                "    m.durabilidade,\n" +
                "    m.peso,\n" +
                "    m.valorMagico\n" +
                "FROM Template t\n" +
                "INNER JOIN TemplateUsedMaterial tum ON t.idTemplate = tum.idTemplate\n" +
                "INNER JOIN Material m ON tum.idMaterial = m.idMaterial\n" +
                "WHERE t.idTemplate = ?;";

        PreparedStatement mStmt = con.prepareStatement(materiaisSql);
        mStmt.setInt(1, idTemplate);
        ResultSet mrs = mStmt.executeQuery();

        while (mrs.next()) {
            Material material = new Material(
                mrs.getInt("idMaterial"),
                mrs.getString("nome"),
                mrs.getFloat("durabilidade"),
                mrs.getFloat("peso"),
                mrs.getFloat("valorMagico")
            );
            materiais.add(material);
        }

        return materiais;
    }

    private static Crafting criarSistemaCrafting(Connection con, String metodo, int idUsuario) throws Exception {
        switch (metodo.toUpperCase()) {
            case "AUTOMATICO":
                return new Automatico(idUsuario);

            case "MANUAL":
                Minijogo minijogo = buscarMinijogoDisponivel(con, 1);
                if (minijogo == null) {
                    System.out.println("Minijogo não encontrado");
                    return null;
                }
                return new Manual(1, minijogo);

            default:
                return null;
        }
    }

    private static Minijogo buscarMinijogoDisponivel(Connection con, int idUsuario) throws Exception {
        String minijogoSql = "SELECT \n" +
                "    mgs.idScore,\n" +
                "    mgt.nome,\n" +
                "    mgt.descricao,\n" +
                "    mgs.pontuacao\n" +
                "FROM MiniGameScore mgs\n" +
                "INNER JOIN MiniGameType mgt ON mgs.idGameType = mgt.idGameType\n" +
                "WHERE mgs.idUsuario = ? AND mgs.idEquipment IS NULL\n" +
                "LIMIT 1;";

        PreparedStatement miniStmt = con.prepareStatement(minijogoSql);
        miniStmt.setInt(1, idUsuario);
        ResultSet minirs = miniStmt.executeQuery();

        if (minirs.next()) {
            return new Minijogo(
                minirs.getInt("idScore"),
                minirs.getString("nome"),
                minirs.getString("descricao"),
                minirs.getFloat("pontuacao")
            );
        }

        return null;
    }

    private static TemplateEquipamento buscarTemplate(Connection con, int idTemplate) throws Exception {
        String templateSql = "SELECT \n" +
                "    t.idTemplateType,\n" +
                "    t.automatizavel\n" +
                "FROM Template t\n" +
                "WHERE t.idTemplate = ?;";

        PreparedStatement tempStmt = con.prepareStatement(templateSql);
        tempStmt.setInt(1, idTemplate);
        ResultSet temprs = tempStmt.executeQuery();

        if (temprs.next()) {
            int idType = temprs.getInt("idTemplateType");
            boolean automatizavel = temprs.getBoolean("automatizavel");
            return new TemplateEquipamento(idTemplate, idType, automatizavel);
        }

        return null;
    }

    private static void marcarComoProcessada(Connection con, int idInstrucao) {
        String updateSql = "UPDATE JavaInstructions SET processado = true WHERE idJavaInstructions = ?";
        try (PreparedStatement updateStmt = con.prepareStatement(updateSql)) {
            updateStmt.setInt(1, idInstrucao);
            int linhasAfetadas = updateStmt.executeUpdate();
            if (linhasAfetadas > 0) {
                System.out.println("Instrução ID " + idInstrucao + " marcada como processada.");
            }
        } catch (Exception ex) {
            System.out.println("Erro ao atualizar instrução: " + ex.getMessage());
        }
    }
}