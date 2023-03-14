package com.example.spalesatin.list_frsgment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.spalesatin.MainActivity
import com.example.spalesatin.R
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class tr_fragment : AppCompatActivity() {
    lateinit var db :FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tr_fragment)

        var shard =  getSharedPreferences("check", MODE_PRIVATE)
        val id = shard.getString("id" , "false")
        val name = shard.getString("name" , "name")
        val user = shard.getString("user" , "user").toString()
        val title = shard.getString("title" , "title")

        db =Firebase.firestore


        val alertDialog = AlertDialog.Builder(this)
        alertDialog.setTitle("طلب الترقية")
        alertDialog.setMessage("سيتم إرسال رسالة الى المسؤول بطلب الترقية")
        alertDialog.setPositiveButton("ترقية"){ a , i ->
            val i = Intent(this , MainActivity::class.java)

            if (user.equals("admin")){
                Toast.makeText(this, "انت مسؤول لا يمكن الترقية ", Toast.LENGTH_SHORT).show()
                val i = Intent(this , MainActivity::class.java)
                startActivity(i)
            }else{
                addtr(id.toString() , "user" , name.toString() ,title.toString())
                Toast.makeText(this, "تم الإرسال", Toast.LENGTH_SHORT).show()
                val i = Intent(this , MainActivity::class.java)
                startActivity(i)
            }


        }
        alertDialog.setNegativeButton("إلغاء"){ a , i ->
            val i = Intent(this , MainActivity::class.java)
            startActivity(i)

        }
        alertDialog.setCancelable(false)

    }

    fun addtr(id_user :String , msg :String , name :String , title :String){
        val s = hashMapOf("id_user" to id_user ,  "msg" to msg ,"name" to name , "title" to title)
        db.collection("tr")
            .add(s)
    }


}