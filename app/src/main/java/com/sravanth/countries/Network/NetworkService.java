package com.sravanth.countries.Network;


import com.sravanth.countries.database.entities.Country;


import java.util.ArrayList;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Url;


/**
 * Rest implementation for Services
 */

public interface NetworkService {

    @GET("name/{name}")
    Observable<ArrayList<Country>> getCountriesByName(@Path("name") String name);

    @GET
    Observable<ResponseBody> downloadFlagWithDynamicUrlSync(@Url String fileUrl);

}
