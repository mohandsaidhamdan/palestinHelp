package com.example.spalesatin.list_frsgment

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.*
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.adapter.myAdaoter
import com.example.myapplication.adapter.myAdaoterH
import com.example.myapplication.adapter.myAdaoterHUser
import com.example.spalesatin.MainActivity
import com.example.spalesatin.R
import com.example.spalesatin.model2.helps
import com.example.spalesatin.model2.people
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_list_aske.*

class list_aske : AppCompatActivity() {

    lateinit var recyclerView: RecyclerView
    lateinit var myAdaoter: myAdaoterH
    lateinit var myAdaoterHUser: myAdaoterHUser
    var arrayList: ArrayList<helps> = ArrayList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_aske)


        recyclerView = findViewById(R.id.rec_list_aske)


        recyclerView.setHasFixedSize(true)

        recyclerView.layoutManager = LinearLayoutManager(this)

        val shard = getSharedPreferences("user", MODE_PRIVATE)
        val user = shard.getString("user", "false").toString()
        val progressDialog = ProgressDialog(this)

        if (user.equals("A")) {
            textNumber122.visibility = View.GONE
            myAdaoter = myAdaoterH(this, arrayList)
            recyclerView.adapter = myAdaoter
        } else {
            myAdaoterHUser = myAdaoterHUser(this, arrayList)
            recyclerView.adapter = myAdaoterHUser
        }


        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("helps")


        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                if (user == "A") {

                    for (userSnapshot in snapshot.children) {

                        val help = userSnapshot.getValue(helps::class.java)

                        if (help != null) {
                            arrayList.add(help)
                        }

                    }
                } else {
                    for (snapshot in snapshot.children) {
                        val people = snapshot.getValue(helps::class.java)
                        val hala = snapshot.getValue(helps::class.java)!!.hala
                        if (hala == "0") {
                            if (people != null) {
                                arrayList.add(people)
                            }
                        }

                    }
                }
                if (user.equals("A")) {
                    myAdaoter.notifyDataSetChanged()
                    if(arrayList.size > 0){
                        progressDialog.dismiss()
                    }

                } else {
                    myAdaoterHUser.notifyDataSetChanged()
                    if(arrayList.size > 0){
                        progressDialog.dismiss()
                    }

                }

            }

            override fun onCancelled(error: DatabaseError) {
            }

        })




        progressDialog.show()
        progressDialog.setContentView(R.layout.progress_dialog)
        progressDialog.window!!.setBackgroundDrawableResource(com.firebase.ui.auth.R.color.fui_transparent)

        Handler().postDelayed({
            progressDialog.dismiss()
        }, 5000)
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.home_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.home -> {
                val i = Intent(this, MainActivity::class.java)
                startActivity(i)
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }


}
