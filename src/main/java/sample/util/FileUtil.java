package sample.util;

import javafx.scene.control.TreeItem;
import sample.model.TableViewUrgeFileTable;
import sample.model.TreeViewUrgeFileTree;

import java.io.File;
import java.util.List;

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
                TreeItem treeItem = listFileDic(file2,
                        new TreeItem(new TreeViewUrgeFileTree(file2.getName(), file2.getPath(), true)));
                item.getChildren().add(treeItem);
            } else {
                item.getChildren().add(new TreeItem(new TreeViewUrgeFileTree(file2.getName(), file2.getPath(), false)));
            }
        }
        return item;
    }


    public static void findRootFileImgAll(File file, List<TableViewUrgeFileTable> fileTables) {
        File[] listFiles = file.listFiles();
        for (File file2 : listFiles) {
            if (file2.isDirectory()) {
                findRootFileImgAll(file2, fileTables);
            } else {
                fileTables.add(TableViewUrgeFileTable.instantiation(file2));
            }
        }

    }

}
