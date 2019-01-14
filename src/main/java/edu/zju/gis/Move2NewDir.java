package edu.zju.gis;

import edu.zju.gis.util.MoveToNewDir;

import java.io.IOException;

public class Move2NewDir {
    public static void main(String[] args) throws IOException {
        if (args.length != 2) {
            System.out.println("请输入原始文件夹和目标文件夹!");
        }
        String sourcePath = args[0];
        String targetPath = args[1];
        MoveToNewDir.moveVectorTile(sourcePath, targetPath);
    }
}
