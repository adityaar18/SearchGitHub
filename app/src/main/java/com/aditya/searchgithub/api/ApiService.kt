package com.aditya.searchgithub.api

import com.aditya.searchgithub.data.DetailUserResponse
import com.aditya.searchgithub.data.User
import com.aditya.searchgithub.data.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("search/users")
    @Headers("Authorization: token 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")
    fun getSearchUsers(
        @Query("q") query: String
    ): Call<UserResponse>

    @GET("users/{username}")
    @Headers("Authorization: token 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")
    fun getUserDetail(
        @Path("username") username: String?
    ): Call<DetailUserResponse>

    @GET("users/{username}/followers")
    @Headers("Authorization: token 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")
    fun getUserFollowers(
        @Path("username") username: String
    ): Call<ArrayList<User>>

    @GET("users/{username}/following")
    @Headers("Authorization: token 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")
    fun getUsersFollowing(
        @Path("username") username: String
    ): Call<ArrayList<User>>
}