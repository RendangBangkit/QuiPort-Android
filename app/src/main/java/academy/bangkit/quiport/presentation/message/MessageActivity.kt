package academy.bangkit.quiport.presentation.message

import academy.bangkit.quiport.core.data.Resource
import academy.bangkit.quiport.databinding.ActivityMessageBinding
import academy.bangkit.quiport.core.features.ExtraFeatures
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import es.dmoral.toasty.Toasty
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

class MessageActivity : AppCompatActivity() {
    private val viewModel: MessageViewModel by viewModel()
    private val extraFeatures: ExtraFeatures by inject()
    private lateinit var binding: ActivityMessageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMessageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.message.observe(this, {
            if (it != null) {
                when (it) {
                    is Resource.Loading -> binding.tvWelcome.text = "Loading"
                    is Resource.Success -> binding.tvWelcome.text = it.data?.get(0)?.welcomeMessage
                    is Resource.Error -> binding.tvWelcome.text = "Error"
                }
            }
        })

        binding.btnExtra.setOnClickListener {
            try {
                startActivity(extraFeatures.getIntent())
            } catch (e: Exception){
                Toasty.error(this, "Module not installed. Trying to install module.", Toast.LENGTH_SHORT).show()

                extraFeatures.installModule()
            }
        }
    }
}