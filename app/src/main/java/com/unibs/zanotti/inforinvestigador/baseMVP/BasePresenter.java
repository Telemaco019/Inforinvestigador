package com.unibs.zanotti.inforinvestigador.baseMVP;



import android.os.Bundle;
import androidx.annotation.CallSuper;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import io.reactivex.disposables.CompositeDisposable;

public abstract class BasePresenter<V extends BaseContract.View> implements LifecycleObserver, BaseContract.Presenter<V> {

    private Bundle stateBundle;
    private V mView;
    protected CompositeDisposable disposables = new CompositeDisposable();

    @Override
    final public V getView() {
        return mView;
    }

    @Override
    final public void attachLifecycle(Lifecycle lifecycle) {
        lifecycle.addObserver(this);
    }

    @Override
    final public void detachLifecycle(Lifecycle lifecycle) {
        lifecycle.removeObserver(this);
    }

    @Override
    final public void attachView(V view) {
        this.mView = view;
    }

    @Override
    final public void detachView() {
        mView = null;
    }

    @Override
    final public boolean isViewAttached() {
        return mView != null;
    }

    @Override
    final public Bundle getStateBundle() {
        return stateBundle == null
                ? stateBundle = new Bundle()
                : stateBundle;
    }

    @CallSuper
    @Override
    public void onPresenterDestroy() {
        if (stateBundle != null && !stateBundle.isEmpty()) {
            stateBundle.clear();
        }
    }

    @Override
    public void onStop() {
        disposables.dispose();
    }
}
