package com.unibs.zanotti.inforinvestigador.feature.addPaper;

import android.util.Log;
import com.unibs.zanotti.inforinvestigador.baseMVP.BasePresenter;
import com.unibs.zanotti.inforinvestigador.data.IPaperRepository;
import com.unibs.zanotti.inforinvestigador.data.IUserRepository;
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
    private IPaperRepository paperRepository;
    private IUserRepository userRepository;

    private Paper paperToShare;

    public AddPaperPresenter(ICrossrefService crossrefService, IPaperRepository paperRepository, IUserRepository userRepository) {
        this.crossrefService = crossrefService;
        this.paperRepository = paperRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void onPresenterCreated() {
        // NO OP
    }

    @Override
    public void searchPaperButtonClicked(String doi) {
        getView().showProgressBar();
        disposables.add(crossrefService.getPaper(doi)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<Paper>() {
                    @Override
                    public void onSuccess(Paper paper) {
                        paperToShare = paper;
                        getView().hideProgressBar();
                        showPaperToShare();
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
                        } else {
                            getView().showSearchPaperError();
                        }
                    }
                }));
    }

    @Override
    public void cancelButtonClicked() {
        paperToShare = null;
        getView().hidePaper();
        getView().clearDoiTextfield();
        getView().clearCommentTextfield();
    }

    @Override
    public void submitButtonClicked(String userComment) {
        if (paperToShare != null) {
            String currentUserId = userRepository.getCurrentUserId();
            getView().showProgressBar();
            paperToShare.setSharingUserComment(userComment);
            paperToShare.setSharingUserId(currentUserId);
            disposables.add(paperRepository.savePaper(paperToShare, currentUserId).subscribeWith(new DisposableSingleObserver<Paper>() {
                @Override
                public void onSuccess(Paper paper) {
                    getView().hideProgressBar();
                    getView().hidePaper();
                    getView().clearDoiTextfield();
                    getView().clearCommentTextfield();
                    getView().showSavePaperSuccessMessage();
                }

                @Override
                public void onError(Throwable e) {
                    Log.d(TAG, e.getMessage(), e);
                    getView().hideProgressBar();
                    getView().showSavePaperErrorMessage();
                }
            }));
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        showPaperToShare();
    }

    private void showPaperToShare() {
        if (paperToShare != null) {
            getView().showPaper(paperToShare);
        }
    }
}
