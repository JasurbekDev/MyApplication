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
import com.example.myapplication.db.models.User
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.fragment_sign_up.*

class SignUpFragment : Fragment(R.layout.fragment_sign_up), View.OnClickListener {
    lateinit var auth: FirebaseAuth
    lateinit var database: FirebaseDatabase
    lateinit var databaseReference: DatabaseReference
    lateinit var signInFragment: SignInFragment

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        signInFragment = SignInFragment()
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        databaseReference = database.getReference().child("users")
        signInTextView.setOnClickListener(this)
        signUpButton.setOnClickListener(this)
    }

    private fun signUpUser(userEmail: String, userPassword: String) {
        auth.createUserWithEmailAndPassword(userEmail, userPassword).addOnCompleteListener(activity as Activity) {
            if (it.isSuccessful) {
                val user = User(userEmail, userPassword)
                databaseReference.push().setValue(user)
                val intent = Intent((activity as Activity), MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
                (activity as Activity).finish()
            }
        }
    }

    override fun onClick(p0: View?) {
        when(p0!!.id) {
            signInTextView.id -> {
                parentFragmentManager.beginTransaction().replace(R.id.log_in_fragment_container, signInFragment).commit()
            }
            signUpButton.id -> {
                val userEmail = emailEditText.text.toString().trim()
                val userPassword = passwordEditText.text.toString().trim()
                signUpUser(userEmail, userPassword)
            }
        }
    }
}