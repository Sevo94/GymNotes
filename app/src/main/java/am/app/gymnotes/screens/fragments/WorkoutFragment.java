package am.app.gymnotes.screens.fragments;

import android.app.DatePickerDialog;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.List;

import am.app.gymnotes.CalenderManager;
import am.app.gymnotes.Constants;
import am.app.gymnotes.ContextualActionOBar;
import am.app.gymnotes.ExerciseRemoveListener;
import am.app.gymnotes.GymNotesApplication;
import am.app.gymnotes.R;
import am.app.gymnotes.adapters.WorkoutLogAdapter;
import am.app.gymnotes.database.AppDatabase;
import am.app.gymnotes.database.entities.Exercise;
import am.app.gymnotes.screens.activities.HomeActivity;
import am.app.gymnotes.viewmodels.ViewModelModule;
import am.app.gymnotes.viewmodels.WorkoutViewModel;
import am.app.gymnotes.viewmodels.WorkoutViewModelNext;
import am.app.gymnotes.viewmodels.WorkoutViewModelPrev;


public class WorkoutFragment extends Fragment implements ContextualActionOBar,
        ExerciseRemoveListener {

    public interface FragmentLoadListener {
        void onFragmentLoadFinished();
    }

    private static final String TAG = WorkoutFragment.class.getCanonicalName();
    private static final String ARG_SECTION_NUMBER = "section_number";

    private FragmentLoadListener fragmentLoadListener;
    private FragmentActivity mActivity;
    private String mDate = "";

    private ViewModelModule viewModel;
    private WorkoutLogAdapter mAdapter = new WorkoutLogAdapter(this, this);

    private boolean contextuatActionBar;

    private final Observer<List<Exercise>> observer = new Observer<List<Exercise>>() {
        @Override
        public void onChanged(@Nullable List<Exercise> exerciseList) {
            Log.i(TAG, "onChanged!!!!!" + (exerciseList != null && !(exerciseList.isEmpty()) ? exerciseList.get(0).getExerciseDate() : CalenderManager.getInstance().getDate()));
            if (exerciseList != null) {

                int lFCount = (fragmentLoadListener != null) ? ((HomeActivity) fragmentLoadListener).getLoadedFragmentsCount() : 0;

                Log.i(TAG, "exercise added on this date!!!!!");

                //&& exerciseList.get(0).getExerciseDate().equals(CalenderManager.getInstance().getCurrentDate()
                mAdapter.updateExercises(exerciseList);
            }
        }
    };

    private final Observer<Boolean> mOnPageSelectedObserver = new Observer<Boolean>() {
        @Override
        public void onChanged(@Nullable Boolean aBoolean) {
            mAdapter.clearAllSelectedItems();
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
            mActivity = (HomeActivity) context;
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

        if (mActivity != null && !mActivity.isFinishing()) {
            ((HomeActivity) mActivity).getOnPageSelected().observe(WorkoutFragment.this, mOnPageSelectedObserver);
        }
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
            Log.i(TAG, "section number = " + sectionNumber);
            if (isAdded()) {
                initFragmentView(view);
                int lFCount = (fragmentLoadListener != null) ? ((HomeActivity) fragmentLoadListener).getLoadedFragmentsCount() : 0;
                if (lFCount == Constants.FRAGMENTS_TO_LOAD) {
                    mDate = CalenderManager.getInstance().getDate();
                    textView.setText(CalenderManager.getInstance().getFormattedDate());

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
                            break;
                        case 1:
                            textView.setText(CalenderManager.getInstance().getNextDay());
                            mDate = CalenderManager.getInstance().getNextDate();
                            break;
                        case 3:
                            textView.setText(CalenderManager.getInstance().getPreviousDay());
                            mDate = CalenderManager.getInstance().getPreviousDate();
                            break;
                    }
                }
                //new FetchExercisesTask(new WeakReference<>(textView)).execute(mDate);
            }
        }
    }

    private void initFragmentView(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.exercise_list_rv);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        mAdapter.setContext(getContext());
        recyclerView.setAdapter(mAdapter);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.i(TAG, "onDestroyView");
    }

    @Override
    public void onContextChanged(boolean isActivated) {
        if (mActivity != null && !mActivity.isFinishing()) {
            contextuatActionBar = isActivated;
            mActivity.invalidateOptionsMenu();
        }
    }

    @Override
    public void onExercisesDeleted(List<Exercise> exerciseList) {
        viewModel.deleteExercises(exerciseList);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        if (contextuatActionBar) {
            inflater.inflate(R.menu.menu_multipleselect, menu);
        } else {
            inflater.inflate(R.menu.menu_home, menu);
        }
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        Intent intent;

        switch (id) {
            case R.id.action_settings:
//                intent = new Intent(getActivity(), ExerciseChooserActivity.class);
//                intent.putExtra("date", mDate);
//                startActivityForResult(intent, 2);
                break;
            case R.id.action_delete:
                if (mAdapter != null) {
                    mAdapter.deleteExercises();
                }
                break;
            case R.id.action_calender:

                CalenderManager.DateModel dateModel = CalenderManager.getInstance().getSelectedPageDate();

                final int mYear = dateModel.getYear();
                final int mMonth = dateModel.getMonth();
                final int mDay = dateModel.getDay();

                if (mActivity != null && !mActivity.isFinishing()) {
                    DatePickerDialog datePickerDialog = new DatePickerDialog(mActivity,
                            new DatePickerDialog.OnDateSetListener() {

                                @Override
                                public void onDateSet(DatePicker view, int year,
                                                      int monthOfYear, int dayOfMonth) {
                                    if (mActivity != null) {
                                        ((HomeActivity) mActivity).updateViewPager(year, monthOfYear, dayOfMonth);
                                    }
                                }
                            }, mYear, mMonth, mDay);

                    datePickerDialog.setButton(DatePickerDialog.BUTTON_POSITIVE, getResources().getString(R.string.jump_to_date), datePickerDialog);
                    datePickerDialog.setButton(DatePickerDialog.BUTTON_NEGATIVE, getResources().getString(R.string.cancel), datePickerDialog);
                    datePickerDialog.show();

                    /* because button's text appear in all capital letters */
                    datePickerDialog.getButton(DialogInterface.BUTTON_POSITIVE).setTransformationMethod(null);
                    datePickerDialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTransformationMethod(null);
                }
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
