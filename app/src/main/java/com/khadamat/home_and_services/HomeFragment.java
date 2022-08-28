package com.khadamat.home_and_services;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.khadamat.R;
import com.khadamat.adapter.ServicesAdapter;
import com.khadamat.adapter.SlidersAdapter;
import com.khadamat.adapter.WorksAdapter;
import com.khadamat.databinding.HomeFragmentBinding;
import com.khadamat.interfaces.ClickService;
import com.khadamat.interfaces.ClickSlider;
import com.khadamat.model.ServicesModel;
import com.khadamat.model.SliderModel;
import com.khadamat.model.WorkEmployeeModel;

import java.util.ArrayList;

public class HomeFragment extends Fragment implements ClickService , ClickSlider
{

    private HomeFragmentBinding binding;
    private HomeViewModel homeViewModel;
    private ServicesAdapter servicesAdapter;
    private WorksAdapter worksAdapter;
    private SlidersAdapter slidersAdapter;




    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        binding = HomeFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);


        initViewModel();
        retriveViewModel(view);
        clickedViews();

    }

    private void initViewModel()
    {
        homeViewModel = new ViewModelProvider(requireActivity()).get(HomeViewModel.class);
    }

    private void retriveViewModel(View view)
    {

        binding.loadingService.setVisibility(View.VISIBLE);
        homeViewModel
                .retriveLimitServices()
                .observe(getViewLifecycleOwner(), new Observer<ArrayList<ServicesModel>>()
                {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onChanged(ArrayList<ServicesModel> servicesModels)
                    {
                        binding.loadingService.setVisibility(View.GONE);

                        servicesAdapter = new ServicesAdapter(servicesModels, HomeFragment.this);
                        binding.rV.setAdapter(servicesAdapter);
                        binding.rV.setLayoutManager(new LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false));
                        binding.rV.addItemDecoration(new DividerItemDecoration(requireActivity(), DividerItemDecoration.HORIZONTAL));
                        servicesAdapter.notifyDataSetChanged();
                    }
                });


        binding.loadingWork.setVisibility(View.VISIBLE);
        homeViewModel
                .retriveWorks()
                .observe(getViewLifecycleOwner(), new Observer<ArrayList<WorkEmployeeModel>>()
                {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onChanged(ArrayList<WorkEmployeeModel> workEmployeeModels)
                    {
                        binding.loadingWork.setVisibility(View.GONE);

                        worksAdapter = new WorksAdapter(workEmployeeModels);
                        binding.rVWorks.setAdapter(worksAdapter);
                        binding.rVWorks.setLayoutManager(new LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false));
                        binding.rVWorks.addItemDecoration(new DividerItemDecoration(requireActivity(), DividerItemDecoration.HORIZONTAL));
                        worksAdapter.notifyDataSetChanged();
                    }
                });

        homeViewModel
                .retriveSliders()
                .observe(getViewLifecycleOwner(), new Observer<ArrayList<SliderModel>>()
                {
                    @Override
                    public void onChanged(ArrayList<SliderModel> sliderModels)
                    {
                        slidersAdapter = new SlidersAdapter(sliderModels, view, HomeFragment.this);
                        binding.sliderImage.setSliderAdapter(slidersAdapter);
                    }
                });
    }

    private void clickedViews()
    {
        binding
                .txtAllServices
                .setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
                        Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_servicesFragment);
                    }
                });
    }

    @Override
    public void ClickedServices(ServicesModel model, View view)
    {

        if (model.getTitle().equals("desktop"))
        {
            HomeFragmentDirections.ActionHomeFragmentToEmployeeHomeFragment action = HomeFragmentDirections.actionHomeFragmentToEmployeeHomeFragment();
            action.setServicesName("Desktop");
            Navigation.findNavController(view).navigate(action);
        }

        if (model.getTitle().equals("mobile"))
        {
            HomeFragmentDirections.ActionHomeFragmentToEmployeeHomeFragment action = HomeFragmentDirections.actionHomeFragmentToEmployeeHomeFragment();
            action.setServicesName("Mobile");
            Navigation.findNavController(view).navigate(action);
        }

        if (model.getTitle().equals("ai"))
        {
            HomeFragmentDirections.ActionHomeFragmentToEmployeeHomeFragment action = HomeFragmentDirections.actionHomeFragmentToEmployeeHomeFragment();
            action.setServicesName("AI");
            Navigation.findNavController(view).navigate(action);
        }

        if (model.getTitle().equals("web"))
        {
            HomeFragmentDirections.ActionHomeFragmentToEmployeeHomeFragment action = HomeFragmentDirections.actionHomeFragmentToEmployeeHomeFragment();
            action.setServicesName("Web");
            Navigation.findNavController(view).navigate(action);
        }

        if (model.getTitle().equals("cyber security"))
        {
            HomeFragmentDirections.ActionHomeFragmentToEmployeeHomeFragment action = HomeFragmentDirections.actionHomeFragmentToEmployeeHomeFragment();
            action.setServicesName("Cyber Security");
            Navigation.findNavController(view).navigate(action);
        }

        if (model.getTitle().equals("data science"))
        {
            HomeFragmentDirections.ActionHomeFragmentToEmployeeHomeFragment action = HomeFragmentDirections.actionHomeFragmentToEmployeeHomeFragment();
            action.setServicesName("Data Science");
            Navigation.findNavController(view).navigate(action);
        }

        if (model.getTitle().equals("ui&ux"))
        {
            HomeFragmentDirections.ActionHomeFragmentToEmployeeHomeFragment action = HomeFragmentDirections.actionHomeFragmentToEmployeeHomeFragment();
            action.setServicesName("UI&UX");
            Navigation.findNavController(view).navigate(action);
        }

        if (model.getTitle().equals("seo"))
        {
            HomeFragmentDirections.ActionHomeFragmentToEmployeeHomeFragment action = HomeFragmentDirections.actionHomeFragmentToEmployeeHomeFragment();
            action.setServicesName("Seo");
            Navigation.findNavController(view).navigate(action);
        }

        if (model.getTitle().equals("testing"))
        {
            HomeFragmentDirections.ActionHomeFragmentToEmployeeHomeFragment action = HomeFragmentDirections.actionHomeFragmentToEmployeeHomeFragment();
            action.setServicesName("Testing");
            Navigation.findNavController(view).navigate(action);
        }

        if (model.getTitle().equals("graphic designer"))
        {
            HomeFragmentDirections.ActionHomeFragmentToEmployeeHomeFragment action = HomeFragmentDirections.actionHomeFragmentToEmployeeHomeFragment();
            action.setServicesName("Graphic Designer");
            Navigation.findNavController(view).navigate(action);
        }

        if (model.getTitle().equals("data analysis"))
        {
            HomeFragmentDirections.ActionHomeFragmentToEmployeeHomeFragment action = HomeFragmentDirections.actionHomeFragmentToEmployeeHomeFragment();
            action.setServicesName("Data Analysis");
            Navigation.findNavController(view).navigate(action);
        }

//        else
//        {
//            Toast.makeText(requireActivity(), "الوظيفة غير متاحة", Toast.LENGTH_SHORT).show();
//        }

    }

    @Override
    public void ClickedSliders(SliderModel model, View view)
    {
        Toast.makeText(requireActivity(), model.getTitle(), Toast.LENGTH_SHORT).show();
    }
}