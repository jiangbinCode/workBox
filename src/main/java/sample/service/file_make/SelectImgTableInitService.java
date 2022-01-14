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
import java.util.List;

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
        trendLoadData(skuImgTable, WorkCache.getWorkData().getSkuImgItems());

    }

    private static void masterImgTableLoadData() {
        trendLoadData(masterImgTable, WorkCache.getWorkData().getMasterImgItems());
    }

    private static void detailImgTableLoadData() {
        trendLoadData(detailImgTable, WorkCache.getWorkData().getDetailsImgItems());
    }

    private static void trendLoadData(TableView<PictureModel> tableView, List<PictureModel> pictureModels) {
        tableView.getItems().setAll(Collections.emptyList());
        tableView.getItems().addAll(pictureModels);
        tableView.setOnMouseClicked((EventHandler<Event>) event -> {
            String name = event.getEventType().getName();
            if (name.equals("MOUSE_CLICKED")) {
                PictureModel selectedItem = tableView.getSelectionModel().getSelectedItem();
                if (selectedItem == null) return;
                MakeConfig.previewImg.setImage(new Image(cn.hutool.core.io.FileUtil.getInputStream(new File(selectedItem.getPath()))));
            }
        });

    }
}
