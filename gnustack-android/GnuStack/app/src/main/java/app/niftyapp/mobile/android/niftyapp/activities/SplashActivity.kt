package app.niftyapp.mobile.android.niftyapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import app.niftyapp.mobile.android.niftyapp.R

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)



        @Suppress("DEPRECATION")
        Handler().postDelayed(
                {
                    startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
                    finish()
                }, 2000
        )
    }
}