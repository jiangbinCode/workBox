package sample.service.file_make;

import cn.hutool.core.collection.CollectionUtil;
import javafx.concurrent.Task;
import javafx.scene.control.*;
import sample.Main;
import sample.config.MakeConfig;
import sample.model.file_make.TreeViewUrgeFileTree;
import sample.util.FileUtil;
import sample.util.ProgressStage;
import sample.util.Util;
import sample.work.WorkCache;

import java.io.File;

/**
 * @program: workBox
 * @description:
 * @author: bin
 * @create: 2022-01-12 09:55
 **/

public class FileTreeViewInitService {

    private static TreeView<TreeViewUrgeFileTree> treeView;


    public static void initTree(final File rootFile, TreeView<TreeViewUrgeFileTree> treeView) {
        FileTreeViewInitService.treeView = treeView;
        TreeItem<TreeViewUrgeFileTree> rootThreeItem = new TreeItem(
                new TreeViewUrgeFileTree(rootFile.getName(), rootFile.getPath(), true, 0));
        rootThreeItem.setExpanded(true);
        rootThreeItem = FileUtil.listFileDic(rootFile, rootThreeItem, 0);
        treeView.setRoot(rootThreeItem);
        treeView.setCellFactory((TreeView<TreeViewUrgeFileTree> p) ->
                new TextFieldTreeCellImpl());
    }


    public static class TextFieldTreeCellImpl extends TreeCell<TreeViewUrgeFileTree> {

        // 添加菜单
        private final ContextMenu addMenu = new ContextMenu();


        TextFieldTreeCellImpl() {
            MenuItem loadFiles = new MenuItem("加载所有图片");
            loadFiles.setOnAction(e -> {
                TreeItem<TreeViewUrgeFileTree> selectItem = treeView.getSelectionModel().getSelectedItem();
                TreeViewUrgeFileTree itemValue = selectItem.getValue();
                if (CollectionUtil.isEmpty(selectItem.getChildren())) {
                    Util.msg("信息", "该文件夹下暂无图片信息!");
                } else {
                    ProgressStage.of(Main.getPrimaryStage(), new FileImgLoad(selectItem), "加载中,请稍后...").show();
                    SelectImgTableInitService.loadData();
                    MakeConfig.productName.setText(itemValue.getValue());
                }
            });
            MenuItem isogenyCreate = new MenuItem("同源生成该文件夹下的数据");

            isogenyCreate.setOnAction(e -> {
                TreeItem<TreeViewUrgeFileTree> selectItem = treeView.getSelectionModel().getSelectedItem();


            });
            addMenu.getItems().addAll(loadFiles, isogenyCreate);
        }

        @Override
        protected void updateItem(TreeViewUrgeFileTree item, boolean empty) {
            super.updateItem(item, empty);
            if (empty) {
                setText(null);
                setGraphic(null);
                return;
            }
            setText(item.getValue());
            if (item.getCatalogue() && item.getHierarchy() == 1) {
                setContextMenu(addMenu);
            } else {
                setContextMenu(null);
            }
            setGraphic(getTreeItem().getGraphic());
        }

    }

    public static class FileImgLoad extends Task {

        private TreeItem<TreeViewUrgeFileTree> selectItem;

        FileImgLoad(TreeItem<TreeViewUrgeFileTree> selectItem) {
            this.selectItem = selectItem;
        }

        @Override
        protected Object call() throws Exception {
            WorkCache.loadWordData(selectItem.getValue().getPath(), selectItem.getValue().getValue());
            FileTableViewInitService.loadData(new File((selectItem.getValue()).getPath()));
            return null;
        }
    }

}
