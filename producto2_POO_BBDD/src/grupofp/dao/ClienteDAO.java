package grupofp.dao;

import java.sql.SQLException;
import java.util.List;

import grupofp.excepciones.ExcepcionesPersonalizadas.DAOException;
import grupofp.modelo.Cliente;

public interface ClienteDAO {
    void insertarCliente(Cliente cliente) throws DAOException;
    void actualizarCliente(Cliente cliente) throws DAOException;
    void eliminarCliente(String email) throws DAOException;
    <T extends Cliente> obtenerCliente(String email) throws DAOException;
    List<Cliente> obtenerTodosClientes() throws DAOException;

}
