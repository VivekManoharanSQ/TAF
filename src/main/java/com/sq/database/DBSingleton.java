package com.sq.database;

import com.sq.constants.TafConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class DBSingleton {

    private static final Logger LOGGER = LoggerFactory.getLogger(DBSingleton.class);
    private static Connection connection = null;

    DBSingleton() {
    }

    // This method would only to be used for a default DB value

    /**
     * Return SQL query result in an array list of values
     *
     * @return SQl query result in an ArrayList
     * @Param query an string that represent the query statement to be executed
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public static List getValData(String query) throws SQLException {
        try (PreparedStatement statement = getDBSingleton().prepareStatement(query)) {
            LOGGER.info("The QUERY to be run is: " + query);
            ResultSet resultSet = statement.executeQuery(query);
            ArrayList<HashMap> list = resultSetToArrayList(resultSet);
            closeOpenConnections();
            return list;
        }
    }


    /**
     * Transform the ResultSet into an ArrayList
     *
     * @param rs represent a result set from an SQL statement execution
     * @return list represent the result set transformed into an array list
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    private static ArrayList resultSetToArrayList(ResultSet rs) {
        ResultSetMetaData md;
        ArrayList<HashMap> list = new ArrayList<>();
        try {
            md = rs.getMetaData();
            int columns = md.getColumnCount();
            while (rs.next()) {
                LOGGER.info("Reading row...");
                HashMap row = new HashMap(columns);
                for (int i = 1; i <= columns; ++i) {
                    row.put(md.getColumnName(i), rs.getObject(i));
                }
                list.add(row);
            }
        } catch (SQLException e) {
            throw new DBException("Exception : ", e);
        }

        return list;
    }

    /**
     * Close the open connection if were an existing one
     */
    private static void closeOpenConnections() {
        try {
            if (connection != null) {
                connection.close();
                LOGGER.info("Active Connection Closed");
            }
        } catch (Exception e) {
            throw new DBException("Exception : ", e);
        }
    }

    /**
     * Create a DB Connection reading the values from the properties AUT.properties
     * aut.conn.ulr
     * aut.conn.username
     * aut.conn.password
     *
     * @return connection object
     */
    private static Connection getDBSingleton() {
        try {
            if (connection == null || connection.isClosed()) {
                return getUnClosedConnection();
            } else {
                return connection;
            }
        } catch (Exception e) {
            throw new DBException("Exception: ", e);
        }
    }

    /**
     * Returns the existing Connection
     */
    private static Connection getUnClosedConnection() {
        try {
            Class.forName(TafConstants.get("db.conn.driver"));
            connection = DriverManager.getConnection(
                    TafConstants.get("db.conn.url"),
                    TafConstants.get("db.conn.username"),
                    TafConstants.get("db.conn.password"));
            LOGGER.info("Connection Established Successfully for {}@{}",
                    TafConstants.get("db.conn.username"),
                    TafConstants.get("db.conn.url"));
            return connection;
        } catch (ClassNotFoundException e) {
            LOGGER.info("Please include Classpath ; where your DB Driver is located");
            throw new DBException("Please include Classpath ; where your DB Driver is located", e);
        } catch (SQLException e) {
            LOGGER.info("Connection cannot Established. Please check configuration");
            throw new DBException("Connection cannot Established. Please check configuration", e);
        }
    }
}
