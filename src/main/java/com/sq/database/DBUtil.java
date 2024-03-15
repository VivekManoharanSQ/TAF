package com.sq.database;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;


public class DBUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(DBUtil.class);

    public static ResultSet executeSelectQuery(Connection connection, String query) {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            LOGGER.debug("Query executed: {}", query);
            return resultSet;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static int getIntegerByColumnName(ResultSet resultSet, String countFieldName) {
        try {
            ensureGoToFirstRow(resultSet);
            return resultSet.getInt(countFieldName);
        } catch (SQLException e) {
            throw new DBException("Could not fetch integer", e);
        }
    }

    public static double getDoubleByColumnName(ResultSet resultSet, String countFieldName) {
        try {
            ensureGoToFirstRow(resultSet);
            return resultSet.getDouble(countFieldName);
        } catch (SQLException e) {
            throw new DBException("Could not fetch double", e);
        }
    }

    private static void ensureGoToFirstRow(ResultSet resultSet) throws SQLException {
        if (!resultSet.next()) {
            throw new DBException("No rows was fetched");
        }
    }

    public static Connection getConnection(DataSource dataSource) {
        try {
            Class.forName(dataSource.getDriver());
            Connection connection = DriverManager.getConnection(dataSource.getConnectionURL(),
                    dataSource.getUsername(), dataSource.getPassword());
            LOGGER.info("Connection Established Successfully for {}", dataSource);
            return connection;
        } catch (ClassNotFoundException | SQLException e) {
            throw new DBException("Could not establish the connection", e);
        }
    }
}
