package academy.bangkit.quiport.presentation.main

import academy.bangkit.quiport.R
import academy.bangkit.quiport.core.adapter.ReportSectionsPagerAdapter
import academy.bangkit.quiport.databinding.ActivityMainBinding
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private var doubleBackToExitPressedOnce = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        val sectionsPagerAdapter = ReportSectionsPagerAdapter(this)

        binding.apply {
            bottomBar.onTabSelected = {
                title = it.title
            }

            viewpagerContainer.adapter = sectionsPagerAdapter
            bottomBar.setupWithViewPager2(viewpagerContainer)
        }
    }

    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            finishAffinity()
            return
        }

        this.doubleBackToExitPressedOnce = true
        Snackbar.make(binding.root, resources.getString(R.string.double_press), Snackbar.LENGTH_SHORT).show()

        Handler(Looper.getMainLooper()).postDelayed( {
            doubleBackToExitPressedOnce = false
        }, DOUBLE_TAP_DELAY)
    }

    companion object {
        private const val DOUBLE_TAP_DELAY = 2000L
    }
}