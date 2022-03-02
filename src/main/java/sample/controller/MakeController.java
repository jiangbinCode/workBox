package sample.controller;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import javafx.beans.binding.ObjectBinding;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;
import sample.Main;
import sample.config.MakeConfig;
import sample.enums.PictureType;
import sample.model.file_make.PictureModel;
import sample.model.file_make.TableViewUrgeFileTable;
import sample.model.file_make.TreeViewUrgeFileTree;
import sample.service.file_make.FileTableViewInitService;
import sample.service.file_make.FileTreeViewInitService;
import sample.util.ProgressStage;
import sample.util.Util;
import sample.work.file_make.IsogenyCreateTask;
import sample.work.file_make.WorkCache;
import sample.work.file_make.WorkTask;

import java.io.File;
import java.io.IOException;

public class MakeController {

    @FXML
    private TextField filePath;
    @FXML
    private TreeView<TreeViewUrgeFileTree> fileTree;
    @FXML
    private TableView<TableViewUrgeFileTable> fileTableTableView;
    @FXML
    private TableColumn<TableViewUrgeFileTable, String> name;
    @FXML
    private TableColumn<TableViewUrgeFileTable, String> fileSize;
    @FXML
    private TableColumn<TableViewUrgeFileTable, String> fileType;
    @FXML
    private TableColumn<TableViewUrgeFileTable, CheckBox> fCheckBox;
    @FXML
    private TableColumn<TableViewUrgeFileTable, String> useWay;
    @FXML
    private TableColumn<TableViewUrgeFileTable, String> pixel;
    @FXML
    private ImageView previewImg;
    @FXML
    private CheckBox fileCheckAll;
    @FXML
    private TableView<PictureModel> masterImgTable;
    @FXML
    private TableColumn<PictureModel, String> m_name;
    @FXML
    private TableColumn<PictureModel, Integer> m_num;
    @FXML
    private TableView<PictureModel> skuImgTable;
    @FXML
    private TableColumn<PictureModel, String> s_name;
    @FXML
    private TableColumn<PictureModel, Integer> s_num;
    @FXML
    private TableView<PictureModel> detailImgTable;
    @FXML
    private TableColumn<PictureModel, String> d_name;
    @FXML
    private TableColumn<PictureModel, Integer> d_num;
    //    @FXML
//    private TextField masterImgMaxNum;
//    @FXML
//    private TextField skuImgMaxNum;
//    @FXML
//    private TextField detailImgMaxNum;
    @FXML
    private TextField fileOutPath;
    @FXML
    private TextField seriesName;

    @FXML
    private TextField productName;
    @FXML
    private Label selectNum;
    @FXML
    private ProgressBar makeProgressBar;


    /**
     * 选择一个文件夹 也是数据包制作的开始
     *
     * @param event
     */
    @FXML
    void selectFile(ActionEvent event) {
        DirectoryChooser file = new DirectoryChooser();
        File newFolder = file.showDialog(Main.getPrimaryStage());//这个file就是选择的文件夹了
        if (newFolder == null) return;
        filePath.setText(newFolder.getPath());
        seriesName.setText(newFolder.getName() + "make");
        fileOutPath.setText(newFolder.getParentFile().getPath() + File.separator + newFolder.getName() + "make");
//        fileOutPath.setDisable(true);
        FileTreeViewInitService.initTree(newFolder, fileTree); //初始化页面左侧的树形数据结构
        WorkCache.getProductDataInfoMap().clear();
    }

    @FXML
    void tagMasterImg(ActionEvent event) {
        FileTableViewInitService.imgMoreJoinWorkCache(PictureType.主图);
    }

    @FXML
    void tagSkuImg(ActionEvent event) {
        FileTableViewInitService.imgMoreJoinWorkCache(PictureType.选项图);
    }

    @FXML
    void tagDetailsImg(ActionEvent event) {
        FileTableViewInitService.imgMoreJoinWorkCache(PictureType.详情图);
    }

    @FXML
    void tagWhiteImg(ActionEvent event) {
        FileTableViewInitService.imgMoreJoinWorkCache(PictureType.透明图);
    }


    @FXML
    void fileCheckAll(ActionEvent event) {
        if (!WorkCache.isWord()) {
            Util.msg("提示", "请先初始化数据!");
            return;
        }
        FileTableViewInitService.checkAll(fileCheckAll.isSelected());
    }


    @FXML
    void makeTaskStart(ActionEvent event) {
        if (!WorkCache.isWord()) {
            Util.msg("提示", "请先初始化数据!");
            return;
        }
        WorkTask workTask = new WorkTask();
        Stage stage = ProgressStage.of(Main.getPrimaryStage(), workTask, "请稍后...").show();
        workTask.setOnSucceeded((e) -> {
            stage.close();
            Util.msg("提示", "生成数据完毕,生成路径为: " + MakeConfig.fileOutPath.getText() + " 本次成功生成的数量为:" + WorkCache.getProductDataInfoMap().size());
        });
    }

    @FXML
    void reprintBuild(ActionEvent event) {
        if (!WorkCache.isWord()) {
            Util.msg("提示", "请先初始化数据!");
            return;
        }
        if (!WorkCache.getWorkData().isUsable()) {
            Util.msg("提示", "请先编辑完数据后在进行该操作!");
            return;
        }
        ProgressStage.of(Main.getPrimaryStage(), new IsogenyCreateTask(), "请稍后...").show();
    }


