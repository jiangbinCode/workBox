package sample.service.pic_tool;

import javafx.scene.control.TableView;
import sample.config.PicToolConfig;
import sample.model.pic_tool.ImgDataTableModel;

import java.util.Collections;
import java.util.List;

/**
 * @program: workBox
 * @description:
 * @author: bin
 * @create: 2022-02-18 15:19
 **/
public class ImgDataTableService {

    private static TableView<ImgDataTableModel> imgDataTable = PicToolConfig.imgDataTableModelTableView;


    public static void initData(List<ImgDataTableModel> models) {
        imgDataTable.getItems().clear();
        imgDataTable.getItems().addAll(models);
        imgDataTable.refresh();
    }

}
