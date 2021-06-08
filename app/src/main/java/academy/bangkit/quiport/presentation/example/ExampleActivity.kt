package academy.bangkit.quiport.presentation.example

import academy.bangkit.quiport.R
import academy.bangkit.quiport.core.data.Resource
import academy.bangkit.quiport.core.domain.model.location.LocationDetail
import academy.bangkit.quiport.databinding.ActivityExampleBinding
import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Criteria
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import es.dmoral.toasty.Toasty
import org.koin.android.viewmodel.ext.android.viewModel
import timber.log.Timber
import java.io.File
import java.io.IOException

class ExampleActivity : AppCompatActivity(), LocationListener {
    private lateinit var binding: ActivityExampleBinding
    private val exampleViewModel: ExampleViewModel by viewModel()

    companion object {
        private const val REQUEST_IMAGE_CAPTURE = 1
        private val REQUIRED_PERMISSIONS = arrayOf(
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExampleBinding.inflate(layoutInflater)

        setContentView(binding.root)

        REQUIRED_PERMISSIONS.all {
            ContextCompat.checkSelfPermission(
                baseContext, it
            ) == PackageManager.PERMISSION_GRANTED
        }

        val locationManager = getSystemService(LOCATION_SERVICE) as LocationManager
        val provider = locationManager.getBestProvider(Criteria(), true)
        val location = locationManager.getLastKnownLocation(provider!!)
        if (location != null) onLocationChanged(location)
        locationManager.requestLocationUpdates(provider, 20000, 0f, this)

        binding.button.setOnClickListener { dispatchTakePictureIntent() }


        if (!isDeviceSupportCamera()) {
            Toasty.error(this, "Device doesn't support camera").show()

            finish()
        }
    }

    override fun onLocationChanged(location: Location) {
        location.apply {
            exampleViewModel.setLocation(LocationDetail(latitude, longitude))
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        super.onActivityResult(requestCode, resultCode, intent)

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            exampleViewModel.postReport(
                "dummy", "dummy",
            ).observe(this, {
                if (it != null) {
                    when (it) {
                        is Resource.Loading -> {
                            Toasty.info(this@ExampleActivity,
                                resources.getString(R.string.loading)
                            ).show()
                        }
                        is Resource.Success -> {
                            Toasty.success(this@ExampleActivity,
                                resources.getString(R.string.success)
                            ).show()
                        }
                        is Resource.Error -> {
                            Timber.e(it.message)
                            Toasty.error(this@ExampleActivity,
                                resources.getString(R.string.error)
                            ).show()
                        }
                    }
                }
            })
        }
    }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        val storageDir: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)

        return File.createTempFile(
            "captured_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        ).apply {
            exampleViewModel.setAbsolutePath(absolutePath)
        }
    }

    private fun isDeviceSupportCamera(): Boolean {
        return applicationContext.packageManager.hasSystemFeature(
            PackageManager.FEATURE_CAMERA_ANY
        )
    }

    private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager)?.also {
                val photoFile: File? = try {
                    createImageFile()
                } catch (ex: IOException) {
                    null
                }

                photoFile?.also {
                    val photoURI: Uri = FileProvider.getUriForFile(
                        this,
                        "academy.bangkit.quiport.fileprovider",
                        it
                    )

                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
                }
            }
        }
    }
}