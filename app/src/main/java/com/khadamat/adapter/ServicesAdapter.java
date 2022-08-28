package com.khadamat.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.khadamat.R;
import com.khadamat.databinding.ItemServicesBinding;
import com.khadamat.databinding.ItemSliderBinding;
import com.khadamat.interfaces.ClickService;
import com.khadamat.model.ServicesModel;

import java.util.ArrayList;
import java.util.List;

public class ServicesAdapter extends RecyclerView.Adapter<ServicesAdapter.ServicesViewHolder>
{

    private ArrayList<ServicesModel> servicesModels;

    private ClickService service;

    public ServicesAdapter(ArrayList<ServicesModel> servicesModels, ClickService service)
    {
        this.servicesModels = servicesModels;
        this.service = service;
    }

    @NonNull
    @Override
    public ServicesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        return new ServicesViewHolder(ItemServicesBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ServicesViewHolder holder, int position)
    {
        ServicesModel model = servicesModels.get(position);
        Glide
                .with(holder.itemView.getContext())
                .load(model.getImage())
                .placeholder(R.drawable.ic_loader_image)
                .into(holder.binding.imgItemService);

        holder.binding.txtItemService.setText(model.getTitle());

        holder.itemView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                service.ClickedServices(model, view);
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return servicesModels.size();
    }

    public class ServicesViewHolder extends RecyclerView.ViewHolder
    {

        private ItemServicesBinding binding;

        public ServicesViewHolder(@NonNull ItemServicesBinding binding)
        {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
