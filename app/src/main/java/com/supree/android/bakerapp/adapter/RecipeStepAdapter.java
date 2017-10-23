package com.supree.android.bakerapp.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.supree.android.bakerapp.R;
import com.supree.android.bakerapp.models.Step;

import java.util.List;

public class RecipeStepAdapter extends RecyclerView.Adapter<RecipeStepAdapter.RecipeStepAdapterViewHolder> {

    private List<Step> steps;

    public RecipeStepAdapter(List<Step> steps) {
        this.steps = steps;
    }

    @Override
    public RecipeStepAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        final View view = inflater.inflate(R.layout.item_recipe_step, viewGroup, false);

        return new RecipeStepAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecipeStepAdapterViewHolder holder, int position) {
        String stepId = String.valueOf(steps.get(position).getId());

        holder.tvStepNumber.setText(stepId);
        holder.tvShortDesc.setText(steps.get(position).getShortDescription());
    }

    @Override
    public int getItemCount() {
        return steps.size();
    }

    class RecipeStepAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final View mView;
        final TextView tvStepNumber;
        final TextView tvShortDesc;

        public RecipeStepAdapterViewHolder(View view) {
            super(view);
            tvStepNumber = view.findViewById(R.id.tvStepNumber);
            tvShortDesc = view.findViewById(R.id.tvShortDesc);
            view.setOnClickListener(this);
            mView = view;
        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
        }
    }
}
