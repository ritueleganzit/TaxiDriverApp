package com.eleganzit.taxidriverapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;


import com.eleganzit.taxidriverapp.api.RetrofitAPI;
import com.eleganzit.taxidriverapp.api.RetrofitInterface;
import com.eleganzit.taxidriverapp.model.DriverSignUpResponse;
import com.eleganzit.taxidriverapp.model.SendUserOtpResponse;
import com.google.gson.JsonObject;

import me.philio.pinentry.PinEntryView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegVerficationActivity extends AppCompatActivity {
    PinEntryView vr_code;
    String pinentered="";
    CardView verification;
    ProgressDialog progressDialog;

TextView resend;
    String name,email,phone,password,gender,date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verfication);
        name=getIntent().getStringExtra("name");
        email=getIntent().getStringExtra("email");
        date=getIntent().getStringExtra("date");
        gender=getIntent().getStringExtra("gender");
phone=getIntent().getStringExtra("phone");
        resend=findViewById(R.id.resend);
        verification=findViewById(R.id.verification);
        vr_code=findViewById(R.id.vr_code);
        progressDialog = new ProgressDialog(RegVerficationActivity.this);
        progressDialog.setMessage("Please Wait");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        verification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pinentered.equals("") || pinentered.length()<4) {

                    Toast.makeText(RegVerficationActivity.this, "Please Enter valid OTP", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    verifyUserOtp();

                }

            }
        });
        resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //resendUserOtp();
            }
        });
        vr_code.setOnPinEnteredListener(new PinEntryView.OnPinEnteredListener() {
            @Override
            public void onPinEntered(String pin) {
                pinentered=pin;

            }
        });
        password=getIntent().getStringExtra("password");
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);

            }
        });
    }

    /*private void resendUserOtp() {

            progressDialog.show();
            JsonObject paramObject = new JsonObject();
            paramObject.addProperty("phone", ""+phone);

            Log.d("hnjh",""+paramObject.toString());

            RetrofitInterface myInterface = RetrofitAPI.getRetrofit().create(RetrofitInterface.class);
            Call<SendUserOtpResponse> call=myInterface.resendUserOtp(paramObject);
            call.enqueue(new Callback<SendUserOtpResponse>() {
                @Override
                public void onResponse(Call<SendUserOtpResponse> call, Response<SendUserOtpResponse> response) {
                    progressDialog.dismiss();
                    if (response.isSuccessful()) {

                        if (response.body().getStatus().toString().equalsIgnoreCase("1")) {
                            Toast.makeText(RegVerficationActivity.this, "Verification Code has been send to mobile", Toast.LENGTH_SHORT).show();


                        }
                        else
                        {
                            Toast.makeText(RegVerficationActivity.this, "---"+response.body().getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    }
                    else
                    {
                        Toast.makeText(RegVerficationActivity.this, ""+response.errorBody(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<SendUserOtpResponse> call, Throwable t) {
                    progressDialog.dismiss();

                    Toast.makeText(RegVerficationActivity.this, "Server and Internet Error", Toast.LENGTH_SHORT).show();
                }
            });



    }


*/
    private void verifyUserOtp() {
        progressDialog.show();
        JsonObject paramObject = new JsonObject();
        paramObject.addProperty("phone", ""+phone);
        paramObject.addProperty("otp", ""+pinentered);

        Log.d("dataaaa",""+paramObject.toString());

        RetrofitInterface myInterface = RetrofitAPI.getRetrofit().create(RetrofitInterface.class);
        Call<SendUserOtpResponse> call=myInterface.verifyDriverOtp(paramObject);
        call.enqueue(new Callback<SendUserOtpResponse>() {
            @Override
            public void onResponse(Call<SendUserOtpResponse> call, Response<SendUserOtpResponse> response) {
                if (response.isSuccessful()) {

                    if (response.body().getStatus().toString().equalsIgnoreCase("1")) {


                        setSignUp();

                    }
                    else
                    {
                        progressDialog.dismiss();
                        Toast.makeText(RegVerficationActivity.this, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();

                    }
                }
            }

            @Override
            public void onFailure(Call<SendUserOtpResponse> call, Throwable t) {
                progressDialog.dismiss();
            }
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);

    }

    public void  setSignUp(){


        JsonObject paramObject = new JsonObject();
        paramObject.addProperty("full_name", ""+name);
        paramObject.addProperty("phone", ""+phone);
        paramObject.addProperty("email", ""+email);
        paramObject.addProperty("password", ""+password);
        paramObject.addProperty("gender", ""+password);
        paramObject.addProperty("d_o_b", ""+password);

        paramObject.addProperty("device_type", "android");
        paramObject.addProperty("device_token", "3pBNjvpqo:APA91bEE51saF1gwcK05-nGZAQOzvaxoGLvSq8hrIeKGjAPtkZye3MFvoMVX6ODz_c0ISDOyUItaXEjHyKW3Ojf_W_xHS5IgGbrMTH3Cf1c-W63vem5njqj98axr66zc6ArZAZpvmApW");


        Log.d("dataaaa",""+paramObject.toString());


        RetrofitInterface myInterface = RetrofitAPI.getRetrofit().create(RetrofitInterface.class);
        Call<DriverSignUpResponse> call=myInterface.driverSignUp(paramObject);
        call.enqueue(new Callback<DriverSignUpResponse>() {
            @Override
            public void onResponse(Call<DriverSignUpResponse> call, Response<DriverSignUpResponse> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {

                    if (response.body().getStatus().toString().equalsIgnoreCase("1")) {


                        Toast.makeText(RegVerficationActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(RegVerficationActivity.this,MainActivity.class));
                        finish();
                        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                    }
                    else
                    {
                        Toast.makeText(RegVerficationActivity.this, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();

                    }
                }

            }

            @Override
            public void onFailure(Call<DriverSignUpResponse> call, Throwable t) {
                progressDialog.dismiss();
                Log.d("nyyhu",""+t.getMessage());


                Toast.makeText(RegVerficationActivity.this, "Server and Internet Error", Toast.LENGTH_SHORT).show();
            }
        });

    }

}
