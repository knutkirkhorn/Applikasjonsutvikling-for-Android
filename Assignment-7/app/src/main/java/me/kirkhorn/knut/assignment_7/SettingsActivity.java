package me.kirkhorn.knut.assignment_7;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by Knut on 11.11.2017.
 */

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    }

    public void onGoBackButton(View view) {
        Intent intent = new Intent();
        intent.putExtra("asd", 1);
        setResult(RESULT_OK, intent);
        finish();
    }
}
