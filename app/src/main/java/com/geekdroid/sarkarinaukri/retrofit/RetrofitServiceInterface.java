package com.geekdroid.sarkarinaukri.retrofit;
import com.geekdroid.sarkarinaukri.model.CategoryDataModel;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RetrofitServiceInterface {

    @GET("important_questions/master/dataList.json")
    Call <CategoryDataModel> getCategory();
}
