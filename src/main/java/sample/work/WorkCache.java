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

    public static ProductDataInfo addImg(TableViewUrgeFileTable vTable, PictureType pictureType) {
        PictureModel pictureModel = PictureModel.instantiation(vTable);
        List<PictureModel> pics = new ArrayList<>();
        switch (pictureType) {
            case 主图:
                pics = workData.getMasterImgItems();
                if (!pics.contains(pictureModel)) pics.add(pictureModel);
                break;
            case 详情图:
                pics = workData.getDetailsImgItems();
                if (!pics.contains(pictureModel)) pics.add(pictureModel);
                break;
            case 选项图:
                pics = workData.getSkuImgItems();
                if (!pics.contains(pictureModel)) pics.add(pictureModel);
                break;
            case 透明图:
                pics = workData.getWhiteImgItems();
                if (!pics.contains(pictureModel)) pics.add(pictureModel);
                break;
        }
        autoAdjustNum(pics);
        return workData;
    }

    public static ProductDataInfo removeImg(TableViewUrgeFileTable vTable, PictureType pictureType) {
        PictureModel pictureModel = PictureModel.instantiation(vTable);
        List<PictureModel> pics = new ArrayList<>();
        switch (pictureType) {
            case 主图:
                pics = workData.getMasterImgItems();
                if (pics.contains(pictureModel)) pics.remove(pictureModel);
                break;
            case 详情图:
                pics = workData.getDetailsImgItems();
                if (pics.contains(pictureModel)) pics.remove(pictureModel);
                break;
            case 选项图:
                pics = workData.getSkuImgItems();
                if (pics.contains(pictureModel)) pics.remove(pictureModel);
                break;
            case 透明图:
                pics = workData.getWhiteImgItems();
                if (pics.contains(pictureModel)) pics.remove(pictureModel);
                break;
        }
        autoAdjustNum(pics);
        return workData;
    }

    public static boolean containImg(TableViewUrgeFileTable vTable, PictureType pictureType) {
        PictureModel pictureModel = PictureModel.instantiation(vTable);
        switch (pictureType) {
            case 主图:
                List<PictureModel> imgItems = workData.getMasterImgItems();
                return imgItems.contains(pictureModel);
            case 详情图:
                List<PictureModel> detailsImgItems = workData.getDetailsImgItems();
                return detailsImgItems.contains(pictureModel);
            case 选项图:
                List<PictureModel> skuImgItems = workData.getSkuImgItems();
                return skuImgItems.contains(pictureModel);
            case 透明图:
                List<PictureModel> whiteImgItems = workData.getWhiteImgItems();
                return whiteImgItems.contains(pictureModel);
        }
        return false;
    }

    public static ProductDataInfo updataMorePhone() {
        return workData;
    }


    public static void autoAdjustNum(List<PictureModel> pictureModels) {
        if (CollectionUtil.isEmpty(pictureModels)) return;
        for (PictureModel pictureModel : pictureModels) {
            int i = pictureModels.indexOf(pictureModel);
            pictureModel.setNum(i + 1);
        }
    }


}
