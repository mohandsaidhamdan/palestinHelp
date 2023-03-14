package com.example.spalesatin.tr

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.HorizontalScrollView
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.adapter.myAdaoter
import com.example.myapplication.adapter.myAdaoter2
import com.example.spalesatin.R
import com.example.spalesatin.model2.people
import com.google.firebase.database.*

class orderOk : AppCompatActivity() {
    lateinit var recyclerView: RecyclerView
    lateinit var myAdaoter: myAdaoter2
    lateinit  var database: DatabaseReference
    var arrayList:ArrayList<people> = ArrayList()


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_ok)




        recyclerView = findViewById(R.id.listView23)

        database = FirebaseDatabase.getInstance().getReference("user")
        recyclerView.setHasFixedSize(true)

        recyclerView.layoutManager = LinearLayoutManager(this)
        myAdaoter = myAdaoter2(this , arrayList)

        recyclerView.adapter = myAdaoter


        val query: Query = database.orderByChild("name")

        query.addValueEventListener(object : ValueEventListener {
            @SuppressLint("NotifyDataSetChanged")
            override fun onDataChange(snapshot: DataSnapshot) {
                    for ( snapshot in snapshot.children){
                        val people = snapshot.getValue(people::class.java)
                        if (people != null) {
                            if(people.che.equals("false")){
                                arrayList.add(people)
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