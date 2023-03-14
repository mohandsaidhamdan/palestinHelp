package com.example.myapplication.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.spalesatin.R
import com.example.spalesatin.detaliess.detlis_people
import com.example.spalesatin.list_mrsh.list_admin_m
import com.example.spalesatin.model2.helpsTa
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_ask_user_admin.view.*
import kotlinx.android.synthetic.main.item_cap12.view.*


class myAdaoterhelppelpeTa2 : RecyclerView.Adapter<myAdaoterhelppelpeTa2.MyViewHolder>   {

    var activity: Activity
    var data: ArrayList<helpsTa>
    //private var filterList: ArrayList<helpsTa>
   // private var filter: filteritem? = null

    constructor(activity: Activity, data: ArrayList<helpsTa>) : super() {
        this.activity = activity
        this.data = data
      //  this.filterList = data
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var name = itemView.text_name
        var des = itemView.text_name2
        var numbur = itemView.text1
        var marid = itemView.text2
        var number_famly = itemView.text3
        var situation = itemView.text_name3
       var worker = itemView.text4
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val root = LayoutInflater.from(activity).inflate(R.layout.item_cap12, parent, false)
        //val root = LayoutInflater.from(activity).inflate(R.layout.activity_item_view_book_favorite, parent, false)
        return MyViewHolder(root)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, @SuppressLint("RecyclerView") position: Int) {
        holder.name.text = data[position].namePeople
        holder.des.text = data[position].idNumber
        holder.situation.text = data[position].type
        holder.marid.text = data[position].nameHelp
        holder.number_famly.text = "تم الإستلام"
        holder.numbur.text = data[position].dateOK


        val idUser  =data[position].name_user
        var database: DatabaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://palestain-51792-default-rtdb.firebaseio.com/")

        database.child("user").addListenerForSingleValueEvent(object : ValueEventListener {
            @SuppressLint("SuspiciousIndentation")
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.hasChild(idUser)) {
                    var getname = snapshot.child(idUser).child("name").getValue(String::class.java)
                    holder.worker.text =getname
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })


    }


}