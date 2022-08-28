package com.khadamat.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.khadamat.R;
import com.khadamat.databinding.ItemRequestsBinding;
import com.khadamat.interfaces.DeleteRequest;
import com.khadamat.model.RequestModel;

import java.util.ArrayList;

public class RequestsAdapter extends RecyclerView.Adapter<RequestsAdapter.RequestsVH>
{

    private ArrayList<RequestModel> requestModels;

    private DeleteRequest request;

    public RequestsAdapter(ArrayList<RequestModel> requestModels, DeleteRequest request)
    {
        this.requestModels = requestModels;
        this.request = request;
    }

    @NonNull
    @Override
    public RequestsVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        return new RequestsVH(ItemRequestsBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RequestsVH holder, int position)
    {
        RequestModel model = requestModels.get(position);

        if (model.getRadio().equals("قبول الطلب"))
        {
            Glide
                    .with(holder.itemView.getContext())
                    .load(model.getImage())
                    .placeholder(R.drawable.ic_loader_image)
                    .into(holder.binding.imgItemRequest);
            holder.binding.txtNameItemRequest.setText(model.getFirstName() + " " + model.getLastName());
            holder.binding.txtJobItemRequest.setText(model.getJob());
            holder.binding.txtRequestItemRequest.setText(model.getRadio());
        }

        else
        {
            Glide
                    .with(holder.itemView.getContext())
                    .load(model.getImage())
                    .placeholder(R.drawable.ic_loader_image)
                    .into(holder.binding.imgItemRequest);
            holder.binding.txtNameItemRequest.setText(model.getFirstName() + " " + model.getLastName());
            holder.binding.txtJobItemRequest.setText(model.getJob());
            holder.binding.txtRequestItemRequest.setText(model.getRadio());

            holder.binding.txtReasonItemRequest.setVisibility(View.VISIBLE);
            holder.binding.txtReasonItemRequest.setText("السبب هو: " + model.getReason());

        }

        holder.binding.imgDeleteItemRequest.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                request.deleteRequestFromEmployee(view, model);
            }
        });

    }

    @Override
    public int getItemCount() {
        return requestModels.size();
    }

    public class RequestsVH extends RecyclerView.ViewHolder
    {

        private ItemRequestsBinding binding;

        public RequestsVH(@NonNull ItemRequestsBinding binding)
        {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
