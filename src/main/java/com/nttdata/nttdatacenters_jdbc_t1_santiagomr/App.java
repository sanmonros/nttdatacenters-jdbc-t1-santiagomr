package com.nttdata.nttdatacenters_jdbc_t1_santiagomr;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Formacion- Primer Taller - jdbc
 * 
 * @author santiagomr
 *
 */
public class App {

	/**
	 * Método principal
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		// Consuta a la BBDD nttdata_mysql_soccer_player (Información adicional)
		runQueryInfoPlayers();

	}

	/**
	 * Metodo de conexión a la BBDD - nttdata_jdbc_ex - mostrar informacion por
	 * tablas
	 */
	private static Connection getConectionInfoTables() {

		// Inicialización de variables
		String url = "jdbc:mysql://localhost/nttdata_jdbc_ex";
		url += "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
		String user = "root";
		String password = "root";
		Connection dbConection = null;

		try {
			dbConection = DriverManager.getConnection(url, user, password);

			DatabaseMetaData dbmd = dbConection.getMetaData();
			ResultSet resul = null;
			String[] types = { "TABLE" };

			// Obtener información de las tablas

			resul = dbmd.getTables("nttdata_jdbc_ex", null, null, types);
			while (resul.next()) {
				// CATALOG
				String catalog = resul.getString("TABLE_CAT");
				// SCHEMA
				String schema = resul.getString("TABLE_SCHEM");
				// NAME
				String table = resul.getString("TABLE_NAME");
				// TYPE
				String type = resul.getString("TABLE_TYPE");
				System.out.println("Catalogo: " + catalog + " Esquema: " + schema + " Tabla: " + table
						+ " Tipo de estructura: " + type);

			}
			// Cierre
			resul.close();
		} catch (SQLException ex) {
			System.out.println("Algun error se ha producido");
		} finally {
			System.out.println("Final");
		}

		// Lanzamiento de excepción NullPointer cuando la BBDD sea nula
		if (dbConection == null) {
			throw new NullPointerException("Datos incorrectos");
		}

		return dbConection;

	}

	/**
	 * Metodo ejecución consulta jugadores
	 */

	private static void runQueryInfoPlayers() {

		Connection dbConection = getConectionInfoTables();

		try (java.sql.Statement sentencia = dbConection.createStatement()) {

			// Preparacion de la consulta

			ResultSet resul = sentencia
					.executeQuery("SELECT id_soccer_player,name,birth_date from nttdata_mysql_soccer_player");

			// Recuperacion de resultados
			while (resul.next()) {
				System.out.println(resul.getInt(1) + " " + resul.getString(2) + " " + resul.getDate(3));
			}

			System.out.println("Todo OK");

			// Cierre
			resul.close();

		} catch (SQLException ex) {
			System.out.println("Algun error se ha producido");

		} finally {
			System.out.println("Final");
		}
	}
}
