package com.example.spalesatin.tr

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.spalesatin.MainActivity
import com.example.spalesatin.R
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_ask_user_admin.*

class ask_user_admin : AppCompatActivity() {
    lateinit var db : FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ask_user_admin)

        db= Firebase.firestore
        btn_ok.setOnClickListener {
            alluser()
            val i = Intent(this , MainActivity::class.java)
            startActivity(i)
            Toast.makeText(this, "تم قبول الطلب بنجاح", Toast.LENGTH_SHORT).show()
        }
        btn_no.setOnClickListener {
            val i = Intent(this , MainActivity::class.java)
            startActivity(i)
        }


    }

    fun alluser(){
        val phone = intent.getStringExtra("phone" )
        val name = intent.getStringExtra("name" )
        db.collection("user")
            .whereEqualTo("phone" , "$phone")
            .get()
            .addOnSuccessListener { des ->
                for (doc in des){
                    val id = doc.id.toString()
                    update(id)
                }
            }
    }



    private fun update(oldId: String){
        val users = HashMap<String, Any>()
        /*  var m =1
          i+=m*/
        users["chek"] = "true"
        // users["$i"] = name
        db.collection("user").document(oldId)
            .update(users)
    }
}