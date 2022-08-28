package com.khadamat.employee.details;

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
import com.khadamat.model.ReservationModel;
import com.khadamat.model.ReviewModel;
import com.khadamat.model.UserModel;
import com.khadamat.model.WorkEmployeeModel;

import java.util.ArrayList;

public class EmployeeDetailsViewModel extends ViewModel
{

    private MutableLiveData<String> stringMutableLiveData;
    private MutableLiveData<Boolean> booleanMutableLiveData;
    private MutableLiveData<ArrayList<WorkEmployeeModel>> worksEmployeeMutableLiveData;
    private MutableLiveData<ArrayList<ReviewModel>> reviewMutableLiveData;
    private MutableLiveData<UserModel> modelMutableLiveData;
    private ArrayList<WorkEmployeeModel> workEmployeeModels;
    private ArrayList<ReviewModel> reviewModels;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference retWorkEmployee, requestsEmployee;
    private String randomKey;


    public EmployeeDetailsViewModel()
    {
        stringMutableLiveData = new MutableLiveData<>();
        booleanMutableLiveData = new MutableLiveData<>();
        worksEmployeeMutableLiveData = new MutableLiveData<>();
        reviewMutableLiveData = new MutableLiveData<>();
        modelMutableLiveData = new MutableLiveData<>();
        workEmployeeModels = new ArrayList<>();
        reviewModels = new ArrayList<>();
        firebaseAuth = FirebaseAuth.getInstance();
        retWorkEmployee = FirebaseDatabase.getInstance().getReference();
        requestsEmployee = FirebaseDatabase.getInstance().getReference();
        randomKey = requestsEmployee.push().getKey();
    }

    public LiveData<ArrayList<WorkEmployeeModel>> retriveWorkEmployee(String id)
    {

        retWorkEmployee
                .child("Employees")
                .child(id)
                .child("Works")
                .addValueEventListener(new ValueEventListener()
                {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot)
                    {
                        workEmployeeModels.clear();

                        for (DataSnapshot dataSnapshot : snapshot.getChildren())
                        {
                            if (snapshot.exists())
                            {
                                WorkEmployeeModel workEmployeeModel = dataSnapshot.getValue(WorkEmployeeModel.class);
                                workEmployeeModels.add(workEmployeeModel);
                            }
                        }
                        worksEmployeeMutableLiveData.postValue(workEmployeeModels);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error)
                    {
                        stringMutableLiveData.setValue(error.getMessage());
                    }
                });

        return worksEmployeeMutableLiveData;
    }

    public LiveData<UserModel> retriveUser()
    {
        requestsEmployee
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
                            modelMutableLiveData.postValue(userModel);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error)
                    {
                        stringMutableLiveData.setValue(error.getMessage());
                    }
                });

        return modelMutableLiveData;
    }

    public LiveData<UserModel> requestUser()
    {
        requestsEmployee
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
                            modelMutableLiveData.postValue(userModel);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error)
                    {
                        stringMutableLiveData.setValue(error.getMessage());
                    }
                });

        return modelMutableLiveData;
    }

    public LiveData<Boolean> reviewUser(String id, String opinion)
    {

        requestsEmployee
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

                            ReviewModel reviewModel = new ReviewModel(userModel.getId(), randomKey, userModel.getImage(), userModel.getFirstName() + " " + userModel.getLastName(), userModel.getLocation(), opinion);

                            requestsEmployee
                                    .child("Employees")
                                    .child(id)
                                    .child("Reviews")
                                    .child(randomKey)
                                    .setValue(reviewModel);

                            booleanMutableLiveData.postValue(true);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error)
                    {
                        stringMutableLiveData.setValue(error.getMessage());
                    }
                });



        return booleanMutableLiveData;
    }

    public LiveData<Boolean> reservationUser(String id, String day, String time)
    {
        requestsEmployee
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

                            ReservationModel reservationModel = new ReservationModel(userModel.getId(), userModel.getRandomKey(), userModel.getImage(), userModel.getFirstName() + " " + userModel.getLastName(), userModel.getLocation(), userModel.getPhoneNumber(), userModel.getEmail(), day, time);
                            requestsEmployee = FirebaseDatabase.getInstance().getReference();

                            requestsEmployee
                                    .child("Reservations")
                                    .child(id)
                                    .child(userModel.getRandomKey())
                                    .setValue(reservationModel);

                            modelMutableLiveData.postValue(userModel);

                            booleanMutableLiveData.postValue(true);

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error)
                    {
                        stringMutableLiveData.setValue(error.getMessage());
                    }
                });

        return booleanMutableLiveData;
    }


    public LiveData<ArrayList<ReviewModel>> retriveReview(String id)
    {
        requestsEmployee
                .child("Employees")
                .child(id)
                .child("Reviews")
                .addValueEventListener(new ValueEventListener()
                {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot)
                    {
                        reviewModels.clear();

                        if (snapshot.exists())
                        {
                            for (DataSnapshot dataSnapshot : snapshot.getChildren())
                            {
                                ReviewModel reviewModel = dataSnapshot.getValue(ReviewModel.class);
                                reviewModels.add(reviewModel);
                            }
                            reviewMutableLiveData.postValue(reviewModels);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error)
                    {
                        stringMutableLiveData.setValue(error.getMessage());
                    }
                });

        return reviewMutableLiveData;
    }

}