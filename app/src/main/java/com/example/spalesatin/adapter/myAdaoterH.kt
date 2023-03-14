package com.example.myapplication.adapter

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
import com.example.spalesatin.model2.helps
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.item_cap2.view.*

class myAdaoterH : RecyclerView.Adapter<myAdaoterH.MyViewHolder> {

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
        var btnc = itemView.btnc
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val root = LayoutInflater.from(activity).inflate(R.layout.item_cap2, parent, false)
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

        // val dateF = data[position].dayf + "/" + data[position].monthF + "/" + data[position].yearF

        val text = "فعالة"
        holder.numbur.text = text
        holder.numbur.setOnClickListener {
            /* if (data[position].yearF.toInt() == data[position].year.toInt()) {
                 val text = "منتهية"
                 holder.numbur.text = text
                 holder.numbur.setBackgroundColor(Color.RED)

             } else {*/


            val i = Intent(activity, detlis_people::class.java)
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
            i.putExtra("numberF", data[position].numberF)
            i.putExtra("numberM", data[position].numberM)
            i.putExtra("numberS", data[position].numberS)
            i.putExtra("hala", data[position].hala)
            i.putExtra("firstNumberPeople", data[position].firstNumberPeople)
            i.putExtra("scendNumberPeople", data[position].scendNumberPeople)
            activity.startActivity(i)
            val shard = activity.getSharedPreferences("user", AppCompatActivity.MODE_PRIVATE)
            val title = shard.getString("title", "false").toString()
            val user = shard.getString("user", "false").toString()
            if (user.equals("A")) {

                //  }
            }


        }


        // button cancel

        holder.btnc.setOnClickListener {
            var database: DatabaseReference = FirebaseDatabase.getInstance().getReference("helps")
            database.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val hala = data[position].hala
                    if (hala == "0") {
                        val builder = AlertDialog.Builder(activity)
                        builder.setTitle("هل انت متأكد من إنهاء هذه المساعدة؟")
                        builder.setMessage("لن تتمكن من تفعيلها مجددا..")
                            .setCancelable(false)
                            .setPositiveButton("إنهاء") { dialog, id ->
                                // Delete selected note from database
                                val date =
                                    data[position].day + data[position].MONTH + data[position].year
                                val sc = data[position].scend
                                database.child(data[position].name).child(date).child(sc)
                                    .child("hala").setValue("1")
                                val i = Intent(activity, MainActivity::class.java)
                                activity.startActivity(i)
                                activity.finish()
                                Toast.makeText(
                                    activity,
                                    "تم إنهاء هذه المساعدة",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                            .setNegativeButton("لا") { dialog, id ->
                                // Dismiss the dialog
                                dialog.dismiss()
                            }
                        val alert = builder.create()
                        alert.show()
                    } else if (hala == "1") {
                        Toast.makeText(activity, "هذه المساعدة منتهية", Toast.LENGTH_SHORT).show()
                    }


                }

                override fun onCancelled(error: DatabaseError) {
                }

            })

        }


        val hala = data[position].hala
        if (hala.equals("1")) {
            // holder.numbur.visibility = View.GONE
            holder.btnc.visibility = View.GONE
            holder.numbur.text = "منتهية"
            holder.numbur.setBackgroundColor(Color.BLUE)
        }


    }


}
