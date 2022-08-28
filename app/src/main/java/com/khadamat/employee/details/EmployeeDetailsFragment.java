package com.khadamat.employee.details;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.text.TextUtils;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.TimePicker;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.khadamat.R;
import com.khadamat.adapter.ReviewsAdapter;
import com.khadamat.adapter.WorksEmployeeAdapter;
import com.khadamat.common.Validation;
import com.khadamat.databinding.BottomSheetReservationBinding;
import com.khadamat.databinding.BottomSheetReviewBinding;
import com.khadamat.databinding.EmployeeDetailsFragmentBinding;
import com.khadamat.model.ReviewModel;
import com.khadamat.model.UserModel;
import com.khadamat.model.WorkEmployeeModel;

import java.util.ArrayList;
import java.util.Calendar;

public class EmployeeDetailsFragment extends Fragment
{

    private Validation validation;

    private EmployeeDetailsFragmentArgs detailsFragmentArgs;
    private String idEmployee, imageEmployee, nameEmployee, jobEmployee;

    private EmployeeDetailsFragmentBinding binding;
    private EmployeeDetailsViewModel employeeDetailsViewModel;

    private WorksEmployeeAdapter employeeAdapter;
    private ReviewsAdapter reviewsAdapter;

    private BottomSheetDialog sheetDialog, sheetDialogReview;
    private BottomSheetReservationBinding sheetReservationBinding;
    private BottomSheetReviewBinding sheetReviewBinding;

    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog;

