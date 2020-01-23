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
import com.eleganzit.taxidriverapp.model.SendUserOtpResponse;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResetPasswordActivity extends AppCompatActivity {
    String user_id;
    CardView verification;
    ProgressDialog progressDialog;

    EditText edpassword, cnfedpassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        findViewById(R.id.submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isValid())
                {

                    resetPassword();

                }

            }
        });
        edpassword=findViewById(R.id.edpassword);
        cnfedpassword=findViewById(R.id.cnfedpassword);
        verification=findViewById(R.id.verification);
        progressDialog = new ProgressDialog(ResetPasswordActivity.this);
        progressDialog.setMessage("Please Wait");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        user_id=getIntent().getStringExtra("user_id");

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
        paramObject.addProperty("driver_id", ""+user_id);
        paramObject.addProperty("password", ""+edpassword.getText().toString());

        Log.d("dataaaa",""+paramObject.toString());

        RetrofitInterface myInterface = RetrofitAPI.getRetrofit().create(RetrofitInterface.class);
        Call<SendUserOtpResponse> call=myInterface.driverResetPassword(paramObject);
        call.enqueue(new Callback<SendUserOtpResponse>() {
            @Override
            public void onResponse(Call<SendUserOtpResponse> call, Response<SendUserOtpResponse> response)
            {
                progressDialog.dismiss();
                if (response.isSuccessful())
                {
                    if (response.body().getStatus().toString().equalsIgnoreCase("1"))
                    {
                        Toast.makeText(ResetPasswordActivity.this, "Password Successfully changed", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(ResetPasswordActivity.this,MainActivity.class));
                        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                        finish();
                    }
                }
            }

            @Override
            public void onFailure(Call<SendUserOtpResponse> call, Throwable t) {
                progressDialog.dismiss();
            }
        });
    }


    public boolean isValid() {
        if (edpassword.getText().toString().trim().equals("") || edpassword.getText().toString().trim().length() < 8) {
            Toast.makeText(this, "Password must contain atleast 8 characters", Toast.LENGTH_SHORT).show();


            edpassword.requestFocus();

            return false;
        }

        else if(!(edpassword.getText().toString().equalsIgnoreCase(cnfedpassword.getText().toString())))
        {
            Toast.makeText(this, "Password doesn't match", Toast.LENGTH_SHORT).show();

            cnfedpassword.requestFocus();
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
