package am.app.gymnotes.screens.fragments;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
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
import am.app.gymnotes.viewmodels.ViewModelModule;
import am.app.gymnotes.viewmodels.WorkoutViewModel;
import am.app.gymnotes.viewmodels.WorkoutViewModelNext;
import am.app.gymnotes.viewmodels.WorkoutViewModelPrev;


public class WorkoutFragment extends Fragment {

    public interface FragmentLoadListener {
        void onFragmentLoadFinished();
    }

    private static final String TAG = WorkoutFragment.class.getCanonicalName();
    private static final String ARG_SECTION_NUMBER = "section_number";

    private FragmentLoadListener fragmentLoadListener;
    private String mDate = "";

    private ViewModelModule viewModel;

    private final Observer<List<Exercise>> observer = new Observer<List<Exercise>>() {
        @Override
        public void onChanged(@Nullable List<Exercise> exerciseList) {
            Log.i(TAG, "onChanged!!!!!" + (exerciseList != null && !(exerciseList.isEmpty()) ? exerciseList.get(0).getExerciseDate() : CalenderManager.getInstance().getDate()));
            if (exerciseList != null && !(exerciseList.isEmpty())) {
                textView.setText(textView.getText() + exerciseList.get(0).getExerciseName());

                int lFCount = (fragmentLoadListener != null) ? ((HomeActivity) fragmentLoadListener).getLoadedFragmentsCount() : 0;
                if (lFCount == Constants.FRAGMENTS_TO_LOAD && exerciseList.get(0).getExerciseDate().equals(CalenderManager.getInstance().getCurrentDate())) {
                    Log.i(TAG, "exercise added on this date!!!!!");
                }
            }
        }
    };

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
        Log.i(TAG, "onCreate");

        setHasOptionsMenu(true);

        int lFCount = (fragmentLoadListener != null) ? ((HomeActivity) fragmentLoadListener).getLoadedFragmentsCount() : 0;

        if (getArguments() != null) {
            int sectionNumber = (lFCount == Constants.FRAGMENTS_TO_LOAD) ? -234 : getArguments().getInt(ARG_SECTION_NUMBER);
            switch (sectionNumber) {
                case 0:
                    Log.i(TAG, "WorkoutViewModel!!!!!");
                    viewModel = ViewModelProviders.of(this).get(WorkoutViewModel.class);
                    viewModel.getWorkoutList().observe(WorkoutFragment.this, observer);
                    break;
                case 1:
                    Log.i(TAG, "WorkoutViewModelNext!!!!!");
                    viewModel = ViewModelProviders.of(this).get(WorkoutViewModelNext.class);
                    viewModel.getWorkoutList().observe(WorkoutFragment.this, observer);
                    break;
                case 3:
                    Log.i(TAG, "WorkoutViewModelPrev!!!!!");
                    viewModel = ViewModelProviders.of(this).get(WorkoutViewModelPrev.class);
                    viewModel.getWorkoutList().observe(WorkoutFragment.this, observer);
                    break;
            }
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.i(TAG, "onActivityCreated");
    }

    private TextView textView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.i(TAG, "onCreateView!!!!!");
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Log.i(TAG, "onViewCreated!!!!!");

        textView = view.findViewById(R.id.section_label);

        if (getArguments() != null) {
            int sectionNumber = getArguments().getInt(ARG_SECTION_NUMBER);
            Log.i(TAG, "sectionNumber = " + String.valueOf(sectionNumber));

            if (isAdded()) {
                int lFCount = (fragmentLoadListener != null) ? ((HomeActivity) fragmentLoadListener).getLoadedFragmentsCount() : 0;
                if (lFCount == Constants.FRAGMENTS_TO_LOAD) {
                    mDate = CalenderManager.getInstance().getDate();
                    textView.setText(CalenderManager.getInstance().getFormattedDate());

//                    if (viewModel != null) {
//                        viewModel.getWorkoutList().removeObserver(observer);
//                    }
                    viewModel = ViewModelProviders.of(this).get(WorkoutViewModel.class);
                    viewModel.getWorkoutList().observe(WorkoutFragment.this, observer);

                } else {
                    if (fragmentLoadListener != null) {
                        fragmentLoadListener.onFragmentLoadFinished();
                    }
                    switch (sectionNumber) {
                        case 0:
                            textView.setText(CalenderManager.getInstance().getFormattedDate());
                            mDate = CalenderManager.getInstance().getDate();

//                            Log.i(TAG, "WorkoutViewModel!!!!!");
//                            viewModel = ViewModelProviders.of(this).get(WorkoutViewModel.class);
                            //viewModel.getWorkoutList().observe(WorkoutFragment.this, observer);
                            break;
                        case 1:
                            textView.setText(CalenderManager.getInstance().getNextDay());
                            mDate = CalenderManager.getInstance().getNextDate();

//                            Log.i(TAG, "WorkoutViewModelNext!!!!!");
//                            viewModel = ViewModelProviders.of(this).get(WorkoutViewModelNext.class);
                            break;
                        case 3:
                            textView.setText(CalenderManager.getInstance().getPreviousDay());
                            mDate = CalenderManager.getInstance().getPreviousDate();

//                            Log.i(TAG, "WorkoutViewModelPrev!!!!!");
//                            viewModel = ViewModelProviders.of(this).get(WorkoutViewModelPrev.class);
                            break;
                    }

                    //viewModel.getWorkoutList().observe(WorkoutFragment.this, observer);
                }
                //new FetchExercisesTask(new WeakReference<>(textView)).execute(mDate);
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.i(TAG, "onDestroyView");
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

//        Log.i(TAG, "onActivityResult!!!!! RESULT CODE = " + resultCode);
//        //TODO change 2 with constant
//        if (requestCode == 2) {
//            if (resultCode == Activity.RESULT_OK) {
//                new FetchExercisesTask(new WeakReference<>(textView)).execute(mDate);
//            }
//        }
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
