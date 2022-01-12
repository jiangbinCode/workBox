package sample.model;

import cn.hutool.core.io.FileUtil;

import java.io.File;

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

    private Float fileSize;

    private String fileType;


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

    public Float getFileSize() {
        return fileSize;
    }

    public void setFileSize(Float fileSize) {
        this.fileSize = fileSize;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }


    public static TableViewUrgeFileTable instantiation(File file) {
        TableViewUrgeFileTable fileTable = new TableViewUrgeFileTable();
        fileTable.setPath(file.getPath());
        fileTable.setName(file.getName());
        fileTable.setFileSize((float) file.length());
        fileTable.setFileType(FileUtil.getType(file));
        return fileTable;
    }

    public TableCheckBox getCheckBox() {
        return checkBox;
    }

    public void setCheckBox(TableCheckBox checkBox) {
        this.checkBox = checkBox;
    }
}
