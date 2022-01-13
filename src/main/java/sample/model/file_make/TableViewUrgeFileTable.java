package sample.model.file_make;

import cn.hutool.core.io.FileUtil;
import sample.enums.PictureType;
import sample.work.WorkCache;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: workBox
 * @description:
 * @author: bin
 * @create: 2022-01-12 11:06
 **/
public class TableViewUrgeFileTable {

    private TableCheckBox checkBox = new TableCheckBox();

    private String path;

    private String name;

    private String fileSize;

    private String fileType;

    private List<PictureType> useWay;

    private String pixel;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }


    public String getPixel() {
        return pixel;
    }

    public void setPixel(String pixel) {
        this.pixel = pixel;
    }

    public static TableViewUrgeFileTable instantiation(File file) {
        TableViewUrgeFileTable fileTable = new TableViewUrgeFileTable();
        fileTable.setPath(file.getPath());
        String[] split = file.getName().split("\\.");
        fileTable.setName(split[0]);
        long l = file.length() / 1021;
        fileTable.setFileSize(l + " KB");
        fileTable.setFileType(FileUtil.getType(file));
        if (WorkCache.getWorkData().containImg(fileTable, PictureType.主图)) fileTable.addPicType(PictureType.主图);
        if (WorkCache.getWorkData().containImg(fileTable, PictureType.详情图)) fileTable.addPicType(PictureType.详情图);
        if (WorkCache.getWorkData().containImg(fileTable, PictureType.选项图)) fileTable.addPicType(PictureType.选项图);
        if (WorkCache.getWorkData().containImg(fileTable, PictureType.透明图)) fileTable.addPicType(PictureType.透明图);
        try {
            BufferedImage read = ImageIO.read(file);
            fileTable.setPixel(read.getWidth() + " x " + read.getHeight());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileTable;
    }

    public List<PictureType> getUseWay() {
        return useWay;
    }

    public void setUseWay(List<PictureType> useWay) {
        this.useWay = useWay;
    }

    public TableCheckBox getCheckBox() {
        return checkBox;
    }

    public void setCheckBox(TableCheckBox checkBox) {
        this.checkBox = checkBox;
    }

    public void addPicType(PictureType pictureType) {
        List<PictureType> useWay = this.useWay;
        if (useWay == null) useWay = new ArrayList<>();
        if (!useWay.contains(pictureType)) {
            useWay.add(pictureType);
        }
        this.useWay = useWay;
    }

    public void removePicType(PictureType pictureType) {
        List<PictureType> useWay = this.useWay;
        if (useWay == null) useWay = new ArrayList<>();
        useWay.remove(pictureType);
        this.useWay = useWay;
    }

}
