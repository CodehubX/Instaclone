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

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

public class SignUp extends AppCompatActivity implements View.OnClickListener {

    private Button btnSignUpSActivity, btnLogInSActivity;
    private EditText edtSignUpEmail, edtSignUpUsername, edtSignUpPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        setTitle("Sign Up");

        edtSignUpEmail = findViewById(R.id.edtSignUpEmail);
        edtSignUpUsername = findViewById(R.id.edtSignUpUsername);
        edtSignUpPassword = findViewById(R.id.edtSignUpPassword);

        edtSignUpPassword.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                    onClick(btnSignUpSActivity);
                }
                return false;
            }
        });

        btnSignUpSActivity = findViewById(R.id.btnSignUpSActivity);
        btnLogInSActivity = findViewById(R.id.btnLogInSActivity);

        btnSignUpSActivity.setOnClickListener(this);
        btnLogInSActivity.setOnClickListener(this);

        if (ParseUser.getCurrentUser() != null) {
            // ParseUser.logOut();
            transitionToSocialMediaActivity();
        }

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btnSignUpSActivity:

                if (edtSignUpEmail.getText().toString().equals("") ||
                        edtSignUpUsername.getText().toString().equals("") ||
                        edtSignUpPassword.getText().toString().equals("")) {
                    FancyToast.makeText(SignUp.this, "Email, Username, Password is required!", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
                } else {

                    final ParseUser appUser = new ParseUser();
                    appUser.setEmail(edtSignUpEmail.getText().toString());
                    appUser.setUsername(edtSignUpUsername.getText().toString());
                    appUser.setPassword(edtSignUpPassword.getText().toString());

                    final ProgressDialog progressDialog = new ProgressDialog(this);
                    progressDialog.setMessage("Signing up " + edtSignUpUsername.getText().toString());
                    progressDialog.show();

                    appUser.signUpInBackground(new SignUpCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {
                                FancyToast.makeText(SignUp.this, appUser.getUsername() + " is signed up!", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();
                                transitionToSocialMediaActivity();
                            } else {
                                FancyToast.makeText(SignUp.this, "There was an error: " + e.getMessage(), FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
                            }
                            progressDialog.dismiss();
                        }
                    });
                }
                break;
            case R.id.btnLogInSActivity:
                Intent intent = new Intent(SignUp.this, LogInActivity.class);
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

        Intent intent = new Intent(SignUp.this, SocialMediaActivity.class);
        startActivity(intent);
        finish();

    }
}
