package am.app.gymnotes;

import java.util.List;

import am.app.gymnotes.database.entities.Exercise;

public interface ExerciseRemoveListener {
    void onExercisesDeleted(List<Exercise> exerciseList);
}
