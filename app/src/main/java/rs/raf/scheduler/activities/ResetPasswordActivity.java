package rs.raf.scheduler.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import rs.raf.scheduler.R;
import rs.raf.scheduler.applications.App;
import rs.raf.scheduler.models.User;
import rs.raf.scheduler.utils.Validator;

public class ResetPasswordActivity extends AppCompatActivity {

    private EditText oldPasswordEdit;
    private TextView oldPasswordError;

    private EditText newPasswordEdit;
    private TextView newPasswordError;

    private EditText repeatPasswordEdit;
    private TextView repeatPasswordError;

    private Button resetButton;
    private Button cancelButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE); // Hide title
        setContentView(R.layout.activity_reset_password);

        init();
    }

    private void init() {
        initView();
        initListeners();
    }

    private void initView() {
        oldPasswordEdit = findViewById(R.id.oldPasswordEdit);
        oldPasswordError = findViewById(R.id.oldPasswordError);
        oldPasswordError.setVisibility(View.INVISIBLE);

        newPasswordEdit = findViewById(R.id.newPasswordEdit);
        newPasswordError = findViewById(R.id.newPasswordError);
        newPasswordError.setVisibility(View.INVISIBLE);

        repeatPasswordEdit = findViewById(R.id.repeatPasswordEdit);
        repeatPasswordError = findViewById(R.id.repeatPasswordError);
        repeatPasswordError.setVisibility(View.INVISIBLE);

        resetButton = findViewById(R.id.resetPasswordButton);
        cancelButton = findViewById(R.id.cancelButton);
    }

    private void initListeners() {
        resetButton.setOnClickListener(view -> {
            // Reset previous errors
            oldPasswordError.setVisibility(View.INVISIBLE);
            newPasswordError.setVisibility(View.INVISIBLE);
            repeatPasswordError.setVisibility(View.INVISIBLE);

            // Validate input
            if (!validate()) return;

            // Get shared preferences
            SharedPreferences preferences = getSharedPreferences(
                    getPackageName(), Context.MODE_PRIVATE
            );

            String username = preferences.getString(LoginActivity.PREF_USERNAME, "");
            User user = (User) App.getUserRepository().get(username);

            // Authenticate input
            if (!authenticate(user)) return;

            // Save new password
            user.setPassword(newPasswordEdit.getText().toString());
            if (user.save())
                Toast.makeText(
                        getApplicationContext(), "Password reset successful!", Toast.LENGTH_SHORT).show();

            finish();
        });

        cancelButton.setOnClickListener(view -> {
            finish();
        });
    }

    private boolean validate() {
        StringBuilder messageBuffer = new StringBuilder();

        String oldPassword = oldPasswordEdit.getText().toString();
        if (!Validator.validatePassword(oldPassword, messageBuffer)) {
            oldPasswordError.setText(messageBuffer.toString());
            oldPasswordError.setVisibility(View.VISIBLE);
            return false;
        }

        String newPassword = newPasswordEdit.getText().toString();
        if (!Validator.validatePassword(newPassword, messageBuffer)) {
            newPasswordError.setText(messageBuffer.toString());
            newPasswordError.setVisibility(View.VISIBLE);
            return false;
        }

        if (newPassword.equals(oldPassword)) {
            newPasswordError.setText("New password can't match old password!");
            newPasswordError.setVisibility(View.VISIBLE);
            return false;
        }

        String repeatPassword = repeatPasswordEdit.getText().toString();
        if (!Validator.validatePassword(repeatPassword, messageBuffer)) {
            repeatPasswordError.setText(messageBuffer.toString());
            repeatPasswordError.setVisibility(View.VISIBLE);
            return false;
        }

        if (!repeatPassword.equals(newPassword)) {
            repeatPasswordError.setText("Repeated password must match new password!");
            repeatPasswordError.setVisibility(View.VISIBLE);
            return false;
        }

        return true;
    }

    private boolean authenticate(User user) {
        if (!oldPasswordEdit.getText().toString().equals(user.getPassword())) {
            oldPasswordError.setText("Invalid old password!");
            oldPasswordError.setVisibility(View.VISIBLE);
            return false;
        }

        return true;
    }
}