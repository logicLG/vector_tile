package edu.zju.gis;

import edu.zju.gis.util.OracleConnection;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class Consumer {
    public static void process(String path) throws IOException {
        Map<String, List<String>> pbfAndFeature = new HashMap<>();

        Stream<Path> streamPath = Files.walk(Paths.get(path), FileVisitOption.values()).filter(
                x -> x.toString().contains(".pbf"));

        OracleConnection oracleConnection = OracleConnection.getInstance();
        streamPath.map(filePath ->
                ReadVector.getXYZAndContentfromPbf(filePath.toString())
        ).forEach(x -> {
            if (pbfAndFeature.size() == 500) {
                Pbf2Oracle.insert2pbfxy(pbfAndFeature, oracleConnection.getConn());
                pbfAndFeature.clear();
                System.out.println("成功导入数据库500条");
            }
            String key = x.keySet().iterator().next();
            JSONArray value = x.get(key);
            if (!value.isEmpty()) {
                List<String> list = new ArrayList<>();
                value.forEach(item -> {
                    JSONObject jsonObject = new JSONObject(item.toString());
                    String uuid = (String) jsonObject.get("UUID");
                    list.add(uuid);
                });
                pbfAndFeature.put(key, list);
            }
        });
        if (!pbfAndFeature.isEmpty()) {
            Pbf2Oracle.insert2pbfxy(pbfAndFeature, oracleConnection.getConn());
            System.out.println("成功导入数据库" + pbfAndFeature.size());
        }
    }
}
