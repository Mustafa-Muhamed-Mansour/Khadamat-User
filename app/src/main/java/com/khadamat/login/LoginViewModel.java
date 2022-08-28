package com.khadamat.login;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginViewModel extends ViewModel
{

    private MutableLiveData<Boolean> booleanMutableLiveData;
    private MutableLiveData<String> stringMutableLiveData;
    private FirebaseAuth auth;

    public LoginViewModel()
    {
        booleanMutableLiveData = new MutableLiveData<>();
        stringMutableLiveData = new MutableLiveData<>();
        auth = FirebaseAuth.getInstance();
    }

    public LiveData<Boolean> loginUser(String email, String password)
    {

        auth
                .signInWithEmailAndPassword(email, password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>()
                {
                    @Override
                    public void onSuccess(AuthResult authResult)
                    {
                        booleanMutableLiveData.postValue(true);
                    }
                }).addOnFailureListener(new OnFailureListener()
        {
            @Override
            public void onFailure(@NonNull Exception e)
            {
                stringMutableLiveData.setValue(e.getMessage());
                booleanMutableLiveData.postValue(false);
            }
        });

        return booleanMutableLiveData;
    }
}