package sample.work.pic_tool;

import cn.hutool.core.collection.CollectionUtil;
import javafx.concurrent.Task;
import sample.model.pic_tool.ImgDataTableModel;
import sample.service.pic_tool.ImgDataTableService;
import sample.util.FileUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @program: workBox
 * @description:
 * @author: bin
 * @create: 2022-02-18 15:26
 **/
public class PicExamineWork extends Task {

    private String rootPath;

    public PicExamineWork(String rootPath) {
        this.rootPath = rootPath;
    }

    @Override
    protected Object call() throws Exception {
        File rootFile = new File(rootPath);
        if (!rootFile.exists()) return null;
        List<File> childrenFile = new ArrayList<>();
        FileUtil.getRootFileSonAll(rootFile, childrenFile);
        if (CollectionUtil.isEmpty(childrenFile)) return null;
        List<ImgDataTableModel> imgDataTableModels = new ArrayList<>();
        for (File file : childrenFile) imgDataTableModels.add(ImgDataTableModel.instantiation(file));
        if (imgDataTableModels.isEmpty()) return "";
        imgDataTableModels = imgDataTableModels.stream().filter(this::firstVerify).collect(Collectors.toList());
        PicCache.addImgCacheData(imgDataTableModels);
        ImgDataTableService.initData(imgDataTableModels);
        return null;
    }


    //图片不能大于 3MB 且必须为jpg和png格式的图片
    //true 表示图片有问题  false没有问题
    public boolean firstVerify(ImgDataTableModel model) {
        if (model == null) return false;
        if (model.getFileSize() > 3072) return true;
        return !"jpg".equalsIgnoreCase(model.getFileType()) && !"png".equalsIgnoreCase(model.getFileType()) && !"jpeg".equalsIgnoreCase(model.getFileType());
    }



}
