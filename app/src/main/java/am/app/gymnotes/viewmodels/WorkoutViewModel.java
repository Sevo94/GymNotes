package am.app.gymnotes.viewmodels;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.List;

import am.app.gymnotes.CalenderManager;
import am.app.gymnotes.GymNotesApplication;
import am.app.gymnotes.database.AppDatabase;
import am.app.gymnotes.database.entities.Exercise;

public class WorkoutViewModel extends ViewModelModule {

    public WorkoutViewModel(@NonNull Application application) {
        super(application);

        workoutList = appDatabase.exerciseDao().getExercisesByDate(CalenderManager.getInstance().getDate());
    }

    private static class FetchExercisesTask extends AsyncTask<String, Void, LiveData<List<Exercise>>> {

        private WeakReference<TextView> textViewWeakReference;

        private FetchExercisesTask(WeakReference<TextView> textViewWeakReference) {
            this.textViewWeakReference = textViewWeakReference;
        }

        @Override
        protected LiveData<List<Exercise>> doInBackground(final String... params) {
            String mDate = params[0];
            return AppDatabase.getAppDatabase(GymNotesApplication.getmInstance()).exerciseDao().
                    getExercisesByDate(mDate);
        }

        @Override
        protected void onPostExecute(LiveData<List<Exercise>> exercises) {
            super.onPostExecute(exercises);

            if (exercises != null && exercises.getValue() != null && !exercises.getValue().isEmpty()) {
                if (textViewWeakReference.get() != null) {
                    textViewWeakReference.get().setText(textViewWeakReference.get().getText() + exercises.getValue().get(exercises.getValue().size() - 1).getExerciseName());
                }
            }
        }
    }
}
