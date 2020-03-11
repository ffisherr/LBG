package com.ffisherr.lbg;

public class EventPesronse {
    private String status;
    private Integer id;
    private String title;
    private String about;
    private String [] tags;
    private String dt;//DateTime YYYY-MM-DD HH:MM

    public EventPesronse(String [] tags) {
        this.id = -1;
        this.title = "";
        this.about = "";
        this.tags = tags;
        this.dt = "";
    }

    public EventPesronse(Integer id, String title, String about, String[] tags, String dt) {
        this.id = id;
        this.title = title;
        this.about = about;
        this.tags = tags;
        this.dt = dt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String[] getTags() {
        return tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }

    public String getDt() {
        return dt;
    }

    public void setDt(String dt) {
        this.dt = dt;
    }
}
