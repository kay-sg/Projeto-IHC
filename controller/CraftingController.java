import repository.ConexaoJava;
import repository.ConexaoRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

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
                                int idUsuario = rs.getInt("idUsuario");
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
