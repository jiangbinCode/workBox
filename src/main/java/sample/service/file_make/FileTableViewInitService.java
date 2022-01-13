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
        fileTableTableView.getItems().setAll(Collections.emptyList());
        List<TableViewUrgeFileTable> fileTables = new ArrayList<>();
        FileUtil.findRootFileImgAll(rootFile, fileTables);
        fileTableTableView.getItems().addAll(fileTables);
        fileTableTableView.setOnMouseClicked((EventHandler<Event>) event -> {
            String name = event.getEventType().getName();
            if (name.equals("MOUSE_CLICKED")) {
                TableViewUrgeFileTable selectedItem = fileTableTableView.getSelectionModel().getSelectedItem();
                MakeConfig.previewImg.setImage(new Image(cn.hutool.core.io.FileUtil.getInputStream(new File(selectedItem.getPath()))));
            }
        });
        menuBind();
    }

    public static List<TableViewUrgeFileTable> getSelectData() {
        ObservableList<TableViewUrgeFileTable> items = fileTableTableView.getItems();
        List<TableViewUrgeFileTable> selectData = new ArrayList<>();
        for (TableViewUrgeFileTable item : items) {
            if (item.getCheckBox().isSelected()) selectData.add(item);
        }
        return selectData;
    }

    public static void clearSelectData() {
        List<TableViewUrgeFileTable> selectData = getSelectData();
        for (TableViewUrgeFileTable item : selectData) {
            item.getCheckBox().change(false);
        }
    }

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

    public static void imgJoinWorkCache(TableViewUrgeFileTable selectedItem, PictureType pictureType) {
        selectedItem.addPicType(pictureType);
        WorkCache.getWorkData().addImg(selectedItem, pictureType);
        fileTableTableView.refresh();
        SelectImgTableInitService.loadData();
    }

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
