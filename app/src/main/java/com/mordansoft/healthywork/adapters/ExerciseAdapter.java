package com.mordansoft.healthywork.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.mordansoft.healthywork.R;
import com.mordansoft.healthywork.models.Exercise;

import java.util.List;

public class ExerciseAdapter  extends RecyclerView.Adapter<ExerciseAdapter.ViewHolder>{


    private final List<Exercise> listExercise;
    private Listener listener;

    public ExerciseAdapter(List<Exercise> listExercise) { //constructor
        this.listExercise = listExercise;
    }

    public interface Listener {
        void onClick(int position);
    }

    public void setListener(ExerciseAdapter.Listener listener){
        this.listener = listener;
    }

    @NonNull
    @Override
    public ExerciseAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, final int viewType) { //view for one item
        CardView v = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.exercise_card, parent, false);
        return new ExerciseAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ExerciseAdapter.ViewHolder viewHolder, final int position) {  //download data from list to THE ite
        Exercise exercise = listExercise.get(position);
        CardView cardView = viewHolder.cardView;
        viewHolder.v_name.setText(String.valueOf(exercise.getName()));
        viewHolder.v_count.setText(String.valueOf(exercise.getCount()));

        if (exercise.isEnable()){
            viewHolder.v_enable.setVisibility(View.VISIBLE);
        } else {
            viewHolder.v_enable.setVisibility(View.INVISIBLE);
        }

        cardView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onClick(exercise.getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return listExercise.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        final TextView v_name;
        final ImageView v_enable;
        final TextView v_count;
        private final CardView cardView;

        public ViewHolder(CardView v){
            super(v);
            cardView       = v;
            v_name         = v.findViewById(R.id.exercise_card_name);
            v_enable       = v.findViewById(R.id.exercise_card_enable);
            v_count         = v.findViewById(R.id.exercise_card_count);
        }
    }

}
