package com.mordansoft.healthywork;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ExerciseAdapter  extends RecyclerView.Adapter<ExerciseAdapter.ViewHolder>{


    private final List<Exercise> listExercise;
    private final Context mContext;
    private Listener listener;

    public ExerciseAdapter(List<Exercise> listExercise, Context mContext) { //constructor
        this.listExercise = listExercise;
        this.mContext = mContext;
    }

    interface Listener {
        void onClick(int position);
    }

    public void setListener(ExerciseAdapter.Listener listener){
        this.listener = listener;
    }

    @Override
    public ExerciseAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, final int viewType) { //view for one item
        CardView v = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.exercise_card, parent, false);
        return new ExerciseAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ExerciseAdapter.ViewHolder viewHolder, final int position) {  //download data from list to THE ite
        Exercise exercise = listExercise.get(position);
        CardView cardView = viewHolder.cardView;
        viewHolder.v_name.setText(String.valueOf        (exercise.getName()));
        viewHolder.v_enable.setText(String.valueOf        (exercise.isEnable()));


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
        final TextView v_enable;
        private final CardView cardView;

        public ViewHolder(CardView v){
            super(v);
            cardView       = v;
            v_name         = v.findViewById(R.id.exercise_card_name);
            v_enable       = v.findViewById(R.id.exercise_card_enable);
        }
    }

}
