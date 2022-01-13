package sample.model.file_make;

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
