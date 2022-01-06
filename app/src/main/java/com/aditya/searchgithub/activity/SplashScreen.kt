package com.aditya.searchgithub.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import com.aditya.searchgithub.R
import com.aditya.searchgithub.activity.main.MainActivity
import kotlinx.coroutines.*

class SplashScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        actionBar?.hide()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        val markGit: ImageView = findViewById(R.id.git_mark)
        val logoGit: ImageView = findViewById(R.id.git_logo)
        val topAnim: Animation = AnimationUtils.loadAnimation(this, R.anim.top_animation)
        val bottomAnim: Animation = AnimationUtils.loadAnimation(this, R.anim.bottom_animation)

        markGit.animation = topAnim
        logoGit.animation = bottomAnim

        launchIntent()
    }


    private fun launchIntent() {
        CoroutineScope(Dispatchers.Default).launch {
            delay(5000)
            val intent = Intent(this@SplashScreen, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}