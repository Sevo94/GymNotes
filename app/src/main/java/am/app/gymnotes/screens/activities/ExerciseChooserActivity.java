package am.app.gymnotes.screens.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import am.app.gymnotes.R;
import am.app.gymnotes.screens.fragments.ExerciseChooserFragment;

public class ExerciseChooserActivity extends AppCompatActivity implements ExerciseChooserFragment.OnListFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_chooser);
    }

    @Override
    public void onListFragmentInteraction(String item) {

    }
}
