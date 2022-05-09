package com.example.whatsappclone


import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle

import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

import com.google.firebase.auth.FirebaseAuth

import com.google.firebase.database.FirebaseDatabase

import com.google.firebase.storage.FirebaseStorage
import java.net.URI
import java.text.SimpleDateFormat

import java.util.*


class profile : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var storage: FirebaseStorage
    private lateinit var imageuri: Uri
    private lateinit var userimg: ImageView
    private lateinit var name: EditText
    private lateinit var nextbtn: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        storage = FirebaseStorage.getInstance()
        userimg = findViewById(R.id.userimg);
        nextbtn = findViewById(R.id.nextBtn);
        name = findViewById(R.id.name);

        userimg.setOnClickListener {
            // this code for select the image from gallery
            val intent = Intent();
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT

            startActivityForResult(intent, 101)
        }
        nextbtn.setOnClickListener {

            if (name.text.isEmpty()) {
                Toast.makeText(this, "Enter your name ", Toast.LENGTH_SHORT).show()

            } else if (userimg == null) {
                Toast.makeText(this, "select image", Toast.LENGTH_SHORT).show()
            } else uploadData()
        }


    }

    private fun uploadData() {
        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Uploading File")
        progressDialog.setCancelable(false)
        progressDialog.show()

        val formatter = SimpleDateFormat("yyyy_mmm_dd_hh_mm_ss", Locale.getDefault())
        val now = Date();
        val filename = formatter.format(now);
        val storageReference = FirebaseStorage.getInstance().getReference("Images/$filename")
        storageReference.putFile(imageuri)
            .addOnSuccessListener {
                userimg.setImageURI(null)
                Toast.makeText(this, "successfully uploaded", Toast.LENGTH_SHORT).show()
                if (progressDialog.isShowing) progressDialog.dismiss()
            }.addOnFailureListener {
                if (progressDialog.isShowing) progressDialog.dismiss()
                Toast.makeText(this, "upload failed", Toast.LENGTH_SHORT).show()

            }


    }


    // Here we override the method which called when user select the images from gallery
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 101 && resultCode == RESULT_OK) {
            imageuri = data?.data!!
            userimg.setImageURI(imageuri)

        }
    }

}