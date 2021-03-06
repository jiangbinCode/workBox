package sample.controller;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;
import sample.Main;
import sample.config.MakeConfig;
import sample.config.MorePhoneMakeConfig;
import sample.model.file_make.PictureModel;
import sample.model.m_p_make.MorePhoneMakeTableModel;
import sample.service.m_p_make.MorePhoneTableService;
import sample.util.ProgressStage;
import sample.util.Util;
import sample.work.file_make.WorkCache;
import sample.work.m_p_make.M_P_MakeCache;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

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
    @FXML
    private Label selectPhoneText;
    @FXML
    private Label selectPhoneNumText;
    @FXML
    private Label selectNum;
    @FXML
    private TableView<MorePhoneMakeTableModel> modelTable;
    @FXML
    private TableColumn<MorePhoneMakeTableModel, String> modelFolderName;
    @FXML
    private ChoiceBox masterPhone;
    @FXML
    private ChoiceBox moveFolderName;
    @FXML
    private CheckBox fileCheckAll;


    @FXML
    void modelSelectAllOrNotAll(ActionEvent event) {
        boolean isSelect = fileCheckAll.isSelected();
        MorePhoneTableService.checkAll(isSelect);
        morePhoneTable.refresh();
    }

    /**
     * ?????????????????????
     *
     * @param event
     */
    @FXML
    void selectFile(ActionEvent event) {
        DirectoryChooser file = new DirectoryChooser();
        File newFolder = file.showDialog(Main.getPrimaryStage());//??????file???????????????????????????
        if (newFolder == null) return;
        filePath.setText(newFolder.getPath());
        MorePhoneTableService.loadData(newFolder);
    }

    @FXML
    void selectPhone() throws IOException {
        MorePhoneMakeTableModel workData = M_P_MakeCache.workData;
        if (workData == null) {
            Util.msg("??????", "?????????????????????????????????,????????????????????????????????????????????????????????????");
            return;
        }
        Stage secondWindow = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/morePhoneSelect.fxml"));
        Scene scene = new Scene(root, 600, 400);
        secondWindow.setTitle("???????????????");
        secondWindow.getIcons().add(new Image(
                Main.class.getResourceAsStream("/icon/zndz.jpg")));
        secondWindow.setScene(scene);
        secondWindow.show();
    }

    @FXML
    void createMorePropTextData() {
        MorePhoneMakeTableModel workData = M_P_MakeCache.workData;
        if (workData == null) {
            Util.msg("??????", "?????????????????????????????????,????????????????????????????????????????????????????????????,???????????????????????????????????????????????????");
            return;
        }
        List<String> selectPhone = workData.getSelectPhone();
        String path = workData.getPath();
        File propTextFile = new File(path, "?????????.txt");
        List<String> textList = selectPhone.stream().map(x -> "??????????????????:" + x).collect(Collectors.toList());
        FileUtil.appendUtf8Lines(textList, propTextFile);
        workData.setMsg("????????????,????????????:" + selectPhone.size());
        morePhoneTable.refresh();
        Util.msg("??????", "????????????,????????????:" + selectPhone.size());

    }

    @FXML
    void confirmEdit() {
        ObservableList<MorePhoneMakeTableModel> items = modelTable.getItems();
        if (CollectionUtil.isEmpty(items)) {
            Util.msg("??????", "????????????????????????????????????,?????????????????????????????????????????????????????????");
            return;
        }
        if (StrUtil.isBlank((String) moveFolderName.getValue())) {
            Util.msg("??????", "????????????????????????????????????");
            return;
        }
        MorePhoneTableService.MorePhoneMakeDivideSKUPicTask task = new MorePhoneTableService.MorePhoneMakeDivideSKUPicTask();
        Stage stage = ProgressStage.of(Main.getPrimaryStage(), task, "?????????").show();
        task.setOnSucceeded(p -> {
            stage.close();
            Util.msg("??????", "????????????!");
        });

    }

    private void eventRegister() {
        fName.setCellValueFactory(new PropertyValueFactory("name"));
        fMsg.setCellValueFactory(new PropertyValueFactory("msg"));
        fCheckBox.setCellValueFactory(cellData -> cellData.getValue().getCheckBox().getCheckBox());
        modelFolderName.setCellValueFactory(new PropertyValueFactory("name"));
        modelFolderName.setCellFactory(TextFieldTableCell.forTableColumn());
        modelFolderName.setOnEditCommit(
                (TableColumn.CellEditEvent<MorePhoneMakeTableModel, String> t) -> {
                    if (StrUtil.isBlank(t.getNewValue())) {
                        Util.msg("??????", "?????????sku??????");
                    } else {
                        MorePhoneMakeTableModel rowValue = t.getRowValue();
                        rowValue.setName(t.getNewValue());
                        MorePhoneTableService.loadPhoneChoiceBox(MorePhoneTableService.getSelectData());
                    }
                    modelTable.refresh();
                    morePhoneTable.refresh();
                });
//        modelTable.setEditable(true);
    }


    public void initialize() {
        eventRegister();
        MorePhoneMakeConfig.morePhoneTable = morePhoneTable;
        MorePhoneMakeConfig.selectPhoneText = selectPhoneText;
        MorePhoneMakeConfig.selectPhoneNumText = selectPhoneNumText;
        MorePhoneMakeConfig.modelTable = modelTable;
        MorePhoneMakeConfig.masterPhone = masterPhone;
        MorePhoneMakeConfig.moveFolderName = moveFolderName;
        MorePhoneMakeConfig.selectNum = selectNum;
    }


}
