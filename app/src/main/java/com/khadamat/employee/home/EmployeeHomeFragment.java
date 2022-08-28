package com.khadamat.employee.home;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

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
import com.khadamat.adapter.EmployeesAdapter;
import com.khadamat.databinding.EmployeeHomeFragmentBinding;
import com.khadamat.interfaces.ClickedEmployeeDetail;
import com.khadamat.model.EmployeeModel;

import java.util.ArrayList;

public class EmployeeHomeFragment extends Fragment implements ClickedEmployeeDetail
{

    private EmployeeHomeFragmentArgs homeFragmentArgs;
    private String serviceName, servicesName;

    private EmployeeHomeFragmentBinding binding;
    private EmployeeHomeViewModel employeeHomeViewModel;

    private EmployeesAdapter employeesAdapter;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        binding = EmployeeHomeFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);



        initView();
        initViewModel();
        retriveViewModel();
        clickedViews();

    }

    private void initView()
    {
        homeFragmentArgs = EmployeeHomeFragmentArgs.fromBundle(getArguments());
        serviceName = homeFragmentArgs.getServiceName();
        homeFragmentArgs = EmployeeHomeFragmentArgs.fromBundle(getArguments());
        servicesName = homeFragmentArgs.getServicesName();
    }

    private void initViewModel()
    {
        employeeHomeViewModel = new ViewModelProvider(this).get(EmployeeHomeViewModel.class);
    }

    private void retriveViewModel()
    {
        binding.loadingEmployee.setVisibility(View.VISIBLE);

        employeeHomeViewModel
                .retriveEmployees(serviceName)
                .observe(getViewLifecycleOwner(), new Observer<ArrayList<EmployeeModel>>()
                {
                    @Override
                    public void onChanged(ArrayList<EmployeeModel> employeeModels)
                    {
                        binding.loadingEmployee.setVisibility(View.GONE);

                        employeesAdapter = new EmployeesAdapter(employeeModels, EmployeeHomeFragment.this);
                        binding.rVEmployee.setAdapter(employeesAdapter);
                        binding.rVEmployee.setLayoutManager(new LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false));
                        binding.rVEmployee.addItemDecoration(new DividerItemDecoration(requireActivity(), DividerItemDecoration.VERTICAL));
                    }
                });

        employeeHomeViewModel
                .retriveEmployees(servicesName)
                .observe(getViewLifecycleOwner(), new Observer<ArrayList<EmployeeModel>>()
                {
                    @Override
                    public void onChanged(ArrayList<EmployeeModel> employeeModels)
                    {
                        binding.loadingEmployee.setVisibility(View.GONE);

                        employeesAdapter = new EmployeesAdapter(employeeModels, EmployeeHomeFragment.this);
                        binding.rVEmployee.setAdapter(employeesAdapter);
                        binding.rVEmployee.setLayoutManager(new LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false));
                        binding.rVEmployee.addItemDecoration(new DividerItemDecoration(requireActivity(), DividerItemDecoration.VERTICAL));
                    }
                });
    }

    private void clickedViews()
    {
    }

    @Override
    public void EmployeeDetails(EmployeeModel employeeModel, View view)
    {
        EmployeeHomeFragmentDirections.ActionEmployeeHomeFragmentToEmployeeDetailsFragment action = EmployeeHomeFragmentDirections.actionEmployeeHomeFragmentToEmployeeDetailsFragment(employeeModel);
        action.setDetailsEmployee(employeeModel);
        Navigation.findNavController(view).navigate(action);
    }
}