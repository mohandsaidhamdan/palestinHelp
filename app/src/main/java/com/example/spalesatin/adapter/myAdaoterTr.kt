package com.example.spalesatin.adapter

import android.app.Activity
import android.content.ContentValues
import android.util.Log
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.spalesatin.R
import com.example.spalesatin.model2.helps
import com.example.spalesatin.model2.people
import com.google.firebase.database.*
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.item_cap_tr.view.*
import java.util.*
import kotlin.collections.ArrayList
var checkBoxArrau = SparseBooleanArray()
var checkBoxArrau2 = SparseBooleanArray()
class myAdaoterTr : RecyclerView.Adapter<myAdaoterTr.MyViewHolder> , Filterable {


    var activity: Activity
    var data: ArrayList<helps>
    private var filterList: ArrayList<helps>

    private val checkedItems = mutableSetOf<Int>()

    constructor(activity: Activity, data: ArrayList<helps> ,
    ) : super() {
        this.activity = activity
        this.data = data
        this.filterList = data
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var name = itemView.text_name
        var numbur = itemView.text1
        var phone = itemView.text5
        var checkBox = itemView.btn_tr

//        init {
//
//
//
//            btn_tr.setOnClickListener {
//                if (!checkBoxArrau.get(adapterPosition, false)) {
//                    btn_tr.isChecked = true
//                    checkBoxArrau.put(adapterPosition ,true)
//                }else{
//                    btn_tr.isChecked = false
//                   checkBoxArrau.put(adapterPosition ,false)
//                }
//            }
//        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val root = LayoutInflater.from(activity).inflate(R.layout.item_cap_tr, parent, false)
        //val root = LayoutInflater.from(activity).inflate(R.layout.activity_item_view_book_favorite, parent, false)
        return MyViewHolder(root)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.name.text = data[position].name
        holder.numbur.text = data[position].numberID
        holder.phone.text = data[position].phone

holder.checkBox.setOnClickListener {
    try {
        val nameHelps = activity.intent.getStringExtra("name").toString()
        val type = activity.intent.getStringExtra("type").toString()

        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        var month = calendar.get(Calendar.MONTH) + 1
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        if (month  < 10){
            val mo = "0" + "$month"
            val date = "$year-$mo-$day"
            var shard =
                activity.getSharedPreferences("user", AppCompatActivity.MODE_PRIVATE)
            val name_user = shard.getString("name", "name")
            val id_user = shard.getString("id", "id")
            databeasRealtime().addPerssone(
                nameHelps,
                type,
                data[position].numberID,
                data[position].name,
                id_user.toString(),
                name_user.toString(),
                data[position].Title,
                date,
                "0"
            )
        }else{

            val date = "$year-$month-$day"
            var shard =
                activity.getSharedPreferences("user", AppCompatActivity.MODE_PRIVATE)
            val name_user = shard.getString("name", "name")
            val id_user = shard.getString("id", "id")
            databeasRealtime().addPerssone(
                nameHelps,
                type,
                data[position].numberID,
                data[position].name,
                id_user.toString(),
                name_user.toString(),
                data[position].Title,
                date,
                "0")
        }

        Toast.makeText(activity, "تم الترشيح بنجاح", Toast.LENGTH_SHORT).show()


    }catch (_: Exception){}
}

//                    databeasRealtime().getidDelete(id_user.toString(), data[position].numberID, date, type)



        }






    override fun getFilter(): Filter {
        return object : Filter(){
            override fun performFiltering(p0: CharSequence?): FilterResults {
                val filterResults = FilterResults()
                if (p0 == null || p0.length < 0 ){
                    filterResults.count =filterList.size
                    filterResults.values = filterList
                }else{
                    var searchchar = p0.toString().toLowerCase()
                    val itemModel = ArrayList<helps>()
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
                data = filterResults!!.values as ArrayList<helps> /* = java.util.ArrayList<com.example.vicky.contactreader.ContactDTO> */
                notifyDataSetChanged()
            }

        }
    }


}

class databeasRealtime {
    val db = FirebaseFirestore.getInstance()

    fun addPerssone(
        nameHelp: String,
                            type: String,
                            idNumber: String,
                            namePeople: String,
                            id_user: String,
                            name_user: String,
                            title: String,
                            date: String,
                            hala: String){
// Create a new user with a first and last name
        val persone = hashMapOf(
            "nameHelp" to nameHelp,
            "type" to type,
            "idNumber" to idNumber,
            "namePeople" to namePeople,
            "id_user" to id_user,
            "name_user" to name_user,
            "title" to title,
            "date" to date,
            "hala" to hala,
        )

// Add a new document with a generated ID
        db.collection("notification")
            .add(persone)
            .addOnSuccessListener { documentReference  ->
                Log.d(ContentValues.TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w(ContentValues.TAG, "Error adding document", e)
            }
    }


    fun getidDelete(id_user : String , idNumber : String, date : String, type : String){
        db.collection("notification")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {

                    val id_users = document.get("id_user")
                    val idNumbers =  document.get("idNumber")
                    val date2 =  document.get("date")
                    val type2 =  document.get("type")
                    if (id_user== id_users && idNumber == idNumbers&& date2 == date&& type2 == type){
                        val id = document.id
                        delete(id)
                    }

                }
            }
            .addOnFailureListener { exception ->
                Log.w(ContentValues.TAG, "Error getting documents.", exception)
            }
    }

    fun delete(id : String){
        db.collection("notification").document(id).delete()

    }


}