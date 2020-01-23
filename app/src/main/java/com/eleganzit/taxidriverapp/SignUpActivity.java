package com.eleganzit.taxidriverapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.eleganzit.taxidriverapp.api.RetrofitAPI;
import com.eleganzit.taxidriverapp.api.RetrofitInterface;
import com.eleganzit.taxidriverapp.model.DriverSignUpResponse;
import com.eleganzit.taxidriverapp.model.SendUserOtpResponse;
import com.google.gson.JsonObject;

import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {
    ProgressDialog progressDialog;

    CardView date;
    TextView signin;
    CardView verification;
    String strdate;
    TextView tv_date;
    EditText edname,edemail,edphone,edgender,edauth,edpassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        edname=findViewById(R.id.edname);
        edemail=findViewById(R.id.edemail);
        edphone=findViewById(R.id.edphone);
        edgender=findViewById(R.id.edgender);
        edauth=findViewById(R.id.edauth);
        edpassword=findViewById(R.id.edpassword);
        signin=findViewById(R.id.signin);
        verification=findViewById(R.id.verification);
        tv_date=findViewById(R.id.tv_date);
        progressDialog = new ProgressDialog(SignUpActivity.this);
        progressDialog.setMessage("Please Wait");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        date=findViewById(R.id.date);
        edgender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog=new Dialog(SignUpActivity.this);
                dialog.setContentView(R.layout.gender_dialog);
                final TextView male=dialog.findViewById(R.id.nid);
                final TextView female=dialog.findViewById(R.id.pid);

                male.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        edgender.setText("Male");
                        dialog.dismiss();
                    }
                });
                female.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        edgender.setText("Female");
                        dialog.dismiss();
                    }
                });

                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                dialog.show();
            }
        });
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar newCalendar = Calendar.getInstance();
                DatePickerDialog dialog=   new DatePickerDialog(SignUpActivity.this, R.style.datepicker,new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        Calendar newDate = Calendar.getInstance();
                        newDate.set(year, monthOfYear, dayOfMonth);
int month=monthOfYear+1;
                        strdate=dayOfMonth+"/"+month+"/"+year;
                        tv_date.setText(strdate);
                    }

                }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
                // dialog.setTitle("Schedule a Ride");


                dialog.show();
            }

        });

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUpActivity.this,MainActivity.class));
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                finish();
            }
        }); verification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isValid())
                {
                    sendotp();

                }

            }
        });
    }

    private void sendotp() {
        progressDialog.show();
        JsonObject paramObject = new JsonObject();
        paramObject.addProperty("phone", ""+edphone.getText().toString());

        Log.d("hnjh",""+paramObject.toString());

        RetrofitInterface myInterface = RetrofitAPI.getRetrofit().create(RetrofitInterface.class);
        Call<SendUserOtpResponse> call=myInterface.sendDriverOtp(paramObject);
        call.enqueue(new Callback<SendUserOtpResponse>() {
            @Override
            public void onResponse(Call<SendUserOtpResponse> call, Response<SendUserOtpResponse> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {

                    if (response.body().getStatus().toString().equalsIgnoreCase("1")) {
                        Toast.makeText(SignUpActivity.this, "Verification Code has been send to mobile", Toast.LENGTH_SHORT).show();

                        Intent i = new Intent(SignUpActivity.this, RegVerficationActivity.class);
                        i.putExtra("name",""+edname.getText().toString());
                        i.putExtra("email",""+edemail.getText().toString());
                        i.putExtra("phone",""+edphone.getText().toString());
                        i.putExtra("gender",""+edgender.getText().toString());
                        i.putExtra("date",""+tv_date.getText().toString());
                        i.putExtra("password",""+edpassword.getText().toString());

                        // Add new Flag to start new Activity
                        startActivity(i);

                        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);

                    }
                    else
                    {
                        Toast.makeText(SignUpActivity.this, "---"+response.body().getMessage(), Toast.LENGTH_SHORT).show();

                    }
                }
                else
                {
                    Toast.makeText(SignUpActivity.this, ""+response.errorBody(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SendUserOtpResponse> call, Throwable t) {
                progressDialog.dismiss();

                Toast.makeText(SignUpActivity.this, "Server and Internet Error", Toast.LENGTH_SHORT).show();

            }
        });

    }

    public boolean isValid() {
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern;
        Matcher matcher;
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(edemail.getText().toString());


        if (edname.getText().toString().trim().equals("")) {


            Toast.makeText(this, "Please enter full name", Toast.LENGTH_SHORT).show();

            edname.requestFocus();

            return false;
        }
        else if (!matcher.matches()) {
            Toast.makeText(this, "Please enter a Valid e-mail id", Toast.LENGTH_SHORT).show();

            edemail.requestFocus();
            return false;
        }
        else if (edphone.getText().toString().trim().equals("") || edphone.getText().toString().trim().length()<10) {

            Toast.makeText(this, "Phone number must contain atleast 10 digits", Toast.LENGTH_SHORT).show();


            edphone.requestFocus();

            return false;
        }
else if (edgender.getText().toString().trim().equals("") ) {

            Toast.makeText(this, "Please select gender", Toast.LENGTH_SHORT).show();



            return false;
        }else if (tv_date.getText().toString().trim().equals("") ) {

            Toast.makeText(this, "Please select birthdate", Toast.LENGTH_SHORT).show();



            return false;
        }

        else if (edpassword.getText().toString().trim().equals("") || edpassword.getText().toString().trim().length() < 8) {
            Toast.makeText(this, "Password must contain atleast 8 characters", Toast.LENGTH_SHORT).show();


            edpassword.requestFocus();

            return false;
        }


        return true;
    }
}
