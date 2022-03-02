package sample.controller;

import cn.hutool.core.collection.CollectionUtil;
import javafx.beans.binding.ObjectBinding;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import sample.Main;
import sample.config.PicToolConfig;
import sample.model.pic_tool.ImgDataTableModel;
import sample.util.ProgressStage;
import sample.work.pic_tool.PicExamineWork;

import java.io.File;

/**
 * @program: workBox
 * @description:
 * @author: bin
 * @create: 2022-02-17 11:20
 **/
public class PicToolController {


    @FXML
    private TextField filePath;

    @FXML
    private TableView<ImgDataTableModel> imgDataTable;
    @FXML
    private TableColumn<ImgDataTableModel, String> name;

    @FXML
    private TableColumn<ImgDataTableModel, String> path;
    @FXML
    private TableColumn<ImgDataTableModel, String> fileSize;
    @FXML
    private TableColumn<ImgDataTableModel, String> fileType;
    //    @FXML
//    private TableColumn<ImgDataTableModel, CheckBox> fCheckBox;
    @FXML
    private TableColumn<ImgDataTableModel, String> type;
    @FXML
    private TableColumn<ImgDataTableModel, String> pixel;

    @FXML
    void selectFile(ActionEvent event) {
        DirectoryChooser file = new DirectoryChooser();
        File newFolder = file.showDialog(Main.getPrimaryStage());//这个file就是选择的文件夹了
        if (newFolder == null) return;
        filePath.setText(newFolder.getPath());
        Stage show = ProgressStage.of(Main.getPrimaryStage(), new PicExamineWork(newFolder.getPath()), "数据检验中..").show();
    }


    @FXML
    void repair() {

    }

    public void initialize() {
        fileTableTableBindColumn();
        PicToolConfig.imgDataTableModelTableView = imgDataTable;
    }

    private void fileTableTableBindColumn() {
        name.setCellValueFactory(new PropertyValueFactory(name.getId()));
        fileSize.setCellValueFactory(new PropertyValueFactory(fileSize.getId()));
        fileType.setCellValueFactory(new PropertyValueFactory(fileType.getId()));
        path.setCellValueFactory(new PropertyValueFactory(path.getId()));
//        fCheckBox.setCellValueFactory(cellData -> cellData.getValue().getCheckBox().getCheckBox());
        type.setCellValueFactory(cellData -> new ObjectBinding<String>() {
            @Override
            protected String computeValue() {
                return cellData.getValue().getType().toString();
            }
        });
        pixel.setCellValueFactory(cellData -> new ObjectBinding<String>() {
            @Override
            protected String computeValue() {
                return cellData.getValue().getWidth() + " x " + cellData.getValue().getHeight();
            }
        });
    }

}
