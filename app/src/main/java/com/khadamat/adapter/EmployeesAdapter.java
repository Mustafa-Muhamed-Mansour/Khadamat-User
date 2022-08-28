package com.khadamat.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.khadamat.R;
import com.khadamat.databinding.ItemEmployeeBinding;
import com.khadamat.interfaces.ClickedEmployeeDetail;
import com.khadamat.model.EmployeeModel;

import java.util.ArrayList;

public class EmployeesAdapter extends RecyclerView.Adapter<EmployeesAdapter.EmployeesVH>
{

    private ArrayList<EmployeeModel> employeeModels;

    private ClickedEmployeeDetail employeeDetail;

    public EmployeesAdapter(ArrayList<EmployeeModel> employeeModels, ClickedEmployeeDetail employeeDetail)
    {
        this.employeeModels = employeeModels;
        this.employeeDetail = employeeDetail;
    }

    @NonNull
    @Override
    public EmployeesVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        return new EmployeesVH(ItemEmployeeBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull EmployeesVH holder, int position)
    {
        EmployeeModel model = employeeModels.get(position);
        Glide
                .with(holder.itemView.getContext())
                .load(model.getImage())
                .placeholder(R.drawable.ic_loader_image)
                .into(holder.binding.imgItemEmployee);
        holder.binding.txtNameItemEmployee.setText(model.getFirstName() + " " + model.getLastName());

        holder.itemView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                employeeDetail.EmployeeDetails(model, view);
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return employeeModels.size();
    }

    public class EmployeesVH extends RecyclerView.ViewHolder
    {

        private ItemEmployeeBinding binding;

        public EmployeesVH(@NonNull ItemEmployeeBinding binding)
        {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
