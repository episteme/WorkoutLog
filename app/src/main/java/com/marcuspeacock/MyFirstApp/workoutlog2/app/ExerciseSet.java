package com.marcuspeacock.MyFirstApp.workoutlog2.app;

public class ExerciseSet {

    public final String exercise;
    public final Integer reps;
    public final Double weight;
    public final Integer _ID;

    public ExerciseSet(String exercise, Integer reps, Double weight, Integer _ID) {
        this.exercise = exercise;
        this.reps = reps;
        this.weight = weight;
        this._ID = _ID;
    }

    public ExerciseSet(String exercise, Integer reps, Double weight, Integer _ID, String notes) {
        this.exercise = exercise;
        this.reps = reps;
        this.weight = weight;
        this._ID = _ID;
    }

}
