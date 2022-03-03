package sample.work.pic_tool;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.img.ImgUtil;
import cn.hutool.core.io.FileUtil;
import javafx.concurrent.Task;
import javafx.scene.control.ProgressBar;
import sample.config.PicToolConfig;
import sample.model.pic_tool.ImgDataTableModel;
import sample.service.pic_tool.ImgDataTableService;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @program: workBox
 * @description:
 * @author: bin
 * @create: 2022-03-02 14:58
 **/
public class PicCache {

    private static List<ImgDataTableModel> imgDataTableModels = new ArrayList<>();


    public static void addImgCacheData(List<ImgDataTableModel> imgDataTableModels) {
        PicCache.imgDataTableModels = imgDataTableModels;
    }

    public static List<ImgDataTableModel> getPicCacheData() {
        return PicCache.imgDataTableModels;
    }


    public static class RepairTask extends Task {
        private List<ImgDataTableModel> imgDataTableModels;

        public RepairTask(List<ImgDataTableModel> imgDataTableModels) {
            this.imgDataTableModels = imgDataTableModels;
        }

        @Override
        protected Object call() throws Exception {
            if (CollectionUtil.isEmpty(this.imgDataTableModels)) return null;
            ProgressBar repairBar = PicToolConfig.repairBar;
            repairBar.setProgress(0);
            int sumSize = this.imgDataTableModels.size();
            Iterator<ImgDataTableModel> iterator = imgDataTableModels.iterator();
            double count = 0;
            while (iterator.hasNext()) {
                ImgDataTableModel tableModel = iterator.next();
                File file = new File(tableModel.getPath());
                if (tableModel.getFileType().equalsIgnoreCase("gif")) {
                    File nFile = new File(tableModel.getPath().replace(".gif", ".png"));
                    ImgUtil.convert(file, nFile);
                    FileUtil.del(file);
                    iterator.remove();
                    ImgDataTableService.initData(imgDataTableModels);
                    double i = ++count / sumSize;
                    repairBar.setProgress(i);
                }
            }
            return null;
        }


    }

}
