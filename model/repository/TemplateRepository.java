package model.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import model.TemplateEquipamento;

public class TemplateRepository {

    private final ConexaoRepository conexao;

    public TemplateRepository(
            ConexaoRepository conexao) {

        this.conexao = conexao;
    }

    public TemplateEquipamento buscarPorId(
            int idTemplate) {

        String sql =
                "SELECT " +
                "idTemplateType, " +
                "automatizavel," +
                "nome " +
                "FROM Template " +
                "WHERE idTemplate = ?";

        try (
                Connection con = conexao.conectar();
                PreparedStatement stmt =
                        con.prepareStatement(sql)
        ) {

            stmt.setInt(1, idTemplate);

            try (ResultSet rs =
                         stmt.executeQuery()) {

                if (rs.next()) {

                    return new TemplateEquipamento(
                            idTemplate,
                            rs.getInt(
                                "idTemplateType"
                            ),
                            rs.getBoolean(
                                "automatizavel"
                            ),
                            rs.getString("nome")
                    );
                }
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return null;
    }
}