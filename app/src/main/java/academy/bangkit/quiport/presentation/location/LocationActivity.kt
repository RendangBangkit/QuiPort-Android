package academy.bangkit.quiport.presentation.location

import academy.bangkit.quiport.databinding.ActivityLocationBinding
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import org.koin.android.viewmodel.ext.android.viewModel

class LocationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLocationBinding
    private val locationViewModel: LocationViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLocationBinding.inflate(layoutInflater)

        setContentView(binding.root)

        locationViewModel.getLocationLiveData().observe(this, {
            binding.textView.text = "${it.latitude}, ${it.longitude}"
        })
    }
}