package com.example.myapplication.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.spalesatin.MainActivity
import com.example.spalesatin.R
import com.example.spalesatin.model2.user
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.item_add.*
import kotlinx.android.synthetic.main.item_cap11.view.*
import kotlinx.android.synthetic.main.item_tr_admin.view.*
import kotlinx.android.synthetic.main.login.*

class myAdaoter3 : RecyclerView.Adapter<myAdaoter3.MyViewHolder>   {

    var activity: Activity
    var data: ArrayList<user>
    private var filterList: ArrayList<user>

    constructor(activity: Activity, data: ArrayList<user>) : super() {
        this.activity = activity
        this.data = data
        this.filterList = data
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var name = itemView.textname
        var title = itemView.textname2
        var tr = itemView.textname3
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val root = LayoutInflater.from(activity).inflate(R.layout.item_tr_admin, parent, false)
        //val root = LayoutInflater.from(activity).inflate(R.layout.activity_item_view_book_favorite, parent, false)
        return MyViewHolder(root)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.name.text = data[position].name
        holder.title.text = data[position].Title
        holder.tr.text = "ترقية الحساب"
        holder.tr.setOnClickListener {
            //val dilalog = Dialog(activity)
           // val title  = dilalog.setTitle("هل انت متأكد من ترقية الحساب ؟؟")

            val dilalog = AlertDialog.Builder(activity)
            dilalog.setTitle("ترقية الحساب")
            dilalog.setMessage("هل انت متأكد من ترقية الحساب ؟؟")
            dilalog.setPositiveButton("نعم") { dialog, which ->
                // Do something when the positive button is clicked
                updateuser(data[position].phone)
                Toast.makeText(activity, data[position].phone, Toast.LENGTH_SHORT).show()

            }
            dilalog.setNegativeButton("لا") { dialog, which ->
                // Do something when the negative button is clicked
                dialog.dismiss()

            }
            dilalog.show()


        }

    }
    fun updateuser(phone:String ){
        var database: DatabaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://palestain-51792-default-rtdb.firebaseio.com/")

        database.child("user").addListenerForSingleValueEvent(object : ValueEventListener {
            @SuppressLint("SuspiciousIndentation")
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.hasChild(phone)) {
                    val getche = snapshot.child(phone).child("che").getValue(String::class.java)
                    val getphone = snapshot.child(phone).child("phone").getValue(String::class.java)
                    if (getphone.equals(phone)) {
                        if (getche.equals("true")) {
                            database.child("user").child(phone).child("user").setValue("A")
                            activity.startActivity(Intent(activity , MainActivity::class.java))
                            activity.finish()
                        }

                    }

                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("firebase", error.message)
            }


        })
    }

    /*override fun getFilter(): Filter {
        if (filter == null) {
            filter = filteritem(filterList, this)
        }
        return filter as filteritem
    }*/

}