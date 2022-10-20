package com.example.chatapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignUp : AppCompatActivity() {

    private lateinit var edtName: EditText
    private lateinit var edtEmail: EditText
    private lateinit var edtPassword: EditText
    private lateinit var btnSignUp: Button

    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        supportActionBar?.hide()

        mAuth = FirebaseAuth.getInstance()

        edtName = findViewById(R.id.edt_name)
        edtEmail = findViewById(R.id.edt_email)
        edtPassword = findViewById(R.id.edt_password)
        btnSignUp = findViewById(R.id.btnSignUp)

        btnSignUp.setOnClickListener {
            val name = edtName.text.toString()
            val email = edtEmail.text.toString()
            val password = edtPassword.text.toString()

            if ((name == "" && email == "" && password == "") || (name == "" && email == "") || (email == "" && password == "") || (name == "" && password == "")) {
                // When all or multiple values are blank
                Toast.makeText(
                    applicationContext,
                    "Please fill all the required fields to create an account.",
                    Toast.LENGTH_SHORT
                ).show()
            }
            else if (name == "") {
                // When name is blank
                Toast.makeText(
                    applicationContext,
                    "Please enter your name to create an account.",
                    Toast.LENGTH_SHORT
                ).show()
            }
            else if (email == "") {
                // When email is blank
                Toast.makeText(
                    applicationContext,
                    "Please enter your email to create an account.",
                    Toast.LENGTH_SHORT
                ).show()
            }
            else if (password == "") {
                // When password is blank
                Toast.makeText(
                    applicationContext,
                    "Please enter the password to create an account.",
                    Toast.LENGTH_SHORT
                ).show()
            }
            else {
                signUp(name, email, password)
            }
        }
    }
    private fun signUp(name: String, email: String, password: String) {
        // logic of creating user
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // code for jumping to home
                    addUserToDatabase(name, email, mAuth.currentUser?.uid!!)
                    val intent = Intent(this@SignUp, MainActivity::class.java)
                    finish()
                    startActivity(intent)
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(this@SignUp, "Authentication failed.", Toast.LENGTH_SHORT).show()
                }
            }
    }
    private fun addUserToDatabase(name: String, email: String, uid: String) {
        mDbRef = FirebaseDatabase.getInstance().getReference()
        mDbRef.child("user").child(uid).setValue(User(name, email, uid))
    }
}