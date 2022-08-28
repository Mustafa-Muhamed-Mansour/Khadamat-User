package com.khadamat.register;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.Toast;

import com.khadamat.R;
import com.khadamat.databinding.RegisterFragmentBinding;

public class RegisterFragment extends Fragment
{

    private RegisterFragmentBinding binding;
    private RegisterViewModel registerViewModel;

    private ActivityResultLauncher<Intent> someResultLauncher;

    private RadioButton radioButton;
    private String job, image;

    private Uri resultURI;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        binding = RegisterFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);


        initViews();
        initViewModel();
        clickedViews();
        backgroundProcess();

    }

    private void initViews()
    {
    }

    private void initViewModel()
    {
        registerViewModel = new ViewModelProvider(requireActivity()).get(RegisterViewModel.class);
    }

    private void clickedViews()
    {

        binding.addPhoto.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                openGallery();
            }
        });

        binding.editLocation.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                openMap();
            }
        });

        binding.radioBtnMale.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                int i = binding.radioGroupGender.getCheckedRadioButtonId();
                radioButton = view.findViewById(i);
            }
        });

        binding.radioBtnCustom.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                int i = binding.radioGroupGender.getCheckedRadioButtonId();
                radioButton = view.findViewById(i);
            }
        });

        binding.radioBtnFemale.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                int i = binding.radioGroupGender.getCheckedRadioButtonId();
                radioButton = view.findViewById(i);
            }
        });

        binding
                .btnCreateAccount
                .setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {

                        String fn = binding.editFirstName.getText().toString();
                        String ln = binding.editLastName.getText().toString();
                        String loca = binding.editLocation.getText().toString();
                        String pn = binding.editPhoneNumber.getText().toString();
                        String email = binding.editEmail.getText().toString();
                        String password = binding.editPassword.getText().toString();

                        if (TextUtils.isEmpty(fn))
                        {
                            Toast.makeText(requireActivity(), "من فضلك أدخل الإسم الأول لك", Toast.LENGTH_SHORT).show();
                            binding.editFirstName.requestFocus();
                            return;
                        }

                        if (TextUtils.isEmpty(ln))
                        {
                            Toast.makeText(requireActivity(), "من فضلك أدخل الإسم الأخير لك", Toast.LENGTH_SHORT).show();
                            binding.editLastName.requestFocus();
                            return;
                        }

                        if (TextUtils.isEmpty(loca))
                        {
                            Toast.makeText(requireActivity(), "من فضلك قم بإدخال العنوان الخاص بك", Toast.LENGTH_SHORT).show();
                            binding.editLocation.requestFocus();
                            return;
                        }

                        if (TextUtils.isEmpty(pn))
                        {
                            Toast.makeText(requireActivity(), "رجاء أدخل كود البلد ومن ثم رقم الهاتف", Toast.LENGTH_SHORT).show();
                            binding.editPhoneNumber.requestFocus();
                            return;
                        }

                        if (TextUtils.isEmpty(email))
                        {
                            Toast.makeText(requireActivity(), "من فضلك أدخل البريد الإلكتروني", Toast.LENGTH_SHORT).show();
                            binding.editEmail.requestFocus();
                            return;
                        }

                        if (TextUtils.isEmpty(password))
                        {
                            Toast.makeText(requireActivity(), "من فضلك أدخل كلمة المرور", Toast.LENGTH_SHORT).show();
                            binding.editPassword.requestFocus();
                            return;
                        }

                        else
                        {
                            retriveViewModel(view, image, fn, ln, loca, pn, email, password, radioButton.getText().toString());
                        }
                    }
                });
    }

    private void backgroundProcess()
    {
        someResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>()
        {
            @Override
            public void onActivityResult(ActivityResult result)
            {
                Intent data = result.getData();

                if (result.getResultCode() == Activity.RESULT_OK && data != null && data.getData() != null)
                {
                    resultURI = data.getData();
                    image = resultURI.toString();
                    binding.addPhoto.setImageURI(resultURI);
                }
            }
        });
    }

    private void openGallery()
    {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        someResultLauncher.launch(intent);
    }

    private void openMap()
    {
    }

    private void retriveViewModel(View view, String image, String fn, String ln, String loca, String pn, String email, String password, String toString)
    {
        binding.loadingCreateAccount.setVisibility(View.VISIBLE);
        binding.btnCreateAccount.setVisibility(View.GONE);

        registerViewModel
                .registerUser(image, fn, ln, loca, pn, email, password, toString)
                .observe(getViewLifecycleOwner(), new Observer<Boolean>()
                {
                    @Override
                    public void onChanged(Boolean aBoolean)
                    {

                        if (aBoolean)
                        {
                            binding.addPhoto.setImageURI(null);
                            binding.loadingCreateAccount.setVisibility(View.GONE);

                            Navigation.findNavController(view).navigate(R.id.action_registerFragment_to_loginFragment);
                            Toast.makeText(requireActivity(), "Welcome, Login", Toast.LENGTH_SHORT).show();
                        }

                        else
                        {
                            binding.loadingCreateAccount.setVisibility(View.GONE);
                            binding.btnCreateAccount.setVisibility(View.VISIBLE);

                            Toast.makeText(requireActivity(), "يوجد مشكلة , أعد المحاولة مرة آخري", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}