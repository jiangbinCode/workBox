package sample.model.file_make;

import cn.hutool.core.collection.CollectionUtil;
import sample.enums.PictureType;

import java.util.ArrayList;
import java.util.List;

public class ProductDataInfo {

    private String editPath;

    private String productName;

    private List<PictureModel> masterImgItems = new ArrayList<>();

    private List<PictureModel> skuImgItems = new ArrayList<>();

    private List<PictureModel> detailsImgItems = new ArrayList<>();

    private List<PictureModel> whiteImgItems = new ArrayList<>();

    private List<String> morePhoneItems = new ArrayList<>();

    public ProductDataInfo() {
    }

    public ProductDataInfo(String editPath, String productName) {
        this.editPath = editPath;
        this.productName = productName;

    }


    public ProductDataInfo addImg(TableViewUrgeFileTable vTable, PictureType pictureType) {
        PictureModel pictureModel = PictureModel.instantiation(vTable);
        List<PictureModel> pics = new ArrayList<>();
        switch (pictureType) {
            case 主图:
                pics = this.getMasterImgItems();
                if (!pics.contains(pictureModel)) pics.add(pictureModel);
                break;
            case 详情图:
                pics = this.getDetailsImgItems();
                if (!pics.contains(pictureModel)) pics.add(pictureModel);
                break;
            case 选项图:
                pics = this.getSkuImgItems();
                if (!pics.contains(pictureModel)) pics.add(pictureModel);
                break;
            case 透明图:
                pics = this.getWhiteImgItems();
                if (!pics.contains(pictureModel)) pics.add(pictureModel);
                break;
        }
        autoAdjustNum(pics);
        return this;
    }

    public ProductDataInfo removeImg(TableViewUrgeFileTable vTable, PictureType pictureType) {
        PictureModel pictureModel = PictureModel.instantiation(vTable);
        List<PictureModel> pics = new ArrayList<>();
        switch (pictureType) {
            case 主图:
                pics = this.getMasterImgItems();
                if (pics.contains(pictureModel)) pics.remove(pictureModel);
                break;
            case 详情图:
                pics = this.getDetailsImgItems();
                if (pics.contains(pictureModel)) pics.remove(pictureModel);
                break;
            case 选项图:
                pics = this.getSkuImgItems();
                if (pics.contains(pictureModel)) pics.remove(pictureModel);
                break;
            case 透明图:
                pics = this.getWhiteImgItems();
                if (pics.contains(pictureModel)) pics.remove(pictureModel);
                break;
        }
        autoAdjustNum(pics);
        return this;
    }

    public boolean containImg(TableViewUrgeFileTable vTable, PictureType pictureType) {
        PictureModel pictureModel = PictureModel.instantiation(vTable);
        switch (pictureType) {
            case 主图:
                List<PictureModel> imgItems = this.getMasterImgItems();
                return imgItems.contains(pictureModel);
            case 详情图:
                List<PictureModel> detailsImgItems = this.getDetailsImgItems();
                return detailsImgItems.contains(pictureModel);
            case 选项图:
                List<PictureModel> skuImgItems = this.getSkuImgItems();
                return skuImgItems.contains(pictureModel);
            case 透明图:
                List<PictureModel> whiteImgItems = this.getWhiteImgItems();
                return whiteImgItems.contains(pictureModel);
        }
        return false;
    }

    public boolean updateNun(PictureModel pModel, Integer tagNum, PictureType pictureType) {
        List<PictureModel> pics = new ArrayList<>();
        switch (pictureType) {
            case 主图:
                pics = this.getMasterImgItems();
                break;
            case 详情图:
                pics = this.getDetailsImgItems();
                break;
            case 选项图:
                pics = this.getSkuImgItems();
                break;
            case 透明图:
                pics = this.getWhiteImgItems();
                break;
        }
        tagNum = tagNum - 1;
        PictureModel model = pics.get(tagNum);
        pics.set(tagNum, pModel);
        pics.set(pModel.getNum() - 1, model);
        autoAdjustNum(pics);
        return false;
    }


    public ProductDataInfo updateMorePhone() {
        return this;
    }


    private void autoAdjustNum(List<PictureModel> pictureModels) {
        if (CollectionUtil.isEmpty(pictureModels)) return;
        for (PictureModel pictureModel : pictureModels) {
            int i = pictureModels.indexOf(pictureModel);
            pictureModel.setNum(i + 1);
        }
    }


    public void opsMorePhoneItems(String phone, Boolean flag) {
        if (flag) {
            if (!this.morePhoneItems.contains(phone)) this.morePhoneItems.add(phone);
        } else {
            this.morePhoneItems.remove(phone);
        }

    }

    public String getEditPath() {
        return editPath;
    }

    public void setEditPath(String editPath) {
        this.editPath = editPath;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public List<PictureModel> getMasterImgItems() {
        return masterImgItems;
    }

    public void setMasterImgItems(List<PictureModel> masterImgItems) {
        this.masterImgItems = masterImgItems;
    }

    public List<PictureModel> getSkuImgItems() {
        return skuImgItems;
    }

    public void setSkuImgItems(List<PictureModel> skuImgItems) {
        this.skuImgItems = skuImgItems;
    }

    public List<PictureModel> getDetailsImgItems() {
        return detailsImgItems;
    }

    public void setDetailsImgItems(List<PictureModel> detailsImgItems) {
        this.detailsImgItems = detailsImgItems;
    }

    public List<PictureModel> getWhiteImgItems() {
        return whiteImgItems;
    }

    public void setWhiteImgItems(List<PictureModel> whiteImgItems) {
        this.whiteImgItems = whiteImgItems;
    }

    public List<String> getMorePhoneItems() {
        return morePhoneItems;
    }

    public void setMorePhoneItems(List<String> morePhoneItems) {
        this.morePhoneItems = morePhoneItems;
    }
}
