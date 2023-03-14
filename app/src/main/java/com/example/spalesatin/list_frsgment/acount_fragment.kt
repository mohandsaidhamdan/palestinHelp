package com.example.spalesatin.list_frsgment

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.spalesatin.MainActivity
import com.example.spalesatin.R
import com.google.firebase.database.*
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.android.synthetic.main.activity_acount_fragment.*
import kotlinx.android.synthetic.main.activity_detiles_user.*
import kotlinx.android.synthetic.main.activity_sigin.*
import java.io.ByteArrayOutputStream
import kotlin.toString as toString1

class acount_fragment : AppCompatActivity() {
    var database: DatabaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://palestain-51792-default-rtdb.firebaseio.com/")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_acount_fragment)

        button10.setOnClickListener {


            val name = text_name_f.text.toString()
            val email = text_email_f.text.toString()
            val password = txt_password_f.text.toString()
            if (name.isEmpty()) {
                Toast.makeText(this, "أدخل الإسم", Toast.LENGTH_SHORT).show()
            } else if (name.isNotEmpty()){
                database.child("user").addListenerForSingleValueEvent(object :
                    ValueEventListener {
                    @SuppressLint("SuspiciousIndentation")
                    val shard =getSharedPreferences("user", AppCompatActivity.MODE_PRIVATE)
                    val id = shard.getString("id", "false")!!.toString()
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (snapshot.hasChild(id)) {
                            database.child("user").child(id).child("name").setValue(name)
                            Toast.makeText(this@acount_fragment, "تن تعديل الإسم", Toast.LENGTH_SHORT).show()
                            text_name_f.setText("")
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }
                })
            }
                    if (email.isEmpty()) {
                        Toast.makeText(this, "أدخل العنوان", Toast.LENGTH_SHORT).show()
                    } else if (email.isNotEmpty()){
                        database.child("user").addListenerForSingleValueEvent(object :
                            ValueEventListener {
                            @SuppressLint("SuspiciousIndentation")
                            val shard =getSharedPreferences("user", AppCompatActivity.MODE_PRIVATE)
                            val id = shard.getString("id", "false")!!.toString()
                            override fun onDataChange(snapshot: DataSnapshot) {
                                if (snapshot.hasChild(id)) {
                                    database.child("user").child(id).child( "title").setValue(email)
                                    Toast.makeText(this@acount_fragment, "تم تعديل العنوان  ", Toast.LENGTH_SHORT).show()
                                    text_email_f.setText("")
                                }
                            }

                            override fun onCancelled(error: DatabaseError) {
                                TODO("Not yet implemented")
                            }
                        })
                    }
                        if (password.isEmpty()) {
                            Toast.makeText(this, "أدخل كلمة السر", Toast.LENGTH_SHORT).show()
                        }  else if (password.isNotEmpty()){
            database.child("user").addListenerForSingleValueEvent(object :
                ValueEventListener {
                @SuppressLint("SuspiciousIndentation")
                val shard =getSharedPreferences("user", AppCompatActivity.MODE_PRIVATE)
                val id = shard.getString("id", "false")!!.toString()
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.hasChild(id)) {
                        database.child("user").child(id).child( "password").setValue(password)
                        Toast.makeText(this@acount_fragment, "تم تعدسل كلمة السر", Toast.LENGTH_SHORT).show()
                        txt_password_f.setText("")
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(this@acount_fragment, "حاول لاحقا", Toast.LENGTH_SHORT).show()
                }
            })
        }


        }

    }


}