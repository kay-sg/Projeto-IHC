package model.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import model.JavaInstruction;

public class JavaInstructionsRepository {

    private final ConexaoRepository conexao;

    public JavaInstructionsRepository(
            ConexaoRepository conexao) {

        this.conexao = conexao;
    }

    public List<JavaInstruction> buscarPendentes() {

        List<JavaInstruction> instrucoes =
                new ArrayList<>();

        String sql =
                "SELECT * FROM JavaInstructions " +
                "WHERE processado = false";

        try (
                Connection con = conexao.conectar();
                PreparedStatement stmt =
                        con.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()
        ) {

            while (rs.next()) {

                JavaInstruction instrucao =
                        new JavaInstruction(
                                rs.getInt(
                                    "idJavaInstructions"
                                ),
                                rs.getString("tipo"),
                                rs.getString("instruction"),
                                rs.getInt("idUsuario"),
                                rs.getInt("idTemplate")
                        );

                instrucoes.add(instrucao);
            }

        } catch (Exception e) {

            System.out.println(
                    "Erro ao buscar instruções:"
            );

            e.printStackTrace();
        }

        return instrucoes;
    }

    public void marcarComoProcessada(
            int idInstrucao) {

        String sql =
                "UPDATE JavaInstructions " +
                "SET processado = true " +
                "WHERE idJavaInstructions = ?";

        try (
                Connection con = conexao.conectar();
                PreparedStatement stmt =
                        con.prepareStatement(sql)
        ) {

            stmt.setInt(1, idInstrucao);

            int linhasAfetadas =
                    stmt.executeUpdate();

            if (linhasAfetadas > 0) {

                System.out.println(
                        "Instrução ID "
                        + idInstrucao
                        + " marcada como processada."
                );
            }

        } catch (Exception e) {

            System.out.println(
                    "Erro ao atualizar instrução:"
            );

            e.printStackTrace();
        }
    }
}