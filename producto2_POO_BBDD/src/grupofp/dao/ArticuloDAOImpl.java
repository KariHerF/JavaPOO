package grupofp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import grupofp.modelo.Articulo;

public class ArticuloDAOImpl implements ArticuloDAO {

	private Connection conexion;

    public ArticuloDAOImpl(Connection conexion) {
        this.conexion = conexion;
    }

    @Override
    public void agregarArticulo(Articulo articulo) throws SQLException {
        String sql = "INSERT INTO articulos (codigo, descripcion, pvp, tiempo_prep, gastos_envio) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement statement = conexion.prepareStatement(sql)) {
            statement.setString(1, articulo.getCodigo());
            statement.setString(2, articulo.getDescripcion());
            statement.setFloat(3, articulo.getPvp());
            statement.setLong(4, articulo.getTiempoPrep().getSeconds());
            statement.setFloat(5, articulo.getGastosEnvio());
            statement.executeUpdate();
        }
    }

    @Override
    public Articulo obtenerArticulo(String codigo) throws SQLException {
        Articulo articulo = null;
        String sql = "SELECT codigo, descripcion, pvp, tiempo_prep, gastos_envio FROM articulos WHERE codigo=?";
        try (PreparedStatement statement = conexion.prepareStatement(sql)) {
            statement.setString(1, codigo);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    articulo = new Articulo(resultSet.getString("codigo"), resultSet.getString("descripcion"),
                            resultSet.getFloat("pvp"), Duration.ofSeconds(resultSet.getLong("tiempo_prep")),
                            resultSet.getFloat("gastos_envio"));
                }
            }
        }
        return articulo;
    }

    @Override
    public List<Articulo> obtenerTodosLosArticulos() throws SQLException {
        List<Articulo> listaArticulos = new ArrayList<>();
        String sql = "SELECT codigo, descripcion, pvp, tiempo_prep, gastos_envio FROM articulos";
        try (PreparedStatement statement = conexion.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Articulo articulo = new Articulo(resultSet.getString("codigo"), resultSet.getString("descripcion"),
                        resultSet.getFloat("pvp"), Duration.ofSeconds(resultSet.getLong("tiempo_prep")),
                        resultSet.getFloat("gastos_envio"));
                listaArticulos.add(articulo);
            }
        }
        return listaArticulos;
    }

    @Override
    public void actualizarArticulo(Articulo articulo) throws SQLException {
        String sql = "UPDATE articulos SET descripcion=?, pvp=?, tiempo_prep=?, gastos_envio=? WHERE codigo=?";
        try (PreparedStatement statement = conexion.prepareStatement(sql)) {
            statement.setString(1, articulo.getDescripcion());
            statement.setFloat(2, articulo.getPvp());
            statement.setLong(3, articulo.getTiempoPrep().getSeconds());
            statement.setFloat(4, articulo.getGastosEnvio());
            statement.setString(5, articulo.getCodigo());
            statement.executeUpdate();
        }
    }
    
    @Override
    public void eliminarArticulo(Articulo articulo)throws SQLException {
    	
    	String sql_delete = "DELETE FROM articulos WHERE codigo = ?";
    	PreparedStatement stmt = conexion.prepareStatement(sql_delete);
        
        try {

            stmt.setString(1, articulo.getCodigo());
            
            int filasAfectadas = stmt.executeUpdate();
            
            if (filasAfectadas == 0) {
                throw new SQLException("No se ha encontrado ningún artículo con el código " + articulo.getCodigo());
            }
        } finally {
            if (stmt != null) {
                stmt.close();
            }
            if (conexion != null) {
            	conexion.close();
            }
        }
    }
}
