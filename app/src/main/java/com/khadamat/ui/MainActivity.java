package com.khadamat.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;

import com.khadamat.R;
import com.khadamat.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity
{

    private NavController navController;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        initView(MainActivity.this);
    }


    private void initView(MainActivity mainActivity)
    {
        navController = Navigation.findNavController(mainActivity, R.id.nav_host);
        NavigationUI.setupWithNavController(binding.bottomNav, navController);
        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener()
        {
            @SuppressLint("NonConstantResourceId")
            @Override
            public void onDestinationChanged(@NonNull NavController navController, @NonNull NavDestination navDestination, @Nullable Bundle bundle)
            {
                switch (navDestination.getId())
                {
                    case R.id.splashFragment:
                    case R.id.loginFragment:
                    case R.id.registerFragment:
                    case R.id.servicesFragment:
                    case R.id.employeeHomeFragment:
                    case R.id.employeeDetailsFragment:
                        binding.bottomNav.setVisibility(View.GONE);
                        break;
                    default:
                        binding.bottomNav.setVisibility(View.VISIBLE);
                        break;
                }
            }
        });
    }

}