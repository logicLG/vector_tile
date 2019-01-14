package edu.zju.gis;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class Pbf2Oracle {
    public static void insert2pbfxy(Map<String, List<String>> pbfAndFeature, Connection conn) {
        try{
            String sql = "insert into TILE_MAP_2019_2(ID,content) values(?,?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            conn.setAutoCommit(false);
            pbfAndFeature.forEach((key, value) -> {
                try {
                    stmt.setString(1, key);
                    StringBuilder sb = new StringBuilder();
                    for (String tmp : value) {
                        if (sb.length() < 3900)
                            sb.append(tmp + ",");
                        else {
                            try {
                                stmt.setString(2, sb.substring(0, sb.lastIndexOf(",")));
                                stmt.addBatch();
                                stmt.executeBatch();
                                sb = new StringBuilder();
                                System.out.println("截断数据");
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    if (sb.length() > 0) {
                        stmt.setString(2, sb.substring(0, sb.lastIndexOf(",")));
                        stmt.addBatch();
                        stmt.executeBatch();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            conn.commit();
        }catch(Exception e){
            // 处理 Class.forName 错误
            e.printStackTrace();
        }finally{
            // 关闭资源
        }
    }

//    public static void main(String[] args) {
//        try{
//
//            File file=new File("/Users/LG/Documents/project/shandong/lukuang/input.csv");
//            BufferedReader reader =new BufferedReader(new FileReader(file));
//            String tempString=null;
//            Map<Integer,Integer> result=new HashMap<>();
//            while ((tempString = reader.readLine()) != null) {
//                // 显示行号
//                result.put(Integer.valueOf(tempString.split(",")[0]), Integer.valueOf(tempString.split(",")[1]));
//            }
//
//            String sql="insert into features(UUID,LOS) values(?,?)";
//            PreparedStatement stmt = conn.prepareStatement(sql);
//            conn.setAutoCommit(false);
//            int Allcount=result.size();
//            AtomicInteger count=new AtomicInteger(0);
//            result.entrySet().forEach(x -> {
//                        try {
//                            if(count.incrementAndGet()%10000==0){
//                                stmt.executeBatch();
//                                conn.commit();
//                                stmt.clearBatch();
//                                System.out.println("成功导入10000条");
//                            }
//                            stmt.setInt(1, x.getKey());
//                            stmt.setInt(2, x.getValue());
//                            stmt.addBatch();
//                            if(count.get()==Allcount){
//                                stmt.executeBatch();
//                                conn.commit();
//                                System.out.println("导入完成");
//                            }
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//            );
//
//        }catch(Exception e){
//            // 处理 Class.forName 错误
//            e.printStackTrace();
//        }finally{
//            // 关闭资源
//            pool.releaseConnection(conn);
//        }
//    }

}
