package com.example.spalesatin.list_mrsh

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.adapter.myAdaoterhelppelpeTa
import com.example.myapplication.adapter.myAdaoterhelppelpeTa2
import com.example.spalesatin.MainActivity
import com.example.spalesatin.R
import com.example.spalesatin.model2.helpsTa
import com.google.firebase.database.*
import java.util.ArrayList

class reseltSearch : AppCompatActivity() {
    lateinit var recyclerView: RecyclerView
    lateinit var myAdaoter: myAdaoterhelppelpeTa2
    lateinit  var database: DatabaseReference
    var arrayList: ArrayList<helpsTa> = ArrayList()

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reselt_search)
        val numberID = intent.getStringExtra("id")

        recyclerView = findViewById(R.id.listView3)

        database = FirebaseDatabase.getInstance().getReference("helpsTarshPeoples")
        recyclerView.setHasFixedSize(true)

        recyclerView.layoutManager = LinearLayoutManager(this)
        myAdaoter = myAdaoterhelppelpeTa2(this , arrayList)

        recyclerView.adapter = myAdaoter
        val query: Query = database.orderByChild("name")


        query.addValueEventListener(object : ValueEventListener {
            @SuppressLint("NotifyDataSetChanged")
            override fun onDataChange(snapshot: DataSnapshot) {
                val shard = getSharedPreferences("user", MODE_PRIVATE)
                val title = shard.getString("title", "false").toString()
                val user = shard.getString("user", "false").toString()

                    for (snapshot in snapshot.children) {
                      //  Toast.makeText(this@reseltSearch, "$id", Toast.LENGTH_SHORT).show()

                    //    if (snapshot.hasChild(id.toString())) {
                       val hala = snapshot.getValue(helpsTa::class.java)!!.hala
                       val id = snapshot.getValue(helpsTa::class.java)!!.idNumber
                        //     Toast.makeText(this@reseltSearch, "$hala", Toast.LENGTH_SHORT).show()
                       if (id.equals(numberID.toString())) {
                            val people = snapshot.getValue(helpsTa::class.java)
                            if (people != null) {
                                arrayList.add(people)
                           // }
                        }

                    }
                }


                myAdaoter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
            }

        })

    }





    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.home_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.home ->{
                val i = Intent(this, MainActivity::class.java)
                startActivity(i)
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}