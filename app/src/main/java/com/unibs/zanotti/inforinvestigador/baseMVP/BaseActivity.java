package com.unibs.zanotti.inforinvestigador.baseMVP;

import android.os.Bundle;
import androidx.annotation.CallSuper;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

public abstract class BaseActivity<V extends BaseContract.View, P extends BaseContract.Presenter<V>>
        extends AppCompatActivity implements BaseContract.View  {
    protected P presenter;

    @SuppressWarnings("unchecked")
    @CallSuper
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
    public void onDestroy() {
        super.onDestroy();
        presenter.detachLifecycle(getLifecycle());
        presenter.detachView();
    }

    protected abstract P createPresenter();
}
