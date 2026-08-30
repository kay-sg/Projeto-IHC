import repository.ConexaoJava;
import java.sql.Connection;
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
            con.close();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

        try{
            while(ativo){
                ativo=false;
            }
        }
        catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

    }
}
