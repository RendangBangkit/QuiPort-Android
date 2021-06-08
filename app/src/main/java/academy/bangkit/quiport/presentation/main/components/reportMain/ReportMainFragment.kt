package academy.bangkit.quiport.presentation.main.components.reportMain

import academy.bangkit.quiport.R
import academy.bangkit.quiport.core.data.Resource
import academy.bangkit.quiport.core.domain.model.location.LocationDetail
import academy.bangkit.quiport.core.domain.model.user.UserDetail
import academy.bangkit.quiport.databinding.FragmentReportMainBinding
import academy.bangkit.quiport.utils.toUserDetail
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
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import es.dmoral.toasty.Toasty
import org.koin.android.viewmodel.ext.android.viewModel
import timber.log.Timber
import java.io.File
import java.io.IOException


class ReportMainFragment : Fragment(), LocationListener {
    private lateinit var googleSignInClient: GoogleSignInClient
    private var _binding : FragmentReportMainBinding? = null
    private val binding get() = _binding as FragmentReportMainBinding
    private val reportMainViewModel: ReportMainViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentReportMainBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        REQUIRED_PERMISSIONS.all {
            ContextCompat.checkSelfPermission(
                requireActivity(), it
            ) == PackageManager.PERMISSION_GRANTED
        }

        val locationManager = requireActivity().getSystemService(AppCompatActivity.LOCATION_SERVICE) as LocationManager
        val provider = locationManager.getBestProvider(Criteria(), true)
        val location = locationManager.getLastKnownLocation(provider!!)
        if (location != null) onLocationChanged(location)
        locationManager.requestLocationUpdates(provider, 20000, 0f, this)

        binding.btnAddReport.setOnClickListener { dispatchTakePictureIntent() }


        if (!isDeviceSupportCamera()) {
            Toasty.error(requireContext(), resources.getString(R.string.not_support_camera)).show()
        }

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .requestProfile()
            .build()

        googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)

        val account = GoogleSignIn.getLastSignedInAccount(requireActivity())
        account.let { reportMainViewModel.setUser(account?.toUserDetail()) }

        binding.apply {
            btnSignIn.apply {
                setSize(SignInButton.SIZE_STANDARD)

                setOnClickListener {
                    signIn()
                }
            }

            btnLogOut.setOnClickListener {
                revokeAccess()
                signOut()
            }
        }

        reportMainViewModel.user.observe(viewLifecycleOwner, {
            updateUI(it)
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }

        if (requestCode == RC_IMAGE_CAPTURE && resultCode == AppCompatActivity.RESULT_OK) {
            reportMainViewModel.postReport().observe(this, {
                if (it != null) {
                    when (it) {
                        is Resource.Loading -> {
                            Toasty.info(requireContext(),
                                resources.getString(R.string.loading)
                            ).show()
                        }
                        is Resource.Success -> {
                            Toasty.success(requireContext(),
                                resources.getString(R.string.success)
                            ).show()
                        }
                        is Resource.Error -> {
                            Timber.e(it.message)
                            Toasty.error(requireContext(),
                                resources.getString(R.string.error)
                            ).show()
                        }
                    }
                }
            })
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onLocationChanged(location: Location) {
        location.apply {
            reportMainViewModel.setLocation(LocationDetail(latitude, longitude))
        }
    }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        val storageDir: File? = requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES)

        return File.createTempFile(
            "captured_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        ).apply {
            reportMainViewModel.setAbsolutePath(absolutePath)
        }
    }

    private fun isDeviceSupportCamera(): Boolean {
        return requireActivity().packageManager.hasSystemFeature(
            PackageManager.FEATURE_CAMERA_ANY
        )
    }

    private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(requireActivity().packageManager)?.also {
                val photoFile: File? = try {
                    createImageFile()
                } catch (ex: IOException) {
                    null
                }

                photoFile?.also {
                    val photoURI: Uri = FileProvider.getUriForFile(
                        requireActivity(),
                        "academy.bangkit.quiport.fileprovider",
                        it
                    )

                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    startActivityForResult(takePictureIntent, RC_IMAGE_CAPTURE)
                }
            }
        }
    }

    private fun signIn() {
        val signInIntent: Intent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)

            reportMainViewModel.setUser(account.toUserDetail())
        } catch (e: ApiException) {
            Timber.w("signInResult:failed code=%s", e.statusCode)
            reportMainViewModel.setUser(null)
        }
    }

    private fun signOut() {
        googleSignInClient.signOut()
            .addOnCompleteListener(requireActivity()) {
                reportMainViewModel.setUser(null)
            }
    }

    private fun revokeAccess() {
        googleSignInClient.revokeAccess()
            .addOnCompleteListener(requireActivity()) {
                reportMainViewModel.setUser(null)
            }
    }

    private fun updateUI(user: UserDetail?) {
        Timber.d(user.toString())

        if (user != null) {
            binding.apply {
                val personName: String? = user.displayName
                val personEmail: String? = user.email
                val personId: String? = user.id
                val personPhoto: Uri? = user.photoUrl

                """
                    Name : $personName
                    Email : $personEmail
                    ID : $personId
                """.trimIndent().also { tvProfile.text = it }

                Glide.with(requireActivity())
                    .load(personPhoto)
                    .into(ivProfile)

                btnLogOut.visibility = View.VISIBLE
                btnAddReport.visibility = View.VISIBLE
                btnSignIn.visibility = View.GONE
            }
        } else {
            binding.apply {
                btnLogOut.visibility = View.GONE
                btnAddReport.visibility = View.GONE
                btnSignIn.visibility = View.VISIBLE
                tvProfile.text = ""

                Glide.with(requireActivity())
                    .load(R.mipmap.ic_launcher)
                    .into(ivProfile)
            }
        }
    }

    companion object {
        private const val RC_SIGN_IN = 1
        private const val RC_IMAGE_CAPTURE = 2
        private val REQUIRED_PERMISSIONS = arrayOf(
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
    }
}