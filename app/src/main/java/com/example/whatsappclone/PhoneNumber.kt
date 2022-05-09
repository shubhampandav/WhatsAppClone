package com.example.whatsappclone

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle

import android.widget.Button
import android.widget.EditText

import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.PhoneAuthProvider.ForceResendingToken

import java.util.concurrent.TimeUnit


class PhoneNumber : AppCompatActivity() {

    var mAuth: FirebaseAuth? = null;  // declare firebaseauth here
    var progressDialog: ProgressDialog? = null;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_phone_number)
        supportActionBar?.hide(); // to hide action bar

        val cbtn = findViewById<Button>(R.id.cbtn);
        val number = findViewById<EditText>(R.id.number);


        mAuth = FirebaseAuth.getInstance() // initialize firebaseAuh
        progressDialog = ProgressDialog(this)
        progressDialog!!.setTitle("Sending ")
        progressDialog!!.setMessage("We are sending OTP to your phone number");


        // add onclick listener on bnt cbtn
        cbtn.setOnClickListener {

            // get phone number to String and store in phone variable
            val phone = number.text.toString();

            //call function for generating otp using mobile number
            sendVerificationCode(phone);


        }
        if (mAuth!!.currentUser!=null)
        {
            startActivity(Intent(this,profile::class.java));
        }

    }

    // sendverificationcode function define
    private fun sendVerificationCode(phone: String) {
        progressDialog!!.show();

        val options = PhoneAuthOptions.newBuilder(mAuth!!)
            .setPhoneNumber(phone)
            .setActivity(this)
            .setCallbacks(mcallBack)
            .setTimeout(60, TimeUnit.SECONDS)
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)

    }

    // this is code ocallback function
    private val mcallBack: PhoneAuthProvider.OnVerificationStateChangedCallbacks =
        object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            override fun onCodeSent(p0: String, p1: ForceResendingToken) {
                super.onCodeSent(p0, p1)

                val intent = Intent(this@PhoneNumber, Verification::class.java)
                intent.putExtra("code", p0)
                startActivity(intent)
                progressDialog!!.dismiss()
            }

            override fun onVerificationCompleted(p0: PhoneAuthCredential) {

            }

            override fun onVerificationFailed(p0: FirebaseException) {
                Toast.makeText(this@PhoneNumber, "failed", Toast.LENGTH_SHORT).show()

            }

        }



}