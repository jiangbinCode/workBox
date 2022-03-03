package sample.model.pic_tool;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.EnumUtil;
import sample.enums.PictureType;
import sample.model.file_make.TableCheckBox;
import sample.model.file_make.TableViewUrgeFileTable;
import sample.util.PictureInspect;
import sample.work.file_make.WorkCache;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @program: workBox
 * @description:
 * @author: bin
 * @create: 2022-02-18 15:13
 **/
public class ImgDataTableModel {

    private String path;

    private String name;

    private Long fileSize;

    private String fileType;

    private PictureType type;

    private Integer width;

    private Integer height;

    public static ImgDataTableModel instantiation(File file) {
        ImgDataTableModel imgData = new ImgDataTableModel();
        imgData.setType(getPicType(file));
        imgData.setPath(file.getPath());
        String[] split = file.getName().split("\\.");
        imgData.setName(split[0]);
        long l = file.length() / 1021;
        imgData.setFileSize(l);
        imgData.setFileType(PictureInspect.getMimeType(file.getPath()));
        try {
            BufferedImage read = ImageIO.read(file);
            imgData.setWidth(read.getWidth());
            imgData.setHeight(read.getHeight());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imgData;
    }

    public static PictureType getPicType(File file) {
        File parentFile = file.getParentFile();
        if (parentFile == null) return PictureType.未知;
        String name = parentFile.getName();
        try {
            return PictureType.valueOf(name.trim());
        } catch (Exception e) {
            return getPicType(file.getParentFile());
        }
    }

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

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public PictureType getType() {
        return type;
    }

    public void setType(PictureType type) {
        this.type = type;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }
}
