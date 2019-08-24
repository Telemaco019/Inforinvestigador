package com.unibs.zanotti.inforinvestigador.baseMVP;

import android.os.Bundle;
import android.view.View;
import androidx.annotation.CallSuper;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import org.jetbrains.annotations.Nullable;

public abstract class BaseFragment<V extends BaseContract.View, P extends BaseContract.Presenter<V>>
        extends Fragment implements BaseContract.View {

    protected P presenter;

    @SuppressWarnings("unchecked")
    @CallSuper
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        BaseViewModel<V, P> viewModel = ViewModelProviders.of(this).get(BaseViewModel.class);
        boolean isPresenterCreated = false;
        if (viewModel.getPresenter() == null) {
            viewModel.setPresenter(createPresenter());
            isPresenterCreated = true;
        }
        presenter = viewModel.getPresenter();
        presenter.attachLifecycle(this.getLifecycle());
        presenter.attachView((V) this);
        if (isPresenterCreated) {
            presenter.onPresenterCreated();
        }
    }

    @CallSuper
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.detachLifecycle(getLifecycle());
        presenter.detachView();
    }

    protected abstract P createPresenter();
}
