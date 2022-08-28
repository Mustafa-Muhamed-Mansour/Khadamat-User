package com.khadamat.login;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.khadamat.R;
import com.khadamat.bottomSheet.BottomSheetForgetPassword;
import com.khadamat.databinding.LoginFragmentBinding;

public class LoginFragment extends Fragment
{

    private LoginFragmentBinding binding;
    private LoginViewModel loginViewModel;

    private BottomSheetForgetPassword sheetForgetPassword;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        binding = LoginFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        initView();
        initViewModel();
        clickedViews();

    }

    private void initView()
    {
        sheetForgetPassword = new BottomSheetForgetPassword();
    }


    private void initViewModel()
    {
        loginViewModel = new ViewModelProvider(requireActivity()).get(LoginViewModel.class);
    }

    private void clickedViews()
    {
        binding
                .btnCreateNewAccount
                .setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
                        Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_registerFragment);
                    }
                });

        binding
                .btnLogin
                .setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
                        String email = binding.editEmail.getText().toString();
                        String password = binding.editPassword.getText().toString();

                        if (TextUtils.isEmpty(email))
                        {
                            Toast.makeText(requireActivity(), "من فضلك أدخل البريد الإلكتروني الخاص بك", Toast.LENGTH_SHORT).show();
                            binding.editEmail.requestFocus();
                            return;
                        }


                        if (TextUtils.isEmpty(password))
                        {
                            Toast.makeText(requireActivity(), "من فضلك أدخل كلمة المرور الخاصة بك", Toast.LENGTH_SHORT).show();
                            binding.editPassword.requestFocus();
                            return;
                        }

                        else
                        {
                            retriveViewModel(view, email, password);
                        }
                    }
                });

        binding.txtForgetPassword.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                sheetForgetPassword.show(getActivity().getSupportFragmentManager(), "BottomSheet");
            }
        });
    }

    private void retriveViewModel(View view, String email, String password)
    {
        binding.loadingLogin.setVisibility(View.VISIBLE);
        binding.btnLogin.setVisibility(View.INVISIBLE);

        loginViewModel
                .loginUser(email, password)
                .observe(getViewLifecycleOwner(), new Observer<Boolean>()
                {
                    @Override
                    public void onChanged(Boolean aBoolean)
                    {
                        if (aBoolean)
                        {
                            binding.loadingLogin.setVisibility(View.GONE);
                            binding.btnLogin.setVisibility(View.VISIBLE);

                            Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_homeFragment);
                        }

                        else
                        {
                            binding.loadingLogin.setVisibility(View.GONE);
                            binding.btnLogin.setVisibility(View.VISIBLE);

                            Toast.makeText(requireActivity(), "يوجد مشكلة , أعد المحاولة مرة آخري", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}