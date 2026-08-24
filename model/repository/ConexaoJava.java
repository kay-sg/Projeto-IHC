package repository;
import java.sql.Connection;
import java.sql.DriverManager;


public class ConexaoJava {

    private static final String HOST = System.getenv("DB_HOST");
    private static final String BANCO = System.getenv("DB_NAME");
    private static final String USUARIO = System.getenv("DB_USER");
    private static final String SENHA = System.getenv("DB_PASSWORD");
    private static final String URL = "jdbc:mysql://" + HOST + "/" + BANCO + "?useSSL=false&serverTimezone=UTC";

    private Connection con;
    private String driver = "com.mysql.cj.jdbc.Driver";

    public Connection conectar(){
        try {
            Class.forName(driver);
            con = DriverManager.getConnection(URL,USUARIO,SENHA);
            return con;
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            return null;
        }
    }
}
