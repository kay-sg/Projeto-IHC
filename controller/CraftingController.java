import repository.ConexaoJava;
import repository.ConexaoRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CraftingController {

    public static void main(String[] args) {
        ConexaoRepository conection = ConexaoJava.obterInstancia();
        Connection con;
        boolean ativo = true;

        try {
            con = conection.conectar();
            if(con==null){
                System.out.println("Erro de conexão");
            }
            else{
                System.out.println("conectado com sucesso");
            }
            String instructionSql = "SELECT * FROM JavaInstructions WHERE processado = false"; // Exemplo de filtro

            while (ativo) {
                try{
                    PreparedStatement pstmt = con.prepareStatement(instructionSql);
                    ResultSet rs = pstmt.executeQuery();
                    boolean encontrouRegistros = false;
                    
                    while (rs.next()) {
                        encontrouRegistros = true;
                        int idJavaInstruction = rs.getInt("idJavaInstructions"); // Substitua pelo nome real da coluna de ID
                        System.out.println("Nova instrução encontrada! ID: " + idJavaInstruction);
                        
                        // TODO: Adicione aqui a lógica para tratar a instrução
                        String tipo = rs.getString("tipo");
                        switch (tipo.toUpperCase()) {
                            case "CREATE":
                                Crafting sistemaCrafting = null;
                                String metodo = rs.getString("instruction");
                                int idUsuario = rs.getInt("idUsuario");
                                int idTemplate = rs.getInt("idTemplate");
                                List<Material> materiais = new ArrayList<>();
                                String materiaisSql = "SELECT \n" + //
                                                                        "    m.idMaterial,\n" + //
                                                                        "    m.nome,\n" + //
                                                                        "    m.durabilidade,\n" + //
                                                                        "    m.peso,\n" + //
                                                                        "    m.valorMagico\n" + //
                                                                        "FROM Template t\n" + //
                                                                        "INNER JOIN TemplateUsedMaterial tum ON t.idTemplate = tum.idTemplate\n" + //
                                                                        "INNER JOIN Material m ON tum.idMaterial = m.idMaterial\n" + //
                                                                        "WHERE t.idTemplate = ?;";  
                                PreparedStatement mStmt= con.prepareStatement(materiaisSql);
                                mStmt.setInt(1, idTemplate);
                                ResultSet mrs = mStmt.executeQuery();
                                while(mrs.next()){
                                    int idMaterial = mrs.getInt("idMaterial");
                                    String nomeMaterial = mrs.getString("nome");
                                    float durabilidade = mrs.getFloat("durabilidade");
                                    float peso = mrs.getFloat("peso");
                                    float valorMagico = mrs.getFloat("valorMagico");
                                    Material material = new Material(idMaterial,nomeMaterial,durabilidade,peso,valorMagico);
                                    materiais.add(material);
                                }
                                switch(metodo.toUpperCase()){
                                    case "AUTOMATICO":
                                        sistemaCrafting = CraftingFactory.criar("automatico", 1);
                                        break;

                                    case "MANUAL":
                                        String minijogoSql = "SELECT \n" + //
                                                                        "    mgs.idScore,\n" + //
                                                                        "    mgt.nome,\n" + //
                                                                        "    mgt.descricao,\n" + //
                                                                        "    mgs.pontuacao,\n" + //
                                                                        "FROM MiniGameScore mgs\n" + //
                                                                        "INNER JOIN MiniGameType mgt ON mgs.idGameType = mgt.idGameType\n" + //
                                                                        "WHERE mgs.idUsuario = ? AND mgs.idEquipment IS NULL\n" + //
                                                                        "LIMIT 1;"; 
                                        PreparedStatement miniStmt= con.prepareStatement(minijogoSql);
                                        miniStmt.setInt(1, idUsuario);
                                        ResultSet minirs = miniStmt.executeQuery();
                                        Minijogo mini; 
                                        if(minirs.next()){
                                            int idScore = minirs.getInt("idScore");
                                            String nomeMinijogo = minirs.getString("nome");
                                            String descricao = minirs.getString("descricao"); 
                                            float pontuacao = minirs.getFloat("pontuacao"); 
                                            mini = new Minijogo(idScore,nomeMinijogo,descricao,pontuacao); 
                                        }
                                        else{ 
                                            System.out.println("Minijogo não encontrado");
                                            return;
                                        }
                                        sistemaCrafting = CraftingFactory.criar("manual", 1, mini);
                                        break; 
                                }
                                String templateSql = "SELECT \n" + //
                                                                "    t.idTemplateType,\n" + //
                                                                "    t.automatizavel\n" + //
                                                                "FROM Template t\n" + //
                                                                "WHERE t.idTemplate = ?;";
                                PreparedStatement tempStmt= con.prepareStatement(templateSql);
                                tempStmt.setInt(1, idTemplate);
                                ResultSet temprs = tempStmt.executeQuery();
                                TemplateEquipamento templateExemplo;
                                if(temprs.next()){
                                    int idType = temprs.getInt("idTemplateType");
                                    boolean automatizavel = temprs.getBoolean("automatizavel");
                                    templateExemplo = new TemplateEquipamento(idTemplate,idType,automatizavel);
                                }
                                else {
                                     System.out.println("Template não encontrado");
                                     return;
                                }
                                if(sistemaCrafting!=null){
                                    Equipamento equipamentoCriado = sistemaCrafting.executarCraft(templateExemplo, materiais);
                                    Inventario.salvarEquipamento(equipamentoCriado, con, idUsuario);
                                     // --- LÓGICA DO UPDATE AQUI ---
                                     String updateSql = "UPDATE JavaInstructions SET processado = true WHERE idJavaInstructions = ?";
                                    try (PreparedStatement updateStmt = con.prepareStatement(updateSql)) {
                                        updateStmt.setInt(1, idJavaInstruction);
                                       int linhasAfetadas = updateStmt.executeUpdate();
                                       if (linhasAfetadas > 0) {
                                           System.out.println("Instrução ID " + idJavaInstruction + " marcada como processada.");
                                           System.out.println("Equipamento criado com sucesso");
                                       }
                                    } catch (Exception ex) {
                                        System.out.println("Erro ao atualizar instrução: " + ex.getMessage());
                                    }
                                // -----------------------------
                                }
                                break;
                            case "END":
                                // --- LÓGICA DO UPDATE AQUI ---
                                String updateSql = "UPDATE JavaInstructions SET processado = true WHERE idJavaInstructions = ?";
                                try (PreparedStatement updateStmt = con.prepareStatement(updateSql)) {
                                    updateStmt.setInt(1, idJavaInstruction);
                                    int linhasAfetadas = updateStmt.executeUpdate();
                                    if (linhasAfetadas > 0) {
                                        System.out.println("Instrução ID " + idJavaInstruction + " marcada como processada.");
                                    }
                                } catch (Exception ex) {
                                    System.out.println("Erro ao atualizar instrução: " + ex.getMessage());
                                }
                                // -----------------------------
                                ativo = false;
                                break;
                            default:
                                break;
                        }

                    }
                    if (!encontrouRegistros){
                        System.out.println("Nenhum registro encontrado");
                    }

                } catch (Exception e) {
                    System.out.println("Erro durante a consulta na tabela:");
                    e.printStackTrace();
                }

                // Pausa de 3 segundos (3000 ms) antes da próxima verificação
                Thread.sleep(3000);
                
            }
            con.close();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
