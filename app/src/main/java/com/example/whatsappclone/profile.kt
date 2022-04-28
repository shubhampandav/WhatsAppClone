package com.example.whatsappclone

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.ImageView

class profile : AppCompatActivity() {

    val userimge: ImageView? = null;

    private var image: Uri? = null
    private  var REQUEST_CODE = 100;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        supportActionBar!!.hide(); // this is fro hide the action bar
        val userimg = findViewById<ImageView>(R.id.userimg);


    }

}








