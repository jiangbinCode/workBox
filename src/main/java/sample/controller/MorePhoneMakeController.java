package sample.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.DirectoryChooser;
import sample.Main;
import sample.config.MorePhoneMakeConfig;
import sample.model.m_p_make.MorePhoneMakeTableModel;
import sample.service.m_p_make.MorePhoneTableService;

import java.io.File;

public class MorePhoneMakeController {
    @FXML
    private TextField filePath;
    @FXML
    private TableView<MorePhoneMakeTableModel> morePhoneTable;
    @FXML
    private TableColumn<MorePhoneMakeTableModel, CheckBox> fCheckBox;
    @FXML
    private TableColumn<MorePhoneMakeTableModel, String> fName;
    @FXML
    private TableColumn<MorePhoneMakeTableModel, String> fMsg;


    /**
     * 选择一个文件夹
     *
     * @param event
     */
    @FXML
    void selectFile(ActionEvent event) {
        DirectoryChooser file = new DirectoryChooser();
        File newFolder = file.showDialog(Main.getPrimaryStage());//这个file就是选择的文件夹了
        if (newFolder == null) return;
        filePath.setText(newFolder.getPath());
        MorePhoneTableService.loadData(newFolder);
    }

    private void eventRegister() {
        fName.setCellValueFactory(new PropertyValueFactory(fName.getId()));
        fMsg.setCellValueFactory(new PropertyValueFactory(fMsg.getId()));
        fCheckBox.setCellValueFactory(cellData -> cellData.getValue().getCheckBox().getCheckBox());
    }


    public void initialize() {
        eventRegister();
        MorePhoneMakeConfig.morePhoneTable = morePhoneTable;
    }

}
