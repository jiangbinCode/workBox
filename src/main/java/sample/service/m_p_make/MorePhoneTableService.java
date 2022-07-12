package sample.service.m_p_make;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import sample.config.MakeConfig;
import sample.config.MorePhoneMakeConfig;
import sample.model.file_make.TableViewUrgeFileTable;
import sample.model.m_p_make.MorePhoneMakeTableModel;
import sample.util.FileUtil;
import sample.util.Util;
import sample.work.m_p_make.M_P_MakeCache;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class MorePhoneTableService {

    private static TableView<MorePhoneMakeTableModel> morePhoneTable = MorePhoneMakeConfig.morePhoneTable;

    private static TableView<MorePhoneMakeTableModel> modelTable = MorePhoneMakeConfig.modelTable;

    private static ChoiceBox masterPhone = MorePhoneMakeConfig.masterPhone;

    private static ChoiceBox moveFolderName = MorePhoneMakeConfig.moveFolderName;


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
        fileTables.forEach(x -> {
            x.getCheckBox().getCheckbox().selectedProperty().addListener((observable, oldValue, newValue) -> {
                MorePhoneMakeConfig.selectNum.setText(getSelectData().size() + "");
            });
        });
        if (CollectionUtil.isEmpty(fileTables)) {
            return;
        }
        //装载至表格中
        morePhoneTable.getItems().addAll(fileTables);
        menuBind();
    }

    private static void menuBind() {
        ContextMenu addMenu = new ContextMenu();
        MenuItem m1 = new MenuItem("淘宝/拼多多不区分孔位图的多型号制作");
        MenuItem m2 = new MenuItem("拼多多区分孔位图的多型号制作");
        m1.setOnAction(p -> {
            MorePhoneMakeTableModel selectedItem = morePhoneTable.getSelectionModel().getSelectedItem();
            M_P_MakeCache.workData = selectedItem;
            MorePhoneMakeConfig.selectPhoneText.setText(selectedItem.getName());
            MorePhoneMakeConfig.selectPhoneNumText.setText(selectedItem.getSelectPhone().size() + "");
        });
        m2.setOnAction(p -> {
            List<MorePhoneMakeTableModel> selectData = getSelectData();
            if (CollectionUtil.isEmpty(selectData) || selectData.size() <= 1) {
                Util.msg("提示", "请至少勾选2个以上的数据再使用该功能");
                return;
            }

            modelTable.getItems().setAll(selectData);

            loadPhoneChoiceBox(selectData);

            List<String> moveFolder = new ArrayList<>();
            for (MorePhoneMakeTableModel datum : selectData) {
                File rootF = new File(datum.getPath());
                if (!rootF.exists()) continue;
                File[] files = rootF.listFiles();
                if (files == null || files.length <= 0) continue;
                List<File> fileList = Arrays.stream(files).filter(File::isDirectory).collect(Collectors.toList());
                if (CollectionUtil.isEmpty(fileList)) continue;
                moveFolder = fileList.stream().map(FileUtil::getFolderName).collect(Collectors.toList());
                break;
            }

            if (CollectionUtil.isEmpty(moveFolder)) {
                Util.msg("提示", "型号数据文件夹中暂无子文件夹信息!");
                return;
            }

            moveFolderName.getItems().setAll(moveFolder);

        });
        addMenu.getItems().addAll(m1, m2);
        morePhoneTable.setContextMenu(addMenu);
    }


    public static void loadPhoneChoiceBox(List<MorePhoneMakeTableModel> models) {
        //设置保留的主型号数据下拉框
        List<String> phoneNames = models.stream().map(MorePhoneMakeTableModel::getName).collect(Collectors.toList());
        masterPhone.getItems().setAll(phoneNames);
        masterPhone.setValue(phoneNames.get(0));
    }


    /**
     * 获取表格中被勾选的数据
     *
     * @return
     */
    public static List<MorePhoneMakeTableModel> getSelectData() {
        ObservableList<MorePhoneMakeTableModel> items = morePhoneTable.getItems();
        List<MorePhoneMakeTableModel> selectData = new ArrayList<>();
        for (MorePhoneMakeTableModel item : items) {
            if (item.getCheckBox().isSelected()) selectData.add(item);
        }
        return selectData;
    }

    //清除表格中已被选择的数据
    public static void clearSelectData() {
        List<MorePhoneMakeTableModel> selectData = getSelectData();
        for (MorePhoneMakeTableModel item : selectData) {
            item.getCheckBox().change(false);
        }
    }

    //全选表格中里面的数据或者全不选
    public static void checkAll(Boolean check) {
        morePhoneTable.getItems().forEach(item -> {
            item.getCheckBox().change(check);
        });
    }

    /**
     * 执行拼多多区分sku图的编辑合并任务
     */
    public static class MorePhoneMakeDivideSKUPicTask extends Task {

        @Override
        protected Object call() throws Exception {
            ObservableList<MorePhoneMakeTableModel> items = modelTable.getItems();
            String folderName = (String) moveFolderName.getValue();
            String main = (String) masterPhone.getValue();
            MorePhoneMakeTableModel mainModel = items.stream().filter(x -> x.getName().equals(main)).findFirst().orElse(null);
            if (mainModel == null) return null;
            File targetFile = new File(mainModel.getPath(), folderName);
            for (MorePhoneMakeTableModel item : items) {
                File moveFile = new File(item.getPath(), folderName);
                File[] moveFileSonF = moveFile.listFiles();
                if (moveFileSonF == null || moveFileSonF.length <= 0) continue;
                List<File> sonFileIsPic = Arrays.stream(moveFileSonF).filter(x -> !x.isDirectory()).collect(Collectors.toList());
                if (CollectionUtil.isEmpty(sonFileIsPic)) continue;
                File newFile = new File(targetFile, item.getName());
                if (newFile.exists()) cn.hutool.core.io.FileUtil.del(newFile);
                newFile.mkdir();
                sonFileIsPic.forEach(x -> cn.hutool.core.io.FileUtil.move(x, newFile, true));
                if (!item.getPath().equals(mainModel.getPath())) {
                    morePhoneTable.getItems().remove(item);
                    cn.hutool.core.io.FileUtil.del(item.getPath());
                    morePhoneTable.refresh();
                }
            }
            mainModel.setMsg("操作完毕,当前合并数量为:" + FileUtil.getRootFileSonFolderNum(targetFile));
            clearSelectData();
            modelTable.getItems().setAll(Collections.emptyList());
            morePhoneTable.refresh();
            return null;
        }
    }
}
