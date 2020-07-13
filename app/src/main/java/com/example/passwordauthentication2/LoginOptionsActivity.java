package com.example.passwordauthentication2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LoginOptionsActivity extends AppCompatActivity {

    public Button buttonEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_options);

        buttonEmail = findViewById(R.id.button_use_email);
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
