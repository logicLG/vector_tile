package util;

import org.junit.Test;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class JdbcPoolTest {

    /**
     * @Field: pool
     *          数据库连接池
     */
    private static JdbcPool pool = new JdbcPool();


    @Test
    public  void getConnection() throws SQLException{
        Connection connection=pool.getConnection();
    }


}