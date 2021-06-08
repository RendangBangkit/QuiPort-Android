package academy.bangkit.quiport.presentation.main

import academy.bangkit.quiport.core.adapter.ReportSectionsPagerAdapter
import academy.bangkit.quiport.databinding.ActivityMainBinding
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding

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
}