package sample.service.file_make;

import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import sample.config.MakeConfig;
import sample.model.file_make.PictureModel;
import sample.model.file_make.ProductDataInfo;
import sample.model.file_make.TableViewUrgeFileTable;
import sample.work.WorkCache;

import java.util.Collections;

/**
 * @program: workBox
 * @description:
 * @author: bin
 * @create: 2022-01-13 11:05
 **/
public class MasterImgTableInitService {

    private final static TableView<PictureModel> masterImgTable = MakeConfig.masterImgTable;


    public static void loadData() {
        masterImgTable.getItems().setAll(Collections.emptyList());
        ProductDataInfo workData = WorkCache.getWorkData();
        masterImgTable.getItems().addAll(workData.getMasterImgItems());
    }

}
