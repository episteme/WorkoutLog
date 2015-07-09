package com.marcuspeacock.MyFirstApp.workoutlog2.app;

import java.util.ArrayList;
import java.util.List;

public class ExerciseGroup {

    public String name;
    public final List<ExerciseSet> children = new ArrayList<ExerciseSet>();

    public ExerciseGroup(String name) {
        this.name = name;
    }

}
