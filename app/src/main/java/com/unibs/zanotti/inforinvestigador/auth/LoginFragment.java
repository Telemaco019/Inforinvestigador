package com.unibs.zanotti.inforinvestigador.auth;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import com.google.android.gms.common.SignInButton;
import com.google.firebase.auth.FirebaseUser;
import com.unibs.zanotti.inforinvestigador.R;
import com.unibs.zanotti.inforinvestigador.navigation.MainNavigationActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment implements LoginContract.View {

    private LoginContract.Presenter mPresenter;

    private EditText emailEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private TextView passwordForgotLink;
    private SignInButton googleSignInButton;


    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        emailEditText = view.findViewById(R.id.login_input_email);
        passwordEditText = view.findViewById(R.id.login_input_password);
        loginButton = view.findViewById(R.id.btn_login);
        passwordForgotLink = view.findViewById(R.id.link_password_forgot);

        loginButton.setOnClickListener(e ->
                mPresenter.performLogin(emailEditText.getText().toString(), passwordEditText.getText().toString())
        );
        passwordForgotLink.setOnClickListener(e -> {
            ;
        });

        mPresenter.start();
        return view;
    }

    @Override
    public void setPresenter(LoginContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public void showErrorEmailFieldEmpty() {
        emailEditText.setError(getResources().getString(R.string.msg_error_email_empty));
    }

    @Override
    public void showErrorPasswordFieldEmpty() {
        passwordEditText.setError(getResources().getString(R.string.msg_error_password_empty));
    }

    @Override
    public void showAuthenticationFailed() {
        passwordEditText.setError(getResources().getString(R.string.msg_login_failed));
    }

    @Override
    public void showPasswordForgotLink() {
        passwordForgotLink.setVisibility(View.VISIBLE);
    }

    @Override
    public void updateUI(FirebaseUser user) {
        if (user != null) {
            Intent intent = new Intent(getContext(), MainNavigationActivity.class);
            //   startActivity(intent);
        }
    }
}
