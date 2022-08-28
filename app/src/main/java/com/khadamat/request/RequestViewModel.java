package com.khadamat.request;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.khadamat.model.RequestModel;

import java.util.ArrayList;

public class RequestViewModel extends ViewModel
{

    private MutableLiveData<Boolean> booleanMutableLiveData;
    private MutableLiveData<String> stringMutableLiveData;
    private MutableLiveData<ArrayList<RequestModel>> requestsMutableLiveData;
    private ArrayList<RequestModel> requestModels;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference retRef;


    public RequestViewModel()
    {
        booleanMutableLiveData = new MutableLiveData<>();
        stringMutableLiveData = new MutableLiveData<>();
        requestsMutableLiveData = new MutableLiveData<>();
        requestModels = new ArrayList<>();
        firebaseAuth = FirebaseAuth.getInstance();
        retRef = FirebaseDatabase.getInstance().getReference();
    }

    public LiveData<ArrayList<RequestModel>> retriveRequests()
    {

        retRef
                .child("Requests")
                .child(firebaseAuth.getUid())
                .addValueEventListener(new ValueEventListener()
                {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot)
                    {
                        requestModels.clear();

                        if (snapshot.exists())
                        {
                            for (DataSnapshot dataSnapshot : snapshot.getChildren())
                            {
                                RequestModel model = dataSnapshot.getValue(RequestModel.class);
                                requestModels.add(model);
                            }
                            requestsMutableLiveData.postValue(requestModels);
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error)
                    {
                        stringMutableLiveData.setValue(error.getMessage());
                        booleanMutableLiveData.postValue(false);
                    }
                });

        return requestsMutableLiveData;
    }

    public LiveData<Boolean> deleteReservaion(String randomKey)
    {
        retRef
                .child("Requests")
                .child(firebaseAuth.getUid())
                .child(randomKey)
                .removeValue();

        booleanMutableLiveData.postValue(true);

        return booleanMutableLiveData;
    }
}