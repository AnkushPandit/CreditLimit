package com.vega.credit.server;

import java.sql.Connection;
import java.sql.DriverManager;

import static com.vega.credit.constants.DBConstants.DB_CONNECTION_URL;
import static com.vega.credit.constants.DBConstants.DB_NAME;
import static com.vega.credit.constants.DBConstants.DB_PASSWORD;
import static com.vega.credit.constants.DBConstants.DB_USERNAME;
import static com.vega.credit.constants.DBConstants.POSTGRESQL_DRIVER;

public class SQL {
    private Connection connection;
    public SQL() {
        try {
            Class.forName(POSTGRESQL_DRIVER);
            connection =
                    DriverManager.getConnection(DB_CONNECTION_URL + DB_NAME,
                            DB_USERNAME, DB_PASSWORD);
            System.out.println("Connection successful");
        } catch (Exception e) {
            System.out.println("Connection Failed");
            e.printStackTrace();
            throw new RuntimeException(String.format("Connection with %s db Failed", DB_NAME));
        }
    }

    public Connection getDBConnection() {
        return connection;
    }
}
