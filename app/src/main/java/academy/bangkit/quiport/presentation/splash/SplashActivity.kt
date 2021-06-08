package academy.bangkit.quiport.presentation.splash

import academy.bangkit.quiport.R
import academy.bangkit.quiport.databinding.ActivitySplashBinding
import academy.bangkit.quiport.presentation.main.MainActivity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {
    private lateinit var binding : ActivitySplashBinding
    private lateinit var topAnimation : Animation
    private lateinit var bottomAnimation : Animation


    companion object {
        private const val SPLASH_DURATION = 2000L
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)

        setContentView(binding.root)

        topAnimation = AnimationUtils.loadAnimation(this, R.anim.top_animation)
        bottomAnimation = AnimationUtils.loadAnimation(this, R.anim.bottom_animation)

        binding.logo.animation = topAnimation
        binding.label.animation = bottomAnimation

        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
        }, SPLASH_DURATION)
    }
}