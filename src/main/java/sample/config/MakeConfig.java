package sample.config;

import javafx.scene.control.TableView;
import javafx.scene.control.TreeView;
import javafx.scene.image.ImageView;
import sample.model.file_make.PictureModel;
import sample.model.file_make.TableViewUrgeFileTable;
import sample.model.file_make.TreeViewUrgeFileTree;

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
    public static TableView<PictureModel> masterImgTable;
}
