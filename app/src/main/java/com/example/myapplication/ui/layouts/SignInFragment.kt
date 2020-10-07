package com.example.myapplication.ui.layouts

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.myapplication.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_sign_in.*
import kotlinx.android.synthetic.main.fragment_sign_in.signInButton

class SignInFragment : Fragment(R.layout.fragment_sign_in), View.OnClickListener {
    lateinit var signUpFragment: SignUpFragment
    lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        signUpFragment = SignUpFragment()
        auth = FirebaseAuth.getInstance()
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        signUpTextView.setOnClickListener(this)
        signInButton.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when(p0!!.id) {
            signUpTextView.id -> parentFragmentManager.beginTransaction().replace(R.id.log_in_fragment_container, signUpFragment).commit()
            signInButton.id -> {
                val email = signInEmailEditText.text.toString().trim()
                val password = signInPasswordEditText.text.toString()
                signIn(email, password)
            }
        }
    }

    private fun signIn(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(activity as Activity) {
            if (it.isSuccessful) {
                val intent = Intent((activity as Activity), MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
                (activity as Activity).finish()
            }
        }
    }
}