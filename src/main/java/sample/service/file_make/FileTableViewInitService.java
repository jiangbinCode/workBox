package sample.service.file_make;


import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventTarget;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import sample.config.MakeConfig;
import sample.controller.MakeController;
import sample.model.TableViewUrgeFileTable;
import sample.util.FileUtil;

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
    private static TableView<TableViewUrgeFileTable> fileTableTableView = MakeConfig.fileTableTableView;

    public static void loadData(File rootFile) {
        fileTableTableView.getItems().setAll(Collections.emptyList());
        List<TableViewUrgeFileTable> fileTables = new ArrayList<>();
        FileUtil.findRootFileImgAll(rootFile, fileTables);
        fileTableTableView.getItems().addAll(fileTables);
        fileTableTableView.setTableMenuButtonVisible(true);
        fileTableTableView.setOnMouseClicked((EventHandler<Event>) event -> {
            TableViewUrgeFileTable selectedItem = fileTableTableView.getSelectionModel().getSelectedItem();
            MakeConfig.previewImg.setImage(new Image(cn.hutool.core.io.FileUtil.getInputStream(new File(selectedItem.getPath()))));
        });

    }


}
