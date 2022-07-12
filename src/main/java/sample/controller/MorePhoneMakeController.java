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

    @FXML
    void selectPhone() throws IOException {
        MorePhoneMakeTableModel workData = M_P_MakeCache.workData;
        if (workData == null) {
            Util.msg("提示", "请先初始化表格中的数据,若已初始化请鼠标右击对应的列数据进行加载");
            return;
        }
        Stage secondWindow = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/morePhoneSelect.fxml"));
        Scene scene = new Scene(root, 600, 400);
        secondWindow.setTitle("多型号选择");
        secondWindow.getIcons().add(new Image(
                Main.class.getResourceAsStream("/icon/zndz.jpg")));
        secondWindow.setScene(scene);
        secondWindow.show();
    }

    @FXML
    void createMorePropTextData() {
        MorePhoneMakeTableModel workData = M_P_MakeCache.workData;
        if (workData == null) {
            Util.msg("提示", "请先初始化表格中的数据,若已初始化请鼠标右击对应的列数据进行加载,若已加载请检查是否已经选择型号数据");
            return;
        }
        List<String> selectPhone = workData.getSelectPhone();
        String path = workData.getPath();
        File propTextFile = new File(path, "多属性.txt");
        List<String> textList = selectPhone.stream().map(x -> "适用手机型号:" + x).collect(Collectors.toList());
        FileUtil.appendUtf8Lines(textList, propTextFile);
        workData.setMsg("生成完毕,生成数量:" + selectPhone.size());
        morePhoneTable.refresh();
        Util.msg("信息", "生成完毕,型号数量:" + selectPhone.size());

    }

    @FXML
    void confirmEdit() {
        ObservableList<MorePhoneMakeTableModel> items = modelTable.getItems();
        if (CollectionUtil.isEmpty(items)) {
            Util.msg("提示", "请先勾选表格中的型号数据,勾选完毕后请鼠标右击表格加载对应的数据");
            return;
        }
        if (StrUtil.isBlank((String) moveFolderName.getValue())) {
            Util.msg("提示", "请先选择需要移动的文件夹");
            return;
        }
        MorePhoneTableService.MorePhoneMakeDivideSKUPicTask task = new MorePhoneTableService.MorePhoneMakeDivideSKUPicTask();
        Stage stage = ProgressStage.of(Main.getPrimaryStage(), task, "请稍后").show();
        task.setOnSucceeded(p -> {
            stage.close();
            Util.msg("信息", "操作完毕!");
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
                        Util.msg("信息", "请输入sku名字");
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
