package com.khadamat.bottomSheet;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class BottomSheetForgetPasswordViewModel extends ViewModel
{
    private MutableLiveData<Boolean> booleanMutableLiveData;
    private MutableLiveData<String> stringMutableLiveData;
    private FirebaseAuth auth;


    public BottomSheetForgetPasswordViewModel()
    {
        booleanMutableLiveData = new MutableLiveData<>();
        stringMutableLiveData = new MutableLiveData<>();
        auth = FirebaseAuth.getInstance();
    }

    public LiveData<Boolean> forgetPassword(String email)
    {
        auth
                .sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>()
                {
                    @Override
                    public void onComplete(@NonNull Task<Void> task)
                    {
                        if (task.isSuccessful())
                        {
                            booleanMutableLiveData.postValue(true);
                        }

                        else
                        {
                            stringMutableLiveData.setValue(task.getException().getMessage());
                            booleanMutableLiveData.postValue(false);
                        }
                    }
                });

        return booleanMutableLiveData;
    }
}
