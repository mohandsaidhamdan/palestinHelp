package com.example.myapplication.adapter

import android.app.Activity
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.spalesatin.R
import com.example.spalesatin.model2.people
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.item_cap.view.*
import java.util.*
import kotlin.collections.ArrayList

class myAdaoterAddHelps : RecyclerView.Adapter<myAdaoterAddHelps.MyViewHolder>   {
    var database: DatabaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://palestain-51792-default-rtdb.firebaseio.com/")

    var activity: Activity
    var data: ArrayList<people>
    private var filterList: ArrayList<people>

    constructor(activity: Activity, data: ArrayList<people>) : super() {
        this.activity = activity
        this.data = data
        this.filterList = data
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var name = itemView.text_name
        var des = itemView.text_name2
        var numbur = itemView.text1
        var marid = itemView.text2
        var number_famly = itemView.text3
        var situation = itemView.text_name3
        var phone = itemView.text5
        var spierp = itemView.text6
       var worker = itemView.text4
        var card = itemView.btnadd
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val root = LayoutInflater.from(activity).inflate(R.layout.item_cap, parent, false)
        //val root = LayoutInflater.from(activity).inflate(R.layout.activity_item_view_book_favorite, parent, false)
        return MyViewHolder(root)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.name.text = data[position].name
        holder.des.text = data[position].Title
        holder.situation.text = data[position].halaEconomy
        holder.number_famly.text = data[position].numberFamly
        holder.marid.text = data[position].halaSocial
        holder.numbur.text = data[position].numberID
        holder.phone.text = data[position].phone
        holder.spierp.text = data[position].halaMar
        holder.worker.text = data[position].Worke

        holder.card.setOnClickListener {
            val text = "ترشيح"
            if (holder.card.text.equals(text)){
                holder.card.text = "إلغاء الترشح"
         //       holder.card.text = data[position].numberID
                holder.card.setBackgroundColor(Color.RED)
                holder.card.setTextColor(Color.WHITE)
                val name =activity.intent.getStringExtra("name").toString()
                val type =activity.intent.getStringExtra("type").toString()


                try {
                    val dat = Calendar.getInstance()
                    val year = dat.get(Calendar.YEAR)
                    val MONTH = dat.get(Calendar.MONTH) + 1
                    val day = dat.get(Calendar.DAY_OF_MONTH)
                    val date = year.toString() + "/" + MONTH.toString() + "/" + day.toString()
                    val shard = activity.getSharedPreferences("user", AppCompatActivity.MODE_PRIVATE)
                    val id = shard.getString("id", "false").toString()
                    val title = shard.getString("title", "false").toString()

                    database.child("helpsTarshPeople").child(data[position].numberID).child("name").setValue(data[position].name)
                    database.child("helpsTarshPeople").child(data[position].numberID).child("numberID").setValue(data[position].numberID)
                    database.child("helpsTarshPeople").child(data[position].numberID).child("hala").setValue("0")
                    database.child("helpsTarshPeople").child(data[position].numberID).child("hala").setValue("0")
                    database.child("helpsTarshPeople").child(data[position].numberID).child("nameHelps").setValue(name)
                    database.child("helpsTarshPeople").child(data[position].numberID).child("typeHelps").setValue(type)
                    database.child("helpsTarshPeople").child(data[position].numberID).child("dateTa").setValue(date)
                    database.child("helpsTarshPeople").child(data[position].numberID).child("user").setValue(id)
                    database.child("helpsTarshPeople").child(data[position].numberID).child("titleUser").setValue(title)

                }catch (e:Exception){
                }


            }else{
                holder.card.text = "ترشيح"
                holder.card.setTextColor(Color.WHITE)
                holder.card.setBackgroundColor(Color.GRAY)
                try {
                database!!.child("helpsTarshPeople").removeValue()
                }catch (e:Exception){
                }
            }

        }
    }

    /*override fun getFilter(): Filter {
        if (filter == null) {
            filter = filteritem(filterList, this)
        }
        return filter as filteritem
    }*/

}