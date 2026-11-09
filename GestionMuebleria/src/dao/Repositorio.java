package dao;
import java.sql.SQLException;
import java.util.ArrayList;
/** Contrato de consulta reutilizable para repositorios JDBC. */
public interface Repositorio<T> {
    ArrayList<T> listar() throws SQLException;
    T buscar(int id) throws SQLException;
}
