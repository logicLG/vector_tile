package edu.zju.gis;

import edu.zju.gis.util.MoveToNewDir;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ReadVectorTest {

    @Test
    public void traverseFolder2() {
        List<String> list = new ArrayList<String>();
        ReadVector.traverseFolder2(list, "/Users/LG/Documents/pratice/geoserver-2.13.4/data_dir/gwc/vector_tiles_third/EPSG_4490_08");
        File file = new File(list.get(0));
        System.out.println(file.getName().split("\\.")[0].split("_")[0]);
    }

    @Test
    public void getXYZAndContentfromPbf() throws IOException {
        String sourcePath = "file:///Users/LG/Documents/pratice/geoserver-2.13.4/data_dir/gwc/vector_tiles_lk1";
        String targetPath = "file:///Users/LG/Documents/pratice/geoserver-2.13.4/data_dir/gwc/vector_tiles_lk1_fine_normal";
        MoveToNewDir.moveVectorTile(sourcePath, targetPath);
    }

    @Test
    public void testList() throws IOException {
        String first = "file:///Users/LG/Documents/pratice/geoserver-2.13.4/data_dir/gwc/vector_tiles_lk1/EPSG_4490_08/13_05/0419_0177.pbf";
        String second = "file:///Users/LG/Documents/pratice/geoserver-2.13.4/data_dir/gwc/tmp/fir.pbf";
        //Files.move(Paths.get(URI.create(first)),Paths.get(URI.create(second)));
        Files.delete(Paths.get(URI.create(second)));
    }

//    @Test
//    public void getXYZAndContentfromPbf2() throws IOException {
//        getXYZAndContentfromPbf("/Users/LG/Documents/pratice/geoserver-2.13.4/data_dir/gwc/vector_tiles_lk_fine/6/105/44.pbf");
//    }


}