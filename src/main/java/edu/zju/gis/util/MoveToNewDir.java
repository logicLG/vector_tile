package edu.zju.gis.util;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

public class MoveToNewDir {
    public static void moveVectorTile(String sourcePath,String targetPath) throws IOException {
        Stream<Path> pathStream=Files.walk(Paths.get(URI.create(sourcePath)), FileVisitOption.FOLLOW_LINKS).filter(x->x.toString().contains(".pbf"));
        AtomicInteger count=new AtomicInteger(0);
        pathStream.forEach(x->{
            if(count.incrementAndGet()%1000==0){
                System.out.println("成功完成1000条");
            }
            String[] fileNames=x.toString().split("/");
            String tileZ=null;
            for(String item : fileNames){
                if(item.contains("EPSG")) {
                    tileZ = String.valueOf(Integer.valueOf(item.split("_")[2].replaceAll("^(0+)", "")) + 1);
                    break;
                }
            }
            String tileX=x.getFileName().toString().split("\\.")[0].split("_")[0].replaceAll("^(0+)","");
            String tileY = x.getFileName().toString().split("\\.")[0].split("_")[1].replaceAll("^(0+)", "");
            String tmpPath=targetPath;
            if(!tmpPath.endsWith("/")){
                tmpPath+="/";
            }
            String resultPath=tmpPath+tileZ+"/"+tileX;
            mkXYdir(resultPath);
            Path filePath=Paths.get(URI.create(resultPath+"/"+tileY+".pbf"));
            if(Files.notExists(filePath)){
                try {
                    Files.move(x,filePath);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else {
                System.out.println(filePath.toString()+"已存在");
            }
        });
    }
    public static void mkXYdir(String newPath){
        if(Files.notExists(Paths.get(URI.create(newPath)))){
            File file=Paths.get(URI.create(newPath)).toFile();
            file.mkdirs();
        }
    }
}
