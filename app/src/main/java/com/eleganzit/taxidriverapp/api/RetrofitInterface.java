package com.eleganzit.taxidriverapp.api;


import com.eleganzit.taxidriverapp.model.DriverForgotPassword;
import com.eleganzit.taxidriverapp.model.DriverForgotPasswordResponse;
import com.eleganzit.taxidriverapp.model.DriverLoginResponse;
import com.eleganzit.taxidriverapp.model.DriverSignUpResponse;
import com.eleganzit.taxidriverapp.model.SendUserOtpResponse;
import com.google.gson.JsonObject;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

/**
 * Created by eleganz on 30/4/19.
 */

public interface RetrofitInterface {


    @POST("/taxiApp/api/sendDriverOtp")
    Call<SendUserOtpResponse> sendDriverOtp(
            @Body JsonObject jsonObject

    );


    @POST("/taxiApp/api/verifyDriverOtp")
    Call<SendUserOtpResponse> verifyDriverOtp(
            @Body JsonObject jsonObject

    );
    @POST("/taxiApp/api/driverSignUp")
    Call<DriverSignUpResponse> driverSignUp(
            @Body JsonObject jsonObject

    );  @POST("/taxiApp/api/driverLogin")
    Call<DriverLoginResponse> driverLogin(
            @Body JsonObject jsonObject

    );

    @POST("/taxiApp/api/driverForgotPassword")
    Call<DriverForgotPasswordResponse> driverForgotPassword(
            @Body JsonObject jsonObject

    );

    @POST("/taxiApp/api/verifyForgotPassOtp")
    Call<SendUserOtpResponse> verifyForgotPassOtp(
            @Body JsonObject jsonObject

    );

    @POST("/taxiApp/api/driverResetPassword")
    Call<SendUserOtpResponse> driverResetPassword(
            @Body JsonObject jsonObject

    );


}
