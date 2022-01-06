package com.aditya.searchgithub.activity.favorite

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.aditya.searchgithub.data.db.FavoriteUserDao
import com.aditya.searchgithub.data.db.FavoriteUser
import com.aditya.searchgithub.data.db.UserDatabase

class FavoriteViewModel(application: Application): AndroidViewModel(application) {
    private var userDatabase: UserDatabase? = UserDatabase.getDatabase(application)
    private var userDao: FavoriteUserDao? = userDatabase?.favoriteUserDao()

    fun getFavoriteUser(): LiveData<List<FavoriteUser>>?{
        return userDao?.getFavoriteUser()
    }
}