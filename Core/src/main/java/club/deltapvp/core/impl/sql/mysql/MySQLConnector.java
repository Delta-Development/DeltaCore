/*
 *     DeltaCore is a Minecraft Java API Provider.
 *     Copyright (C) 2021 DeltaDevelopment
 *
 *     This program is free software; you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation; either version 2 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 */

package club.deltapvp.core.impl.sql.mysql;

import club.deltapvp.deltacore.api.utilities.sql.SQLConnector;
import lombok.Getter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * MySQL Connector
 *
 * @author Negative
 */
public class MySQLConnector implements SQLConnector {

    private final String host;
    private final String port;
    private final String database;
    private final String username;
    private final String password;

    @Getter
    private Connection connection;

    /**
     * MySQL Connector Constructor
     *
     * @param host     Host
     * @param port     Port
     * @param database Database Name
     * @param username Username
     * @param password Password
     */
    public MySQLConnector(String host, String port, String database, String username, String password) {
        this.host = host;
        this.port = port;
        this.database = database;
        this.username = username;
        this.password = password;
    }

    /**
     * Returns connection state
     *
     * @return if you are connected to the given MySQL Database
     */
    public boolean isConnected() {
        return (connection != null);
    }

    /**
     * Connect Function - Attempts to connect to the given MySQL Database
     */
    public void connect() throws ClassNotFoundException, SQLException {
        if (isConnected()) return;

        connection = DriverManager.getConnection("jdbc:mysql://" +
                        host + ":" + port + "/" + database + "?useSSL=false",
                username, password);
    }

    /**
     * Attempts to disconnect from the given MySQL Database
     */
    public void disconnect() {
        if (!isConnected()) return;

        try {
            connection.close();
        } catch (SQLException e) {
            System.out.println("Failed to disconnect from database");
            e.printStackTrace();
        }
    }
}
