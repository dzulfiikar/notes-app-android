package com.dzul.notestapp.data.sources.network

import android.util.Log
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import com.google.gson.annotations.SerializedName
import okhttp3.OkHttpClient
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory



interface NoteAPIService {
    @Headers(
        "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE3NDgwOTMzODMsImlhdCI6MTcxNjU1NzM4MywiaWQiOiIxMmZkMjA5Yy00OTMxLTRkNTgtODU3NS1hYTc4YzUxNTBhMTQiLCJpc3MiOiJub3Rlcy1hcGktZ29sYW5nIiwic3ViIjoiZHp1bGZpa2FyQGdtYWlsLmNvbSJ9.LRPXDy75AgjZ_LolEkxKb2so7s069ljhVADPfpWBJjc",
        "Content-Type: application/json",
        "ngrok-skip-browser-warning: hello"
    )
    @GET("/posts")
    fun fetchAllNotes(): Call<FetchAllNoteResult>
}
