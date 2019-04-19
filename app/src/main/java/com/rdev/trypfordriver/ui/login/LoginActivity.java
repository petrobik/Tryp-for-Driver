package com.rdev.trypfordriver.ui.login;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rdev.trypfordriver.R;
import com.rdev.trypfordriver.data.ApiService;
import com.rdev.trypfordriver.data.localDb.CachedDriver;
import com.rdev.trypfordriver.data.model.CreateUser;
import com.rdev.trypfordriver.data.model.LoginModel;
import com.rdev.trypfordriver.data.model.UserPhoneNumber;
import com.rdev.trypfordriver.data.model.driver_login_response.Driver;
import com.rdev.trypfordriver.data.model.firebase_model.FirebaseDriver;
import com.rdev.trypfordriver.data.model.firebase_model.FirebaseVehicle;
import com.rdev.trypfordriver.data.source.DriverRepository;
import com.rdev.trypfordriver.ui.createAccount.CreateActivity;
import com.rdev.trypfordriver.ui.map.MapActivity;

import java.net.CookieHandler;
import java.net.CookieManager;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import dagger.android.support.DaggerAppCompatActivity;
import okhttp3.JavaNetCookieJar;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends DaggerAppCompatActivity {
    FirebaseAuth auth;
    FragmentManager fm;
    Retrofit retrofit;
    ApiService apiService;
    LoginModel loginModel;
    UserPhoneNumber number;
    @Inject
    DriverRepository driverRepository;

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks callbacks;
    private String verificationId;
    private String verificationCode;

    private boolean isNewUser = false;
    private CreateUser createUser;
    private FirebaseDriver driver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        isNewUser = intent.getBooleanExtra("IsNewUser", false);
        if (intent.getSerializableExtra("CreateUser") != null) {
            createUser = (CreateUser) intent.getSerializableExtra("CreateUser");
        }
        setContentView(R.layout.activity_login2);
        fm = getSupportFragmentManager();
//        fm.beginTransaction().add(R.id.login_container, new LoginFragment())
//                .commit();
        fm.beginTransaction().add(R.id.login_container, new LoginFragment())
                .commit();

//        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
//        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
//        CookieHandler cookieHandler = new CookieManager();
//        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor)
//                .cookieJar(new JavaNetCookieJar(cookieHandler))
//                .build();
//        retrofit = new Retrofit.Builder()
//                .baseUrl("http://52.14.204.198/")
//                .addConverterFactory(GsonConverterFactory.create())
//                .client(client)
//                .build();
//        apiService = retrofit.create(ApiService.class);

        number = new UserPhoneNumber();
        number.setCountry_code("USA");
        number.setDialing_code("1");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

        auth = FirebaseAuth.getInstance();

        callbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                signInWithPhoneAuthCredential(phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                Log.d("LoginActivity", "Verification Failed " + e.getMessage());

            }

            @Override
            public void onCodeSent(String verification, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(verification, forceResendingToken);

                verificationId = verification;

                fm.beginTransaction().replace(R.id.login_container, new ConfirmLoginFragment())
                        .addToBackStack("login").commit();

            }
        };
    }

    public void onFinish() {
        finish();
    }

