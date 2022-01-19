package sample.service.file_make;


import cn.hutool.core.collection.CollectionUtil;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import sample.config.MakeConfig;
import sample.enums.PictureType;
import sample.model.file_make.TableViewUrgeFileTable;
import sample.util.FileUtil;
import sample.util.Util;
import sample.work.WorkCache;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @program: workBox
 * @description:
 * @author: bin
 * @create: 2022-01-12 11:05
 **/
public class FileTableViewInitService {
    private static final TableView<TableViewUrgeFileTable> fileTableTableView = MakeConfig.fileTableTableView;

    public static void loadData(File rootFile) {
        //加载前先清空已有的数据信息
        fileTableTableView.getItems().setAll(Collections.emptyList());
        List<TableViewUrgeFileTable> fileTables = new ArrayList<>();
        //查找这个需要加载的文件夹里面所有的图片信息
        FileUtil.findRootFileImgAll(rootFile, fileTables);
        if (CollectionUtil.isEmpty(fileTables)) {
            return;
        }
        //装载至表格中
        fileTableTableView.getItems().addAll(fileTables);
        fileTableTableView.setOnMouseClicked((EventHandler<Event>) event -> {
            String name = event.getEventType().getName();
            //图片预览
            if (name.equals("MOUSE_CLICKED")) {
                TableViewUrgeFileTable selectedItem = fileTableTableView.getSelectionModel().getSelectedItem();
                MakeConfig.previewImg.setImage(new Image(cn.hutool.core.io.FileUtil.getInputStream(new File(selectedItem.getPath()))));
            }
        });
        //绑定表格每列右击的菜单栏
        menuBind();
    }

    /**
     * 获取表格中被勾选的数据
     *
     * @return
     */
    public static List<TableViewUrgeFileTable> getSelectData() {
        ObservableList<TableViewUrgeFileTable> items = fileTableTableView.getItems();
        List<TableViewUrgeFileTable> selectData = new ArrayList<>();
        for (TableViewUrgeFileTable item : items) {
            if (item.getCheckBox().isSelected()) selectData.add(item);
        }
        return selectData;
    }

    //清除表格中已被选择的数据
    public static void clearSelectData() {
        List<TableViewUrgeFileTable> selectData = getSelectData();
        for (TableViewUrgeFileTable item : selectData) {
            item.getCheckBox().change(false);
        }
    }

    //全选表格中里面的数据或者全不选
    public static void checkAll(Boolean check) {
        fileTableTableView.getItems().forEach(item -> {
            item.getCheckBox().change(check);
        });
    }

    private static void menuBind() {
        ContextMenu addMenu = new ContextMenu();
        MenuItem m1 = new MenuItem("选择/取消选择");
        MenuItem m2 = new MenuItem("标识/取消为主图");
        MenuItem m3 = new MenuItem("标识/取消选项图");
        MenuItem m4 = new MenuItem("标识/取消详情图");
        MenuItem m5 = new MenuItem("标识/取消为透明图");
        m1.setOnAction(p -> {
            TableViewUrgeFileTable selectedItem = fileTableTableView.getSelectionModel().getSelectedItem();
            selectedItem.getCheckBox().change(!Boolean.TRUE.equals(selectedItem.getCheckBox().isSelected()));
        });
        m2.setOnAction(p -> {
            TableViewUrgeFileTable selectedItem = fileTableTableView.getSelectionModel().getSelectedItem();
            if (WorkCache.getWorkData().containImg(selectedItem, PictureType.主图)) {
                imgRemoveWorkCache(selectedItem, PictureType.主图);
            } else {
                imgJoinWorkCache(selectedItem, PictureType.主图);
            }
        });
        m3.setOnAction(p -> {
            TableViewUrgeFileTable selectedItem = fileTableTableView.getSelectionModel().getSelectedItem();
            if (WorkCache.getWorkData().containImg(selectedItem, PictureType.选项图)) {
                imgRemoveWorkCache(selectedItem, PictureType.选项图);
            } else {
                imgJoinWorkCache(selectedItem, PictureType.选项图);
            }
        });
        m4.setOnAction(p -> {
            TableViewUrgeFileTable selectedItem = fileTableTableView.getSelectionModel().getSelectedItem();
            if (WorkCache.getWorkData().containImg(selectedItem, PictureType.详情图)) {
                imgRemoveWorkCache(selectedItem, PictureType.详情图);
            } else {
                imgJoinWorkCache(selectedItem, PictureType.详情图);
            }
        });
        m5.setOnAction(p -> {
            TableViewUrgeFileTable selectedItem = fileTableTableView.getSelectionModel().getSelectedItem();
            if (WorkCache.getWorkData().containImg(selectedItem, PictureType.透明图)) {
                imgRemoveWorkCache(selectedItem, PictureType.透明图);
            } else {
                imgJoinWorkCache(selectedItem, PictureType.透明图);
            }
        });
        addMenu.getItems().addAll(m1, m2, m3, m4, m5);
        fileTableTableView.setContextMenu(addMenu);
    }


    /**
     * 将表格中的一列标识为对应类型的图片数据
     *
     * @param selectedItem
     * @param pictureType
     */
    public static void imgJoinWorkCache(TableViewUrgeFileTable selectedItem, PictureType pictureType) {
        selectedItem.addPicType(pictureType);
        WorkCache.getWorkData().addImg(selectedItem, pictureType);
        fileTableTableView.refresh();
        SelectImgTableInitService.loadData();
    }


    /**
     * 将表格中的一列移除已经被标识图片类型
     *
     * @param selectedItem
     * @param pictureType
     */
    public static void imgRemoveWorkCache(TableViewUrgeFileTable selectedItem, PictureType pictureType) {
        selectedItem.removePicType(pictureType);
        WorkCache.getWorkData().removeImg(selectedItem, pictureType);
        fileTableTableView.refresh();
        SelectImgTableInitService.loadData();
    }


    public static void imgMoreJoinWorkCache(PictureType pictureType) {
        List<TableViewUrgeFileTable> selectData = getSelectData();
        if (CollectionUtil.isEmpty(selectData)) {
            Util.msg("信息", "请至少在表格中选择一个!");
            return;
        }
        selectData.forEach(x -> imgJoinWorkCache(x, pictureType));
        clearSelectData();
    }

}
