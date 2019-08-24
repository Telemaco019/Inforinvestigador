package com.unibs.zanotti.inforinvestigador.feature.editPaper;

import com.unibs.zanotti.inforinvestigador.baseMVP.BasePresenter;
import com.unibs.zanotti.inforinvestigador.data.IPaperRepository;
import com.unibs.zanotti.inforinvestigador.domain.model.Paper;
import com.unibs.zanotti.inforinvestigador.utils.FirebaseUtils;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.observers.DisposableMaybeObserver;
import io.reactivex.schedulers.Schedulers;

public class EditPaperSharePresenter
        extends BasePresenter<EditPaperShareContract.View>
        implements EditPaperShareContract.Presenter {

    private Paper loadedPaper;
    private IPaperRepository paperRepository;
    private String paperId;

    public EditPaperSharePresenter(IPaperRepository paperRepository, String paperId) {
        this.paperRepository = paperRepository;
        this.paperId = paperId;
    }

    @Override
    public void onStart() {
        super.onStart();
        showLoadedPaper();
    }

    @Override
    public void onPresenterCreated() {
        loadPaper();
    }

    private void loadPaper() {
        getView().showProgressBar();
        disposables.add(paperRepository.getPaper(paperId).subscribeWith(new DisposableMaybeObserver<Paper>() {
            @Override
            public void onSuccess(Paper paper) {
                loadedPaper = paper;
                getView().hideProgressBar();
                showLoadedPaper();
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {
                getView().hideProgressBar();
            }
        }));
    }


    private void showLoadedPaper() {
        if (loadedPaper != null) {
            getView().showPaper(loadedPaper);
        }
    }

    @Override
    public void saveButtonClicked(String sharingUserComment) {
        getView().showProgressBar();
        disposables.add(paperRepository.updatePaperField(paperId, FirebaseUtils.FIRESTORE_DOCUMENT_PAPER_SHARING_USER_COMMENT, sharingUserComment)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableCompletableObserver() {
            @Override
            public void onComplete() {
                getView().hideProgressBar();
                getView().finishActivityWithResultOk();
            }

            @Override
            public void onError(Throwable e) {
                getView().hideProgressBar();
                getView().showSavePaperError();
            }
        }));
    }
}
