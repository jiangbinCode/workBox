package sample.service.file_make;

import cn.hutool.core.collection.CollectionUtil;
import javafx.concurrent.Task;
import javafx.scene.control.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sample.Main;
import sample.config.MakeConfig;
import sample.model.file_make.TreeViewUrgeFileTree;
import sample.util.FileUtil;
import sample.util.ProgressStage;
import sample.util.Util;
import sample.work.file_make.WorkCache;

import java.io.File;

/**
 * @program: workBox
 * @description:
 * @author: bin
 * @create: 2022-01-12 09:55
 **/

public class FileTreeViewInitService {

    private static Logger logger = LoggerFactory.getLogger(FileTableViewInitService.class);

    private static TreeView<TreeViewUrgeFileTree> treeView;


    public static void initTree(final File rootFile, TreeView<TreeViewUrgeFileTree> treeView) {
        logger.info("初始化路径:{}", rootFile.getPath());
        FileTreeViewInitService.treeView = treeView;
        //根节点 也就是选择的文件夹
        TreeItem<TreeViewUrgeFileTree> rootThreeItem = new TreeItem(
                new TreeViewUrgeFileTree(rootFile.getName(), rootFile.getPath(), true, 0));
        rootThreeItem.setExpanded(true);
        //查找该文件夹下所有的文件
        rootThreeItem = FileUtil.listFileDic(rootFile, rootThreeItem, 0);
        //设置数据
        treeView.setRoot(rootThreeItem);
        //节点细胞自定义的加载工厂
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
                    //开始加载中心表格里面的数据信息
                    ProgressStage.of(Main.getPrimaryStage(), new FileImgLoad(selectItem), "加载中,请稍后...").show();
                    SelectImgTableInitService.loadData();
                    MakeConfig.productName.setText(WorkCache.getWorkData().getProductName());
                    MakeConfig.selectNum.setText(WorkCache.getWorkData().getMorePhoneItems().size() + "");
                }
            });

            addMenu.getItems().addAll(loadFiles);
        }

        @Override
        protected void updateItem(TreeViewUrgeFileTree item, boolean empty) {
            super.updateItem(item, empty);
            if (empty) {
                setText(null);
                setGraphic(null);
                return;
            }
            setText(item.getShowVal());
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
            //加载当前正在操作路径的执行信息
            WorkCache.loadWordData(selectItem.getValue().getPath(), new File(selectItem.getValue().getPath()).getName());
            //加载表格数据
            FileTableViewInitService.loadData(new File((selectItem.getValue()).getPath()));
            return null;
        }
    }

}
