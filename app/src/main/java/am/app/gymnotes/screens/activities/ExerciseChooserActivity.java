package am.app.gymnotes.screens.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.Calendar;

import am.app.gymnotes.CalenderManager;
import am.app.gymnotes.R;
import am.app.gymnotes.database.AppDatabase;
import am.app.gymnotes.database.entities.Exercise;
import am.app.gymnotes.screens.fragments.ExerciseChooserFragment;

public class ExerciseChooserActivity extends AppCompatActivity implements ExerciseChooserFragment.OnListFragmentInteractionListener {

    private static final String TAG = ExerciseChooserActivity.class.getCanonicalName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_chooser);
    }

    @Override
    public void onListFragmentInteraction(String item) {
        Exercise exercise = new Exercise();
        exercise.setExerciseId("exerciseId" + System.currentTimeMillis());
        exercise.setExerciseDate(String.valueOf(Calendar.getInstance().getTimeInMillis()));
        exercise.setExerciseName(item);


//        Calendar c = Calendar.getInstance();
//
//        int mYear = c.get(Calendar.YEAR);
//        int mMonth = c.get(Calendar.MONTH) + 1;
//        int mDay = c.get(Calendar.DAY_OF_MONTH);
//
        Log.i(TAG, CalenderManager.getInstance().getDate());

        exercise.setExerciseDate(CalenderManager.getInstance().getDate());
        AppDatabase.getAppDatabase(this).exerciseDao().addExercise(exercise);

        finish();
    }
}
