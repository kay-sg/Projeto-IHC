package model.repository;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConexaoJava implements ConexaoRepository {

    private static final String HOST = System.getenv("JAVADB_HOST");
    private static final String BANCO = System.getenv("JAVADB_NAME");
    private static final String USUARIO = System.getenv("JAVADB_USER");
    private static final String SENHA = System.getenv("JAVADB_PASSWORD");
    private static final String URL = "jdbc:mysql://" + HOST + "/" + BANCO + "?useSSL=false&serverTimezone=UTC";

    private static ConexaoJava instancia;
    private Connection con;
    private String driver = "com.mysql.cj.jdbc.Driver";

    private ConexaoJava() {
    }

    public static ConexaoJava obterInstancia() {
        if (instancia == null) {
            instancia = new ConexaoJava();
        }
        return instancia;
    }

    @Override
    public Connection conectar() {
        try {
            if (con == null || con.isClosed()) {
                Class.forName(driver);
                con = DriverManager.getConnection(URL, USUARIO, SENHA);
            }
            return con;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}