package com.example.whatsappclone

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import org.w3c.dom.Text

class Verification : AppCompatActivity() {


    private var mAuth: FirebaseAuth? = null;

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verification)

        val verify = findViewById<Button>(R.id.verifybtn)
        val otp = findViewById<EditText>(R.id.otp)


        mAuth = FirebaseAuth.getInstance(); // init mauth
        val code = intent.getStringExtra("code") // accept the code from previous
        verify.setOnClickListener {
            if (code != null) {
                verifyCode(code, otp.text.toString())
            }
        }
    }

    // create fun for verify code  and pass the both codes
    private fun verifyCode(authcode: String, enterdCode: String) {

        val credential = PhoneAuthProvider.getCredential(authcode, enterdCode)
        signWithCredentials(credential); //call signwithcreadential function
    }

    //define function
    private fun signWithCredentials(credential: PhoneAuthCredential) {
        mAuth!!.signInWithCredential(credential)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    // send to next activity
                    var intent = Intent(this, profile::class.java)
                    startActivity(intent);

                } else {
                    // show the error msg
                    Toast.makeText(this, "verification check your otp and try again", Toast.LENGTH_SHORT).show()
                }
            }

    }
}
