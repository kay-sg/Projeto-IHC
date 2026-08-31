package model.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import model.Atributo;
import model.Equipamento;

public class InventarioRepository {

    private final ConexaoRepository conexao;

    public InventarioRepository(
            ConexaoRepository conexao) {

        this.conexao = conexao;
    }

    public void salvarEquipamento(
            Equipamento equipamento,
            int idUsuario) {

        String sqlEquipment =
                "INSERT INTO Equipment (" +
                "idUsuario, " +
                "nome, " +
                "qualidade, " +
                "metodoUtilizado, " +
                "tipo) " +
                "VALUES (?, ?, ?, ?, ?)";

        String sqlAttribute =
                "INSERT INTO Attribute (" +
                "idEquipment, " +
                "nome, " +
                "valor) " +
                "VALUES (?, ?, ?)";

        try (
                Connection con = conexao.conectar();
                PreparedStatement stmtEquipment =
                        con.prepareStatement(
                                sqlEquipment,
                                Statement.RETURN_GENERATED_KEYS
                        )
        ) {
            // Desliga o auto-commit para gerenciar a transação manualmente (Equipamento + Atributos)
            con.setAutoCommit(false);

            stmtEquipment.setInt(1, idUsuario);
            stmtEquipment.setString(2, equipamento.getNome());
            stmtEquipment.setFloat(3, equipamento.getQualidade());
            stmtEquipment.setBoolean(4, equipamento.isMetodoCrafting());
            stmtEquipment.setString(5, equipamento.getTipo());

            stmtEquipment.executeUpdate();

            try (ResultSet generatedKeys =
                         stmtEquipment.getGeneratedKeys()) {

                if (generatedKeys.next()) {
                    int idEquipment = generatedKeys.getInt(1);

                    try (PreparedStatement stmtAttribute =
                                 con.prepareStatement(sqlAttribute)) {

                        for (Atributo at : equipamento.getAtributos()) {
                            stmtAttribute.setInt(1, idEquipment);
                            stmtAttribute.setString(2, at.getNome());
                            stmtAttribute.setFloat(3, at.getValor());
                            stmtAttribute.addBatch();
                        }

                        stmtAttribute.executeBatch();
                    }

                    // Se tudo ocorreu bem, efetiva a transação
                    con.commit();

                } else {
                    con.rollback();
                    throw new SQLException(
                            "Falha ao criar equipamento, nenhum ID obtido."
                    );
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}