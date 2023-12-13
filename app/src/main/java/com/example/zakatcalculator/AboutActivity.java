package com.example.zakatcalculator;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;


public class AboutActivity extends AppCompatActivity {

    private static final String TAG = "AboutActivity";

    private TextView aboutTextView;
    private LinearLayout aboutLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        aboutTextView = findViewById(R.id.about_text_view);
        aboutLayout = findViewById(R.id.about_parent);

        // Set up the about text with links
        setUpAboutTextWithLinks();

        // Add some padding to the about text
        aboutTextView.setPadding(dpToPx(16), dpToPx(16), dpToPx(16), dpToPx(16));

        // Add some animation to the about page
        aboutLayout.setAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_in));
    }

    private void setUpAboutTextWithLinks() {
        if (aboutTextView != null) {
            SpannableString aboutText = new SpannableString(getString(R.string.about_text));
            aboutText.setSpan(new ClickableSpan() {
                @Override
                public void onClick(View view) {
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

    private int dpToPx(int dp) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }
}
