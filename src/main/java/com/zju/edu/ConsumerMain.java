package com.zju.edu;

import java.io.IOException;

public class ConsumerMain {
    public static void main(String[] args) {
        String fileName = "/Users/LG/Documents/pratice/geoserver-2.13.4/data_dir/gwc/vector_lk_noID";
        try {
            Consumer.process(fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}