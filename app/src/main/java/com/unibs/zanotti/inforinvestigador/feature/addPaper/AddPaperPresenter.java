package com.unibs.zanotti.inforinvestigador.feature.addPaper;

import android.util.Log;
import com.unibs.zanotti.inforinvestigador.baseMVP.BasePresenter;
import com.unibs.zanotti.inforinvestigador.domain.model.Paper;
import com.unibs.zanotti.inforinvestigador.services.retrofit.ICrossrefService;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.HttpException;

import java.net.HttpURLConnection;

public class AddPaperPresenter extends BasePresenter<AddPaperContract.View> implements AddPaperContract.Presenter {
    private static final String TAG = String.valueOf(AddPaperPresenter.class);
    private ICrossrefService crossrefService;
    private Paper displayedPaper;

    public AddPaperPresenter(ICrossrefService crossrefService) {
        this.crossrefService = crossrefService;
    }

    @Override
    public void onPresenterCreated() {

    }

    @Override
    public void onSearchPaperButtonClicked(String doi) {
        getView().showProgressBar();
        disposables.add(crossrefService.getPaper(doi)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<Paper>() {
                    @Override
                    public void onSuccess(Paper paper) {
                        getView().hideProgressBar();
                        getView().showPaper(paper);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, e.getMessage(), e);
                        getView().hideProgressBar();
                        if (e instanceof HttpException) {
                            HttpException exception = (HttpException) e;
                            if (exception.code() == HttpURLConnection.HTTP_NOT_FOUND) {
                                getView().showMessageDoiNotFound();
                            }
                        }
                    }
                }));
    }

    @Override
    public void onCancelButtonClicked() {
        displayedPaper = null;
        getView().hidePaper();
        getView().clearDoiTextfield();
    }
}
