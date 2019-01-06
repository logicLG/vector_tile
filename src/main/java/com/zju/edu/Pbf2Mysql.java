package com.zju.edu;

import util.JdbcPool;

import java.io.*;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class Pbf2Mysql {

    static Connection conn = null;
    private static JdbcPool pool=new JdbcPool();


    public static void insert2pbfxy(Map<String,String> pbfAndFeature) {
        try{
            conn=pool.getConnection();
            String sql="insert into pbfxyz(gridID,content) values(?,?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            conn.setAutoCommit(false);
            pbfAndFeature.entrySet().forEach(x -> {
                    try {
                        stmt.setString(1, x.getKey());
                        stmt.setString(2, x.getValue());
                        stmt.addBatch();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            );
            stmt.executeBatch();
            conn.commit();
        }catch(Exception e){
            // 处理 Class.forName 错误
            e.printStackTrace();
        }finally{
            // 关闭资源
            pool.releaseConnection(conn);
        }
    }

    public static void main(String[] args) {
        try{

            File file=new File("/Users/LG/Documents/project/shandong/lukuang/input.csv");
            BufferedReader reader =new BufferedReader(new FileReader(file));
            String tempString=null;
            Map<Integer,Integer> result=new HashMap<>();
            while ((tempString = reader.readLine()) != null) {
                // 显示行号
                result.put(Integer.valueOf(tempString.split(",")[0]), Integer.valueOf(tempString.split(",")[1]));
            }
            conn=pool.getConnection();
            String sql="insert into features(UUID,LOS) values(?,?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            conn.setAutoCommit(false);
            int Allcount=result.size();
            AtomicInteger count=new AtomicInteger(0);
            result.entrySet().forEach(x -> {
                        try {
                            if(count.incrementAndGet()%10000==0){
                                stmt.executeBatch();
                                conn.commit();
                                stmt.clearBatch();
                                System.out.println("成功导入10000条");
                            }
                            stmt.setInt(1, x.getKey());
                            stmt.setInt(2, x.getValue());
                            stmt.addBatch();
                            if(count.get()==Allcount){
                                stmt.executeBatch();
                                conn.commit();
                                System.out.println("导入完成");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
            );

        }catch(Exception e){
            // 处理 Class.forName 错误
            e.printStackTrace();
        }finally{
            // 关闭资源
            pool.releaseConnection(conn);
        }
    }

}
