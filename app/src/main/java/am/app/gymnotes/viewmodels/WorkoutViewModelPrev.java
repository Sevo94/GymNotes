package am.app.gymnotes.viewmodels;

import android.app.Application;
import android.support.annotation.NonNull;

import am.app.gymnotes.CalenderManager;

public class WorkoutViewModelPrev extends ViewModelModule {

    public WorkoutViewModelPrev(@NonNull Application application) {
        super(application);

        workoutList = appDatabase.exerciseDao().getExercisesByDate(CalenderManager.getInstance().getPreviousDate());
    }


    //    public WorkoutViewModelPrev() {
//        super();
//
//        appDatabase = AppDatabase.getAppDatabase(getApplication());
//        workoutList = appDatabase.exerciseDao().getExercisesByDate(CalenderManager.getInstance().getPreviousDate());
//    }

//    public LiveData<List<Exercise>> getWorkoutList() {
//        //new FetchExercisesTask(null).execute(CalenderManager.getInstance().getDate());
//        return workoutList;
//    }
}
