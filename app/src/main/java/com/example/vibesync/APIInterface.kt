package com.example.vibesync

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface APIInterface {

    @Headers( "X-RapidAPI-Key: 4c12cb5211mshe3eeb4af97a828cp17a2e5jsn71ac77dc5a20",
    "X-RapidAPI-Host: deezerdevs-deezer.p.rapidapi.com")
    @GET("/search")
    fun getData(@Query("q") query : String) : Call<MyData>

}