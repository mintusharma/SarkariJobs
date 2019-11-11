package com.geekdroid.sarkarijobs.model;

public class Data {

    private String jobList, linkList;

    public Data() {
    }

    public Data(String jobList, String linkList) {
        this.jobList = jobList;
        this.linkList = linkList;
    }


    public String getJobList() {
        return jobList;
    }

    public String getLinkList() {
        return linkList;
    }
}
