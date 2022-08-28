package com.khadamat.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.khadamat.R;
import com.khadamat.databinding.ItemReviewBinding;
import com.khadamat.model.ReviewModel;

import java.util.ArrayList;

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ReviewsViewHolder>
{

    private ArrayList<ReviewModel> reviewModels;

    public ReviewsAdapter(ArrayList<ReviewModel> reviewModels)
    {
        this.reviewModels = reviewModels;
    }

    @NonNull
    @Override
    public ReviewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        return new ReviewsViewHolder(ItemReviewBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewsViewHolder holder, int position)
    {
        ReviewModel model = reviewModels.get(position);

        Glide
                .with(holder.itemView.getContext())
                .load(model.getImage())
                .placeholder(R.drawable.ic_loader_image)
                .into(holder.binding.imgItemReview);
        holder.binding.txtNameItemReview.setText(model.getName());
        holder.binding.txtLocationItemReview.setText(model.getLocation());
        holder.binding.txtOpinionItemReview.setText(model.getOpinion());
        holder.binding.txtNumberLikeItemReview.setText(String.valueOf(model.getNumberLikes()));
        holder.binding.txtNumberDislikeItemReview.setText(String.valueOf(model.getNumberDislikes()));

    }

    @Override
    public int getItemCount()
    {
        return reviewModels.size();
    }

    public class ReviewsViewHolder extends RecyclerView.ViewHolder
    {

        private ItemReviewBinding binding;

        public ReviewsViewHolder(@NonNull ItemReviewBinding binding)
        {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
