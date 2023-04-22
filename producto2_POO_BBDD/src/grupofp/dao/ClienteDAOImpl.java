package grupofp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import grupofp.excepciones.ExcepcionesPersonalizadas.DAOException;

import grupofp.modelo.Cliente;
import grupofp.modelo.ClienteEstandar;
import grupofp.modelo.ClientePremium;

public class ClienteDAOImpl implements ClienteDAO {

	private Connection conn;

	public ClienteDAOImpl(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insertarCliente(Cliente cliente) throws DAOException {
		String sql = "INSERT INTO clientes (email, nombre, domicilio, nif) VALUES (?, ?, ?, ?, ?)";
		try (PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, cliente.getEmail());
			ps.setString(2, cliente.getNombre());
			ps.setString(3, cliente.getDomicilio());
			ps.setString(4, cliente.getNif());
			ps.setString(5, cliente.tipoCliente());
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new DAOException("Error al insertar el cliente en la base de datos: ", e);
		}
	}

	@Override
	public void actualizarCliente(Cliente cliente) throws DAOException {
		String sql = "UPDATE clientes SET nombre = ?, domicilio = ?, nif = ?, tipo_cliente = ? WHERE email = ?";
		try (PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, cliente.getNombre());
			ps.setString(2, cliente.getDomicilio());
			ps.setString(3, cliente.getNif());
			ps.setString(4, cliente.tipoCliente());
			ps.setString(5, cliente.getEmail());
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new DAOException("Error al actualizar el cliente en la base de datos: ", e);
		}
	}

	@Override
	public void eliminarCliente(String email) throws DAOException {
		String sql = "DELETE FROM clientes WHERE email = ?";
		try (PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, email);
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new DAOException("Error al eliminar el cliente de la base de datos: ", e);
		}
	}

	@Override
	public <T extends Cliente> obtenerCliente(String email) throws DAOException {
		String sql = "SELECT clientes.nombre,clientes.domicilio,clientes.nif, "
				+ "tipos_cliente.descripcion, tipos_cliente.cuota_anual,tipos_cliente.descuento_envio"
				+ "FROM clientes " + "INNER JOIN tipos_cliente "
				+ "ON clientes.tipo_cliente = tipos_cliente.id_tipo_cliente " 
				+ "WHERE clientes.email = ?";

		try (PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, email);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					String nombre = rs.getString("nombre");
					String domicilio = rs.getString("domicilio");
					String nif = rs.getString("nif");
					String tipo_cliente = rs.getString("descripcion");
					float cuota_anual = rs.getFloat("cuota_anual");
					float descuento_envio = rs.getFloat("descuento_envio");
					if (tipo_cliente.equals("Estandar")) {
						return new ClienteEstandar(email, nombre, domicilio, nif, cuota_anual, descuento_envio);
					} else if (tipo_cliente.equals("Premium")) {
						return new ClientePremium(email, nombre, domicilio, nif, cuota_anual, descuento_envio);
					}

				} else {
					return null;
				}
			}
		} catch (SQLException e) {
			throw new DAOException("Error al obtener el cliente de la base de datos: ", e);
		}
	}

	@Override
	public List<Cliente> obtenerTodosClientes() throws DAOException {
		String sql = "SELECT * FROM clientes";
		try (PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
			List<Cliente> clientes = new ArrayList<>();
			while (rs.next()) {
				String email = rs.getString("email");
				String nombre = rs.getString("nombre");
				String domicilio = rs.getString("domicilio");
				String nif = rs.getString("nif");
				Cliente cliente = new Cliente(email, nombre, domicilio, nif);
				clientes.add(cliente);
			}
			return clientes;
		} catch (SQLException e) {
			throw new DAOException("Error al obtener la lista de clientes de la base de datos: ", e);
		}
	}
}
