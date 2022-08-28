package com.khadamat.home_and_services;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.khadamat.model.ServicesModel;
import com.khadamat.model.WorkEmployeeModel;

import java.util.ArrayList;

public class ServicesViewModel extends ViewModel
{

    private MutableLiveData<String> stringMutableLiveData;
    private MutableLiveData<ArrayList<ServicesModel>> listMutableLiveData;
    private DatabaseReference retService;
    private ArrayList<ServicesModel> servicesModels;

    public ServicesViewModel()
    {
        stringMutableLiveData = new MutableLiveData<>();
        listMutableLiveData = new MutableLiveData<>();
        retService = FirebaseDatabase.getInstance().getReference();
        servicesModels = new ArrayList<>();
    }


    public LiveData<ArrayList<ServicesModel>> retriveAllServices()
    {
        retService
                .child("Services")
                .addValueEventListener(new ValueEventListener()
                {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot)
                    {
                        servicesModels.clear();

                        for (DataSnapshot dataSnapshot : snapshot.getChildren())
                        {
                            if (snapshot.exists())
                            {
                                ServicesModel servicesModel = dataSnapshot.getValue(ServicesModel.class);
                                servicesModels.add(servicesModel);
                            }
                            listMutableLiveData.postValue(servicesModels);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error)
                    {
                        stringMutableLiveData.setValue(error.getMessage());
                    }
                });

        return listMutableLiveData;
    }
}