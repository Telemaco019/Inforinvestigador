package com.unibs.zanotti.inforinvestigador.utils;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class ActivityUtils {
    public static final int FADE_ANIMATION_STANDARD_DURATION_MS = 300;

    /**
     * The {@code fragment} is added to the container view with id {@code frameId}. The operation is
     * performed by the {@code fragmentManager}.
     */
    public static void addFragmentToActivity(@NonNull FragmentManager fragmentManager,
                                             @NonNull Fragment fragment, int frameId) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(frameId, fragment);
        transaction.commit();
    }

    /**
     * @param view         View to animate
     * @param toVisibility Visibility at the end of animation
     * @param toAlpha      Alpha at the end of animation
     * @param duration     Animation duration in ms
     */
    public static void animateViewWithFade(final View view, final int toVisibility, float toAlpha, int duration) {
        boolean show = toVisibility == View.VISIBLE;
        if (show) {
            view.setAlpha(0);
        }
        view.setVisibility(View.VISIBLE);
        view.animate()
                .setDuration(duration)
                .alpha(show ? toAlpha : 0)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        view.setVisibility(toVisibility);
                    }
                });
    }

    /**
     * Hide the first view provided as argument and show the second one, applying respectively a fade-out and a fade-in
     * animations.
     *
     * @param firstView
     * @param secondView
     * @param duration   The duration of the fade-out and fade-in animations
     */
    public static void substituteViewWithFade(final View firstView, final View secondView, int duration) {
        ObjectAnimator firstViewFadeOutAnimator = ObjectAnimator.ofFloat(firstView, "alpha", 1f, 0f);
        firstViewFadeOutAnimator.setDuration(duration);
        firstViewFadeOutAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                firstView.setVisibility(View.GONE);
                secondView.setVisibility(View.VISIBLE);
            }
        });

        ObjectAnimator secondViewFadeInAnimation = ObjectAnimator.ofFloat(secondView, "alpha", 0f, 1f);
        secondViewFadeInAnimation.setDuration(duration);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(firstViewFadeOutAnimator).before(secondViewFadeInAnimation);
        animatorSet.play(secondViewFadeInAnimation).after(firstViewFadeOutAnimator);

        animatorSet.start();
    }

    /**
     * Hide the keyboard from the activity provided as argument
     *
     * @param activity
     */
    public static void dismissKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (null != activity.getCurrentFocus())
            imm.hideSoftInputFromWindow(activity.getCurrentFocus()
                    .getApplicationWindowToken(), 0);
    }
}
