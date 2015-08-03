package com.marcuspeacock.MyFirstApp.workoutlog2.app.track;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.marcuspeacock.MyFirstApp.workoutlog2.app.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class TrackFragment extends Fragment implements View.OnClickListener  {

    private static final String ARG_SECTION_NUMBER = "section_number";
    public static final SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, MMMM dd, yyyy");
    private TextView dateView;
    private View rootView;
    private DBHelper dbHelper;
    private ArrayList<ExerciseGroup> groups = new ArrayList<ExerciseGroup>();
    private MyExpandableListAdapter listAdapter;

    private static Date date;
    public static final String[] DB_COLUMNS = new String[]{DatabaseContract.WorkoutLog._ID, DatabaseContract.WorkoutLog.DATE_COLUMN,
            DatabaseContract.WorkoutLog.EXERCISE_COLUMN, DatabaseContract.WorkoutLog.REPS_COLUMN,
            DatabaseContract.WorkoutLog.WEIGHT_COLUMN, DatabaseContract.WorkoutLog.NOTES_COLUMN};

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
        listenTo(R.id.track);
        updateDate();
        ExpandableListView listView = (ExpandableListView) rootView.findViewById(R.id.expandableListView);
        listAdapter = new MyExpandableListAdapter(getActivity(),
                groups);
        listView.setAdapter(listAdapter);
        listView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Intent intent = new Intent(getActivity(), TrackActivity.class);
                intent.putExtra("_ID", (Integer) v.getTag());
                intent.putExtra("date", dateFormat.format(date));
                intent.putExtra("reps", ((TextView) v.findViewById(R.id.reps)).getText());
                intent.putExtra("weight", ((TextView) v.findViewById(R.id.weight)).getText());
                intent.putExtra("exercise", ((ExerciseGroup) parent.getExpandableListAdapter().getGroup(groupPosition)).name);
                startActivity(intent);
                return true;
            }
        });
        updateExerciseList();
        return rootView;
    }

    private void updateExerciseList() {
        HashMap<String, ArrayList<ExerciseSet>> data = getData();
        groups.clear();
        for (String exercise : data.keySet()) {
            ExerciseGroup exerciseGroup = new ExerciseGroup(exercise);
            for (ExerciseSet exerciseSet : data.get(exercise)) {
                exerciseGroup.children.add(exerciseSet);
            }
            groups.add(exerciseGroup);
        }
        listAdapter.notifyDataSetChanged();
    }

    private HashMap<String, ArrayList<ExerciseSet>> getData() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String sortOrder = DatabaseContract.WorkoutLog._ID + " DESC";
        String where = DatabaseContract.WorkoutLog.DATE_COLUMN + " = \'" + dateFormat.format(date) + "\'";
        Log.i("", where);
        Cursor c = db.query(DatabaseContract.WorkoutLog.TABLE_NAME, DB_COLUMNS, where, null, null, null, sortOrder);
        c.moveToFirst();
        HashMap<String, ArrayList<ExerciseSet>> results = new HashMap();
        while (!c.isAfterLast()) {
            try {
                Integer _ID = c.getInt(c.getColumnIndexOrThrow(DatabaseContract.WorkoutLog._ID));
                String date = c.getString(c.getColumnIndexOrThrow(DatabaseContract.WorkoutLog.DATE_COLUMN));
                String exercise = c.getString(c.getColumnIndexOrThrow(DatabaseContract.WorkoutLog.EXERCISE_COLUMN));
                Integer reps = c.getInt(c.getColumnIndexOrThrow(DatabaseContract.WorkoutLog.REPS_COLUMN));
                Double weight = c.getDouble(c.getColumnIndexOrThrow(DatabaseContract.WorkoutLog.WEIGHT_COLUMN));
                ExerciseSet exerciseSet = new ExerciseSet(exercise, reps, weight, _ID);
                ArrayList<ExerciseSet> setList;
                if (!results.keySet().contains(exercise)) {
                    setList = new ArrayList<ExerciseSet>();
                }
                else {
                    setList = results.get(exercise);
                }
                setList.add(exerciseSet);
                results.put(exercise, setList);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            if (c.isLast()) {
                break;
            }
            else {
                c.moveToNext();
            }
        }
        return results;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateExerciseList();
    }

    private void listenTo(int id) {
        View v = rootView.findViewById(id);
        v.setOnClickListener(this);
    }

    private void drawUpdates() {
        updateDate();
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
                updateExerciseList();
                break;
            case R.id.dateRight:
                increaseDate();
                updateExerciseList();
                break;
            case R.id.track:
                Intent intent = new Intent(getActivity(), TrackActivity.class);
                intent.putExtra("date", dateFormat.format(date));
                startActivity(intent);
//                DialogFragment dialogFragment = new TrackDialogFragment();
//                dialogFragment.show(getActivity().getFragmentManager(), "missiles");
                break;
        }
    }
}