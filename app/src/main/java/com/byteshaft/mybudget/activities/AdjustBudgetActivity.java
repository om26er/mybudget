package com.byteshaft.mybudget.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.byteshaft.mybudget.R;
import com.byteshaft.mybudget.Fragments.HomeFragment;
import com.byteshaft.mybudget.database.DBHelper;
/*
 Prompts user to enter a new budget. Called from MainActivity.
 */
public class AdjustBudgetActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_adjust_budget);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; goto parent activity.
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /*
    Called by button click in layout file.
    */
    public void onSubmit(View v) {

        EditText amountHolder = (EditText) findViewById(R.id.amount);
        int newBudget = Integer.parseInt(amountHolder.getText().toString());

        Context context = getApplicationContext();
        CharSequence text;
        int duration = Toast.LENGTH_SHORT;

        // check that budget does not exceed amount already allocated
        DBHelper myDb = DBHelper.getInstance(this);

        if (newBudget < myDb.getTotalAllocated()) {
            text = "New budget amount is less than amount already allocated, please try again";
            Toast.makeText(context, text, duration).show();
        } else {
            SharedPreferences prefs = getSharedPreferences(HomeFragment.PREFS_NAME, 0);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt("curBudget", newBudget);
            boolean result = editor.commit();

            if (result) {
                text = "Budget adjusted";
                Toast.makeText(context, text, duration).show();
            } else {
                text = "Failed to adjust budget";
                Toast.makeText(context, text, duration).show();
            }

            finish();
        }
    }
}