package repository;

import java.sql.Connection;

public interface ConexaoRepository {
    Connection conectar();
}