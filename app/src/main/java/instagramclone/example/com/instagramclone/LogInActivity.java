package instagramclone.example.com.instagramclone;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.shashank.sony.fancytoastlib.FancyToast;

public class LogInActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edtLogInEmail, edtLogInPassword;
    private Button btnLogINLActivity, btnSignUpLActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        setTitle("Log In");

        edtLogInEmail = findViewById(R.id.edtLogInEmail);
        edtLogInPassword = findViewById(R.id.edtLogInPassword);

        edtLogInPassword.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                    onClick(edtLogInPassword);
                }
                return false;
            }
        });

        btnLogINLActivity = findViewById(R.id.btnLogINLActivity);
        btnSignUpLActivity = findViewById(R.id.btnSignUpLActivity);

        btnLogINLActivity.setOnClickListener(this);
        btnSignUpLActivity.setOnClickListener(this);

        if (ParseUser.getCurrentUser() != null) {
            transitionToSocialMediaActivity();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnLogINLActivity:
                if (edtLogInEmail.getText().toString().equals("") ||
                        edtLogInPassword.getText().toString().equals("")) {
                    FancyToast.makeText(LogInActivity.this, "Email, Password is required!", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
                } else {

                    final ProgressDialog progressDialog = new ProgressDialog(this);
                    progressDialog.setMessage("Login in " + edtLogInEmail.getText().toString());
                    progressDialog.show();

                    ParseUser.logInInBackground(edtLogInEmail.getText().toString(), edtLogInPassword.getText().toString(), new LogInCallback() {
                        @Override
                        public void done(ParseUser user, ParseException e) {
                            if (user != null && e == null) {
                                FancyToast.makeText(LogInActivity.this, user.getUsername() + " is logged in!", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();
                                transitionToSocialMediaActivity();
                            } else {
                                FancyToast.makeText(LogInActivity.this, "There was an error: " + e.getMessage(), FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
                            }
                            progressDialog.dismiss();
                        }

                    });
                }
                break;
            case R.id.btnSignUpLActivity:
                Intent intent = new Intent(LogInActivity.this, SignUp.class);
                startActivity(intent);
                break;
        }
    }

    public void rootLayoutTapped(View v) {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void transitionToSocialMediaActivity() {
        Intent intent = new Intent(LogInActivity.this, SocialMediaActivity.class);
        startActivity(intent);
        finish();
    }

}
