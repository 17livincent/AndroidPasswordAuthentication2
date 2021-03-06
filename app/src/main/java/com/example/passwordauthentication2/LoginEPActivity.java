package com.example.passwordauthentication2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginEPActivity extends AppCompatActivity {

    public EditText emailField;
    public EditText pwField;

    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_ep);

        emailField = findViewById(R.id.editText_email);
        pwField = findViewById(R.id.editText_pw);
    }

    private void hideKeyboard(View view) {
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private void signIn(String email, String password) {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            // set user
                            user = FirebaseAuth.getInstance().getCurrentUser();
                            // alert
                            Toast.makeText(LoginEPActivity.this, "Authentication succeeded.", Toast.LENGTH_SHORT).show();
                            // go back to main activity
                            //Snackbar.make(findViewById(R.id.MainActivity), "Authentication succeeded.", Snackbar.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginEPActivity.this, MainActivity.class);
                            startActivity(intent);
                        }
                        else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(LoginEPActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                            //Snackbar.make(findViewById(R.id.LoginEPActivity), "Authentication failed.", Snackbar.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void onClick(View view) {
        Utilities.hideKeyboard(view);
        String email = emailField.getText().toString().trim();
        String pw = pwField.getText().toString().trim();
        int i = view.getId();
        if(i == R.id.button_login) {    // login
            if(!email.matches("") && !pw.matches(""))
                signIn(email, pw);
        }
    }
}
