import repository.ConexaoJava;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CraftingController {

    public static void main(String[] args) {
        ConexaoJava conection = new ConexaoJava();
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
            String sql = "SELECT * FROM JavaInstructions WHERE processado = false"; // Exemplo de filtro

            while (ativo) {
                try{
                    PreparedStatement pstmt = con.prepareStatement(sql);
                    ResultSet rs = pstmt.executeQuery();
                    boolean encontrouRegistros = false;
                    
                    while (rs.next()) {
                        encontrouRegistros = true;
                        int id = rs.getInt("id"); // Substitua pelo nome real da coluna de ID
                        System.out.println("Nova instrução encontrada! ID: " + id);
                        
                        // TODO: Adicione aqui a lógica para tratar a instrução
                    }

                } catch (Exception e) {
                    System.out.println("Erro durante a consulta na tabela:");
                    e.printStackTrace();
                }

                // Pausa de 3 segundos (3000 ms) antes da próxima verificação
                Thread.sleep(3000);
                
                ativo=false;
            }
            con.close();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
