package grupofp.dao;

import java.sql.SQLException;
import java.util.List;

import grupofp.modelo.Articulo;

public interface ArticuloDAO {
	public void agregarArticulo(Articulo articulo) throws SQLException;
    public Articulo obtenerArticulo(String codigo) throws SQLException;
    public List<Articulo> obtenerTodosLosArticulos() throws SQLException;
    public void actualizarArticulo(Articulo articulo) throws SQLException;
    public void eliminarArticulo(Articulo articulo) throws SQLException;

}
