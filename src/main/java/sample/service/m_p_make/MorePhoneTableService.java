package sample.service.m_p_make;

import cn.hutool.core.collection.CollectionUtil;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import sample.config.MakeConfig;
import sample.config.MorePhoneMakeConfig;
import sample.model.file_make.TableViewUrgeFileTable;
import sample.model.m_p_make.MorePhoneMakeTableModel;
import sample.util.FileUtil;
import sample.util.Util;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class MorePhoneTableService {

    private static TableView<MorePhoneMakeTableModel> morePhoneTable = MorePhoneMakeConfig.morePhoneTable;


    public static void loadData(File file) {
        if (file == null) return;
        //加载前先清空已有的数据信息
        morePhoneTable.getItems().setAll(Collections.emptyList());
        File[] listFiles = file.listFiles();
        if (listFiles == null || listFiles.length <= 0) {
            Util.msg("信息", "该文件夹下暂无文件信息!");
            return;
        }

        List<File> fileList = Arrays.stream(listFiles).filter(File::isDirectory).collect(Collectors.toList());
        if (CollectionUtil.isEmpty(fileList)) {
            Util.msg("信息", "该文件夹下暂无子文件夹信息!");
            return;
        }
        List<MorePhoneMakeTableModel> fileTables = fileList.stream().map(MorePhoneMakeTableModel::new).collect(Collectors.toList());
        if (CollectionUtil.isEmpty(fileTables)) {
            return;
        }
        //装载至表格中
        morePhoneTable.getItems().addAll(fileTables);
    }
}
