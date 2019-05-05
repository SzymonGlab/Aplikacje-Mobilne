package com.example.pongGame

import android.content.Intent
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.WindowManager
import android.view.animation.AnimationUtils
import kotlinx.android.synthetic.main.activity_menu.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_menu)

        var myAnimation = AnimationUtils.loadAnimation(this,R.anim.bounce)
        var interpolator = BounceInterpolator(0.7,10.0)
        myAnimation.interpolator = interpolator
        play_button.startAnimation(myAnimation)
        play_button.setOnClickListener {
            val intent = Intent(this, GameClass::class.java)
            startActivity(intent)
        }
    }
}
