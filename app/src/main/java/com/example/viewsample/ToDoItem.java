package com.example.viewsample;

public class ToDoItem {
    private long id;
    private String name;
    private String detail;
    private String timeStamp;
    private String isStarred;


    /*
     * getter と setter
     */
    public long getId() { return id; }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public  String getTimeStamp() { return timeStamp; }

    public void setTimeStamp(String timeStamp) { this.timeStamp = timeStamp; }

    public String getIsStarred() { return isStarred; }

    // true: "1", false: "0"
    public void setIsStarred(String isStarred) { this.isStarred = isStarred; }
}
