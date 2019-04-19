package com.rdev.trypfordriver.ui.createAccount;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.auth.PhoneAuthProvider.OnVerificationStateChangedCallbacks;
import com.rdev.trypfordriver.data.ApiService;
import com.rdev.trypfordriver.R;
import com.rdev.trypfordriver.data.model.CreateUser;
import com.rdev.trypfordriver.ui.login.LoginActivity;

import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CreateActivity extends AppCompatActivity {
    CreateUser createUser;
    FragmentManager fm;
    Retrofit retrofit;

    public CreateUser getCreateUser() {
        return createUser;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createUser = new CreateUser();
        createUser.setCountry_code("USA");
        createUser.setDialing_code("1");
        setContentView(R.layout.create_activity);
        fm = getSupportFragmentManager();
        fm.beginTransaction().add(R.id.container, new EnterPhoneFragment()).commit();
//        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
//        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
//        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
//        retrofit = new Retrofit.Builder()
//                .baseUrl("http://52.14.204.198/")
//                .addConverterFactory(GsonConverterFactory.create())
//                .client(client)
//                .build();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

    }

    public void onFinish() {
        finish();
    }

    public void goBack() {
        if (fm.getBackStackEntryCount() != 0) {
            fm.popBackStack();
        }
    }

    public void createAccount() {
        fm.beginTransaction().replace(R.id.container, new SignUpFragment()).addToBackStack("ssg").commit();
    }

    public void signUp() {
//        ApiService apiService = retrofit.create(ApiService.class);
//        apiService.createAccount(createUser).enqueue(new Callback<SignUpResponse>() {
//            @Override
//            public void onResponse(Call<SignUpResponse> call, Response<SignUpResponse> response) {
//                if (response.isSuccessful()) {
//                    Intent intent = new Intent(CreateActivity.this, MapActivity.class);
//                    intent.putExtra("tag", "f");
//                    startActivity(intent);
//                } else {
//                    AlertDialog dialog = new AlertDialog.Builder(CreateActivity.this)
//                            .setMessage(response.body().getData().getMessage())
//                            .setCancelable(true).create();
//                    dialog.show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<SignUpResponse> call, Throwable t) {
//                Log.d("tag", t.getMessage());
//            }
//        });
        Log.d("tag", createUser.toString());

        Intent intent = new Intent(CreateActivity.this, LoginActivity.class);
        intent.putExtra("IsNewUser", true);
        intent.putExtra("CreateUser", createUser);
        startActivity(intent);

    }
}
