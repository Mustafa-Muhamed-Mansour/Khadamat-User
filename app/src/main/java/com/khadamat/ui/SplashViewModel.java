package com.khadamat.ui;

import android.os.Handler;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SplashViewModel extends ViewModel
{

    private MutableLiveData<Boolean> booleanMutableLiveData;


    public SplashViewModel()
    {
        booleanMutableLiveData = new MutableLiveData<>();
    }


    public LiveData<Boolean> postDelay()
    {

        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                booleanMutableLiveData.setValue(true);
            }
        }, 11550);

        return booleanMutableLiveData;
    }
}