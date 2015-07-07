package com.marcuspeacock.MyFirstApp.workoutlog2.app;

public class ExerciseSet {

    public final String exercise;
    public final Integer reps;
    public final Double weight;

    public ExerciseSet(String exercise, Integer reps, Double weight) {
        this.exercise = exercise;
        this.reps = reps;
        this.weight = weight;
    }

    public ExerciseSet(String exercise, Integer reps, Double weight, String notes) {
        this.exercise = exercise;
        this.reps = reps;
        this.weight = weight;
    }

}
