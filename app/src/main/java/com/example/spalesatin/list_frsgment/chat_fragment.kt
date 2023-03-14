package com.example.spalesatin.list_frsgment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.spalesatin.R
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_chat_fragment.*

class chat_fragment : AppCompatActivity() {
    lateinit var db : FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_fragment)
        btn_send.setOnClickListener {
            val title = text_tilte.text.toString()
            val msg = text_msge.text.toString()
            var shard =  getSharedPreferences("check", MODE_PRIVATE)
            val id = shard.getString("id" , "false")
            val name = shard.getString("name" , "name")
            addtr(id.toString(),msg,name.toString(),title)
        }
    }
    fun addtr(id_user :String , msg :String , name :String , title :String){
        db = Firebase.firestore
        val s = hashMapOf("id_user" to id_user ,  "msg" to msg ,"name" to name , "title" to title)
        db.collection("chat")
            .add(s)
    }
}