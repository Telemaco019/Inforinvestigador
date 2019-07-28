package com.unibs.zanotti.inforinvestigador.services.retrofit;

import com.unibs.zanotti.inforinvestigador.domain.model.Paper;
import io.reactivex.Single;

public interface ICrossrefService {
    Single<Paper> getPaper(String doi);
}
