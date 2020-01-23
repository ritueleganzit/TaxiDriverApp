package com.eleganzit.taxidriverapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.eleganzit.taxidriverapp.api.RetrofitAPI;
import com.eleganzit.taxidriverapp.api.RetrofitInterface;
import com.eleganzit.taxidriverapp.model.DriverLoginResponse;
import com.google.gson.JsonObject;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    CardView signIn;
    TextView forgotpassword,signup;
    EditText edpassword,edusername;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        signIn=findViewById(R.id.signIn);
        edpassword=findViewById(R.id.edpassword);
        edusername=findViewById(R.id.edusername);
        signup=findViewById(R.id.signup);
        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("Please Wait");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);

        forgotpassword=findViewById(R.id.forgotpassword);
        forgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,ForgotPassword.class));
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                finish();
            }
        }); signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,SignUpActivity.class));
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                finish();
            }
        });signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dexter.withActivity(MainActivity.this)
                        .withPermissions(
                                Manifest.permission.ACCESS_COARSE_LOCATION
                                )
                        .withListener(new MultiplePermissionsListener() {
                            @Override
                            public void onPermissionsChecked(MultiplePermissionsReport report) {
                                // check if all permissions are granted
                                if (report.areAllPermissionsGranted()) {
                                    if (isValid())
                                    {
                                        userLogin();
                                        }


                                }

                                // check for permanent denial of any permission
                                if (report.isAnyPermissionPermanentlyDenied()) {
                                    // show alert dialog navigating to Settings
                                    showSettingsDialog();
                                }
                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                                token.continuePermissionRequest();
                            }
                        }).
                        withErrorListener(new PermissionRequestErrorListener() {
                            @Override
                            public void onError(DexterError error) {
                                Toast.makeText(MainActivity.this, "Error occurred! ", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .onSameThread()
                        .check();

            }
        });
    }

    private void userLogin() {
        progressDialog.show();
        JsonObject paramObject = new JsonObject();
        paramObject.addProperty("user_name", ""+edusername.getText().toString());

        paramObject.addProperty("password", ""+edpassword.getText().toString());

        paramObject.addProperty("device_type", "android");
        paramObject.addProperty("device_token", "3pBNjvpqo:APA91bEE51saF1gwcK05-nGZAQOzvaxoGLvSq8hrIeKGjAPtkZye3MFvoMVX6ODz_c0ISDOyUItaXEjHyKW3Ojf_W_xHS5IgGbrMTH3Cf1c-W63vem5njqj98axr66zc6ArZAZpvmApW");

        RetrofitInterface myInterface = RetrofitAPI.getRetrofit().create(RetrofitInterface.class);
        Call<DriverLoginResponse> call=myInterface.driverLogin(paramObject);
        call.enqueue(new Callback<DriverLoginResponse>() {
            @Override
            public void onResponse(Call<DriverLoginResponse> call, Response<DriverLoginResponse> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {

                    if (response.body().getStatus().toString().equalsIgnoreCase("1")) {


                        Toast.makeText(MainActivity.this, "Logged in Successful", Toast.LENGTH_SHORT).show();

                        startActivity(new Intent(MainActivity.this,HomeScreen.class));
                        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);

                    }
                    else
                    {
                        Toast.makeText(MainActivity.this, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();

                    }
                }

            }

            @Override
            public void onFailure(Call<DriverLoginResponse> call, Throwable t) {
                progressDialog.dismiss();
                Log.d("nyyhu",""+t.getMessage());


                Toast.makeText(MainActivity.this, "Server and Internet Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Need Permissions");
        builder.setMessage("This app needs permission to use this feature. You can grant them in app settings.");
        builder.setPositiveButton("GOTO SETTINGS", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                openSettings();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();

    }
    private void openSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 101);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);

        finish();
    }


    public boolean isValid() {

        if (edusername.getText().toString().trim().equals("")) {


            Toast.makeText(this, "Please enter valid data", Toast.LENGTH_SHORT).show();

            edusername.requestFocus();

            return false;
        }
        else if (edpassword.getText().toString().trim().equals("") || edpassword.getText().toString().trim().length() < 6) {
            Toast.makeText(this, "Password must contain atleast 6 characters", Toast.LENGTH_SHORT).show();



            edpassword.requestFocus();

            return false;
        }

        return true;
    }

}
