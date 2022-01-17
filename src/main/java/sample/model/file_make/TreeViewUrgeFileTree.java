package sample.model.file_make;

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

    private Integer hierarchy;


    public TreeViewUrgeFileTree(String fileName, String path, Boolean isCatalogue, Integer hierarchy) {
        this.value = fileName;
        this.path = path;
        this.isCatalogue = isCatalogue;
        this.hierarchy = hierarchy;
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

    public Integer getHierarchy() {
        return hierarchy;
    }

    public void setHierarchy(Integer hierarchy) {
        this.hierarchy = hierarchy;
    }

    public Boolean getCatalogue() {
        return isCatalogue;
    }

    public void setCatalogue(Boolean catalogue) {
        isCatalogue = catalogue;
    }
}
