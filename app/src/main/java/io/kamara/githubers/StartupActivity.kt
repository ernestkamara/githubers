package io.kamara.githubers

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth


class StartupActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_startup)

        if (isUserSignedIn()) {
            navigateToMainActivity()
        } else {
            createSignInIntent()
        }
    }

    private fun isUserSignedIn(): Boolean {
        val user = FirebaseAuth.getInstance().currentUser
        return user != null
    }

    private fun createSignInIntent() {
        val providers = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build(),
            AuthUI.IdpConfig.GoogleBuilder().build(),
            AuthUI.IdpConfig.AnonymousBuilder().build())

        startActivityForResult(
            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .setLogo(R.drawable.github_logo)
                .setTheme(R.style.AppThemeFirebaseAuth)
                .build(),
            RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val response = IdpResponse.fromResultIntent(data)

            if (resultCode == Activity.RESULT_OK) {
                navigateToMainActivity()
            } else {
                response?.error?.message?.let {
                    Snackbar.make(findViewById(R.id.startupContainer),
                        it, Snackbar.LENGTH_LONG).setAction("Try again") { createSignInIntent() }.show()
                }
            }
        }
    }


    private fun navigateToMainActivity() {
        //TODO: add in navigation and navigate using findNavController().navigate()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }


    companion object {
        private const val RC_SIGN_IN = 123
    }

}