    @FXML
    void morePhoneMake(ActionEvent e) throws IOException {

        Stage secondWindow = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/morePhoneSelect.fxml"));
        Scene scene = new Scene(root, 600, 400);
        secondWindow.setTitle("多型号选择");
        secondWindow.getIcons().add(new Image(
                Main.class.getResourceAsStream("/icon/zndz.jpg")));
        secondWindow.setScene(scene);
        secondWindow.show();
    }


    private void bindColumn() {
        fileTableTableBindColumn();
        masterImgTableBindColumn();
        skuImgTableBindColumn();
        detailImgTableBindColumn();
    }


    private void skuImgTableBindColumn() {
        s_name.setCellValueFactory(new PropertyValueFactory("name"));
        s_num.setCellValueFactory(new PropertyValueFactory("num"));
        s_name.setCellFactory(TextFieldTableCell.forTableColumn());
        s_num.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        s_name.setOnEditCommit(
                (TableColumn.CellEditEvent<PictureModel, String> t) -> {
                    if (StrUtil.isBlank(t.getNewValue())) {
                        Util.msg("信息", "请输入sku名字");
                    } else {
                        PictureModel rowValue = t.getRowValue();
                        rowValue.setName(t.getNewValue());
                    }
                    skuImgTable.refresh();
                });

        s_num.setOnEditCommit(
                (TableColumn.CellEditEvent<PictureModel, Integer> t) -> {
                    int maxNum = WorkCache.getWorkData().getSkuImgItems().size();
                    PictureModel rowValue = t.getRowValue();
                    try {
                        Integer newValue = t.getNewValue();
                        if (newValue < 1 || newValue > maxNum) throw new NumberFormatException();
                        WorkCache.getWorkData().updateNun(rowValue, newValue, PictureType.选项图);
                    } catch (Exception e) {
                        Util.msg("信息", "请输入数字,数字不能小于1,不能大于:" + maxNum);
                    } finally {
                        skuImgTable.refresh();
                    }
                });
        skuImgTable.setEditable(true);
    }

    private void detailImgTableBindColumn() {
        d_name.setCellValueFactory(new PropertyValueFactory("name"));
        d_num.setCellValueFactory(new PropertyValueFactory("num"));
        d_num.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        d_num.setOnEditCommit(
                (TableColumn.CellEditEvent<PictureModel, Integer> t) -> {
                    int maxNum = WorkCache.getWorkData().getDetailsImgItems().size();
                    PictureModel rowValue = t.getRowValue();
                    try {
                        Integer newValue = t.getNewValue();
                        if (newValue < 1 || newValue > maxNum) throw new NumberFormatException();
                        WorkCache.getWorkData().updateNun(rowValue, newValue, PictureType.详情图);
                    } catch (Exception e) {
                        Util.msg("信息", "请输入数字,数字不能小于1,不能大于:" + maxNum);
                    } finally {
                        detailImgTable.refresh();
                    }

                });
        detailImgTable.setEditable(true);

    }


    private void masterImgTableBindColumn() {
        m_name.setCellValueFactory(new PropertyValueFactory("name"));
        m_num.setCellValueFactory(new PropertyValueFactory("num"));
        m_num.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        m_num.setOnEditCommit(
                (TableColumn.CellEditEvent<PictureModel, Integer> t) -> {
                    int maxNum = WorkCache.getWorkData().getMasterImgItems().size();
                    PictureModel rowValue = t.getRowValue();
                    try {
                        Integer newValue = t.getNewValue();
                        if (newValue < 1 || newValue > maxNum) throw new NumberFormatException();
                        WorkCache.getWorkData().updateNun(rowValue, newValue, PictureType.主图);
                    } catch (Exception e) {
                        Util.msg("信息", "请输入数字,数字不能小于1,不能大于:" + maxNum);
                    } finally {
                        masterImgTable.refresh();
                    }

                });
        masterImgTable.setEditable(true);

    }


    private void fileTableTableBindColumn() {
        name.setCellValueFactory(new PropertyValueFactory(name.getId()));
        fileSize.setCellValueFactory(new PropertyValueFactory(fileSize.getId()));
        fileType.setCellValueFactory(new PropertyValueFactory(fileType.getId()));
        pixel.setCellValueFactory(new PropertyValueFactory(pixel.getId()));
        fCheckBox.setCellValueFactory(cellData -> cellData.getValue().getCheckBox().getCheckBox());
        useWay.setCellValueFactory(cellData -> new ObjectBinding<String>() {
            @Override
            protected String computeValue() {
                return CollectionUtil.join(cellData.getValue().getUseWay(), ",");
            }
        });
    }


    public void initialize() {
        eventRegister();
        MakeConfig.fileTableTableView = this.fileTableTableView;
        MakeConfig.fileTree = this.fileTree;
        MakeConfig.previewImg = this.previewImg;
        MakeConfig.masterImgTable = this.masterImgTable;
        MakeConfig.skuImgTable = this.skuImgTable;
        MakeConfig.detailImgTable = this.detailImgTable;
        MakeConfig.selectNum = this.selectNum;
//        MakeConfig.masterImgMaxNum = this.masterImgMaxNum;
//        MakeConfig.skuImgMaxNum = this.skuImgMaxNum;
//        MakeConfig.detailImgMaxNum = this.detailImgMaxNum;
        MakeConfig.fileOutPath = this.fileOutPath;
        MakeConfig.productName = this.productName;
        MakeConfig.makeProgressBar = this.makeProgressBar;
    }

    private void eventRegister() {
        bindColumn();
        productName.textProperty().addListener((observable, oldValue, newValue) -> {
                    WorkCache.getWorkData().setProductName(newValue);
                }
        );

    }

}
