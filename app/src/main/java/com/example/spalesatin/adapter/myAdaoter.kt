package com.example.myapplication.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.example.spalesatin.R
import com.example.spalesatin.detaliess.detlis_people
import com.example.spalesatin.model2.people
import kotlinx.android.synthetic.main.item_cap11.view.*

class myAdaoter : RecyclerView.Adapter<myAdaoter.MyViewHolder>  , Filterable {


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
        var card = itemView.text_name3
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val root = LayoutInflater.from(activity).inflate(R.layout.item_cap11, parent, false)
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
            val i = Intent(activity, detlis_people::class.java)
            i.putExtra("data", data[position].name)
            activity.startActivity(i)

        }
    }

    override fun getFilter(): Filter {
        return object :Filter(){
            override fun performFiltering(p0: CharSequence?): FilterResults {
                val filterResults = FilterResults()
                if (p0 == null || p0.length < 0 ){
                    filterResults.count =filterList.size
                    filterResults.values = filterList
                }else{
                    var searchchar = p0.toString().toLowerCase()
                    val itemModel = ArrayList<people>()
                    for (item in filterList){
                        if (item.name.contains(searchchar)){
                            itemModel.add(item)
                        }
                    }
                    filterResults.count = itemModel.size
                    filterResults.values = itemModel
                }

                return filterResults

            }

            override fun publishResults(p0: CharSequence?, filterResults: FilterResults?) {
                data = filterResults!!.values as ArrayList<people> /* = java.util.ArrayList<com.example.vicky.contactreader.ContactDTO> */
                notifyDataSetChanged()
            }

        }
    }


}


