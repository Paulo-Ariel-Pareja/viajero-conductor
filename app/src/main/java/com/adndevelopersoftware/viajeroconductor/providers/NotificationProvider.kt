package com.adndevelopersoftware.viajeroconductor.providers

import com.adndevelopersoftware.viajeroconductor.api.IFCMApi
import com.adndevelopersoftware.viajeroconductor.api.RetrofitClient
import com.adndevelopersoftware.viajeroconductor.models.FCMBody
import com.adndevelopersoftware.viajeroconductor.models.FCMResponse

import retrofit2.Call

class NotificationProvider {

    private val URL = "https://fcm.googleapis.com"

    fun sendNotification(body: FCMBody): Call<FCMResponse> {
        return RetrofitClient.getClient(URL).create(IFCMApi::class.java).send(body)
    }

}