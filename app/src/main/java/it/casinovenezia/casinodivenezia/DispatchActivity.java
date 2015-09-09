package it.casinovenezia.casinodivenezia;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;

import com.parse.ParseUser;

/**
 * Created by massimomoro on 01/06/15.
 */
public class DispatchActivity extends AppCompatActivity {

    public DispatchActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Check if there is current user info
        if (ParseUser.getCurrentUser() != null) {
            // Start an intent for the logged in activity
           // startActivity(new Intent(this, MainActivity.class));
        } else {
            // Start and intent for the logged out activity
            //startActivity(new Intent(this, WelcomeActivity.class));
        }
    }
}