    private Calendar calendar;
    private int year, month, day;
    private int hour, minute;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        binding = EmployeeDetailsFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);


        initView();
        initViewModel();
        retriveViewModel();
        clickedViews();

    }

    private void initView()
    {
        validation = new Validation(getContext());

        detailsFragmentArgs = EmployeeDetailsFragmentArgs.fromBundle(getArguments());
        idEmployee = detailsFragmentArgs.getDetailsEmployee().getId();
        imageEmployee = detailsFragmentArgs.getDetailsEmployee().getImage();
        nameEmployee = detailsFragmentArgs.getDetailsEmployee().getFirstName() + " " + detailsFragmentArgs.getDetailsEmployee().getLastName();
        jobEmployee = detailsFragmentArgs.getDetailsEmployee().getJob();
        Glide
                .with(requireActivity())
                .load(imageEmployee)
                .placeholder(R.drawable.ic_loader_image)
                .into(binding.imgEmployee);
        binding.txtNameEmployee.setText(nameEmployee);
        binding.txtJobEmployee.setText(jobEmployee);

        sheetDialog = new BottomSheetDialog(requireActivity());
        sheetReservationBinding = DataBindingUtil.inflate(LayoutInflater.from(requireActivity()), R.layout.bottom_sheet_reservation, requireActivity().findViewById(R.id.relative_bottom_sheet_reservation), false);
        sheetDialog.setContentView(sheetReservationBinding.getRoot());
        sheetDialogReview = new BottomSheetDialog(requireActivity());
        sheetReviewBinding = DataBindingUtil.inflate(LayoutInflater.from(requireActivity()), R.layout.bottom_sheet_review, requireActivity().findViewById(R.id.relative_bottom_sheet_review), false);
        sheetDialogReview.setContentView(sheetReviewBinding.getRoot());
        FrameLayout frameLayout = sheetDialogReview.findViewById(com.google.android.material.R.id.design_bottom_sheet);
        BottomSheetBehavior<View> viewBottomSheetBehavior = BottomSheetBehavior.from(frameLayout);
        viewBottomSheetBehavior.setPeekHeight(Resources.getSystem().getDisplayMetrics().heightPixels);
        viewBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);

        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
    }

    private void initViewModel()
    {
        employeeDetailsViewModel = new ViewModelProvider(this).get(EmployeeDetailsViewModel.class);
    }

    private void retriveViewModel()
    {

        employeeDetailsViewModel
                .retriveWorkEmployee(idEmployee)
                .observe(getViewLifecycleOwner(), new Observer<ArrayList<WorkEmployeeModel>>()
                {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onChanged(ArrayList<WorkEmployeeModel> workEmployeeModels)
                    {
                        employeeAdapter = new WorksEmployeeAdapter(workEmployeeModels);
                        binding.rVWorksEmployee.setAdapter(employeeAdapter);
                        binding.rVWorksEmployee.setLayoutManager(new LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false));
                        binding.rVWorksEmployee.addItemDecoration(new DividerItemDecoration(requireActivity(), DividerItemDecoration.HORIZONTAL));
                        employeeAdapter.notifyDataSetChanged();
                    }
                });

        employeeDetailsViewModel
                .retriveUser()
                .observe(getViewLifecycleOwner(), new Observer<UserModel>()
                {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onChanged(UserModel userModel)
                    {
                        Glide
                                .with(requireActivity())
                                .load(userModel.getImage())
                                .placeholder(R.drawable.ic_loader_image)
                                .into(sheetReservationBinding.imgUser);
                        sheetReservationBinding.txtNameUser.setText(userModel.getFirstName() + " " + userModel.getLastName());
                        sheetReservationBinding.txtLocationUser.setText(userModel.getLocation());
                        sheetReservationBinding.txtPhoneUser.setText(userModel.getPhoneNumber());
                        sheetReservationBinding.txtEmailUser.setText(userModel.getEmail());
                    }
                });

        sheetReviewBinding.loadingReview.setVisibility(View.VISIBLE);
        employeeDetailsViewModel
                .retriveReview(idEmployee)
                .observe(getViewLifecycleOwner(), new Observer<ArrayList<ReviewModel>>()
                {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onChanged(ArrayList<ReviewModel> reviewModels)
                    {
                        sheetReviewBinding.loadingReview.setVisibility(View.GONE);

                        reviewsAdapter = new ReviewsAdapter(reviewModels);
                        sheetReviewBinding.rVReview.setAdapter(reviewsAdapter);
                        sheetReviewBinding.rVReview.setLayoutManager(new LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false));
                        sheetReviewBinding.rVReview.addItemDecoration(new DividerItemDecoration(requireActivity(), DividerItemDecoration.VERTICAL));
                        reviewsAdapter.notifyDataSetChanged();
                    }
                });
    }

    private void clickedViews()
    {
        binding
                .btnRequest
                .setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
                        employeeDetailsViewModel
                                .requestUser()
                                .observe(getViewLifecycleOwner(), new Observer<UserModel>()
                                {
                                    @Override
                                    public void onChanged(UserModel userModel)
                                    {
                                        clickedViewsOfBottomSheet();
                                        sheetDialog.show();
                                    }
                                });
                    }
                });

        binding
                .btnRevivew
                .setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
                        clickedViewsOfBottomSheet();
                        sheetDialogReview.show();
                    }
                });

        binding
                .fabPhone
                .setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
                        requestPermission();
                    }
                });
    }

    private void requestPermission()
    {
        if (ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.CALL_PHONE}, 100);
        }

        else
        {
            String phone = detailsFragmentArgs.getDetailsEmployee().getPhoneNumber();
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:" + phone));
            startActivity(intent);
        }
    }

    private void clickedViewsOfBottomSheet()
    {

        sheetReservationBinding
                .imgCloseBottomSheetReservation
                .setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
                        sheetDialog.dismiss();
                    }
                });

        sheetReservationBinding
                .imgDay
                .setOnClickListener(new View.OnClickListener()
                        {
                            @RequiresApi(api = Build.VERSION_CODES.N)
                            @Override
                            public void onClick(View view)
                            {
                                datePicker();
                            }
                        });

        sheetReservationBinding
                .imgTime
                .setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                timePicker();
            }
        });

        sheetReservationBinding
                .btnDone
                .setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
                        String day = sheetReservationBinding.editDay.getText().toString();
                        String time = sheetReservationBinding.editTime.getText().toString();

                        if (!validation.validationOfDay(day))
                        {
                            sheetReservationBinding.editDay.requestFocus();
                        }

                        if (!validation.validationOfTime(time))
                        {
                            sheetReservationBinding.editTime.requestFocus();
                            return;
                        }

                        else
                        {
                            employeeDetailsViewModel
                                    .reservationUser(idEmployee, day, time)
                                    .observe(getViewLifecycleOwner(), new Observer<Boolean>()
                                    {
                                        @Override
                                        public void onChanged(Boolean aBoolean)
                                        {
                                            if (aBoolean)
                                            {

                                                sheetDialog.dismiss();
                                                NavHostFragment.findNavController(getParentFragment()).navigate(R.id.homeFragment);

                                                Toast.makeText(requireActivity(), "تم إرسال طلبك للموظف , الرجاء الإنتظار حتي يتم الرد علي طلبك", Toast.LENGTH_LONG).show();
                                            }

                                            else
                                            {
                                                Toast.makeText(requireActivity(), "Error", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                        }
                    }
                });

        sheetReviewBinding
                .imgSend
                .setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
                        String opinion = sheetReviewBinding.editOpinion.getText().toString();

                        if (TextUtils.isEmpty(opinion))
                        {
                            Toast.makeText(requireActivity(), "من فضلك أدخل رأيك في المكان المطلوب", Toast.LENGTH_SHORT).show();
                            sheetReviewBinding.editOpinion.requestFocus();
                            return;
                        }

                        else
                        {

                            employeeDetailsViewModel
                                    .reviewUser(idEmployee, opinion)
                                    .observe(getViewLifecycleOwner(), new Observer<Boolean>()
                                    {
                                        @Override
                                        public void onChanged(Boolean aBoolean)
                                        {
                                            if (aBoolean)
                                            {
//                                                sheetDialogReview.dismiss();
//                                                NavHostFragment.findNavController(getParentFragment()).navigate(R.id.homeFragment);

                                                sheetReviewBinding.editOpinion.setText(null);

                                                Toast.makeText(requireActivity(), "Done Review", Toast.LENGTH_SHORT).show();
                                            }

                                            else
                                            {
                                                Toast.makeText(requireActivity(), "Error", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                        }
                    }
                });

    }

    private void datePicker()
    {
        datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth)
            {
                month = month + 1;
                String day = dayOfMonth + " / " + month + " / " + year;
                sheetReservationBinding.editDay.setText(day);
            }
        }, year, month, day);

        datePickerDialog.show();
    }

    private void timePicker()
    {
        timePickerDialog = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener()
        {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minute)
            {
                calendar = Calendar.getInstance();
                calendar.set(0, 0, 0, hour, minute);
                sheetReservationBinding.editTime.setText(DateFormat.format("hh:mm aa", calendar));
            }
        }, hour, minute, false);

        timePickerDialog.updateTime(hour, minute);
        timePickerDialog.show();
    }
}