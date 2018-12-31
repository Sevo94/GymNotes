package am.app.gymnotes.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import am.app.gymnotes.R;
import am.app.gymnotes.database.entities.Exercise;
import am.app.gymnotes.screens.fragments.ExerciseChooserFragment.OnListFragmentInteractionListener;

public class WorkoutLogAdapter extends RecyclerView.Adapter<WorkoutLogAdapter.ViewHolder> {

    private List<Exercise> mExerciseList;
    private OnListFragmentInteractionListener mListener;

    public WorkoutLogAdapter() {
        mExerciseList = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.exercise_item, parent, false);

        return  new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        holder.mContentView.setText(mExerciseList.get(position).getExerciseName());
    }

    public void updateExercises(List<Exercise> exerciseList) {
        mExerciseList = exerciseList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mExerciseList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final TextView mContentView;

        ViewHolder(View view) {
            super(view);
            mContentView = view.findViewById(R.id.content);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}

