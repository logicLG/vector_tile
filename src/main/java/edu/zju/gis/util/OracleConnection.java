package edu.zju.gis.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class OracleConnection {
    private Connection conn;

    private OracleConnection(String url, String user, String password) throws SQLException {
        conn = DriverManager.getConnection(url, user, password);
    }

    public static OracleConnection getInstance() {
        return Holder.conn;
    }

    public Connection getConn() {
        return conn;
    }

    private static class Holder {
        static OracleConnection conn = null;

        static {
            try (InputStream is = ClassLoader.getSystemResourceAsStream("db.properties")) {
                Properties props = new Properties();
                props.load(is);
                conn = new OracleConnection(props.getProperty("url"), props.getProperty("user"), props.getProperty("password"));
            } catch (IOException | SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
