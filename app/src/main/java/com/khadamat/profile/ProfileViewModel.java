package com.khadamat.profile;

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
import com.google.firebase.firestore.auth.User;
import com.khadamat.model.UserModel;

public class ProfileViewModel extends ViewModel
{

    private MutableLiveData<String> stringMutableLiveData;
    private MutableLiveData<UserModel> employeeModelMutableLiveData;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference dataRef;



    public ProfileViewModel()
    {
        stringMutableLiveData = new MutableLiveData<>();
        employeeModelMutableLiveData = new MutableLiveData<>();
        firebaseAuth = FirebaseAuth.getInstance();
        dataRef = FirebaseDatabase.getInstance().getReference();
    }

    public LiveData<UserModel> RetriveData()
    {

        dataRef
                .child("Users")
                .child(firebaseAuth.getUid())
                .addValueEventListener(new ValueEventListener()
                {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot)
                    {
                        if (snapshot.exists())
                        {
                            UserModel userModel = snapshot.getValue(UserModel.class);
                            employeeModelMutableLiveData.setValue(userModel);

                            stringMutableLiveData.setValue("Success");
                        }

                        else
                        {
                            stringMutableLiveData.setValue("Error");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error)
                    {
                        stringMutableLiveData.setValue(error.getMessage());
                    }
                });


        return employeeModelMutableLiveData;
    }

}