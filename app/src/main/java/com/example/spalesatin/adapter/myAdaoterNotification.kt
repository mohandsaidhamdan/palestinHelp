package com.example.spalesatin.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.spalesatin.MainActivity
import com.example.spalesatin.R
import com.example.spalesatin.detaliess.detlis_people
import com.example.spalesatin.list_frsgment.list_aske
import com.example.spalesatin.model2.helps
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.itemnotification.view.*

class myAdaoterNotification : RecyclerView.Adapter<myAdaoterNotification.MyViewHolder> {

    var activity: Activity
    var data: ArrayList<helps>

    constructor(activity: Activity, data: ArrayList<helps>) : super() {
        this.activity = activity
        this.data = data
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var type = itemView.itemtypehelps
        var date = itemView.itemDate
        var hala = itemView.itemhala
        var itemClickCard = itemView.itemcared
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val root = LayoutInflater.from(activity).inflate(R.layout.itemnotification, parent, false)
        //val root = LayoutInflater.from(activity).inflate(R.layout.activity_item_view_book_favorite, parent, false)
        return MyViewHolder(root)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    @SuppressLint("SuspiciousIndentation")
    override fun onBindViewHolder(
        holder: MyViewHolder,
        @SuppressLint("RecyclerView") position: Int
    ) {
        holder.type.text = data[position].type
        holder.date.text = data[position].date

        val text = "فعالة"
        holder.hala.text = text

        holder.itemClickCard.setOnClickListener {
            activity.startActivity(Intent(activity , list_aske::class.java))
            activity.finish()
        }



    }


}
