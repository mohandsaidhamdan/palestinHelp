package com.example.spalesatin.list_mrsh

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.spalesatin.MainActivity
import com.example.spalesatin.R
import com.google.firebase.database.*
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.ditlels.*

class search : AppCompatActivity() {
    lateinit var db: FirebaseFirestore
    var database: DatabaseReference = FirebaseDatabase.getInstance()
        .getReferenceFromUrl("https://palestain-51792-default-rtdb.firebaseio.com/")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        btnSearch.setOnClickListener {
            val numberid = textIdSearch.text.toString()

            if (numberid.isEmpty()) {
                Toast.makeText(this@search, "كل الحقول مطلوبة", Toast.LENGTH_SHORT).show()
            } else {
                database.child("people")
                    .addListenerForSingleValueEvent(object : ValueEventListener {
                        @SuppressLint("SuspiciousIndentation")
                        override fun onDataChange(snapshot: DataSnapshot) {
                            if (snapshot.hasChild(numberid)) {
                                var getname = snapshot.child(numberid).child("name")
                                    .getValue(String::class.java)
                                val i = Intent(this@search , reseltSearch::class.java)
                                i.putExtra("id" , numberid)
                                startActivity(i)
                            } else {
                                Toast.makeText(this@search, "رقم الهوية خطأ", Toast.LENGTH_SHORT)
                                    .show()
                            }
                        }

                        override fun onCancelled(error: DatabaseError) {
                            Log.e("firebase", error.message)
                            Toast.makeText(this@search, "حاول لاحقا", Toast.LENGTH_SHORT).show()
                        }


                    })
            }

        }
    }
}