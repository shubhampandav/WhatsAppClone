package com.example.whatsappclone

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import de.hdodenhof.circleimageview.CircleImageView
import java.net.URI
import java.util.*


class profile : AppCompatActivity() {

    private lateinit var auth:FirebaseAuth
    private lateinit var database:FirebaseDatabase
    private lateinit var storage: FirebaseStorage
    private lateinit var imguri:Uri
    private lateinit var userimg:ImageView
    private lateinit var name:EditText
    private lateinit var nextbtn:Button


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
            val intent = Intent();
            intent.type = "image/*"
            startActivityForResult(intent,101)
        }
        nextbtn.setOnClickListener {

            if (name.text.isEmpty()){
                Toast.makeText(this, "Enter your name ", Toast.LENGTH_SHORT).show()
                
            }else if(userimg == null){
                Toast.makeText(this, "select image", Toast.LENGTH_SHORT).show()
            }else uploadData()
        }



    }

    private fun uploadData() {
        val ref = storage.reference.child("profile")
            .child(Date().time.toString())
        ref.putFile(imguri).addOnCompleteListener{
            if (it.isSuccessful){
                ref.downloadUrl.addOnSuccessListener { task ->
                    uploadInfo(task.toString())
                }
            }
        }
    }

    private fun uploadInfo(imgurl: String) {

        val user =User(auth.uid.toString(), name.text.toString(), imgurl)
        database.reference.child("users")
            .child(auth.uid.toString())
            .setValue(user)
            .addOnSuccessListener {
                startActivity(Intent(this,MainActivity::class.java))
                    finish()
            }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data != null){
            if (data.data != null){
                imguri = data.data!!
                userimg.setImageURI(imguri)
            }
        }
    }

}