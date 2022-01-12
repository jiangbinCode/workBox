package sample.config;

import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeView;
import javafx.scene.image.ImageView;
import sample.model.TableViewUrgeFileTable;
import sample.model.TreeViewUrgeFileTree;

/**
 * @program: workBox
 * @description:
 * @author: bin
 * @create: 2022-01-12 15:20
 **/
public class MakeConfig {
    public static TreeView<TreeViewUrgeFileTree> fileTree;
    public static TableView<TableViewUrgeFileTable> fileTableTableView;
    public static ImageView previewImg;
}
