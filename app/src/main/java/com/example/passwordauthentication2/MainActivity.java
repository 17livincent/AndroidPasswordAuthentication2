package com.example.passwordauthentication2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    public Toolbar toolbar;

    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // set up toolbar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    @Override
    public void onStart() {
        super.onStart();

        // check if a user is signed in
        user = FirebaseAuth.getInstance().getCurrentUser();
        if(user == null) {  // go to login activity
            Intent intent = new Intent(this, LoginOptionsActivity.class);
            startActivity(intent);
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

    public void onClick(View view) {
        Utilities.hideKeyboard(view);
        int i = view.getId();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int i = item.getItemId();
        if(i == R.id.menu_view_profile) {
            // go to view profile activity
            Intent intent = new Intent(this, ViewProfileActivity.class);
            startActivity(intent);
            return true;
        }
        else if(i == R.id.menu_settings) {
            // go to settings page

            return true;
        }
        else if(i == R.id.menu_sign_out) {
            // sign out
            signOut();
            refresh();
            return true;
        }
        else {
            return super.onOptionsItemSelected(item);
        }
    }
}
