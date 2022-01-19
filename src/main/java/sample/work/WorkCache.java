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

    /**
     * 存放已经被编辑过的路径数据
     * key 目录路径
     * val 产品编辑的数据
     */
    private static final Map<String, ProductDataInfo> productDataInfoMap = new HashMap<>();

    /**
     * 当前正在编辑的路径文件数据
     */
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

    public static Map<String, ProductDataInfo> getProductDataInfoMap() {
        return productDataInfoMap;
    }

    public static boolean isWord() {
        return null != workData;
    }

    public static void addMap(String key, ProductDataInfo data) {
        productDataInfoMap.put(key, data);
    }
}
