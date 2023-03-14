package com.example.spalesatin.tr

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.spalesatin.Login.Login
import com.example.spalesatin.MainActivity
import com.example.spalesatin.R
import com.google.firebase.database.*
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_aske_admin.*
import kotlin.math.acos

class aske_admin : AppCompatActivity() {
    lateinit var db :FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_aske_admin)
        db= Firebase.firestore

        button11.setOnClickListener {
            val i = Intent(this,MainActivity::class.java)
            startActivity(i)
        }

        val shard =  getSharedPreferences("user", MODE_PRIVATE)
        val id =  shard.getString("id" , "false")

        button3.setOnClickListener {
            databeasRealtime2().Regster(this , this, id.toString() , "false")

        }


    }


}
class databeasRealtime2{
    var database: DatabaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://palestain-51792-default-rtdb.firebaseio.com/")

    fun Regster(context2 : Context,activity: Activity , phone : String, che : String){
        database.child("notification").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.hasChild(phone)){
                    Toast.makeText(context2, "تم الإرسال الطلب  من قبل", Toast.LENGTH_SHORT).show()
                }else{
                    // add data user in realtime Firebase
                    database.child("notification").child(phone).child("id").setValue(phone)
                    database.child("notification").child(phone).child("che").setValue(che)
                    Toast.makeText(context2, "تم إرسال الطلب", Toast.LENGTH_SHORT).show()
                    val i = Intent(context2,MainActivity::class.java)
                    context2.startActivity(i)
                    activity.finish()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("firebase", error.message)
                Toast.makeText(context2, "حاول لاحقا", Toast.LENGTH_SHORT).show()
            }


        })
    }

}
