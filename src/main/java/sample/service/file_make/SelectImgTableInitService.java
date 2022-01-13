package sample.service.file_make;

import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import sample.config.MakeConfig;
import sample.model.file_make.PictureModel;
import sample.model.file_make.ProductDataInfo;
import sample.model.file_make.TableViewUrgeFileTable;
import sample.work.WorkCache;

import java.io.File;
import java.util.Collections;

/**
 * @program: workBox
 * @description:
 * @author: bin
 * @create: 2022-01-13 11:05
 **/
public class SelectImgTableInitService {

    private final static TableView<PictureModel> masterImgTable = MakeConfig.masterImgTable;

    private final static TableView<PictureModel> skuImgTable = MakeConfig.skuImgTable;

    private final static TableView<PictureModel> detailImgTable = MakeConfig.detailImgTable;


    public static void loadData() {
        masterImgTableLoadData();
        skuImgTableLoadData();
        detailImgTableLoadData();

    }


    private static void skuImgTableLoadData() {
        skuImgTable.getItems().setAll(Collections.emptyList());
        ProductDataInfo workData = WorkCache.getWorkData();
        skuImgTable.getItems().addAll(workData.getSkuImgItems());
        skuImgTable.setOnMouseClicked((EventHandler<Event>) event -> {
            String name = event.getEventType().getName();
            if (name.equals("MOUSE_CLICKED")) {
                PictureModel selectedItem = skuImgTable.getSelectionModel().getSelectedItem();
                MakeConfig.previewImg.setImage(new Image(cn.hutool.core.io.FileUtil.getInputStream(new File(selectedItem.getPath()))));
            }
        });

    }

    private static void masterImgTableLoadData() {
        masterImgTable.getItems().setAll(Collections.emptyList());
        ProductDataInfo workData = WorkCache.getWorkData();
        masterImgTable.getItems().addAll(workData.getMasterImgItems());
        masterImgTable.setOnMouseClicked((EventHandler<Event>) event -> {
            String name = event.getEventType().getName();
            if (name.equals("MOUSE_CLICKED")) {
                PictureModel selectedItem = masterImgTable.getSelectionModel().getSelectedItem();
                MakeConfig.previewImg.setImage(new Image(cn.hutool.core.io.FileUtil.getInputStream(new File(selectedItem.getPath()))));
            }
        });
    }

    private static void detailImgTableLoadData() {
        detailImgTable.getItems().setAll(Collections.emptyList());
        ProductDataInfo workData = WorkCache.getWorkData();
        detailImgTable.getItems().addAll(workData.getDetailsImgItems());
        detailImgTable.setOnMouseClicked((EventHandler<Event>) event -> {
            String name = event.getEventType().getName();
            if (name.equals("MOUSE_CLICKED")) {
                PictureModel selectedItem = detailImgTable.getSelectionModel().getSelectedItem();
                MakeConfig.previewImg.setImage(new Image(cn.hutool.core.io.FileUtil.getInputStream(new File(selectedItem.getPath()))));
            }
        });
    }


}
