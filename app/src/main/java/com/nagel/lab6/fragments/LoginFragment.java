package com.nagel.lab6.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.nagel.lab6.R;
import com.nagel.lab6.viewmodel.LoginRegisterViewModel;

import java.util.Objects;

public class LoginFragment extends Fragment {

    private TextInputEditText userEmail;
    private TextInputEditText userPassword;
    private CoordinatorLayout contained;
    private LoginRegisterViewModel loginRegisterViewModel;

    public static boolean isAnyStringNullOrEmpty(String... strings) {
        for (String s : strings)
            if (s == null || s.isEmpty())
                return true;
        return false;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginRegisterViewModel = new ViewModelProvider(this).get(LoginRegisterViewModel.class);
        loginRegisterViewModel.getUserMutableLiveData().observe(this,firebaseUser -> {
            if (firebaseUser != null){
                if(getView() != null) Navigation.findNavController(getView()).navigate(R.id.action_loginFragment_to_userFragment);
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login,container,false);

        requireActivity().setTitle(getString(R.string.thk));

        contained = view.findViewById(R.id.login_coordinator);
        userEmail = view.findViewById(R.id.etUserEmailLogin);
        userPassword = view.findViewById(R.id.etUserPasswordLogin);

        view.findViewById(R.id.btnLoginUser).setOnClickListener(view1 -> {
            String email = Objects.requireNonNull(userEmail.getText()).toString().trim();
            String password = Objects.requireNonNull(userPassword.getText()).toString().trim();
            if (isAnyStringNullOrEmpty(email, password)) {
                Snackbar.make(contained, getString(R.string.field_err), Snackbar.LENGTH_SHORT).show();
            }else{
                loginRegisterViewModel.login(email,password);
            }
        });

        view.findViewById(R.id.btnRegisterUser).setOnClickListener(view1 -> {
            Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_registerFragment);
        });

        return view;
    }
}
