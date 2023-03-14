package com.example.myapplication.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.spalesatin.R
import com.example.spalesatin.detaliess.ListAllUserAddHelp
import com.example.spalesatin.model2.helps
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.item_cap2_user.view.*


class myAdaoterHUser : RecyclerView.Adapter<myAdaoterHUser.MyViewHolder> {

    var activity: Activity
    var data: ArrayList<helps>
    private var filterList: ArrayList<helps>

    constructor(activity: Activity, data: ArrayList<helps>) : super() {
        this.activity = activity
        this.data = data
        this.filterList = data
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var name = itemView.text_name
        var des = itemView.text_name2
        var numbur = itemView.text1
        var situation = itemView.text_name3
        var card = itemView.text_name3
        var numberM = itemView.text_name31

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val root = LayoutInflater.from(activity).inflate(R.layout.item_cap2_user, parent, false)
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
        holder.name.text = data[position].name
        holder.des.text = data[position].type
        holder.situation.text = data[position].date

        var shard =  activity.getSharedPreferences("user", AppCompatActivity.MODE_PRIVATE)
        val title = shard.getString("title" , "فلسطين")
        holder.situation.text = title
        if (title == "فلسطين"){
            holder.numberM.text = data[position].numberF
        }
        if (title == "المجاهدين"){
            holder.numberM.text = data[position].numberM
        }
        if (title == "السلام"){
            holder.numberM.text = data[position].numberS

        }

        // val dateF = data[position].dayf + "/" + data[position].monthF + "/" + data[position].yearF

        val text = "فعالة"
        holder.numbur.text = text
        holder.numbur.setOnClickListener {
            /* if (data[position].yearF.toInt() == data[position].year.toInt()) {
                 val text = "منتهية"
                 holder.numbur.text = text
                 holder.numbur.setBackgroundColor(Color.RED)

             } else {*/


            val i = Intent(activity, ListAllUserAddHelp::class.java)
            i.putExtra("name", data[position].name)
            i.putExtra("type", data[position].type)
            i.putExtra("number", data[position].number)
            i.putExtra("numberFamly", data[position].numberFamly)
            i.putExtra("date", data[position].date)
            i.putExtra("halaEconomy", data[position].halaEconomy)
            i.putExtra("halaSocial", data[position].halaSocial)
            i.putExtra("Worke", data[position].Worke)
            i.putExtra("helps", data[position].helps)
            i.putExtra("helps", data[position].helps)
            i.putExtra("day", data[position].day)
            i.putExtra("MONTH", data[position].MONTH)
            i.putExtra("year", data[position].year)
            i.putExtra("numberF", data[position].numberF)
            i.putExtra("numberM", data[position].numberM)
            i.putExtra("numberS", data[position].numberS)
            i.putExtra("hala", data[position].hala)
            i.putExtra("firstNumberPeople", data[position].firstNumberPeople)
            i.putExtra("scendNumberPeople", data[position].scendNumberPeople)
            activity.startActivity(i)
        }


        val hala = data[position].hala
        if (hala.equals("1")) {
            // holder.numbur.visibility = View.GONE
         //   holder.btnc.visibility = View.GONE
            holder.numbur.text = "منتهية"
            holder.numbur.setBackgroundColor(Color.BLUE)
        }


    }


}
