package persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ConnectionPool {
    private static  ConnectionPool instance;
    private final List<Connection> connections = new ArrayList<>();
    private final static int POOL_SIZE = 5;
    private final static String URL = "jdbc:postgresql://localhost:5432/grade_tracker";
    private final static String USER = "postgres";
    private final static String PASSWORD = "postgres";

    private ConnectionPool() throws SQLException {
        for (int i = 0; i < POOL_SIZE; i++) {
            connections.add( DriverManager.getConnection(URL, USER, PASSWORD));
        }
    }
    public static ConnectionPool getInstance() throws SQLException {
        synchronized (ConnectionPool.class) {
            if (instance == null) {
                instance = new ConnectionPool();
            }
            return instance;
        }
    }
    public Connection getConnection() throws SQLException {
        if (connections.isEmpty()) {
            throw new RuntimeException("No connection available");
        }
        return connections.remove(connections.size() -1);
    }
    public synchronized void releaseConnection(Connection connection) {
        connections.add(connection);
    }

}
