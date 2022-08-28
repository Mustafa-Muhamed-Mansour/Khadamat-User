package com.khadamat.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.khadamat.R;
import com.khadamat.databinding.ItemWorksBinding;
import com.khadamat.databinding.ItemWorksBindingImpl;
import com.khadamat.model.WorkEmployeeModel;

import java.util.ArrayList;

public class WorksAdapter extends RecyclerView.Adapter<WorksAdapter.WorksVH>
{

    private ArrayList<WorkEmployeeModel> workEmployeeModels;

    public WorksAdapter(ArrayList<WorkEmployeeModel> workEmployeeModels)
    {
        this.workEmployeeModels = workEmployeeModels;
    }

    @NonNull
    @Override
    public WorksVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        return new WorksVH(ItemWorksBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull WorksVH holder, int position)
    {
        WorkEmployeeModel employeeModel = workEmployeeModels.get(position);

        Glide
                .with(holder.itemView.getContext())
                .load(employeeModel.getImage())
                .placeholder(R.drawable.ic_loader_image)
                .into(holder.binding.imgItemWork);
        holder.binding.txtItemWorkEmployee.setText(employeeModel.getTitle());
    }

    @Override
    public int getItemCount()
    {
        return workEmployeeModels.size();
    }

    public class WorksVH extends RecyclerView.ViewHolder
    {

        private ItemWorksBinding binding;

        public WorksVH(@NonNull ItemWorksBinding binding)
        {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
