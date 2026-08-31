import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class Inventario {

    private Inventario() {}

   public static void salvarEquipamento(Equipamento equipamento, Connection con, int idUsuario) {
        String sql = "INSERT INTO Equipment (idUsuario, nome, qualidade, metodoUtilizado, tipo) VALUES ( ?, ?, ?, ?, ?)";

        try{
            PreparedStatement stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setInt(1,idUsuario);
            stmt.setString(2, equipamento.getNome());
            stmt.setFloat(3, equipamento.getQualidade());
            stmt.setBoolean(4, equipamento.isMetodoCrafting());
            stmt.setString(5, equipamento.getTipo());

            stmt.executeUpdate();
            
            // Captura o ID gerado pelo auto_increment
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int idGerado = generatedKeys.getInt(1);
                    
                    // Salva os atributos usando o ID recém-capturado do banco
                    salvarAtributos(con, equipamento, idGerado);
                } else {
                    throw new SQLException("Falha ao criar equipamento, nenhum ID obtido.");
                }
            }

        } catch (SQLException e) {
            System.err.println("Erro ao salvar equipamento no banco de dados: " + e.getMessage());
        }
    }

   private static void salvarAtributos(Connection conn, Equipamento equipamento, int idEquipment) throws SQLException {
        // Ajustado para o nome correto da coluna no banco: idEquipment
        String sqlAtributo = "INSERT INTO Attribute (idEquipment, nome, valor) VALUES (?, ?, ?)";
        
        
        PreparedStatement stmt = conn.prepareStatement(sqlAtributo);
        for (Atributo at : equipamento.getAtributos()) {
            stmt.setInt(1, idEquipment); // Utiliza o ID obtido do banco
            stmt.setString(2, at.getNome()); 
            stmt.setFloat(3, at.getValor());
            stmt.addBatch();
        }
        stmt.executeBatch();
    }
}
