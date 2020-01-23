package com.eleganzit.taxidriverapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.eleganzit.taxidriverapp.api.RetrofitAPI;
import com.eleganzit.taxidriverapp.api.RetrofitInterface;
import com.eleganzit.taxidriverapp.model.SendUserOtpResponse;
import com.google.gson.JsonObject;

import me.philio.pinentry.PinEntryView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VerficationActivity extends AppCompatActivity {
    String user_id;
    PinEntryView vr_code;
    String pinentered="";
    CardView verification;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verfication);
        user_id=getIntent().getStringExtra("user_id");
        verification=findViewById(R.id.verification);
        vr_code=findViewById(R.id.vr_code);
        progressDialog = new ProgressDialog(VerficationActivity.this);
        progressDialog.setMessage("Please Wait");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        vr_code.setOnPinEnteredListener(new PinEntryView.OnPinEnteredListener() {
            @Override
            public void onPinEntered(String pin) {
                pinentered=pin;

            }
        });


        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);

            }
        });  findViewById(R.id.submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pinentered.equals("") || pinentered.length()<4) {

                    Toast.makeText(VerficationActivity.this, "Please Enter valid OTP", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    verifyUserOtp();


                }

            }
        });
    }

    private void verifyUserOtp() {
        progressDialog.show();
        JsonObject paramObject = new JsonObject();
        paramObject.addProperty("driver_id", ""+user_id);
        paramObject.addProperty("otp", ""+pinentered);
        paramObject.addProperty("type", "driver");

        Log.d("dataaaa",""+paramObject.toString());

        RetrofitInterface myInterface = RetrofitAPI.getRetrofit().create(RetrofitInterface.class);
        Call<SendUserOtpResponse> call=myInterface.verifyForgotPassOtp(paramObject);
        call.enqueue(new Callback<SendUserOtpResponse>() {
            @Override
            public void onResponse(Call<SendUserOtpResponse> call, Response<SendUserOtpResponse> response) {
                if (response.isSuccessful()) {

                    if (response.body().getStatus().toString().equalsIgnoreCase("1")) {

                        startActivity(new Intent(VerficationActivity.this,ResetPasswordActivity.class).putExtra("user_id",""+user_id));
                        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                        finish();


                    }
                    else
                    {
                        progressDialog.dismiss();
                        Toast.makeText(VerficationActivity.this, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();

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
}
