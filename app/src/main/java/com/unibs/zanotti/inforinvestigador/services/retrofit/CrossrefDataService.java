package com.unibs.zanotti.inforinvestigador.services.retrofit;

import com.unibs.zanotti.inforinvestigador.domain.model.crossref.Example;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface CrossrefDataService {
    @GET("/works/{doi}")
    Call<Example> getPaper(@Path("doi") String doi);
}
