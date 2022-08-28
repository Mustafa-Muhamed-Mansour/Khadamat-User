package com.khadamat.profile;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.khadamat.R;
import com.khadamat.databinding.ProfileFragmentBinding;
import com.khadamat.model.UserModel;

public class ProfileFragment extends Fragment
{

    private ProfileFragmentBinding binding;
    private ProfileViewModel profileViewModel;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        binding = ProfileFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        clickedViews();
        initViewModel();
        retriveViewModel();
    }

    private void clickedViews()
    {
        binding.btnSaveProfile.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Navigation.findNavController(view).navigate(R.id.action_profileFragment_to_homeFragment);
            }
        });

        binding.btnExitProfile.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                FirebaseAuth.getInstance().signOut();
                Navigation.findNavController(view).navigate(R.id.action_profileFragment_to_loginFragment);
            }
        });
    }

    private void initViewModel()
    {
        profileViewModel = new ViewModelProvider(requireActivity()).get(ProfileViewModel.class);
    }

    private void retriveViewModel()
    {

        profileViewModel
                .RetriveData()
                .observe(getViewLifecycleOwner(), new Observer<UserModel>()
                {
                    @Override
                    public void onChanged(UserModel userModel)
                    {
                        Glide
                                .with(getActivity())
                                .load(userModel.getImage())
                                .placeholder(R.drawable.ic_loader_image)
                                .error(R.drawable.ic_no_photo)
                                .into(binding.imgProfile);

                        if (userModel.getGender().equals("Male"))
                        {
                            binding.editGenderProfile.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_symbol_male, 0, 0, 0);
                            binding.editGenderProfile.setCompoundDrawablePadding(10);
                            binding.editGenderProfile.setText(userModel.getGender());
                        }
                        if (userModel.getGender().equals("Female"))
                        {
                            binding.editGenderProfile.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_symbol_woman, 0, 0, 0);
                            binding.editGenderProfile.setCompoundDrawablePadding(10);
                            binding.editGenderProfile.setText(userModel.getGender());
                        }


                        binding.editFnProfile.setText(userModel.getFirstName());
                        binding.editLnProfile.setText(userModel.getLastName());
                        binding.editPhoneProfile.setText(userModel.getPhoneNumber());
                        binding.editLocationProfile.setText(userModel.getLocation());
                        binding.editEmailProfile.setText(userModel.getEmail());
                    }
                });
    }
}