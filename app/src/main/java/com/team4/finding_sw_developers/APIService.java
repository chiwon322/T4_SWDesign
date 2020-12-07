package com.team4.finding_sw_developers;

import com.team4.finding_sw_developers.Notification.MyResponse;
import com.team4.finding_sw_developers.Notification.Sender;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService {
    @Headers(
            {
                    "Content-Type:application/json",
                    "Authorization:key=AAAA7oDhiWs:APA91bH0YTxGrmuQXDb3PCxtxW-TLdbNx-RpFZfBRW6xWupkul_m5dCmy2_ZI2KySHjFDNi6B_g9lMK2bR8JKgXXOODU0WXgNM7R_UCPoIofK82-PCVfUEQtBN_nRwBjdmWRzJEdCK3L"
            }
    )

    @POST("fcm/send")
    Call<MyResponse> sendNotification(@Body Sender body);
}
