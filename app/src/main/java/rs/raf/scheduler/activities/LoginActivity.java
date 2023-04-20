package rs.raf.scheduler.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.util.Calendar;

import rs.raf.scheduler.R;
import rs.raf.scheduler.applications.App;
import rs.raf.scheduler.models.User;
import rs.raf.scheduler.repositories.user.FileUserRepository;
import rs.raf.scheduler.utils.Validator;
import timber.log.Timber;

public class LoginActivity extends AppCompatActivity {

    public final static String PREF_USERNAME = "PREF_USERNAME";

    private TextView error;

    private EditText emailEdit;
    private TextView emailError;

    private EditText usernameEdit;
    private TextView usernameError;

    private EditText passwordEdit;
    private TextView passwordError;

    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Start with splash screen
        SplashScreen.installSplashScreen(this);

        // Set context
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE); // Hide title
        setContentView(R.layout.activity_login); // Set view

        init(); // Initialize activity

        checkSession(); // Check session
    }

    private void checkSession() {
        // Get shared preferences
        SharedPreferences preferences = getSharedPreferences(
                getPackageName(), Context.MODE_PRIVATE
        );

        if (!preferences.getString(LoginActivity.PREF_USERNAME, "").equals("")) {
            openMainActivity();
        }
    }

    private void init() {
        initView();
        initListeners();
    }



    private void initView() {
        error = findViewById(R.id.error);
        error.setVisibility(View.INVISIBLE);

        emailEdit = findViewById(R.id.emailEdit);
        emailError = findViewById(R.id.emailError);
        emailError.setVisibility(View.INVISIBLE);

        usernameEdit = findViewById(R.id.usernameEdit);
        usernameError = findViewById(R.id.usernameError);
        usernameError.setVisibility(View.INVISIBLE);

        passwordEdit = findViewById(R.id.passwordEdit);
        passwordError = findViewById(R.id.passwordError);
        passwordError.setVisibility(View.INVISIBLE);

        loginButton = findViewById(R.id.loginButton);
    }

    private void initListeners() {
        loginButton.setOnClickListener((view) -> {
            // Hide previous errors
            error.setVisibility(View.INVISIBLE);
            emailError.setVisibility(View.INVISIBLE);
            usernameError.setVisibility(View.INVISIBLE);
            passwordError.setVisibility(View.INVISIBLE);

            if (!validate()) return; // Validate data
            if (!authenticate()) return; // Authenticate user

            // Save session
            SharedPreferences preferences = getSharedPreferences(
                    getPackageName(), Context.MODE_PRIVATE
            );
            preferences
                    .edit()
                    .putString(LoginActivity.PREF_USERNAME, usernameEdit.getText().toString())
                    .apply();

            // Open calendar activity
            openMainActivity();
        });
    }

    private boolean validate() {
        StringBuilder messageBuffer = new StringBuilder();

        // Validate email
        String email = emailEdit.getText().toString();
        if (!Validator.validateEmail(email, messageBuffer)) {
            emailError.setText(messageBuffer.toString());
            emailError.setVisibility(View.VISIBLE);
            return false;
        }

        // Validate username
        String username = usernameEdit.getText().toString();
        if (!Validator.validateUsername(username, messageBuffer)) {
            usernameError.setText(messageBuffer.toString());
            usernameError.setVisibility(View.VISIBLE);
            return false;
        }

        // Validate password length
        String password = passwordEdit.getText().toString();
        if (!Validator.validatePassword(password, messageBuffer)) {
            passwordError.setText(messageBuffer);
            passwordError.setVisibility(View.VISIBLE);
            return false;
        }

        return true;
    }

    private boolean authenticate() {
        User user = (User) App.getUserRepository().get(usernameEdit.getText().toString());
        if (user == null || !emailEdit.getText().toString().equals(user.getEmail()) ||
                !passwordEdit.getText().toString().equals(user.getPassword())) {
            error.setText("Invalid e-mail, username or password!");
            error.setVisibility(View.VISIBLE);
            return false;
        }
        return true;
    }

    private void openMainActivity() {
        Intent mainIntent = new Intent(this, MainActivity.class);
        mainIntent.putExtra(MainActivity.EXTRA_USERNAME, usernameEdit.getText().toString());

        // Get current date
        Calendar calendar = Calendar.getInstance();
        String date = calendar.get(Calendar.DAY_OF_MONTH) + "-" +
                (calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(Calendar.YEAR);
        mainIntent.putExtra(MainActivity.EXTRA_DATE, date);
        finish();
        startActivity(mainIntent);
    }
}