package com.example.zakatcalculator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
        RadioButton radioButtonKeep = findViewById(R.id.radioButtonKeep);

        calculate.setOnClickListener(v -> calculateZakat(radioButtonKeep.isChecked()));

        Button resetButton = findViewById(R.id.reset);
        resetButton.setOnClickListener(v -> resetCalculation());

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
    private void calculateZakat(boolean isKeepSelected) {
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

            double goldWeightMinusX;
            double zakatPayableValue;
            double totalZakatValue;

            // Calculate Gold Weight minus X (uruf)
            if (isKeepSelected) {
                goldWeightMinusX = w - 85; // 85 grams for gold keeping category
            } else { // "wear" is selected
                goldWeightMinusX = w - 200; // 200 grams for gold wearing category
            }

            if (goldWeightMinusX <= 0) {
                zakatPayableValue = 0;
            } else {
                zakatPayableValue = goldWeightMinusX * v;
            }

            totalZakatValue = 0.025 * zakatPayableValue; // Calculate total zakat

            result.setText(String.format(Locale.getDefault(), "Gold Weight minus uruf (g): %.2f", goldWeightMinusX));
            zakatPayable.setText(String.format(Locale.getDefault(), "Gold value for Zakat Payable (RM): %.2f", zakatPayableValue));
            totalZakat.setText(String.format(Locale.getDefault(), "Total Zakat (RM): %.2f", totalZakatValue));

        } catch (NumberFormatException e) {
            Toast.makeText(this, "Please enter valid numbers!", Toast.LENGTH_SHORT).show();
        }
    }

    private void resetCalculation() {
        weight.setText(""); // Reset weight field
        value.setText(""); // Reset value field
        result.setText(getString(R.string.result_label)); // Reset result field
        zakatPayable.setText(getString(R.string.zakat_payable_label)); // Reset zakatPayable field
        totalZakat.setText(getString(R.string.total_zakat_label)); // Reset totalZakat field
        // Uncheck radio buttons if needed
        RadioGroup radioGroupGoldType = findViewById(R.id.radioGroupGoldType);
        radioGroupGoldType.clearCheck();
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