//    public void onSendCode(String email, String password) {
//        if (isNetworkOnline() && email.length() >= 4 && password.length() >= 4) {
//
//            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
//                @Override
//                public void onComplete(@NonNull Task<AuthResult> task) {
//                    if (!task.isSuccessful()) {
//                        Toast.makeText(LoginActivity.this, "Login Error", Toast.LENGTH_SHORT).show();
//                    } else {
//                        String userId = auth.getCurrentUser().getUid();
//                        DatabaseReference user = FirebaseDatabase.getInstance().getReference().child("driversDb").child(userId);
//                        user.addListenerForSingleValueEvent(new ValueEventListener() {
//                            @Override
//                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                Driver driver = dataSnapshot.getValue(Driver.class);
//                                CachedDriver cachedDriver = new CachedDriver();
//                                cachedDriver.setFirstName(driver.getFirstName());
//                                cachedDriver.setLastName(driver.getLastName());
//                                driverRepository.insertCachedDriver(cachedDriver);
//                            }
//
//                            @Override
//                            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                            }
//                        });
//                        startActivity(new Intent(LoginActivity.this, MapActivity.class));
//                    }
//                }
//            });
//
////            apiService.login_driver(new DriverLoginBody(email,
////                    password, "mm99999", "android")).enqueue(new Callback<DriverLoginResponse>() {
////                @Override
////                public void onResponse(Call<DriverLoginResponse> call, Response<DriverLoginResponse> response) {
////                    if (response.body().getData() != null) {
////                        Driver driver = response.body().getData().getDriver();
////                        CachedDriver cachedDriver = new CachedDriver();
////                        cachedDriver.setCategory("");
////                        cachedDriver.setDriverId(driver.getId());
////                        cachedDriver.setFirstName(driver.getFirstName());
////                        cachedDriver.setLastName(driver.getLastName());
////                        cachedDriver.setPhoneNumber(driver.getMobilePhoneNumber());
////                        driverRepository.insertCachedDriver(cachedDriver);
////
////                        startActivity(new Intent(LoginActivity.this, MapActivity.class));
////                    } else {
////                        Toast.makeText(LoginActivity.this, "Error", Toast.LENGTH_SHORT).show();
////                    }
////                }
////
////                @Override
////                public void onFailure(Call<DriverLoginResponse> call, Throwable t) {
////
////                }
////            });
////            apiService.sendSms(number).enqueue(new Callback<LoginResponse>() {
////                @Override
////                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
////                    fm.beginTransaction().replace(R.id.login_container, new ConfirmLoginFragment())
////                            .addToBackStack("login").commit();
////                }
////
////                @Override
////                public void onFailure(Call<LoginResponse> call, Throwable t) {
////                    Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
////                }
////            });
//        } else if (email.length() < 5) {
//            Toast.makeText(LoginActivity.this, "Please enter email", Toast.LENGTH_LONG).show();
//        } else {
//            Toast.makeText(LoginActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
//        }
//    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    public void onSendCode() {
        if (isNetworkOnline() && number.getPhone_number().length() >= 7 && number.getPhone_number().length() >= 4) {

            PhoneAuthProvider.getInstance().verifyPhoneNumber(
                    "+" + number.getDialing_code() + number.getPhone_number(),
                    60,
                    TimeUnit.SECONDS,
                    this,
                    callbacks
            );

//            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
//                @Override
//                public void onComplete(@NonNull Task<AuthResult> task) {
//                    if (!task.isSuccessful()) {
//                        Toast.makeText(LoginActivity.this, "Login Error", Toast.LENGTH_SHORT).show();
//                    } else {
//                        String userId = auth.getCurrentUser().getUid();
//                        DatabaseReference user = FirebaseDatabase.getInstance().getReference().child("driversDb").child(userId);
//                        user.addListenerForSingleValueEvent(new ValueEventListener() {
//                            @Override
//                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                Driver driver = dataSnapshot.getValue(Driver.class);
//                                CachedDriver cachedDriver = new CachedDriver();
//                                cachedDriver.setFirstName(driver.getFirstName());
//                                cachedDriver.setLastName(driver.getLastName());
//                                driverRepository.insertCachedDriver(cachedDriver);
//                            }
//
//                            @Override
//                            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                            }
//                        });
//                        startActivity(new Intent(LoginActivity.this, MapActivity.class));
//                    }
//                }
//            });

//            apiService.login_driver(new DriverLoginBody(email,
//                    password, "mm99999", "android")).enqueue(new Callback<DriverLoginResponse>() {
//                @Override
//                public void onResponse(Call<DriverLoginResponse> call, Response<DriverLoginResponse> response) {
//                    if (response.body().getData() != null) {
//                        Driver driver = response.body().getData().getDriver();
//                        CachedDriver cachedDriver = new CachedDriver();
//                        cachedDriver.setCategory("");
//                        cachedDriver.setDriverId(driver.getId());
//                        cachedDriver.setFirstName(driver.getFirstName());
//                        cachedDriver.setLastName(driver.getLastName());
//                        cachedDriver.setPhoneNumber(driver.getMobilePhoneNumber());
//                        driverRepository.insertCachedDriver(cachedDriver);
//
//                        startActivity(new Intent(LoginActivity.this, MapActivity.class));
//                    } else {
//                        Toast.makeText(LoginActivity.this, "Error", Toast.LENGTH_SHORT).show();
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<DriverLoginResponse> call, Throwable t) {
//
//                }
//            });
//            apiService.sendSms(number).enqueue(new Callback<LoginResponse>() {
//                @Override
//                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
//                    fm.beginTransaction().replace(R.id.login_container, new ConfirmLoginFragment())
//                            .addToBackStack("login").commit();
//                }
//
//                @Override
//                public void onFailure(Call<LoginResponse> call, Throwable t) {
//                    Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
//                }
//            });
        } else if (number.getPhone_number().length() < 5) {
            Toast.makeText(LoginActivity.this, "Please enter valid phone number", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(LoginActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }

    private void verifyPhoneNumberWithCode() {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, verificationCode);
        signInWithPhoneAuthCredential(credential);
    }

    public void setVerificationCode(String code) {
        verificationCode = code;
    }

    public void verifySms(String code) {
        verificationCode = code;
        verifyPhoneNumberWithCode();
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential phoneAuthCredential) {
        FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            userIsLoggedIn();
                        }
                    }
                });
    }

    private void userIsLoggedIn() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userId = user.getUid();
        DatabaseReference refDb = FirebaseDatabase.getInstance().getReference();
        DatabaseReference refDriver = refDb.child("driversDb").child(userId);
        refDriver.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (!dataSnapshot.exists()) {
//                    user.updateEmail(createUser.getEmail());
                    driver = new FirebaseDriver();
                    driver.setId(userId);
                    driver.setFirstName(createUser.getFirst_name());
                    driver.setLastName(createUser.getLast_name());
                    driver.setCategory("Tryp");
                    driver.setType("Hatchback");
                    driver.setMaxLuggage(2);
                    driver.setMaxPassenger(4);
                    driver.setRating(4);
                    driver.setImage("https://firebasestorage.googleapis.com/v0/b/tryp-ab90c.appspot.com/o/driversDb%2Fdriver_default.png?alt=media&token=0b2a4cf2-6caa-4833-bd0a-110b22a167ad");
                    FirebaseVehicle vehicle = new FirebaseVehicle();
                    vehicle.setColor("#FFFFFF");
                    vehicle.setPlateNumber("A123AA");
                    vehicle.setModel("Audi");
                    vehicle.setImage("https://firebasestorage.googleapis.com/v0/b/tryp-ab90c.appspot.com/o/vehicles%2Ftryp_asist.png?alt=media&token=bc67642e-d8f2-4ff6-b025-794624fdc1ba");
                    driver.setVehicle(vehicle);
                    refDriver.setValue(driver);
//                    refDriver.setValue(true);

                    driverRepository.setDriver(userId);
//                    refDriver.child("firstName").setValue(createUser.getFirst_name());
//                    refDriver.child("lastName").setValue(createUser.getLast_name());
//                    refDriver.child("refCode").setValue(createUser.getRef_code());
                } else {
                    driverRepository.setDriver(userId);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        if (isNewUser) {

//            drivers.child("firstName").setValue(createUser.getFirst_name());
//            drivers.child("lastName").setValue(createUser.getLast_name());
//            drivers.child("email").setValue(createUser.getEmail());
//            user.


        }

        if (user != null) {
            startActivity(new Intent(LoginActivity.this, MapActivity.class));
        }
    }

    public void goBack() {
        if (fm.getBackStackEntryCount() > 0) {
            fm.popBackStack();
        }
    }

