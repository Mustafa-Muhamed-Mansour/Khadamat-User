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
import com.khadamat.model.SliderModel;
import com.khadamat.model.WorkEmployeeModel;

import java.util.ArrayList;

public class HomeViewModel extends ViewModel
{
    private MutableLiveData<String> stringMutableLiveData;
    private MutableLiveData<ArrayList<ServicesModel>> listMutableLiveData;
    private MutableLiveData<ArrayList<WorkEmployeeModel>> arrayListMutableLiveData;
    private MutableLiveData<ArrayList<SliderModel>> sliderMutableLiveData;
    private DatabaseReference retService, retWork, retSlider;
    private ArrayList<ServicesModel> servicesModels;
    private ArrayList<WorkEmployeeModel> workEmployeeModels;
    private ArrayList<SliderModel> sliderModels;


    public HomeViewModel()
    {
        stringMutableLiveData = new MutableLiveData<>();
        listMutableLiveData = new MutableLiveData<>();
        arrayListMutableLiveData = new MutableLiveData<>();
        sliderMutableLiveData = new MutableLiveData<>();
        retService = FirebaseDatabase.getInstance().getReference();
        retWork = FirebaseDatabase.getInstance().getReference();
        retSlider = FirebaseDatabase.getInstance().getReference();
        servicesModels = new ArrayList<>();
        workEmployeeModels = new ArrayList<>();
        servicesModels = new ArrayList<>();
        sliderModels = new ArrayList<>();
    }

    public LiveData<ArrayList<ServicesModel>> retriveLimitServices()
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
                               if (dataSnapshot.hasChild("star") == true)
                               {
                                   ServicesModel servicesModel = dataSnapshot.getValue(ServicesModel.class);
                                   servicesModels.add(servicesModel);
                               }
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

    public LiveData<ArrayList<WorkEmployeeModel>> retriveWorks()
    {
        retWork
                .child("Works")
                .addValueEventListener(new ValueEventListener()
                {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot)
                    {

                        workEmployeeModels.clear();

                        for (DataSnapshot dataSnapshot : snapshot.getChildren())
                        {
                            if (snapshot.exists())
                            {
                                WorkEmployeeModel workEmployeeModel = dataSnapshot.getValue(WorkEmployeeModel.class);
                                workEmployeeModels.add(workEmployeeModel);
                            }
                            arrayListMutableLiveData.postValue(workEmployeeModels);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error)
                    {
                        stringMutableLiveData.setValue(error.getMessage());
                    }
                });

        return arrayListMutableLiveData;
    }

    public LiveData<ArrayList<SliderModel>> retriveSliders()
    {

        retSlider
                .child("Sliders")
                .addValueEventListener(new ValueEventListener()
                {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot)
                    {
                        sliderModels.clear();

                        if (snapshot.exists())
                        {
                            for (DataSnapshot dataSnapshot : snapshot.getChildren())
                            {
                                SliderModel model = dataSnapshot.getValue(SliderModel.class);
                                sliderModels.add(model);
                            }
                            sliderMutableLiveData.postValue(sliderModels);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error)
                    {
                        stringMutableLiveData.setValue(error.getMessage());
                    }
                });

        return sliderMutableLiveData;
    }

}