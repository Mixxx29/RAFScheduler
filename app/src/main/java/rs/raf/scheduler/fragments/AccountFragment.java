package rs.raf.scheduler.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import rs.raf.scheduler.R;
import rs.raf.scheduler.activities.LoginActivity;
import rs.raf.scheduler.activities.ResetPasswordActivity;
import rs.raf.scheduler.applications.App;
import rs.raf.scheduler.models.User;
import timber.log.Timber;

public class AccountFragment extends Fragment {

    private TextView usernameView;
    private TextView emailView;
    private Button resetPassword;
    private Button logoutButton;

    public AccountFragment() {
        super(R.layout.fragment_account);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        init(view);
    }

    private void init(View view) {
        initView(view);
        initListeners();
    }

    private void initView(View view) {
        usernameView = view.findViewById(R.id.usernameText);
        emailView = view.findViewById(R.id.emailText);

        SharedPreferences preferences = getActivity().getSharedPreferences(
                getActivity().getPackageName(), Context.MODE_PRIVATE
        );
        User user = (User) App.getUserRepository().get(preferences.getString(LoginActivity.PREF_USERNAME, ""));
        usernameView.setText(user.getUsername());
        emailView.setText(user.getEmail());

        resetPassword = view.findViewById(R.id.resetPasswordButton);
        logoutButton = view.findViewById(R.id.logoutButton);

    }

    private void initListeners() {
        resetPassword.setOnClickListener(view -> {
            startActivity(new Intent(getActivity(), ResetPasswordActivity.class));
        });

        logoutButton.setOnClickListener(view -> {
            // Save session
            SharedPreferences preferences = getActivity().getSharedPreferences(
                    getActivity().getPackageName(), Context.MODE_PRIVATE
            );
            preferences
                    .edit()
                    .putString(LoginActivity.PREF_USERNAME, "")
                    .apply();

            getActivity().finish();
            startActivity(new Intent(getActivity(), LoginActivity.class));
        });
    }
}
