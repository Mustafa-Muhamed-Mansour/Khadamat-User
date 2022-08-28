package com.khadamat.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.khadamat.R;
import com.khadamat.databinding.ItemWorksEmployeeBinding;
import com.khadamat.model.WorkEmployeeModel;

import java.util.ArrayList;

public class WorksEmployeeAdapter extends RecyclerView.Adapter<WorksEmployeeAdapter.WorksEmployeeViewHolder>
{

    private ArrayList<WorkEmployeeModel> workEmployeeModels;

    public WorksEmployeeAdapter(ArrayList<WorkEmployeeModel> workEmployeeModels)
    {
        this.workEmployeeModels = workEmployeeModels;
    }

    @NonNull
    @Override
    public WorksEmployeeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        return new WorksEmployeeViewHolder(ItemWorksEmployeeBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull WorksEmployeeViewHolder holder, int position)
    {
        WorkEmployeeModel model = workEmployeeModels.get(position);

        Glide
                .with(holder.itemView.getContext())
                .load(model.getImage())
                .placeholder(R.drawable.ic_loader_image)
                .into(holder.binding.imgItemWorkEmployee);
        holder.binding.txtItemWorkEmployee.setText(model.getTitle());
    }

    @Override
    public int getItemCount()
    {
        return workEmployeeModels.size();
    }

    public class WorksEmployeeViewHolder extends RecyclerView.ViewHolder
    {

        private ItemWorksEmployeeBinding binding;

        public WorksEmployeeViewHolder(@NonNull ItemWorksEmployeeBinding binding)
        {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
