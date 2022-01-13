package sample.model.file_make;

import java.util.Objects;

public class PictureModel {

    private String path;

    private String name;

    private Integer num;

    private String rawName;

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

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getRawName() {
        return rawName;
    }

    public void setRawName(String rawName) {
        this.rawName = rawName;
    }

    public static PictureModel instantiation(TableViewUrgeFileTable vTable) {
        PictureModel model = new PictureModel();
        model.setPath(vTable.getPath());
        model.setName(vTable.getName());
        model.setRawName(null);
        return model;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PictureModel model = (PictureModel) o;
        return Objects.equals(path, model.path) && Objects.equals(name, model.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(path, name);
    }
}
