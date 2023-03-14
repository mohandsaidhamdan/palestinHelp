package com.example.myapplication.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.spalesatin.MainActivity
import com.example.spalesatin.R
import com.example.spalesatin.model2.people
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.item_cap111.view.*

class myAdaoter2 : RecyclerView.Adapter<myAdaoter2.MyViewHolder>   {

    var activity: Activity
    var data: ArrayList<people>
    private var filterList: ArrayList<people>

    constructor(activity: Activity, data: ArrayList<people>) : super() {
        this.activity = activity
        this.data = data
        this.filterList = data
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var name = itemView.tex1
        var title = itemView.tex2
        var order = itemView.tex3
        var ok = itemView.tex4
        var cancel = itemView.tex5

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val root = LayoutInflater.from(activity).inflate(R.layout.item_cap111, parent, false)
        //val root = LayoutInflater.from(activity).inflate(R.layout.activity_item_view_book_favorite, parent, false)
        return MyViewHolder(root)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.name.text = data[position].name
        holder.title.text = data[position].Title
        holder.order.text ="يطلب التسجيل بالموقع"
        val id_user= data[position].phone

        holder.ok.setOnClickListener {
            var database: DatabaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://palestain-51792-default-rtdb.firebaseio.com/")
            database.child("user").addListenerForSingleValueEvent(object : ValueEventListener {
                @SuppressLint("SuspiciousIndentation")
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.hasChild(id_user)) {
                        database.child("user").child(id_user).child("che").setValue("true")
                        Toast.makeText(activity, "تم قبول الطلب بنجاح", Toast.LENGTH_SHORT).show()
                        val i = Intent(activity , MainActivity::class.java)
                        activity.startActivity(i)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })
        }
        holder.cancel.setOnClickListener {
            var database: DatabaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://palestain-51792-default-rtdb.firebaseio.com/")

            database.child("user").addListenerForSingleValueEvent(object : ValueEventListener {
                @SuppressLint("SuspiciousIndentation")
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.hasChild(id_user)) {
                        val builder = AlertDialog.Builder(activity)
                        builder.setTitle("رفض الطلب")
                        builder.setMessage("هل أنت متأكد من رفض الطلب")
                        builder.setPositiveButton("رفض") { dialog, _ ->
                            database.child("user").child(id_user).child("che").setValue("r")
                            Toast.makeText(activity, "تم رفض الطلب بنجاح", Toast.LENGTH_SHORT).show()
                            val i = Intent(activity , MainActivity::class.java)
                            activity.startActivity(i)
                        }
                        builder.setNegativeButton("لا") { dialog, _ ->
                            dialog.dismiss()
                        }
                        val alertDialog = builder.create()
                        alertDialog.show()



                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })
        }



    }

    /*override fun getFilter(): Filter {
        if (filter == null) {
            filter = filteritem(filterList, this)
        }
        return filter as filteritem
    }*/

}