package com.khadamat.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.khadamat.R;
import com.khadamat.databinding.ItemSliderBinding;
import com.khadamat.interfaces.ClickSlider;
import com.khadamat.model.SliderModel;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.ArrayList;

public class SlidersAdapter extends SliderViewAdapter<SlidersAdapter.SlidersViewH>
{

    private ArrayList<SliderModel> sliderModels;
    private View view;
    private ClickSlider slider;


    public SlidersAdapter(ArrayList<SliderModel> sliderModels, View view, ClickSlider slider)
    {
        this.sliderModels = sliderModels;
        this.view = view;
        this.slider = slider;
    }

    @Override
    public SlidersViewH onCreateViewHolder(ViewGroup parent)
    {
        return new SlidersViewH(ItemSliderBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(SlidersViewH viewHolder, int position)
    {
        SliderModel model = sliderModels.get(position);

        Glide
                .with(view.getContext())
                .load(model.getImage())
                .placeholder(R.drawable.ic_loader_image)
                .into(viewHolder.binding.imgItemSlider);
        viewHolder.binding.btnNameItemSlider.setText(model.getTitle());

        viewHolder.binding.btnNameItemSlider.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                slider.ClickedSliders(model, view);
            }
        });
    }

    @Override
    public int getCount()
    {
        return sliderModels.size();
    }

    public class SlidersViewH extends SliderViewAdapter.ViewHolder
    {

        private ItemSliderBinding binding;

        public SlidersViewH(ItemSliderBinding binding)
        {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
