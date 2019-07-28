package com.unibs.zanotti.inforinvestigador.services.retrofit;

import com.unibs.zanotti.inforinvestigador.domain.model.crossref.Example;
import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ICrossrefController {
    @GET("/works/{doi}")
    Single<Example> getPaper(@Path("doi") String doi);
}
