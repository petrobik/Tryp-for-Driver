package com.rdev.trypfordriver.ui.map;

import android.content.Context;
import android.location.LocationManager;

import com.google.firebase.database.FirebaseDatabase;
import com.rdev.trypfordriver.data.ApiService;
import com.rdev.trypfordriver.data.DirectionsApiService;
import com.rdev.trypfordriver.data.localDb.DriverRoomDatabase;

import java.net.CookieHandler;
import java.net.CookieManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.JavaNetCookieJar;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.Context.LOCATION_SERVICE;

@Module
public abstract class MapRepositoryModule {

    @Singleton
    @Provides
    static Retrofit provideRetrofit() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        CookieHandler cookieHandler = new CookieManager();
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor)
                .cookieJar(new JavaNetCookieJar(cookieHandler))
                .build();
        return new Retrofit.Builder()
                .baseUrl("http://52.14.204.198/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
    }

    @Singleton
    @Provides
    static ApiService provideApiService(Retrofit retrofit) {
        return retrofit.create(ApiService.class);
    }

    @Singleton
    @Provides
    static DirectionsApiService provideDirectionsApiService() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://maps.googleapis.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        return retrofit.create(DirectionsApiService.class);
    }


    @Singleton
    @Provides
    static LocationManager provideLocationManager(Context context) {
        return (LocationManager) context.getSystemService(LOCATION_SERVICE);
    }

    @Singleton
    @Provides
    static FirebaseDatabase provideFirebaseDataBase() {
        return FirebaseDatabase.getInstance();
    }

    @Singleton
    @Provides
    static DriverRoomDatabase provideRoomDatabase(Context context) {
        return DriverRoomDatabase.getDatabase(context);
    }


}
