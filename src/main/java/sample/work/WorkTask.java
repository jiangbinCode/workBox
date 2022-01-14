package sample.work;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.io.FileUtil;
import javafx.concurrent.Task;
import sample.enums.PictureType;
import sample.model.file_make.PictureModel;
import sample.model.file_make.ProductDataInfo;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @program: workBox
 * @description:
 * @author: bin
 * @create: 2022-01-14 09:57
 **/
public class WorkTask extends Task<String> {

    private final String Q_Z = "适用手机型号:%s";


    @Override
    protected String call() throws Exception {
        Map<String, ProductDataInfo> productDataInfoMap = WorkCache.getProductDataInfoMap();
        String outPath = "E:\\outPath\\测试数据包";
        File rFile = new File(outPath);
        if (!rFile.exists()) rFile.mkdir();
        if (productDataInfoMap.size() <= 0) return null;
        for (Map.Entry<String, ProductDataInfo> entry : productDataInfoMap.entrySet()) {
            cProductFile(rFile, entry.getValue());
        }
        return null;
    }


    private void cProductFile(File rely, ProductDataInfo dataInfo) {
        File pFolder = new File(rely, dataInfo.getProductName());
        if (!pFolder.exists()) pFolder.mkdir();
        cProductImgFile(dataInfo.getMasterImgItems(), PictureType.主图, pFolder);
        cProductImgFile(dataInfo.getDetailsImgItems(), PictureType.详情图, pFolder);
        cProductImgFile(dataInfo.getWhiteImgItems(), PictureType.透明图, pFolder);
        cProductImgFile(dataInfo.getSkuImgItems(), PictureType.选项图, pFolder);
        makeMorePhoneText(dataInfo.getMorePhoneItems(), pFolder);

    }

    private void cProductImgFile(List<PictureModel> pictureModels, PictureType pictureType, File fFile) {
        if (CollectionUtil.isEmpty(pictureModels)) return;
        pictureModels = pictureModels.stream().sorted(Comparator.comparing(PictureModel::getNum)).collect(Collectors.toList());
        File imgFolder = new File(fFile, pictureType.name());
        if (!imgFolder.exists()) imgFolder.mkdir();
        for (PictureModel model : pictureModels) {
            File srcFile = new File(model.getPath());
            File nFile = new File(imgFolder, getImgFileName(model, pictureType) + "." + FileUtil.extName(srcFile));
            FileUtil.copy(srcFile, nFile, true);
        }
    }


    private String getImgFileName(PictureModel model, PictureType pictureType) {
        Integer num = model.getNum();
        switch (pictureType) {
            case 主图:
                return num.toString();
            case 选项图:
                return num + "-" + model.getName();
            case 详情图:
                return "0" + num;
            case 透明图:
                return "透明图";
            default:
                return "";
        }

    }

    private void makeMorePhoneText(List<String> phones, File fFile) {
        if (CollectionUtil.isEmpty(phones)) return;
        File file = new File(fFile, "多属性.txt");
        if (!fFile.exists()) try {
            fFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<String> oPhone = new ArrayList<>();
        for (String phone : phones) oPhone.add(String.format(Q_Z, phone));
        FileUtil.writeLines(oPhone, file, "gbk");

    }

}
