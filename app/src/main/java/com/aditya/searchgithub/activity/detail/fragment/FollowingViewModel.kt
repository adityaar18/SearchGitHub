package com.aditya.searchgithub.activity.detail.fragment

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aditya.searchgithub.data.User
import com.aditya.searchgithub.api.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowingViewModel: ViewModel() {
    val list = MutableLiveData<ArrayList<User>>()

    fun getFollowing(): LiveData<ArrayList<User>>{
        return list
    }

    fun setFollowing(user: String){
        ApiConfig.getApiService()
            .getUsersFollowing(user)
            .enqueue(object : Callback<ArrayList<User>>{
                override fun onResponse(
                    call: Call<ArrayList<User>>,
                    response: Response<ArrayList<User>>,
                ) {
                    if (response.isSuccessful){
                        list.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<ArrayList<User>>, t: Throwable) {
                    Log.d("Failure : ", t.message!!)
                }

            })
    }
}