package model.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import model.Material;

public class MaterialRepository {

    private final ConexaoRepository conexao;

    public MaterialRepository(
            ConexaoRepository conexao) {

        this.conexao = conexao;
    }

    public List<Material> buscarPorTemplate(
            int idTemplate) {

        List<Material> materiais =
                new ArrayList<>();

        String sql =
                "SELECT " +
                "m.idMaterial, " +
                "m.nome, " +
                "m.durabilidade, " +
                "m.peso, " +
                "m.valorMagico " +
                "FROM Template t " +
                "INNER JOIN TemplateUsedMaterial tum " +
                "ON t.idTemplate = tum.idTemplate " +
                "INNER JOIN Material m " +
                "ON tum.idMaterial = m.idMaterial " +
                "WHERE t.idTemplate = ?";

        try (
                Connection con = conexao.conectar();
                PreparedStatement stmt =
                        con.prepareStatement(sql)
        ) {

            stmt.setInt(1, idTemplate);

            try (ResultSet rs =
                         stmt.executeQuery()) {

                while (rs.next()) {

                    materiais.add(
                            new Material(
                                    rs.getInt(
                                        "idMaterial"
                                    ),
                                    rs.getString(
                                        "nome"
                                    ),
                                    rs.getFloat(
                                        "durabilidade"
                                    ),
                                    rs.getFloat(
                                        "peso"
                                    ),
                                    rs.getFloat(
                                        "valorMagico"
                                    )
                            )
                    );
                }
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return materiais;
    }
}