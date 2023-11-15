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
        "Authorization:key=SOME_KEY"
    )
    @POST("fcm/send")
    fun send(@Body body: FCMBody): Call<FCMResponse>


}