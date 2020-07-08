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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class SignUpActivity extends AppCompatActivity {

    public EditText emailField;
    public EditText pwField1;
    public EditText pwField2;
    public EditText dnField;

    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        emailField = findViewById(R.id.editText_email);
        pwField1 = findViewById(R.id.editText_pw);
        pwField2 = findViewById(R.id.editText_pw_v);
        dnField = findViewById(R.id.editText_display_name);
    }

    private void createAccount(String email, String password) {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {  // Sign in success, update UI with the signed-in user's information
                            // set user
                            user = FirebaseAuth.getInstance().getCurrentUser();
                            // add display name
                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(dnField.getText().toString().trim())
                                    .build();
                            user.updateProfile(profileUpdates);
                            user = FirebaseAuth.getInstance().getCurrentUser();
                            // alert
                            Toast.makeText(SignUpActivity.this, "Create user successful.", Toast.LENGTH_SHORT).show();
                            // update view
                            Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                            startActivity(intent);
                        }
                        else {  // If sign in fails, display a message to the user.
                            Toast.makeText(SignUpActivity.this, "Create user failed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void onClick(View view) {
        Utilities.hideKeyboard(view);
        int i = view.getId();
        String email = emailField.getText().toString().trim();
        String pw1 = pwField1.getText().toString().trim();
        String pw2 = pwField2.getText().toString().trim();
        String dn = dnField.getText().toString().trim();
        if(i == R.id.button_register) {
            if(!email.matches("")
                    && !pw1.matches("")
                    && !pw2.matches("")
                    && !dn.matches("")
                    && pw1.matches(pw2)) {
                // create account
                createAccount(email, pw1);
            }
        }
    }
}
