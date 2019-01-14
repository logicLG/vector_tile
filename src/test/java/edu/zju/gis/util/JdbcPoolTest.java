package edu.zju.gis.util;

import org.junit.Test;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.spi.FileSystemProvider;


public class JdbcPoolTest {


    @Test
    public void testURI() throws MalformedURLException, URISyntaxException {
        FileSystemProvider.installedProviders().forEach(fs -> System.out.println(fs.getScheme()));
        URI uri = new URL("http://172.20.3.106/road").toURI();
        Path path = Paths.get(uri);
        System.out.println(path.getFileSystem());
    }


}