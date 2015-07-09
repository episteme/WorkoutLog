package com.marcuspeacock.MyFirstApp.workoutlog2.app;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class TrackFragment extends Fragment implements View.OnClickListener  {

    private static final String ARG_SECTION_NUMBER = "section_number";
    public static final SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, MMMM dd, yyyy");
    private TextView dateView;
    private View rootView;
    private DBHelper dbHelper;
    private ArrayList<ExerciseGroup> groups = new ArrayList<ExerciseGroup>();

    private static Date date;

    public static TrackFragment newInstance(int sectionNumber) {
        TrackFragment fragment = new TrackFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        date = getDate(0);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i("Track", "Track View Opened");

        dbHelper = new DBHelper(getActivity());
        this.rootView = inflater.inflate(R.layout.fragment_track, container, false);
        this.dateView = (TextView) rootView.findViewById(R.id.date);
        listenTo(R.id.dateLeft);
        listenTo(R.id.dateRight);
        listenTo(R.id.saveButton);
        listenTo(R.id.track);
        updateText();
        updateDate();
        writeData();
        ExpandableListView listView = (ExpandableListView) rootView.findViewById(R.id.expandableListView);
        MyExpandableListAdapter adapter = new MyExpandableListAdapter(getActivity(),
                groups);
        listView.setAdapter(adapter);
        ExerciseGroup exerciseGroup = new ExerciseGroup("Squat");
        exerciseGroup.children.add(new ExerciseSet("1", 2, 3.0));
        groups.add(exerciseGroup);
        return rootView;
    }

    private void writeData() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] projection = { DatabaseContract.WorkoutLog._ID, DatabaseContract.WorkoutLog.DATE_COLUMN,
            DatabaseContract.WorkoutLog.EXERCISE_COLUMN, DatabaseContract.WorkoutLog.REPS_COLUMN,
            DatabaseContract.WorkoutLog.WEIGHT_COLUMN, DatabaseContract.WorkoutLog.NOTES_COLUMN };
        String sortOrder = DatabaseContract.WorkoutLog._ID + " DESC";
        String where = DatabaseContract.WorkoutLog.DATE_COLUMN + " = \'" + dateFormat.format(date) + "\'";
        Log.i("", where);
        Cursor c = db.query(DatabaseContract.WorkoutLog.TABLE_NAME, projection, where, null, null, null, sortOrder);
        c.moveToFirst();
        while (!c.isAfterLast()) {
            Log.i("", c.getString(c.getColumnIndexOrThrow(DatabaseContract.WorkoutLog._ID)));
            Log.i("", c.getString(c.getColumnIndexOrThrow(DatabaseContract.WorkoutLog.DATE_COLUMN)));
            Log.i("", c.getString(c.getColumnIndexOrThrow(DatabaseContract.WorkoutLog.EXERCISE_COLUMN)));
            Log.i("", c.getString(c.getColumnIndexOrThrow(DatabaseContract.WorkoutLog.REPS_COLUMN)));
            Log.i("", c.getString(c.getColumnIndexOrThrow(DatabaseContract.WorkoutLog.WEIGHT_COLUMN)));
            if (c.isLast()) {
                break;
            }
            else {
                c.moveToNext();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        writeData();
    }

    private void listenTo(int id) {
        View v = rootView.findViewById(id);
        v.setOnClickListener(this);
    }

    private void drawUpdates() {
        updateDate();
        updateText();
    }

    public static Date getDate(int n) {
        if (date == null) {
            date = new Date();
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, n);
        return c.getTime();
    }

    private void decreaseDate() {
        date = getDate(-1);
        drawUpdates();
    }

    private void increaseDate() {
        date = getDate(1);
        drawUpdates();
    }

    private void updateText() {
        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        EditText editText = (EditText) rootView.findViewById(R.id.savedNumber);
        int n = sharedPref.getInt("int" + dateFormat.format(date), 0);
        editText.setText(String.valueOf(n));
    }

    private void updateDate() {
        dateView.setText(dateFormat.format(date));
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(
                getArguments().getInt(ARG_SECTION_NUMBER));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dateLeft:
                decreaseDate();
                break;
            case R.id.dateRight:
                increaseDate();
                break;
            case R.id.saveButton:
                SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                EditText editText = (EditText) rootView.findViewById(R.id.savedNumber);
                int n = Integer.parseInt(editText.getText().toString());
                editor.putInt("int" + dateFormat.format(date), n);
                editor.commit();
                break;
            case R.id.track:
                Intent intent = new Intent(getActivity(), TrackActivity.class);
                startActivity(intent);
//                DialogFragment dialogFragment = new TrackDialogFragment();
//                dialogFragment.show(getActivity().getFragmentManager(), "missiles");
                break;
        }
    }
}