package sample.work;

import cn.hutool.core.bean.BeanUtil;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.scene.control.TreeItem;
import sample.config.MakeConfig;
import sample.model.file_make.PictureModel;
import sample.model.file_make.ProductDataInfo;
import sample.model.file_make.TreeViewUrgeFileTree;
import sample.util.FileUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @program: workBox
 * @description: 同源构造的执行方法
 * @author: bin
 * @create: 2022-01-17 17:51
 **/
public class IsogenyCreateTask extends Task {

    private ProductDataInfo workData = WorkCache.getWorkData();


    @Override
    protected Object call() throws Exception {
        TreeItem<TreeViewUrgeFileTree> root = MakeConfig.fileTree.getRoot();
        ObservableList<TreeItem<TreeViewUrgeFileTree>> children = root.getChildren();
        WorkCache.getProductDataInfoMap().clear();
        WorkCache.addMap(workData.getEditPath(), workData);
        for (TreeItem<TreeViewUrgeFileTree> child : children) {
            TreeViewUrgeFileTree value = child.getValue();
            if (WorkCache.getProductDataInfoMap().containsKey(value.getPath())) continue;
            reprint(value.getValue(), value.getPath());
            if (WorkCache.getProductDataInfoMap().containsKey(value.getPath())) {
                value.setShowVal(new File(value.getPath()).getName() + " [同源构建成功]");
            } else {
                value.setShowVal(new File(value.getPath()).getName() + " [同源构建失败]");
            }
            MakeConfig.fileTree.refresh();
        }
        return null;
    }


    public void reprint(String name, String path) {
        File file = new File(path);
        if (!file.isDirectory()) return;
        ProductDataInfo currData = new ProductDataInfo();
        List<File> sonAll = new ArrayList<>();
        FileUtil.getRootFileSonAll(file, sonAll);
        portray(workData.getMasterImgItems(), currData.getMasterImgItems(), sonAll);
        portray(workData.getDetailsImgItems(), currData.getDetailsImgItems(), sonAll);
        portray(workData.getSkuImgItems(), currData.getSkuImgItems(), sonAll);
        portray(workData.getWhiteImgItems(), currData.getWhiteImgItems(), sonAll);
        currData.setProductName(name);
        currData.setEditPath(path);
        if (currData.isUsable()) WorkCache.addMap(path, currData);
    }


    public void portray(List<PictureModel> src, List<PictureModel> target, List<File> sonAll) {
        for (PictureModel pic : src) {
            File pFile = new File(pic.getPath());
            File cFile;
            List<File> files = sonAll.stream().filter(x -> x.getName().equals(pFile.getName())).collect(Collectors.toList());
            if (files.isEmpty()) continue;
            if (files.size() > 1) {
                cFile = files.stream().filter(x -> x.getParentFile().getName().equals(pFile.getParentFile().getName())).findFirst().orElse(null);
            } else {
                cFile = files.get(0);
            }
            if (cFile == null) continue;
            PictureModel currPic = new PictureModel();
            BeanUtil.copyProperties(pic, currPic);
            currPic.setPath(cFile.getPath());
            target.add(currPic);
        }
    }
}
