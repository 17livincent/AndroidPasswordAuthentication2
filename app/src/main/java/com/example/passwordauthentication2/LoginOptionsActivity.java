package com.example.passwordauthentication2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class LoginOptionsActivity extends AppCompatActivity {

    public Button buttonEmail;
    public SignInButton buttonGoogle;

    private GoogleSignInOptions gso;
    private GoogleSignInClient mGoogleSignInClient;
    private static final int RC_SIGN_IN = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_options);

        buttonEmail = findViewById(R.id.button_use_email);
        buttonGoogle = findViewById(R.id.google_sign_in_button);

        // listener for google sign-in button
        findViewById(R.id.google_sign_in_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                googleSignIn();
            }
        });

        // google sign-in
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    private void googleSignIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> task) {
        try {   // google sign-in was successful
            GoogleSignInAccount account = task.getResult(ApiException.class);
            Toast.makeText(LoginOptionsActivity.this, "Google sign-in succeeded", Toast.LENGTH_SHORT).show();

            firebaseAuthWithGoogle(account.getIdToken());
        } catch (ApiException e) {
            // google sign-in failed
            Toast.makeText(LoginOptionsActivity.this, "Google sign-in failed", Toast.LENGTH_SHORT).show();
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        final AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        FirebaseAuth.getInstance().signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            // sign-in was successful
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                            Toast.makeText(LoginOptionsActivity.this, "Authentication succeeded.", Toast.LENGTH_SHORT).show();

                            // link credential
                            FirebaseAuth.getInstance().getCurrentUser().linkWithCredential(credential).addOnCompleteListener(LoginOptionsActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()) {
                                        // Google credential linked successfully
                                        Toast.makeText(LoginOptionsActivity.this, "Google credential linked", Toast.LENGTH_SHORT).show();
                                    }
                                    else {
                                        Toast.makeText(LoginOptionsActivity.this, "Google credential not linked", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                            // go to main activity
                            Intent intent = new Intent(LoginOptionsActivity.this, MainActivity.class);
                            startActivity(intent);
                        }
                        else {
                            // sign-in fails
                            Toast.makeText(LoginOptionsActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void onClick(View view) {
        int i = view.getId();
        if(i == R.id.button_use_email) {
            // go to the login page for email/pw
            Intent intent = new Intent(this, LoginEPActivity.class);
            startActivity(intent);
        }
        else if(i == R.id.textView_create_account) {
            Intent intent = new Intent(this, SignUpOptionsActivity.class);
            startActivity(intent);
        }
    }
}
