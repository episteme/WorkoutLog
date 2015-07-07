package com.marcuspeacock.MyFirstApp.workoutlog2.app;

import android.provider.BaseColumns;

public class DatabaseContract {

    public DatabaseContract() {}

    public static abstract class WorkoutLog implements BaseColumns {
        public static final String TABLE_NAME = "log";
        public static final String DATE_COLUMN = "date";
        public static final String EXERCISE_COLUMN = "exercise";
        public static final String WEIGHT_COLUMN = "weight";
        public static final String REPS_COLUMN = "reps";
        public static final String NOTES_COLUMN = "notes";

        public static final String COMMA_SEP = ",";

        public static final String SQL_CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "(" + _ID +
                " INTEGER PRIMARY KEY," + DATE_COLUMN + " INTEGER" + COMMA_SEP +
                EXERCISE_COLUMN + " TEXT" + COMMA_SEP + WEIGHT_COLUMN + " REAL" + COMMA_SEP +
                REPS_COLUMN + " INTEGER" + COMMA_SEP + NOTES_COLUMN + " TEXT" + ")";

        public static final String SQL_DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }

}
