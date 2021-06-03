package academy.bangkit.quiport.presentation.profile

import academy.bangkit.quiport.databinding.ActivityProfileBinding
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import timber.log.Timber

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    private lateinit var mGoogleSignInClient: GoogleSignInClient

    companion object {
        const val RC_SIGN_IN = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .requestProfile()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        val account = GoogleSignIn.getLastSignedInAccount(this)
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

        setContentView(binding.root)
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
                val personGivenName: String? = account.givenName
                val personFamilyName: String? = account.familyName
                val personEmail: String? = account.email
                val personId: String? = account.id
                val personPhoto: Uri? = account.photoUrl

                tvProfile.text = """
                    Name : $personName
                    Given Name : $personGivenName
                    Family Name : $personFamilyName
                    Email : $personEmail
                    ID : $personId
                """.trimIndent()

                Glide.with(this@ProfileActivity)
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

        Timber.w("signInResult:success data=%s", account?.email)
    }

    private fun signOut() {
        mGoogleSignInClient.signOut()
            .addOnCompleteListener(this) {
                updateUI(null)
            }
    }

    private fun revokeAccess() {
        mGoogleSignInClient.revokeAccess()
            .addOnCompleteListener(this) {
                updateUI(null)
            }
    }
}