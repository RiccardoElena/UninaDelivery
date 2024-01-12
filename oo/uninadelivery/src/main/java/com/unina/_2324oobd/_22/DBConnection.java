package com.unina._2324oobd._22;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Objects;

public final class DBConnection {
    /**
     * Default constructor.
     */
    private static DBConnection dbConn = null;
    /**
     * Default constructor.
     */
    private static Connection conn = null;

    /**
     * Default constructor.
     */
    private DBConnection() { }

    /**
     * Default constructor.
     * @return ciao
     */
    public static DBConnection getDBConnection() {
        // se la classe connessione è nulla, la crea
        if (dbConn == null) {
            dbConn = new DBConnection();
        }
        // e la restituisce
        return dbConn;
    }

    // metodo pubblico per ottenere la connessione,
    // in questo caso passo anche il nome dello schema a cui connettermi
    /**
     * Default constructor.
     * @param schemaName ciao
     * @return ciao
     */
    public static Connection getConnectionBySchema(final String schemaName) {
        String pwd = null;
        BufferedReader b = null;
        if (Objects.equals(schemaName, "")) {
            throw new RuntimeException("Schema name is empty");
        }
        try {
        // se la connessione non esiste oppure è stata chiusa
            if (conn == null || conn.isClosed()) {
                //legge la pwd dal file
                b = new BufferedReader(new FileReader(new File(
                    "../../../../../resources/pwdfile.txt")));
                pwd = b.readLine();
                // registra il driver
                Class.forName("org.postgresql.Driver");
                // chiama il DriverManager e chiedi la connessione
                String sUrl = "jdbc:postgresql://localhost:5432/"
                            + "uninadelivery?currentSchema="
                            + schemaName;
                System.out.println("surl" + sUrl);
                conn = DriverManager.getConnection(sUrl, "riccardoelena", pwd);

            }
        } catch
        (SQLException | ClassNotFoundException | IOException throwables) {
            throwables.printStackTrace();
        }

        return conn;
    }

}
