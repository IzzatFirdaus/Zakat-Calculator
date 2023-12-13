package com.example.zakatcalculator;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;


public class AboutActivity extends AppCompatActivity {

    private static final String TAG = "AboutActivity";
    private TextView aboutTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        aboutTextView = findViewById(R.id.about_text_view);

        // Set an OnClickListener for the Visit Website Button
        Button visitWebsiteButton = findViewById(R.id.buttonVisitWebsite);
        visitWebsiteButton.setOnClickListener(v -> openWebsite()); // Use lambda expression for OnClickListener

        // Initialize aboutLayout within the onCreate() method
        LinearLayout aboutLayout = findViewById(R.id.about_parent);

        // Set up the about text with links
        setUpAboutTextWithLinks();

        // Add some padding to the about text
        aboutTextView.setPadding(dpToPx(), dpToPx(), dpToPx(), dpToPx());

        // Add some animation to the about page
        aboutLayout.setAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_in));
    }


    // Override onCreateOptionsMenu to inflate the menu resource
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    // Override onOptionsItemSelected to handle menu item clicks
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_about) {
            // Handle About item click
            openAboutPage();
            return true;
        } else if (item.getItemId() == R.id.action_share) {
            // Handle Share item click
            shareApp();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    // Method to open the About page
    private void openAboutPage() {
        // Add code to open the About page or perform any specific action related to 'About'
        Toast.makeText(this, "About clicked!", Toast.LENGTH_SHORT).show();
    }

    // Method to share the app
    private void shareApp() {
        // Add code to share the app or perform any specific action related to 'Share'
        Toast.makeText(this, "Share clicked!", Toast.LENGTH_SHORT).show();
    }

    private void setUpAboutTextWithLinks() {
        if (aboutTextView != null) {
            SpannableString aboutText = new SpannableString(getString(R.string.about_text));
            aboutText.setSpan(new ClickableSpan() {
                @Override
                public void onClick(@NonNull View view) {
                    handleAboutTextClick();
                }
            }, 0, aboutText.length(), SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);
            aboutTextView.setText(aboutText);
            aboutTextView.setMovementMethod(LinkMovementMethod.getInstance());
        }
    }

    private void handleAboutTextClick() {
        String url = getString(R.string.website_url);
        openBrowserWithUrl(url);
    }

    private void openBrowserWithUrl(String url) {
        if (Objects.requireNonNull(url).startsWith("http://") || url.startsWith("https://")) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(intent);
        } else {
            Log.w(TAG, "Invalid URL: " + url);
        }
    }

    private int dpToPx() {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (16 * scale + 0.5f);
    }

    // Method to open the website
    private void openWebsite() {
        String url = getString(R.string.website_url);
        openBrowserWithUrl(url);
    }
}
