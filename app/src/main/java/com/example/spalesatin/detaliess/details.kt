package com.example.spalesatin.detaliess

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.os.Bundle
import android.os.Handler
import android.view.*
import android.widget.Filter
import android.widget.HorizontalScrollView
import android.widget.LinearLayout
import android.widget.SearchView
import android.widget.SearchView.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.adapter.myAdaoter
import com.example.spalesatin.R
import com.example.spalesatin.model2.people
import com.google.firebase.database.*
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_details.*
import kotlinx.android.synthetic.main.activity_detiltes_help.*

class details : AppCompatActivity()  {
    lateinit var db: FirebaseFirestore


    lateinit var recyclerView: RecyclerView
    lateinit var myAdaoter: myAdaoter
    lateinit  var database: DatabaseReference
    var arrayList:ArrayList<people> = ArrayList()
    var arrayList2:ArrayList<people> = ArrayList()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        val scrollView = findViewById<HorizontalScrollView>(R.id.startHorizontal)
        val layout = LinearLayout(this)
        layout.orientation = LinearLayout.HORIZONTAL
        scrollView.setLayoutDirection(View.LAYOUT_DIRECTION_RTL)


        val progressDialog = ProgressDialog(this)


        recyclerView = findViewById(R.id.listView)

         database = FirebaseDatabase.getInstance().getReference("people")
        recyclerView.setHasFixedSize(true)

        recyclerView.layoutManager = LinearLayoutManager(this)
        arrayList2.addAll(arrayList)
        myAdaoter = myAdaoter(this , arrayList)
        recyclerView.adapter = myAdaoter


        val query: Query = database.orderByChild("name")
        query.addValueEventListener(object :ValueEventListener{
            @SuppressLint("NotifyDataSetChanged")
            override fun onDataChange(snapshot: DataSnapshot) {
                val shard = getSharedPreferences("user", MODE_PRIVATE)
                val title = shard.getString("title", "false").toString()
                val user = shard.getString("user", "false").toString()
                if (user.equals("A")){
                    for ( snapshot in snapshot.children){
                            val people = snapshot.getValue(people::class.java)
                            if (people != null) {
                                arrayList.add(people)
                            }
                    }
                }else{
                    val titleAll = "مسجد " + title
                    for ( snapshot in snapshot.children){
                        val title = snapshot.getValue(people::class.java)!!.Title

                        if (title.equals(titleAll.toString())) {
                            val people = snapshot.getValue(people::class.java)
                            if (people != null) {
                                arrayList.add(people)
                            }
                        }
                    }
                }

                myAdaoter.notifyDataSetChanged()
                if(arrayList.size > 0){
                    progressDialog.dismiss()
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

        menuInflater.inflate(R.menu.search_item , menu)
        val menuItem=menu!!.findItem(R.id.serchview)
        val searchview = menuItem.actionView as androidx.appcompat.widget.SearchView
        searchview.maxWidth = Int.MAX_VALUE

        searchview.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                myAdaoter!!.filter.filter(p0)

                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {

                myAdaoter!!.filter.filter(p0)
                return true
            }

        })
        return true
    }


}



