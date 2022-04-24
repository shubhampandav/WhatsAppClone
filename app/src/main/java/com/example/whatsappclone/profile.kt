package com.example.whatsappclone

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.firebase.auth.FirebaseAuth

class profile : AppCompatActivity() {

    var mAuth: FirebaseAuth? = null;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        mAuth = FirebaseAuth.getInstance()
        val signout = findViewById<Button>(R.id.button);

        signout.setOnClickListener {

            mAuth!!.signOut();
            val intent = Intent(this, PhoneNumber::class.java)
            startActivity(intent);

        }
    }
}