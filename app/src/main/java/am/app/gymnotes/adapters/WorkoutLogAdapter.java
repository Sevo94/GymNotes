package am.app.gymnotes.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import am.app.gymnotes.R;
import am.app.gymnotes.database.entities.Exercise;
import am.app.gymnotes.screens.fragments.ExerciseChooserFragment.OnListFragmentInteractionListener;

public class WorkoutLogAdapter extends RecyclerView.Adapter<WorkoutLogAdapter.ViewHolder> {

    private List<Exercise> mExerciseList;
    private OnListFragmentInteractionListener mListener;

    private boolean multiSelectMode;
    private Set<String> mSelectedExercises = new HashSet<>();

    private Context context;

    public void setContext(Context context) {
        this.context = context;
    }

    public WorkoutLogAdapter() {
        mExerciseList = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.exercise_item, parent, false);

        final ViewHolder viewHolder = new ViewHolder(view);
        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                String exerciseId = mExerciseList.get(viewHolder.getAdapterPosition()).getExerciseId();
                if (multiSelectMode) {
                    if (mSelectedExercises.contains(exerciseId)) {
                        mSelectedExercises.remove(exerciseId);

                        if (mSelectedExercises.isEmpty()) {
                            multiSelectMode = false;
                        }

                        viewHolder.containerView.setBackgroundResource(R.drawable.exercise_item_shapte_deactive);
                    } else {
                        mSelectedExercises.add(exerciseId);
                        viewHolder.containerView.setBackgroundResource(R.drawable.exercise_item_shape);
                    }
                } else {
                    multiSelectMode = true;
                    mSelectedExercises.add(exerciseId);
                    viewHolder.containerView.setBackgroundResource(R.drawable.exercise_item_shape);
                }
                return true;
            }
        });

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (multiSelectMode) {

                    String exerciseId = mExerciseList.get(viewHolder.getAdapterPosition()).getExerciseId();
                    if (mSelectedExercises.contains(exerciseId)) {
                        mSelectedExercises.remove(exerciseId);

                        if (mSelectedExercises.isEmpty()) {
                            multiSelectMode = false;
                        }

                        viewHolder.containerView.setBackgroundResource(R.drawable.exercise_item_shapte_deactive);
                    } else {
                        mSelectedExercises.add(exerciseId);
                        viewHolder.containerView.setBackgroundResource(R.drawable.exercise_item_shape);
                    }
                }
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        String exerciseId = mExerciseList.get(holder.getAdapterPosition()).getExerciseId();

        if (multiSelectMode) {
            if (mSelectedExercises.contains(exerciseId)) {
                holder.containerView.setBackgroundResource(R.drawable.exercise_item_shape);
            } else {
                holder.containerView.setBackgroundResource(R.drawable.exercise_item_shapte_deactive);
            }
        } else {
            holder.containerView.setBackgroundResource(R.drawable.exercise_item_shapte_deactive);
        }
        holder.mContentView.setText(mExerciseList.get(position).getExerciseName());
    }

    public void updateExercises(List<Exercise> exerciseList) {
        mExerciseList = exerciseList;
        notifyDataSetChanged();
    }

    public void clearAllSelectedItems() {
        if (multiSelectMode) {
            multiSelectMode = false;
            mSelectedExercises.clear();
            notifyDataSetChanged();
        }
    }

    @Override
    public int getItemCount() {
        return mExerciseList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final View containerView;
        final TextView mContentView;

        ViewHolder(View view) {
            super(view);
            mContentView = view.findViewById(R.id.content);
            containerView = view.findViewById(R.id.container_layout);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}

