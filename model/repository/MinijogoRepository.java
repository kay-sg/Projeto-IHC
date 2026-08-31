package model.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import model.Minijogo;

public class MinijogoRepository {

    private final ConexaoRepository conexao;

    public MinijogoRepository(
            ConexaoRepository conexao) {

        this.conexao = conexao;
    }

    public Minijogo buscarDisponivel(
            int idUsuario) {

        String sql =
                "SELECT " +
                "mgs.idScore, " +
                "mgt.nome, " +
                "mgt.descricao, " +
                "mgs.pontuacao " +
                "FROM MiniGameScore mgs " +
                "INNER JOIN MiniGameType mgt " +
                "ON mgs.idGameType = mgt.idGameType " +
                "WHERE mgs.idUsuario = ? " +
                "AND mgs.idEquipment IS NULL " +
                "LIMIT 1";

        try (
                Connection con = conexao.conectar();
                PreparedStatement stmt =
                        con.prepareStatement(sql)
        ) {

            stmt.setInt(1, idUsuario);

            try (ResultSet rs =
                         stmt.executeQuery()) {

                if (rs.next()) {

                    return new Minijogo(
                            rs.getInt("idScore"),
                            rs.getString("nome"),
                            rs.getString("descricao"),
                            rs.getFloat("pontuacao")
                    );
                }
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return null;
    }
}