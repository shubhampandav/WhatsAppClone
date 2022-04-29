package com.example.whatsappclone

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.whatsappclone.R
import com.example.whatsappclone.model.userModel
import com.google.common.collect.Iterables
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import java.util.*

class profile : AppCompatActivity() {

    lateinit var image: ImageView;
    lateinit var nextbnt: Button
    lateinit var name: TextView;
    private var pickImage = 100
    private var imageUri: Uri? = null
    lateinit var auth: FirebaseAuth;
    lateinit var storage:FirebaseStorage;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        image = findViewById(R.id.image);
        auth = FirebaseAuth.getInstance();
        nextbnt = findViewById(R.id.nextbtn);
        name = findViewById(R.id.name);
        storage = FirebaseStorage.getInstance();

        supportActionBar?.hide() // this is for hide the action bar
        image.setOnClickListener {
            val gallery =
                Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI).also {
                    startActivityForResult(it, pickImage)
                }

        }
        nextbnt.setOnClickListener {
            if (name.text.toString().isEmpty()) {
                Toast.makeText(this, "Enter your name", Toast.LENGTH_SHORT).show()
            } else if (image == null) {
                Toast.makeText(this, "select profile picture ", Toast.LENGTH_SHORT).show()
            } else {
                uploadData();
            }

        }

    }

    private fun uploadData() {
        val reference = storage.reference.child("profile").child(Date().time.toString())
        imageUri?.let {
            reference.putFile(it).addOnCompleteListener() {
                if(it.isSuccessful){
                  reference.downloadUrl.addOnCompleteListener {
                      uploadInfo(it.toString())
                  }
                }
            }
        }

    }

    private fun uploadInfo(imageUrl: String) {



    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == pickImage) {
            imageUri = data?.data
            image.setImageURI(imageUri)

        }
    }
}



