package com.khadamat.employee.home;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.khadamat.model.EmployeeModel;

import java.util.ArrayList;

public class EmployeeHomeViewModel extends ViewModel
{

    private MutableLiveData<String> stringMutableLiveData;
    private MutableLiveData<ArrayList<EmployeeModel>> listMutableLiveData;
    private DatabaseReference retEmployee;
    private ArrayList<EmployeeModel> employeeModels;

    public EmployeeHomeViewModel()
    {
        stringMutableLiveData = new MutableLiveData<>();
        listMutableLiveData = new MutableLiveData<>();
        retEmployee = FirebaseDatabase.getInstance().getReference();
        employeeModels = new ArrayList<>();
    }

    public LiveData<ArrayList<EmployeeModel>> retriveEmployees(String serviceName)
    {

        retEmployee
                .child("Jobs")
                .child(serviceName)
                .addValueEventListener(new ValueEventListener()
                {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot)
                    {
                        employeeModels.clear();

                        for (DataSnapshot dataSnapshot : snapshot.getChildren())
                        {
                            if (snapshot.exists())
                            {
                                EmployeeModel employeeModel = dataSnapshot.getValue(EmployeeModel.class);
                                employeeModels.add(employeeModel);
                            }
                            listMutableLiveData.postValue(employeeModels);
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