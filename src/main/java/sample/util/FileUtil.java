package sample.util;

import javafx.scene.control.TreeItem;

import java.io.File;

/**
 * @program: workBox
 * @description:
 * @author: bin
 * @create: 2022-01-11 15:36
 **/
@SuppressWarnings("ALL")
public class FileUtil {


    public static TreeItem listFileDic(File file, TreeItem item) {
        File[] listFiles = file.listFiles();
        for (File file2 : listFiles) {
            if (file2.isDirectory()) {
                TreeItem treeItem = listFileDic(file2, new TreeItem(file2.getName()));
                item.getChildren().add(treeItem);
            } else {
                item.getChildren().add(new TreeItem<String>(file2.getName()));
            }
        }
        return item;
    }


}
