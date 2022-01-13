package sample.service.file_make;

import cn.hutool.core.collection.CollectionUtil;
import javafx.scene.control.*;
import sample.model.TreeViewUrgeFileTree;
import sample.util.FileUtil;
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
                new TreeViewUrgeFileTree(rootFile.getName(), rootFile.getPath(), true));
        rootThreeItem.setExpanded(true);
        rootThreeItem = FileUtil.listFileDic(rootFile, rootThreeItem);
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
                if (CollectionUtil.isEmpty(selectItem.getChildren())) {
                    Util.msg("信息", "该文件夹下暂无图片信息!");
                } else {
                    WorkCache.loadWordData(selectItem.getValue().getPath(), selectItem.getValue().getValue());
                    FileTableViewInitService.loadData(new File((selectItem.getValue()).getPath()));
                }
            });
            addMenu.getItems().add(loadFiles);
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
            if (item.getCatalogue()) {
                setContextMenu(addMenu);
            } else {
                setContextMenu(null);
            }
            setGraphic(getTreeItem().getGraphic());
        }

    }

}
