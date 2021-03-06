package it.casinovenezia.casinodivenezia;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by massimomoro on 30/11/16.
 */

public class SignUp extends AppCompatActivity implements View.OnClickListener {
    private Button buttonRegister;
    private EditText editTextName;
    private EditText editTextSurname;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private EditText passwordAgainEditText;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    private FirebaseAuth.AuthStateListener mAuthListener;
    private CallbackManager mCallbackManager;
    private static final String TAG = "SignUp";
    @Override
    public void onClick(View v) {
        if (v == buttonRegister) {
            registerUser();
        }
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.singup_firebase);
        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        buttonRegister = (Button) findViewById(R.id.buttonRegister);
        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextSurname = (EditText) findViewById(R.id.editTextSurname);
        editTextEmail = (EditText) findViewById(R.id.editTextMail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        passwordAgainEditText = (EditText) findViewById(R.id.editTextRePassword);
        passwordAgainEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == R.id.edittext_action_signup ||
                        actionId == EditorInfo.IME_ACTION_UNSPECIFIED) {
                    registerUser();
                    return true;
                }
                return false;
            }
        });
        //FAcebook
        mCallbackManager = CallbackManager.Factory.create();
        LoginButton loginButton = (LoginButton) findViewById(R.id.button_facebook_login);
        loginButton.setReadPermissions("email", "public_profile");
        loginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                //Log.d(TAG, "facebook:onSuccess:" + loginResult);
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
               // Log.d(TAG, "facebook:onCancel");
                // ...
            }

            @Override
            public void onError(FacebookException error) {
               // Log.d(TAG, "facebook:onError", error);
                // ...
            }
        });

        buttonRegister.setOnClickListener(this);


        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in

                    mRootRef.child("users").child(user.getUid()).child("name").setValue(editTextName.getText().toString().trim());
                    mRootRef.child("users").child(user.getUid()).child("surname").setValue(editTextSurname.getText().toString().trim());
                } else {
                    // User is signed out

                }

            }
        };
    }
    // [START on_activity_result]
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Pass the activity result back to the Facebook SDK
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }
    // [END on_activity_result]
    // [START auth_with_facebook]
    private void handleFacebookAccessToken(AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);
        // [START_EXCLUDE silent]
        showProgressDialog();
        // [END_EXCLUDE]

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        linkWithCred(credential);

    }
    // [END auth_with_facebook]

    private void registerUser() {
        String name = editTextName.getText().toString().trim();
        String surname = editTextSurname.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String passwordAgain = passwordAgainEditText.getText().toString().trim();

        boolean validationError = false;
        StringBuilder validationErrorMessage = new StringBuilder(getString(R.string.error_intro));
        if (name.length() == 0) {
            validationError = true;
            validationErrorMessage.append(getString(R.string.error_blank_name));
        }
        if (surname.length() == 0) {
            validationError = true;
            validationErrorMessage.append(getString(R.string.error_blank_surname));
        }
        if (email.length() == 0) {
            validationError = true;
            validationErrorMessage.append(getString(R.string.error_blank_username));
        }
        if (password.length() == 0) {
            if (validationError) {
                validationErrorMessage.append(getString(R.string.error_join));
            }
            validationError = true;
            validationErrorMessage.append(getString(R.string.error_blank_password));
        }
        if (!password.equals(passwordAgain)) {
            if (validationError) {
                validationErrorMessage.append(getString(R.string.error_join));
            }
            validationError = true;
            validationErrorMessage.append(getString(R.string.error_mismatched_passwords));
        }
        validationErrorMessage.append(getString(R.string.error_end));

        // If there is a validation error, display the error
        if (validationError) {
            Toast.makeText(SignUp.this, validationErrorMessage.toString(), Toast.LENGTH_LONG)
                    .show();
            return;
        }
        showProgressDialog();
        AuthCredential credential = EmailAuthProvider.getCredential(email, password);
        linkWithCred(credential);
    }
    private void linkWithCred (AuthCredential credential) {
        firebaseAuth.getCurrentUser().linkWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Toast.makeText(SignUp.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                        finish();
                        // [START_EXCLUDE]
                        hideProgressDialog();
                        // [END_EXCLUDE]
                    }
                });
    }
private void hideProgressDialog () {
    if (progressDialog != null && progressDialog.isShowing()) {
        progressDialog.dismiss();
    }
}
private void showProgressDialog() {
    progressDialog.setMessage("Registering User...");
    progressDialog.show();
}
    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            firebaseAuth.removeAuthStateListener(mAuthListener);
        }
    }
}
