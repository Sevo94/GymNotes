package am.app.gymnotes.viewmodels;

import android.app.Application;
import android.support.annotation.NonNull;

import am.app.gymnotes.CalenderManager;

public class WorkoutViewModelNext extends ViewModelModule {

    public WorkoutViewModelNext(@NonNull Application application) {
        super(application);

        workoutList = appDatabase.exerciseDao().getExercisesByDate(CalenderManager.getInstance().getNextDate());
    }
}
