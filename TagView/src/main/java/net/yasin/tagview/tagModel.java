package net.yasin.tagview;

public class tagModel {

    private int id;
    private String title;
    private Boolean select;

    public tagModel(int id, String title, Boolean select) {
        this.id = id;
        this.title = title;
        this.select = select;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Boolean getSelect() {
        return select;
    }

    public void setSelect(Boolean select) {
        this.select = select;
    }
}
