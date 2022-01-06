package com.aditya.searchgithub.activity.main

import android.util.Log
import androidx.lifecycle.*
import com.aditya.searchgithub.activity.SettingPreferences
import com.aditya.searchgithub.data.User
import com.aditya.searchgithub.data.UserResponse
import com.aditya.searchgithub.api.ApiConfig
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(private val pref: SettingPreferences): ViewModel() {
    val list = MutableLiveData<ArrayList<User>>()

    fun getSearch(): LiveData<ArrayList<User>>{
        return list
    }

    fun getThemeSetting(): LiveData<Boolean>{
        return pref.getThemeSetting().asLiveData()
    }

    fun saveThemeSetting(isDarkModeActive: Boolean){
        viewModelScope.launch {
            pref.saveThemeSetting(isDarkModeActive)
        }
    }

    fun setSearch(user: String){
        ApiConfig.getApiService()
            .getSearchUsers(user)
            .enqueue(object : Callback<UserResponse>{
                override fun onResponse(
                    call: Call<UserResponse>,
                    response: Response<UserResponse>,
                ) {
                    if (response.isSuccessful){
                        list.postValue(response.body()?.items)
                    }
                }

                override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                    Log.d("onFailure: ", t.message!!)
                }

            })
    }
}