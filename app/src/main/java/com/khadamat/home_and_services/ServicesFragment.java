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
import com.khadamat.databinding.FragmentServicesBinding;
import com.khadamat.interfaces.ClickService;
import com.khadamat.model.ServicesModel;

import java.util.ArrayList;

public class ServicesFragment extends Fragment implements ClickService
{

    private FragmentServicesBinding binding;
    private ServicesViewModel servicesViewModel;

    private ServicesAdapter servicesAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        binding = FragmentServicesBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);


        initViewModel();
        retriveViewModel();
        clickedViews();

    }

    private void initViewModel()
    {
        servicesViewModel = new ViewModelProvider(this).get(ServicesViewModel.class);
    }

    private void retriveViewModel()
    {
        binding.loadingService.setVisibility(View.VISIBLE);

        servicesViewModel
                .retriveAllServices()
                .observe(getViewLifecycleOwner(), new Observer<ArrayList<ServicesModel>>()
                {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onChanged(ArrayList<ServicesModel> servicesModels)
                    {
                        binding.loadingService.setVisibility(View.GONE);

                        servicesAdapter = new ServicesAdapter(servicesModels, ServicesFragment.this);
                        binding.rV.setAdapter(servicesAdapter);
                        binding.rV.setLayoutManager(new LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false));
                        binding.rV.addItemDecoration(new DividerItemDecoration(requireActivity(), DividerItemDecoration.VERTICAL));
                        servicesAdapter.notifyDataSetChanged();
                    }
                });
    }

    private void clickedViews()
    {
        binding
                .imgClose
                .setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
                        Navigation.findNavController(view).navigate(R.id.action_servicesFragment_to_homeFragment);
                    }
                });
    }

    @Override
    public void ClickedServices(ServicesModel model, View view)
    {

        if (model.getTitle().equals("desktop"))
        {
            ServicesFragmentDirections.ActionServicesFragmentToEmployeeHomeFragment action = ServicesFragmentDirections.actionServicesFragmentToEmployeeHomeFragment();
            action.setServicesName("Desktop");
            Navigation.findNavController(view).navigate(action);
        }

        if (model.getTitle().equals("mobile"))
        {
            ServicesFragmentDirections.ActionServicesFragmentToEmployeeHomeFragment action = ServicesFragmentDirections.actionServicesFragmentToEmployeeHomeFragment();
            action.setServicesName("Mobile");
            Navigation.findNavController(view).navigate(action);
        }

        if (model.getTitle().equals("ai"))
        {
            ServicesFragmentDirections.ActionServicesFragmentToEmployeeHomeFragment action = ServicesFragmentDirections.actionServicesFragmentToEmployeeHomeFragment();
            action.setServicesName("AI");
            Navigation.findNavController(view).navigate(action);
        }

        if (model.getTitle().equals("web"))
        {
            ServicesFragmentDirections.ActionServicesFragmentToEmployeeHomeFragment action = ServicesFragmentDirections.actionServicesFragmentToEmployeeHomeFragment();
            action.setServicesName("Web");
            Navigation.findNavController(view).navigate(action);
        }

        if (model.getTitle().equals("cyber security"))
        {
            ServicesFragmentDirections.ActionServicesFragmentToEmployeeHomeFragment action = ServicesFragmentDirections.actionServicesFragmentToEmployeeHomeFragment();
            action.setServicesName("Cyber Security");
            Navigation.findNavController(view).navigate(action);
        }

        if (model.getTitle().equals("data science"))
        {
            ServicesFragmentDirections.ActionServicesFragmentToEmployeeHomeFragment action = ServicesFragmentDirections.actionServicesFragmentToEmployeeHomeFragment();
            action.setServicesName("Data Science");
            Navigation.findNavController(view).navigate(action);
        }

        if (model.getTitle().equals("ui&ux"))
        {
            ServicesFragmentDirections.ActionServicesFragmentToEmployeeHomeFragment action = ServicesFragmentDirections.actionServicesFragmentToEmployeeHomeFragment();
            action.setServicesName("UI&UX");
            Navigation.findNavController(view).navigate(action);
        }

        if (model.getTitle().equals("seo"))
        {
            ServicesFragmentDirections.ActionServicesFragmentToEmployeeHomeFragment action = ServicesFragmentDirections.actionServicesFragmentToEmployeeHomeFragment();
            action.setServicesName("Seo");
            Navigation.findNavController(view).navigate(action);
        }

        if (model.getTitle().equals("testing"))
        {
            ServicesFragmentDirections.ActionServicesFragmentToEmployeeHomeFragment action = ServicesFragmentDirections.actionServicesFragmentToEmployeeHomeFragment();
            action.setServicesName("Testing");
            Navigation.findNavController(view).navigate(action);
        }

        if (model.getTitle().equals("graphic designer"))
        {
            ServicesFragmentDirections.ActionServicesFragmentToEmployeeHomeFragment action = ServicesFragmentDirections.actionServicesFragmentToEmployeeHomeFragment();
            action.setServicesName("Graphic Designer");
            Navigation.findNavController(view).navigate(action);
        }

        if (model.getTitle().equals("data analysis"))
        {
            ServicesFragmentDirections.ActionServicesFragmentToEmployeeHomeFragment action = ServicesFragmentDirections.actionServicesFragmentToEmployeeHomeFragment();
            action.setServicesName("Data Analysis");
            Navigation.findNavController(view).navigate(action);
        }

//        else
//        {
//            Toast.makeText(requireActivity(), "الوظيفة غير متاحة", Toast.LENGTH_SHORT).show();
//        }
    }
}