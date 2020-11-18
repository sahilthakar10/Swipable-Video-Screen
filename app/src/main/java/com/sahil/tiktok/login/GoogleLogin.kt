package com.sahil.tiktok.login

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.internal.OnConnectionFailedListener
import com.google.android.gms.tasks.Task
import com.sahil.tiktok.R
import com.sahil.tiktok.utils.UserPreferences
import com.sahil.tiktok.extension.toast
import com.sahil.tiktok.home.activity.VideoPlayerActivity
import kotlinx.android.synthetic.main.activity_google_login.*
import kotlinx.coroutines.launch

class GoogleLogin : AppCompatActivity(), OnConnectionFailedListener {

    private val RC_SIGN_IN = 1
    private var googleApiClient: GoogleSignInClient? = null
    private lateinit var userPreferences: UserPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_google_login)

        userPreferences = UserPreferences(this)

        initGoogleSignInClient()
        setOnClickListeners()

    }

    private fun dataStore(){
        lifecycleScope.launch {
            userPreferences.saveBookmark(true)
        }
    }

    private fun setOnClickListeners(){
        sign_in_button.setOnClickListener {
            val intent = googleApiClient?.signInIntent
            startActivityForResult(intent, RC_SIGN_IN)
        }
    }

    private fun initGoogleSignInClient() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build()
        googleApiClient =  GoogleSignIn.getClient(this, gso);
    }

    override fun onConnectionFailed(p0: ConnectionResult) {
        // Failed Login
        toast("Failed to login")
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }
    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            // Signed in successfully, show authenticated UI.
            toast("Login successfully")
            gotoProfile()
        } catch (e: ApiException) {
            e.printStackTrace()
        }
    }


    private fun gotoProfile() {
        dataStore()
        val intent = Intent(this, VideoPlayerActivity::class.java)
        startActivity(intent)
        finish()
    }
}