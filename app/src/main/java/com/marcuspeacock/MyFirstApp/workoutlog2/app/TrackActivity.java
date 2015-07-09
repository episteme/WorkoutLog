package com.marcuspeacock.MyFirstApp.workoutlog2.app;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;


public class TrackActivity extends ActionBarActivity {

    private EditText exercise;
    private EditText weight;
    private EditText reps;
    private EditText notes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.track_dialog);
        exercise = (EditText) findViewById(R.id.exerciseText);
        weight = (EditText) findViewById(R.id.weightText);
        reps = (EditText) findViewById(R.id.repsText);
        notes = (EditText) findViewById(R.id.notesText);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_track, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void trackWorkout(View v) {
        DBHelper mDbHelper = new DBHelper(this);
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        if (validInputs()) {
            ContentValues values = new ContentValues();
            values.put(DatabaseContract.WorkoutLog.DATE_COLUMN, TrackFragment.dateFormat.format(TrackFragment.getDate(0)));
            values.put(DatabaseContract.WorkoutLog.EXERCISE_COLUMN, exercise.getText().toString());
            values.put(DatabaseContract.WorkoutLog.REPS_COLUMN, reps.getText().toString());
            values.put(DatabaseContract.WorkoutLog.WEIGHT_COLUMN, weight.getText().toString());
            values.put(DatabaseContract.WorkoutLog.NOTES_COLUMN, notes.getText().toString());
            long newRowId = db.insert(DatabaseContract.WorkoutLog.TABLE_NAME, null, values);
            finish();
        }
    }

    private boolean validInputs() {
        if (exercise.getText() == null || exercise.getText().equals("") || weight.getText() == null
                || weight.getText().equals("") || reps.getText() == null || weight.getText() == null
                || weight.getText().toString().equals("")) {
            return false;
        }
        return true;
    }
}
