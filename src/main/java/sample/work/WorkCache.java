package sample.work;

import cn.hutool.core.collection.CollectionUtil;
import sample.enums.PictureType;
import sample.model.file_make.PictureModel;
import sample.model.file_make.ProductDataInfo;
import sample.model.file_make.TableViewUrgeFileTable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("all")
public class WorkCache {
    private static final Map<String, ProductDataInfo> productDataInfoMap = new HashMap<>();

    private static ProductDataInfo workData;


    public static ProductDataInfo loadWordData(String path, String name) {
        if (productDataInfoMap.containsKey(path)) {
            workData = productDataInfoMap.get(path);
            return workData;
        }
        workData = new ProductDataInfo(path, name);
        productDataInfoMap.put(path, workData);
        return workData;
    }


    public static ProductDataInfo getWorkData() {
        return workData;
    }

}
