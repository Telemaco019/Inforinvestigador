package com.unibs.zanotti.inforinvestigador.paperdetail;

/**
 * Listens to user action from the UI (e.g. {@link PaperDetailFragment}), retrieves the data and updates the UI as
 * required.
 */
public class PaperDetailPresenter implements PaperDetailContract.Presenter {
    private final PaperDetailContract.View view;
    private long paperId;

    public PaperDetailPresenter(long paperId, PaperDetailContract.View view) {
        this.view = view;
        this.paperId = paperId;

        view.setPresenter(this);
    }

    @Override
    public void start() {
        openPaper();
    }

    private void openPaper() {

    }
}
