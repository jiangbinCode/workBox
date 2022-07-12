package sample.model.m_p_make;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.io.FileUtil;
import sample.model.file_make.TableCheckBox;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MorePhoneMakeTableModel {

    private TableCheckBox checkBox = new TableCheckBox();

    private String path;

    private String name;

    private String msg;

    private String fileName;

    private List<String> selectPhone = new ArrayList<>();


    public MorePhoneMakeTableModel(File file) {
        String path = file.getPath();
        String[] split = path.split("\\\\");
        String fN = split[split.length - 1];
        this.path = path;
        this.name = fN;
        this.msg = "";
        this.fileName = fN;
    }

    public MorePhoneMakeTableModel() {
    }


    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<String> getSelectPhone() {
        if (CollectionUtil.isEmpty(this.selectPhone)) return Collections.emptyList();
        return selectPhone;
    }

    public void setSelectPhone(List<String> selectPhone) {
        this.selectPhone = selectPhone;
    }

    public void opsMorePhoneItems(String phone, Boolean isSelect) {
        if (isSelect) {
            if (!this.selectPhone.contains(phone)) this.selectPhone.add(phone);
        } else {
            this.selectPhone.remove(phone);
        }

    }
}
