package com.example.zakatcalculator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.content.Intent;
import android.widget.Toast;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    EditText weight, value;
    Button calculate, shareButton;
    TextView result, zakatPayable, totalZakat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        weight = findViewById(R.id.weight);
        value = findViewById(R.id.value);
        calculate = findViewById(R.id.calculate);
        result = findViewById(R.id.result);
        zakatPayable = findViewById(R.id.zakatPayable);
        totalZakat = findViewById(R.id.totalZakat);
        shareButton = findViewById(R.id.share_button);

        calculate.setOnClickListener(v -> calculateZakat());

        shareButton.setOnClickListener(v -> shareApp());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_about) {
            // Open the About Page
            Intent aboutIntent = new Intent(this, com.example.zakatcalculator.AboutActivity.class);
            startActivity(aboutIntent);
            return true;
        } else if (id == R.id.action_share) {
            // Share the app
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.share_app_subject));
            shareIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.share_app_text));
            startActivity(shareIntent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    private void calculateZakat() {
        String weightString = weight.getText().toString();
        String valueString = value.getText().toString();

        if (weightString.isEmpty() || valueString.isEmpty()) {
            Toast.makeText(this, "Please enter weight and value!", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            double w = Double.parseDouble(weightString);
            double v = Double.parseDouble(valueString);

            if (w <= 0 || v <= 0) {
                Toast.makeText(this, "Weight and value must be greater than 0!", Toast.LENGTH_SHORT).show();
                return;
            }

            double totalGoldValue = w * v;
            double zakatPayableValue;
            double totalZakatValue;

            if (w <= 100) {
                zakatPayableValue = 0;
                totalZakatValue = 0;
            } else {
                zakatPayableValue = (w - 100) * v;
                totalZakatValue = 0.025 * totalGoldValue;
            }

            result.setText(String.format(Locale.getDefault(), "%.2f", totalGoldValue));
            zakatPayable.setText(String.format(Locale.getDefault(), "%.2f", zakatPayableValue));
            totalZakat.setText(String.format(Locale.getDefault(), "%.2f", totalZakatValue));

        } catch (NumberFormatException e) {
            Toast.makeText(this, "Please enter valid numbers!", Toast.LENGTH_SHORT).show();
        }
    }

    private void shareApp() {
        String appName = "Zakat Payment Estimation App";
        String message = "I found this great Zakat Payment Estimation App! Check it out: " + appName;

        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, appName);
        shareIntent.putExtra(Intent.EXTRA_TEXT, message);

        startActivity(Intent.createChooser(shareIntent, "Share with"));
    }
}