//    public void verifySms(String verification_code) {
//        loginModel = new LoginModel(number);
//        loginModel.setVerification_code(verification_code);
//        if (isNetworkOnline() && loginModel.getVerification_code().length() == 4) {
//            apiService.verifySms(loginModel).enqueue(new Callback<VerifySmsResponse>() {
//                @Override
//                public void onResponse(Call<VerifySmsResponse> call, Response<VerifySmsResponse> response) {
//                    Intent intent = new Intent(LoginActivity.this, MapActivity.class);
//                    intent.putExtra("tag", "f");
//                    startActivity(intent);
//                }
//
//                @Override
//                public void onFailure(Call<VerifySmsResponse> call, Throwable t) {
//                    Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
//                }
//            });
//        } else if (loginModel.getVerification_code().length() < 4) {
//            Toast.makeText(LoginActivity.this, "Please enter verification code", Toast.LENGTH_SHORT).show();
//        } else {
//            Toast.makeText(LoginActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
//        }
//    }

    public boolean isNetworkOnline() {
        boolean status = false;
        try {
            ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getNetworkInfo(0);
            if (netInfo != null && netInfo.getState() == NetworkInfo.State.CONNECTED) {
                status = true;
            } else {
                netInfo = cm.getNetworkInfo(1);
                if (netInfo != null && netInfo.getState() == NetworkInfo.State.CONNECTED)
                    status = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return status;
    }
}
