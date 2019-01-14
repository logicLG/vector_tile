package edu.zju.gis;

import no.ecc.vectortile.VectorTileDecoder;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReadVector {
    public static Map<String, JSONArray> getXYZAndContentfromPbf(String path) {
        String[] fileNames = path.split(File.separator);
        String z = fileNames[fileNames.length - 3];
        String x = fileNames[fileNames.length - 2];
        String y = fileNames[fileNames.length - 1].split("\\.")[0];

//        for(String item : fileNames){
//            if(item.contains("EPSG")) {
//                z = item.split("_")[2].replaceAll("^(0+)", "");
//                break;
//            }
//        }
        File file = new File(path);
//        String fileName=file.getName();
//        String x=fileName.split("\\.")[0].split("_")[0].replaceAll("^(0+)", "");
//        String y=fileName.split("\\.")[0].split("_")[1].replaceAll("^(0+)", "");

        String key = z + "_" + x + "_" + y;
        InputStream is = null;
        JSONArray jsonArray = new JSONArray();
        try {
            is = new FileInputStream(file);
            byte[] encoded = toBytes(is);
            VectorTileDecoder d = new VectorTileDecoder();
            String layerName = null;
            if (!d.decode(encoded).getLayerNames().isEmpty()) {
                layerName = d.decode(encoded).getLayerNames().iterator().next();
            }
            List<VectorTileDecoder.Feature> features = d.decode(encoded, layerName).asList();
            for (VectorTileDecoder.Feature feature : features) {
                JSONObject jsonObject = new JSONObject(feature.getAttributes());
                jsonArray.put(jsonObject);
            }
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Map<String, JSONArray> map = new HashMap();
        map.put(key, jsonArray);
        return map;

    }


    private static byte[] toBytes(InputStream in) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buf = new byte[8192];
        int bytesRead = 0;
        while ((bytesRead = in.read(buf)) != -1) {
            baos.write(buf, 0, bytesRead);
        }
        return baos.toByteArray();
    }

    public static void traverseFolder2(List<String> listFiles, String path) {
        File file = new File(path);
        if (file.exists()) {
            File[] files = file.listFiles();
            if (files.length == 0) {
                System.out.println("文件夹是空的!");
                return;
            } else {
                for (File file2 : files) {
                    if (file2.isDirectory()) {
                        traverseFolder2(listFiles, file2.getAbsolutePath());
                    } else {
                        listFiles.add(file2.getAbsolutePath());
                    }
                }
            }
        } else {
            System.out.println("文件不存在!");
        }
    }

}
