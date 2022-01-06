package com.aditya.searchgithub.activity.detail

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.aditya.searchgithub.data.DetailUserResponse
import com.aditya.searchgithub.api.ApiConfig
import com.aditya.searchgithub.data.db.FavoriteUserDao
import com.aditya.searchgithub.data.db.FavoriteUser
import com.aditya.searchgithub.data.db.UserDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(application: Application): AndroidViewModel(application) {
    private var userDatabase: UserDatabase? = UserDatabase.getDatabase(application)
    private var userDao: FavoriteUserDao? = userDatabase?.favoriteUserDao()

    val user = MutableLiveData<DetailUserResponse>()

    fun getDetail() : LiveData<DetailUserResponse>{
        return user
    }

    fun setDetail(mUser: String?){
        ApiConfig.getApiService()
            .getUserDetail(mUser)
            .enqueue(object : Callback<DetailUserResponse>{
                override fun onResponse(
                    call: Call<DetailUserResponse>,
                    response: Response<DetailUserResponse>,
                ) {
                    if (response.isSuccessful){
                        user.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                    Log.d("Failure : ", t.message!!)
                }
            })
    }

    fun addFavorite(username: String?, id: Int, avatarUrl: String?){
        CoroutineScope(Dispatchers.IO).launch {
            var user = FavoriteUser(
                username,
                id,
                avatarUrl
            )
            userDao?.addToFavorite(user)
        }
    }

    fun removeFavorite(id: Int){
        CoroutineScope(Dispatchers.IO).launch {
            userDao?.removeFromFavorite(id)
        }
    }

    suspend fun checkUser(id: Int) = userDao?.checkUser(id)
}