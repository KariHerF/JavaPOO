package grupofp.dao;

import java.sql.SQLException;

public abstract class DAOFactory {

	// Lista de tipos de DAO disponibles en la factoria
	  public static final int MYSQL = 1;

	  // Habr� un m�todo para cada DAO que se pueda crear
	  // Las factor�as concretas contendr�n la implementaci�n de cada DAO.
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
