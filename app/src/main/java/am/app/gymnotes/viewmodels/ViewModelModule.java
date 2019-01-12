package am.app.gymnotes.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

import am.app.gymnotes.database.AppDatabase;
import am.app.gymnotes.database.entities.Exercise;

public class ViewModelModule extends AndroidViewModel {

    LiveData<List<Exercise>> workoutList;
    AppDatabase appDatabase;

    ViewModelModule(@NonNull Application application) {
        super(application);

        appDatabase = AppDatabase.getAppDatabase(getApplication());
    }

    public LiveData<List<Exercise>> getWorkoutList() {
        return workoutList;
    }

    public void deleteExercises(List<Exercise> exerciseList) {
        AppDatabase.getAppDatabase(getApplication()).exerciseDao().delete(exerciseList);
    }
}
