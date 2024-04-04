package com.unina.oobd2324gr22.entity.DAO;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Objects;

/** Singleton class to manage the connection to the DB. */
public final class DBConnection {

  /** Istance private and static of the class DBConnection. */
  private static DBConnection dbConn = null;

  /** Istance private and static of the connection to the DB. */
  private static Connection conn = null;

  /** Private constructor. */
  private DBConnection() {}

  /**
   * Public method to get the instance of the class DBConnection.
   *
   * @return instance of the class DBConnection
   */
  static DBConnection getDBConnection() {
    if (dbConn == null) {
      dbConn = new DBConnection();
    }
    return dbConn;
  }

  /**
   * Public method to get the connection to DB given the schema name.
   *
   * @param schemaName name of the schema to connect to
   * @return connection to the DB
   */
  static Connection getConnectionBySchema(final String schemaName) {
    String pwd = null;
    BufferedReader b = null;
    if (Objects.equals(schemaName, "")) {
      throw new RuntimeException("Schema name is empty");
    }
    try {
      if (conn == null || conn.isClosed()) {
        InputStream is = DBConnection.class.getResourceAsStream("/pwddb.txt");
        if (is == null) {
          throw new FileNotFoundException(
              "File contenente la password per la connessione al database non trovato");
        }
        b = new BufferedReader(new InputStreamReader(is));
        pwd = b.readLine();
        Class.forName("org.postgresql.Driver");
        String url =
            "jdbc:postgresql://localhost:5432/" + "uninadelivery?currentSchema=" + schemaName;
        conn = DriverManager.getConnection(url, "riccardoelena", pwd);
      }
    } catch (SQLException | ClassNotFoundException | IOException throwables) {
      String url =
          "jdbc:postgresql://localhost:5432/" + "uninadelivery?currentSchema=" + schemaName;
      try {
        conn = DriverManager.getConnection(url, "zgenny", "securepassword");
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }

    return conn;
  }
}