package com.eleganzit.taxidriverapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.eleganzit.taxidriverapp.api.RetrofitAPI;
import com.eleganzit.taxidriverapp.api.RetrofitInterface;
import com.eleganzit.taxidriverapp.model.DriverForgotPasswordResponse;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPassword extends AppCompatActivity {
    EditText edphone;
    ProgressDialog progressDialog;
    CardView verification;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        verification=findViewById(R.id.verification);
        edphone=findViewById(R.id.edphone);
        progressDialog = new ProgressDialog(ForgotPassword.this);
        progressDialog.setMessage("Please Wait");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);

        verification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isValid())
                {
                    resetPassword();

                }


            }
        });

        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);

            }
        });
    }
    private void resetPassword() {
        progressDialog.show();
        JsonObject paramObject = new JsonObject();
        paramObject.addProperty("phone", ""+edphone.getText().toString());

        Log.d("hnjh",""+paramObject.toString());

        RetrofitInterface myInterface = RetrofitAPI.getRetrofit().create(RetrofitInterface.class);
        Call<DriverForgotPasswordResponse> call=myInterface.driverForgotPassword(paramObject);
        call.enqueue(new Callback<DriverForgotPasswordResponse>() {
            @Override
            public void onResponse(Call<DriverForgotPasswordResponse> call, Response<DriverForgotPasswordResponse> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {

                    if (response.body().getStatus().toString().equalsIgnoreCase("1")) {
                        startActivity(new Intent(ForgotPassword.this,VerficationActivity.class).putExtra("user_id",""+response.body().getData().getUserId()));
                        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                        finish();
                    }
                }
            }

            @Override
            public void onFailure(Call<DriverForgotPasswordResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(ForgotPassword.this, "-"+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public boolean isValid() {
        if (edphone.getText().toString().trim().equals("") || edphone.getText().toString().trim().length()<10) {

            Toast.makeText(this, "Phone number must contain atleast 10 digits", Toast.LENGTH_SHORT).show();


            edphone.requestFocus();

            return false;
        }
        return true;
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);

    }
}
