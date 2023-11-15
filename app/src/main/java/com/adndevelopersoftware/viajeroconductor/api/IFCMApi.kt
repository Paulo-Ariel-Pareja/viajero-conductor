package com.adndevelopersoftware.viajeroconductor.api

import com.adndevelopersoftware.viajeroconductor.models.FCMBody
import com.adndevelopersoftware.viajeroconductor.models.FCMResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface IFCMApi {

    @Headers(
        "Content-Type:application/json",
        "Authorization:key=AAAAX6jY73s:APA91bHvvqpG474H51c9NKGfGv_yrZWIINCF3m--oO-vo0FI_pxfqxhl-Jh-euN6LU-IbhRQmHzZLO74r3GoHJSp3agPraErVedolGK5DUnbWZbt5WeJvfQigjbMofch9ZDv26x0fTKV"
    )
    @POST("fcm/send")
    fun send(@Body body: FCMBody): Call<FCMResponse>


}