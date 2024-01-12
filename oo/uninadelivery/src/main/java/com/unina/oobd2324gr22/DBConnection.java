package com.unina.oobd2324gr22;

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
     * Istance private and static of the class DBConnection.
     */
    private static DBConnection dbConn = null;
    /**
     * Istance private and static of the connection to the DB.
     */
    private static Connection conn = null;

    /**
     * Private constructor.
     */
    private DBConnection() { }

    /**
     * Public method to get the instance of the class DBConnection.
     * @return instance of the class DBConnection
     */
    public static DBConnection getDBConnection() {
        // if the connection doesn't exist or is closed
        if (dbConn == null) {
            dbConn = new DBConnection();
        }
        return dbConn;
    }

    /**
     * Public method to get the connection to DB given the schema name.
     * @param schemaName name of the schema to connect to
     * @return connection to the DB
     */
    public static Connection getConnectionBySchema(final String schemaName) {
        String pwd = null;
        BufferedReader b = null;
        if (Objects.equals(schemaName, "")) {
            throw new RuntimeException("Schema name is empty");
        }
        try {
            if (conn == null || conn.isClosed()) {
                b = new BufferedReader(new FileReader(new File(
                    "../../../../../resources/pwdfile.txt")));
                pwd = b.readLine();
                Class.forName("org.postgresql.Driver");
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
