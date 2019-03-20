package com.rdev.trypfordriver.ui.login;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.rdev.trypfordriver.R;
import com.rdev.trypfordriver.data.ApiService;
import com.rdev.trypfordriver.data.model.DriverLoginBody;
import com.rdev.trypfordriver.data.model.LoginModel;
import com.rdev.trypfordriver.data.model.UserPhoneNumber;
import com.rdev.trypfordriver.data.model.driver_login_response.Driver;
import com.rdev.trypfordriver.data.model.driver_login_response.DriverLoginResponse;
import com.rdev.trypfordriver.ui.map.MapActivity;

import java.net.CookieHandler;
import java.net.CookieManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import okhttp3.JavaNetCookieJar;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {
    FragmentManager fm;
    Retrofit retrofit;
    ApiService apiService;
    LoginModel loginModel;
    UserPhoneNumber number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);
        fm = getSupportFragmentManager();
        fm.beginTransaction().add(R.id.login_container, new LoginFragment())
                .commit();
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        CookieHandler cookieHandler = new CookieManager();
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor)
                .cookieJar(new JavaNetCookieJar(cookieHandler))
                .build();
        retrofit = new Retrofit.Builder()
                .baseUrl("http://52.14.204.198/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        apiService = retrofit.create(ApiService.class);
        number = new UserPhoneNumber();
        number.setCountry_code("USA");
        number.setDialing_code("1");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
    }

    public void onFinish() {
        finish();
    }

    public void onSendCode(String email, String password) {
        if (isNetworkOnline() && email.length() >= 4 && password.length() >= 4) {
            apiService.login_driver(new DriverLoginBody(email,
                    password, "mm99999", "android")).enqueue(new Callback<DriverLoginResponse>() {
                @Override
                public void onResponse(Call<DriverLoginResponse> call, Response<DriverLoginResponse> response) {
                    if (response.body().getData().getDriver() != null) {
                        Driver driver = response.body().getData().getDriver();
                        startActivity(new Intent(LoginActivity.this, MapActivity.class).putExtra("id", Integer.toString(driver.getId())));
                    }
                }

                @Override
                public void onFailure(Call<DriverLoginResponse> call, Throwable t) {

                }
            });
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
        } else if (email.length() < 5) {
            Toast.makeText(LoginActivity.this, "Please enter email", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(LoginActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
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
