package sample.work;

import sample.enums.PictureType;
import sample.model.PictureModel;
import sample.model.ProductDataInfo;
import sample.model.TableViewUrgeFileTable;

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
        switch (pictureType) {
            case 主图:
                List<PictureModel> imgItems = workData.getMasterImgItems();
                if (!imgItems.contains(pictureModel)) imgItems.add(pictureModel);
                break;
            case 详情图:
                List<PictureModel> detailsImgItems = workData.getDetailsImgItems();
                if (!detailsImgItems.contains(pictureModel)) detailsImgItems.add(pictureModel);
                break;
            case 选项图:
                List<PictureModel> skuImgItems = workData.getSkuImgItems();
                if (!skuImgItems.contains(pictureModel)) skuImgItems.add(pictureModel);
                break;
            case 透明图:
                List<PictureModel> whiteImgItems = workData.getWhiteImgItems();
                if (!whiteImgItems.contains(pictureModel)) whiteImgItems.add(pictureModel);
                break;
        }
        return workData;
    }

    public static ProductDataInfo removeImg(TableViewUrgeFileTable vTable, PictureType pictureType) {
        PictureModel pictureModel = PictureModel.instantiation(vTable);
        switch (pictureType) {
            case 主图:
                List<PictureModel> imgItems = workData.getMasterImgItems();
                if (imgItems.contains(pictureModel)) imgItems.remove(pictureModel);
                break;
            case 详情图:
                List<PictureModel> detailsImgItems = workData.getDetailsImgItems();
                if (detailsImgItems.contains(pictureModel)) detailsImgItems.remove(pictureModel);
                break;
            case 选项图:
                List<PictureModel> skuImgItems = workData.getSkuImgItems();
                if (skuImgItems.contains(pictureModel)) skuImgItems.remove(pictureModel);
                break;
            case 透明图:
                List<PictureModel> whiteImgItems = workData.getWhiteImgItems();
                if (whiteImgItems.contains(pictureModel)) whiteImgItems.remove(pictureModel);
                break;
        }
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


}
