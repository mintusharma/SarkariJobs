package com.geekdroid.sarkarinaukri.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CategoryDataModel {

    @SerializedName("categoryData")
    @Expose
    private List<CategoryData> categoryData = null;

    public List<CategoryData> getCategoryData() {
        return categoryData;
    }
    public class CategoryData {

        @SerializedName("title")
        @Expose
        private String title;
        @SerializedName("url")
        @Expose
        private String url;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
