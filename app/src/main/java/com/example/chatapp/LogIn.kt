package com.example.chatapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase

class LogIn : AppCompatActivity() {

    private lateinit var edtEmail: EditText
    private lateinit var edtPassword: EditText
    private lateinit var btnLogin: Button
    private lateinit var btnSignUp: Button

    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)

        supportActionBar?.hide()

        mAuth = FirebaseAuth.getInstance()

        edtEmail = findViewById(R.id.edt_email)
        edtPassword = findViewById(R.id.edt_password)
        btnLogin = findViewById(R.id.btnLogin)
        btnSignUp = findViewById(R.id.btnSignUp)

        btnSignUp.setOnClickListener {
            val intent = Intent(this, SignUp::class.java)
            startActivity(intent)
        }

        btnLogin.setOnClickListener {
            val email = edtEmail.text.toString()
            val password = edtPassword.text.toString()

            if (email == "" && password == "") {
                // When both the values are blank
                Toast.makeText(
                    applicationContext,
                    "Enter both the fields to proceed.",
                    Toast.LENGTH_SHORT
                ).show()
            }
            else if (email == "") {
                // When the email is blank
                Toast.makeText(
                    applicationContext,
                    "Enter the registered email address to log in to your account.",
                    Toast.LENGTH_SHORT
                ).show()
            }
            else if (password == "") {
                // When the password is blank
                Toast.makeText(
                    applicationContext,
                    "Enter the password to log in to your account.",
                    Toast.LENGTH_SHORT
                ).show()
            }
            else {
                login(email, password);
            }
        }
    }
    private fun login(email: String, password: String) {
        // logic for logging in user
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // code for logging in user
                    val intent = Intent(this@LogIn, MainActivity::class.java)
                    finish()
                    startActivity(intent)
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(this@LogIn, "User does not exist.", Toast.LENGTH_SHORT).show()
                }
            }
    }
}