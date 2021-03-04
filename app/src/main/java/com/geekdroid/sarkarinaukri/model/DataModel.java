package com.geekdroid.sarkarinaukri.model;

public class DataModel {

    private String jobList, linkList, lastDate;

    public DataModel() {
    }

    public DataModel(String jobList, String linkList,String lastDate) {
        this.jobList = jobList;
        this.linkList = linkList;
        this.lastDate=lastDate;
    }

    public String getLastDate() {
        return lastDate;
    }

    public String getJobList() {
        return jobList;
    }

    public String getLinkList() {
        return linkList;
    }
}
