package edu.zju.gis;

import org.junit.Test;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class ConsumerTest {

    @Test
    public void process() throws IOException {
        Set<String> set=new HashSet<>();
        set.add("1");
        String a=new String("1");
        set.add("1");
        System.out.println(set);
    }
}