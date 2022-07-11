package sample.model.m_p_make;

import sample.model.file_make.TableCheckBox;

import java.io.File;

public class MorePhoneMakeTableModel {

    private TableCheckBox checkBox = new TableCheckBox();

    private String path;

    private String fName;

    private String fMsg;

    public MorePhoneMakeTableModel(File file) {
        this.path = file.getPath();
        this.fName = file.getName();
        this.fName = "";
    }

    public MorePhoneMakeTableModel() {
    }

    public TableCheckBox getCheckBox() {
        return checkBox;
    }

    public void setCheckBox(TableCheckBox checkBox) {
        this.checkBox = checkBox;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getfMsg() {
        return fMsg;
    }

    public void setfMsg(String fMsg) {
        this.fMsg = fMsg;
    }
}
