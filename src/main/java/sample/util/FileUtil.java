package sample.util;

import cn.hutool.core.collection.CollectionUtil;
import javafx.scene.control.TreeItem;
import sample.model.file_make.TableViewUrgeFileTable;
import sample.model.file_make.TreeViewUrgeFileTree;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: workBox
 * @description:
 * @author: bin
 * @create: 2022-01-11 15:36
 **/
@SuppressWarnings("ALL")
public class FileUtil {


    private static final List<String> IMG_FORMAT = new ArrayList<>();

    static {
        IMG_FORMAT.add("jpg");
        IMG_FORMAT.add("jepg");
        IMG_FORMAT.add("png");
        IMG_FORMAT.add("gif ");
    }

    public static TreeItem listFileDic(File file, TreeItem item, Integer hierarchy) {
        ++hierarchy;
        File[] listFiles = file.listFiles();
        for (File file2 : listFiles) {
            if (file2.isDirectory()) {
                TreeItem treeItem = listFileDic(file2,
                        new TreeItem(new TreeViewUrgeFileTree(file2.getName(), file2.getPath(), true, hierarchy)), hierarchy);
                item.getChildren().add(treeItem);
            } else {
                item.getChildren().add(new TreeItem(new TreeViewUrgeFileTree(file2.getName(), file2.getPath(), false, hierarchy)));
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
                String extName = cn.hutool.core.io.FileUtil.extName(file2);
                if (IMG_FORMAT.contains(extName.toLowerCase())) {
                    fileTables.add(TableViewUrgeFileTable.instantiation(file2));
                }
            }
        }

    }

}
