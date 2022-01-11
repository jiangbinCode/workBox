package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.DirectoryChooser;
import sample.util.FileUtil;

import java.io.File;

public class Controller {

    @FXML
    private TextField filePath;
    @FXML
    private TreeView<String> fileTree;


    @FXML
    void selectFile(ActionEvent event) {
        DirectoryChooser file = new DirectoryChooser();
        File newFolder = file.showDialog(Main.getPrimaryStage());//这个file就是选择的文件夹了
        TreeItem<String> rootThreeItem = new TreeItem<String>(newFolder.getPath());
        rootThreeItem.setExpanded(true);
        rootThreeItem = FileUtil.listFileDic(newFolder, rootThreeItem);
        filePath.setText(newFolder.getPath());
        fileTree.setRoot(rootThreeItem);
    }



}
