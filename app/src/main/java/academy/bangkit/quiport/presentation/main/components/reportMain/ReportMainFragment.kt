package academy.bangkit.quiport.presentation.main.components.reportMain

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import academy.bangkit.quiport.databinding.FragmentReportMainBinding
import android.content.Intent
import android.net.Uri
import com.bumptech.glide.Glide
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import timber.log.Timber

class ReportMainFragment : Fragment() {
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private var _binding : FragmentReportMainBinding? = null
    private val binding get() = _binding as FragmentReportMainBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentReportMainBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .requestProfile()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)

        val account = GoogleSignIn.getLastSignedInAccount(requireActivity())
        updateUI(account)

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
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    private fun signIn() {
        val signInIntent: Intent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)

            updateUI(account)
        } catch (e: ApiException) {
            Timber.w("signInResult:failed code=%s", e.statusCode)
            updateUI(null)
        }
    }

    private fun updateUI(account: GoogleSignInAccount?) {
        Timber.d(account.toString())

        if (account != null) {
            binding.apply {
                val personName: String? = account.displayName
                val personEmail: String? = account.email
                val personId: String? = account.id
                val personPhoto: Uri? = account.photoUrl

                tvProfile.text = """
                    Name : $personName
                    Email : $personEmail
                    ID : $personId
                """.trimIndent()

                Glide.with(requireActivity())
                    .load(personPhoto)
                    .into(ivProfile)

                btnLogOut.visibility = View.VISIBLE
                btnSignIn.visibility = View.GONE
            }
        } else {
            binding.apply {
                btnLogOut.visibility = View.GONE
                btnSignIn.visibility = View.VISIBLE
            }
        }
    }

    private fun signOut() {
        mGoogleSignInClient.signOut()
            .addOnCompleteListener(requireActivity()) {
                updateUI(null)
            }
    }

    private fun revokeAccess() {
        mGoogleSignInClient.revokeAccess()
            .addOnCompleteListener(requireActivity()) {
                updateUI(null)
            }
    }

    companion object {
        const val RC_SIGN_IN = 1
    }
}