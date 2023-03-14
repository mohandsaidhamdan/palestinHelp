package com.example.spalesatin.list_frsgment

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.adapter.myAdaoter
import com.example.myapplication.adapter.myAdaoter3
import com.example.spalesatin.R
import com.example.spalesatin.model2.people
import com.example.spalesatin.model2.user
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_b.*
import kotlinx.android.synthetic.main.activity_item_tr_admin.view.*
import kotlinx.android.synthetic.main.activity_tr_admin2.*
import kotlinx.android.synthetic.main.item_tr_admin.view.*


class tr_admin : AppCompatActivity() {

    lateinit var recyclerView: RecyclerView
    lateinit var myAdaoter: myAdaoter3
    lateinit  var database: DatabaseReference
    var arrayList:ArrayList<user> = ArrayList()


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tr_admin2)

        recyclerView = findViewById(R.id.recv22)

        database = FirebaseDatabase.getInstance().getReference("user")
        recyclerView.setHasFixedSize(true)

        recyclerView.layoutManager = LinearLayoutManager(this)
        myAdaoter = myAdaoter3(this , arrayList)

        recyclerView.adapter = myAdaoter


        val query: Query = database.orderByChild("name")
        query.addValueEventListener(object : ValueEventListener {
            @SuppressLint("NotifyDataSetChanged")
            override fun onDataChange(snapshot: DataSnapshot) {
                    for ( snapshot in snapshot.children){
                        val getche =      snapshot.child("che").getValue(String::class.java)
                        val getuser =     snapshot.child("user").getValue(String::class.java)
                        val getTilte =    snapshot.child("title").getValue(String::class.java)
                        val getname =     snapshot.child("name").getValue(String::class.java)
                        val getphone =     snapshot.child("phone").getValue(String::class.java)
                   if (getuser.equals("b") && getche.equals("true")){
                       val users = snapshot.getValue(user::class.java)
                       if (users != null) {
                           arrayList.add(users)
                       }
                   }



                    }


                myAdaoter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
            }

        })



    }









}