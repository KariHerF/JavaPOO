package grupofp.dao;

import java.sql.SQLException;

public abstract class DAOFactory {

	// Lista de tipos de DAO disponibles en la factoria
	  public static final int MYSQL = 1;

	  // Habrá un método para cada DAO que se pueda crear
	  // Las factorías concretas contendrán la implementación de cada DAO.
	  public abstract ArticuloDAO obtenerArticuloDAO() throws SQLException;

	  public static DAOFactory getDAOFactory(
	      int whichFactory) {
	  
	    switch (whichFactory) {
	      case MYSQL: 
	          return new MySQLDAOFactory();
	      default           : 
	          return null;
	    }
	  }

}
