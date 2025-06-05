package com.example.musicplayer.ui.theme

import com.example.musicplayer.model.SongInfoResponse
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.Call
import retrofit2.http.Query

/** The RetrofitClient object is a singleton class that creates a
 * service instance to access the API
 */
object RetrofitClient {
    // Base URL for the API
    private const val BASE_URL = "https://ws.audioscrobbler.com/2.0/"

    val apiServiceInstance: ApiService by lazy {
        // Create a Retrofit instance using the getClient() method to access the API
        // via the ApiService interface
        getClient().create(ApiService::class.java)
    }
    // Function to build and return a Retrofit instance
    private fun getClient(): Retrofit {
        // Create a Moshi instance for JSON parsing KotlinJsonAdapterFactory is used to
        // automatically generate the adapter for the data classes
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory()) //required because generateAdapter = true
            .build()
        // Create a Retrofit instance with the BASE_URL and Moshi converter
        // using the builder pattern
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()

        // Return the Retrofit instance
        return retrofit
    }

}
/**
 * The ApiService interface defines the endpoints for the API.
 */
interface ApiService {

    /**
     * GET request to fetch the SongInfo from the API.
     * [Call] is used to make the network request asynchronously and return the response in a callback.
     *
     * @return [Call] object with [SongInfoResponse]
     */
    @GET("?method=track.getInfo&format=json")
    fun getSongInfo(
        @Query("api_key") apiKey: String,
        @Query("artist") artist: String,
        @Query("track") track: String
    ): Call<SongInfoResponse>


}