package sample.model;

/**
 * @program: workBox
 * @description:
 * @author: bin
 * @create: 2022-01-12 09:49
 **/
public class TreeViewUrgeFileTree {

    private String value;

    private String path;

    private Boolean isCatalogue;


    public TreeViewUrgeFileTree(String fileName, String path, Boolean isCatalogue) {
        this.value = fileName;
        this.path = path;
        this.isCatalogue = isCatalogue;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Boolean getCatalogue() {
        return isCatalogue;
    }

    public void setCatalogue(Boolean catalogue) {
        isCatalogue = catalogue;
    }
}
