package com.example.passwordauthentication2;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ViewProfileActivity extends AppCompatActivity {

    private FirebaseUser user;

    public Toolbar toolbar;
    public TextView name;
    public TextView email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        name = findViewById(R.id.textView_name);
        email = findViewById(R.id.textView_email);
    }

    @Override
    public void onStart() {
        super.onStart();

        user = FirebaseAuth.getInstance().getCurrentUser();
        name.setText(user.getDisplayName());
        email.setText(user.getEmail());
    }

    private void deleteAccount() {
        user.delete();
        Toast.makeText(ViewProfileActivity.this, "Account deleted.", Toast.LENGTH_SHORT).show();
    }

    public void onClick(View view) {
        int i = view.getId();
        if(i == R.id.textView_delete_account) {
            // reauthenticate

            // delete account
        }
    }

}
