package com.zju.edu;

import java.io.IOException;

public class ConsumerMain {
    public static void main(String[] args) {
        String fileName="file:///Users/LG/Documents/pratice/geoserver-2.13.4/data_dir/gwc/lk1-1-7";
        try {
            Consumer.process(fileName,"lk1");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
