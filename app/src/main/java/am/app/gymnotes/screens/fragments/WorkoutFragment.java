package am.app.gymnotes.screens.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.List;

import am.app.gymnotes.CalenderManager;
import am.app.gymnotes.Constants;
import am.app.gymnotes.GymNotesApplication;
import am.app.gymnotes.R;
import am.app.gymnotes.database.AppDatabase;
import am.app.gymnotes.database.entities.Exercise;
import am.app.gymnotes.screens.activities.ExerciseChooserActivity;
import am.app.gymnotes.screens.activities.HomeActivity;


public class WorkoutFragment extends Fragment {

    public interface FragmentLoadListener {
        void onFragmentLoadFinished();
    }

    private static final String TAG = WorkoutFragment.class.getCanonicalName();
    private static final String ARG_SECTION_NUMBER = "section_number";

    private FragmentLoadListener fragmentLoadListener;
    private String mDate = "";

    public static WorkoutFragment newInstance(int sectionNumber) {
        WorkoutFragment fragment = new WorkoutFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public WorkoutFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            fragmentLoadListener = (FragmentLoadListener) context;
        } catch (ClassCastException e) {
            //TODO Host activity should implement this interface
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
        Log.i(TAG, "onCreate");
    }

    private TextView textView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.i(TAG, "onCreateView!!!!!");
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Log.i(TAG, "onViewCreated!!!!!");

        textView = view.findViewById(R.id.section_label);

        if (getArguments() != null) {
            int sectionNumber = getArguments().getInt(ARG_SECTION_NUMBER);
            Log.i(TAG, "sectionNumber = " + String.valueOf(sectionNumber));

            if (isAdded()) {
                int lFCount = ((HomeActivity) getActivity()).getLoadedFragmentsCount();
                if (lFCount == Constants.FRAGMENTS_TO_LOAD) {
                    mDate = CalenderManager.getInstance().getDate();
                    textView.setText(CalenderManager.getInstance().getFormattedDate());
                } else {
                    if (fragmentLoadListener != null) {
                        fragmentLoadListener.onFragmentLoadFinished();
                    }
                    switch (sectionNumber) {
                        case 0:
                            mDate = CalenderManager.getInstance().getDate();
                            textView.setText(CalenderManager.getInstance().getCurrentDate());
                            break;
                        case 1:
                            mDate = CalenderManager.getInstance().getNextDate();
                            textView.setText(CalenderManager.getInstance().getNextDay());
                            break;
                        case 3:
                            mDate = CalenderManager.getInstance().getPreviousDate();
                            textView.setText(CalenderManager.getInstance().getPreviousDay());
                            break;
                    }
                }
                new FetchExercisesTask(new WeakReference<>(textView)).execute(mDate);
            }
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_home, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Intent intent = new Intent(getActivity(), ExerciseChooserActivity.class);
            intent.putExtra("date", mDate);
            startActivityForResult(intent, 2);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        List<Exercise> exerciseList = AppDatabase.getAppDatabase(getContext()).exerciseDao().
                getExercisesByDate(CalenderManager.getInstance().getDate());


    }

    @Override
    public void onResume() {
        super.onResume();
    }


    @Override
    public void onDetach() {
        super.onDetach();
        fragmentLoadListener = null;
    }

    private static class FetchExercisesTask extends AsyncTask<String, Void, List<Exercise>> {

        private WeakReference<TextView> textViewWeakReference;

        private FetchExercisesTask(WeakReference<TextView> textViewWeakReference) {
            this.textViewWeakReference = textViewWeakReference;
        }

        @Override
        protected List<Exercise> doInBackground(final String... params) {
            String mDate = params[0];
            return AppDatabase.getAppDatabase(GymNotesApplication.getmInstance()).exerciseDao().
                    getExercisesByDate(mDate);
        }

        @Override
        protected void onPostExecute(List<Exercise> exercises) {
            super.onPostExecute(exercises);

            if (exercises != null && !exercises.isEmpty()) {
                if (textViewWeakReference.get() != null) {
                    textViewWeakReference.get().setText(textViewWeakReference.get().getText() + exercises.get(exercises.size() - 1).getExerciseName());
                }
            }
        }
    }
}
