package com.example.passwordauthentication2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    public TextView status;

    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onStart() {
        super.onStart();

        status = findViewById(R.id.textView_user_desc);
        // check if a user is signed in
        user = FirebaseAuth.getInstance().getCurrentUser();
        updateStatus();
        if(user == null) {  // go to login activity
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
    }

    private void hideKeyboard(View view) {
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private void signOut() {
        FirebaseAuth.getInstance().signOut();
    }

    public void refresh() {
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }

    public void updateStatus() {
        if(user == null) {
            status.setText("No one is logged in.");
        }
        else {
            String s = "";
            s += user.getDisplayName() + "\n" + user.getEmail();
            status.setText(s);
        }
    }

    public void onClick(View view) {
        hideKeyboard(view);
        int i = view.getId();
        if(i == R.id.button_sign_out) {
            signOut();
            refresh();
        }
    }
}
