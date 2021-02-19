package com.formationandroid.worldvisit.ws

import retrofit2.Call
import retrofit2.Callback
import retrofit2.http.GET
import retrofit2.http.Url

interface WSInterface {
    @GET("rest/v2/all?fields=name;capital;region;alpha2Code")
    fun getCountries(): Call<MutableList<ReturnWSCountry>>

    @GET
    fun getCountries(@Url url: String): Call<MutableList<ReturnWSCountry>>
}