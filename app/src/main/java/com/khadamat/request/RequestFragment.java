package com.khadamat.request;

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
import com.khadamat.adapter.RequestsAdapter;
import com.khadamat.databinding.RequestFragmentBinding;
import com.khadamat.interfaces.DeleteRequest;
import com.khadamat.model.RequestModel;

import java.util.ArrayList;

public class RequestFragment extends Fragment implements DeleteRequest
{

    private RequestFragmentBinding binding;
    private RequestViewModel requestViewModel;
    
    private RequestsAdapter requestsAdapter;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        binding = RequestFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);


        initViewModel();
        rertiveViewModel();
    }

    private void initViewModel()
    {
        requestViewModel = new ViewModelProvider(requireActivity()).get(RequestViewModel.class);
    }

    private void rertiveViewModel()
    {

        binding.loadingRequest.setVisibility(View.VISIBLE);
        requestViewModel
                .retriveRequests()
                .observe(getViewLifecycleOwner(), new Observer<ArrayList<RequestModel>>()
                {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onChanged(ArrayList<RequestModel> requestModels)
                    {
                        binding.loadingRequest.setVisibility(View.GONE);

                        requestsAdapter = new RequestsAdapter(requestModels, RequestFragment.this);
                        binding.rV.setAdapter(requestsAdapter);
                        binding.rV.setLayoutManager(new LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false));
                        binding.rV.addItemDecoration(new DividerItemDecoration(requireActivity(), DividerItemDecoration.VERTICAL));
                        requestsAdapter.notifyDataSetChanged();
                    }
                });
    }

    @Override
    public void deleteRequestFromEmployee(View view, RequestModel requestModel)
    {
        requestViewModel
                .deleteReservaion(requestModel.getRandomKey())
                .observe(getViewLifecycleOwner(), new Observer<Boolean>()
                {
                    @Override
                    public void onChanged(Boolean aBoolean)
                    {
                        if (aBoolean)
                        {
                            Toast.makeText(requireActivity(), "تم مسح رد الطلب بنجاح ..", Toast.LENGTH_SHORT).show();
                            Navigation.findNavController(view).navigate(R.id.action_requestFragment_to_homeFragment);
                        }

                        else
                        {
                            Toast.makeText(requireActivity(), "Error", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}