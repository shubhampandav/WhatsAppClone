package com.example.whatsappclone

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.firebase.auth.FirebaseAuth

class signup : AppCompatActivity() {
    var mAuth: FirebaseAuth? = null;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        supportActionBar?.hide();

        val phone = findViewById<Button>(R.id.phonebtn);
        mAuth = FirebaseAuth.getInstance() // initialize firebaseAuh
        if(mAuth!!.currentUser != null)
        {
            startActivity(Intent(this,profile::class.java));


        }

        phone.setOnClickListener {
            val intent = Intent(this,PhoneNumber::class.java);
            startActivity(intent);
        }
    }
}