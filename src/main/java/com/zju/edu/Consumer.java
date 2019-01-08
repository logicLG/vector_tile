package com.zju.edu;

import com.sun.prism.shader.Solid_TextureYV12_AlphaTest_Loader;
import org.json.JSONArray;
import org.json.JSONObject;
import util.OracleConnection;


import java.io.IOException;
import java.net.URI;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

public class Consumer {
    public static void process(String path) throws IOException {
        Map<String, List<String>> pbfAndFeature = new HashMap<>();

        Stream<Path> streamPath=Files.walk(Paths.get(URI.create(path)), FileVisitOption.values()).filter(
                    x->x.toString().contains(".pbf"));

        AtomicInteger counter = new AtomicInteger(0);
        OracleConnection oracleConnection = OracleConnection.getInstance();
        streamPath.map(filePath ->
                ReadVector.getXYZAndContentfromPbf(filePath.toString())
        ).forEach(x -> {
            if(pbfAndFeature.size()==500){
                Pbf2Oracle.insert2pbfxy(pbfAndFeature, oracleConnection.getConn());
                pbfAndFeature.clear();
                System.out.println("成功导入数据库500条");
            }
            String key=x.keySet().iterator().next();
            JSONArray value=x.get(key);
            if(!value.isEmpty()) {
                List<String> list = new ArrayList<>();
                value.forEach(item -> {
                    JSONObject jsonObject = new JSONObject(item.toString());
                    String uuid = (String) jsonObject.get("UUID");
                    list.add(uuid);
                });
                pbfAndFeature.put(key, list);
            }
            });
        if(!pbfAndFeature.isEmpty()){
            Pbf2Oracle.insert2pbfxy(pbfAndFeature, oracleConnection.getConn());
            System.out.println("成功导入数据库"+pbfAndFeature.size());
        }
    }
}
