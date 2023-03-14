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
import com.example.spalesatin.R
import com.example.spalesatin.list_mrsh.list_admin_m
import com.example.spalesatin.model2.helpFinishPeople
import com.example.spalesatin.model2.helpsTa
import com.example.spalesatin.model2.people
import com.example.spalesatin.rfresf.Refresh
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_ask_user_admin.view.*
import kotlinx.android.synthetic.main.item_cap10.view.*
import java.util.*
import kotlin.collections.ArrayList
import kotlin.random.Random


class myAdaoterhelppelpeTa : RecyclerView.Adapter<myAdaoterhelppelpeTa.MyViewHolder>   {

    lateinit  var database: DatabaseReference
    var activity: Activity
    var data: ArrayList<helpsTa>
    //private var filterList: ArrayList<helpsTa>
   // private var filter: filteritem? = null

    constructor(activity: Activity, data: ArrayList<helpsTa>) : super() {
        this.activity = activity
        this.data = data
      //  this.filterList = data
    }


    fun refreshData(newData: ArrayList<helpsTa>) {
        data = newData  // Update the adapter's data set
        notifyDataSetChanged()  // Notify the adapter that the data set has changed
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var name = itemView.text_name
        var des = itemView.text_name2
        var numbur = itemView.text1
        var marid = itemView.text2
        var situation = itemView.text_name3
       var worker = itemView.text4
        var btnadd = itemView.btnadd
        var buttonNo = itemView.btNo
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val root = LayoutInflater.from(activity).inflate(R.layout.item_cap10, parent, false)
        //val root = LayoutInflater.from(activity).inflate(R.layout.activity_item_view_book_favorite, parent, false)
        return MyViewHolder(root)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, @SuppressLint("RecyclerView") position: Int) {
        holder.name.text = data[position].namePeolpe
        holder.des.text = data[position].idNumber
        holder.situation.text = data[position].type
        holder.marid.text = data[position].nameHelp
        holder.numbur.text = data[position].date
        holder.worker.text =data[position].name_user



        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val date = day+month+year

        holder.btnadd.setOnClickListener {
            databeasRealtime2().Regster(data[position].nameHelp , data[position].type , data[position].idNumber,
                data[position].namePeolpe , data[position].id_user , data[position].name_user , data[position].title , date.toString() , data[position].date)
              databeasRealtime2().delete(data[position].nameHelp , data[position].type , data[position].idNumber,data[position].datetr)
           val i = Intent(activity, Refresh::class.java)
            Toast.makeText(activity, "تم الموافقة بنجاح", Toast.LENGTH_SHORT).show()
             activity.startActivity(i)
             activity.finish()

        }

        holder.buttonNo.setOnClickListener {

            databeasRealtime2().delete(data[position].nameHelp , data[position].type , data[position].idNumber,data[position].datetr)
            val i = Intent(activity, Refresh::class.java)
            val builder = AlertDialog.Builder(activity)
            builder.setTitle("رفض الإسم")
            builder.setMessage("هل انت متأكد من الرفض ؟")
            builder.setPositiveButton("رفض") { dialog, _ ->
                Toast.makeText(activity, "تم الرفض بنجاح", Toast.LENGTH_SHORT).show()
                activity.startActivity(i)
                activity.finish()
            }
            builder.setNegativeButton("لا"){ d , _ ->
                d.dismiss()

            }
            val alertDialog = builder.create()
            alertDialog.show()


        }

    }
}

class databeasRealtime2{
    var database: DatabaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://palestain-51792-default-rtdb.firebaseio.com/")
    fun Regster( nameHelp :String,type : String , idNumber : String, namePeople : String, id_user : String , name_user : String , title : String , date :String, date2 :String){







        try {


            val database = FirebaseDatabase.getInstance()
            val myRef = database.getReference("helpFinshPeople")

           // create a new user with auto-generated ID
            val newUser = myRef.push()

            newUser.child("nameHelp").setValue(nameHelp)
            newUser.child("type").setValue(type)
            newUser.child("idNumber").setValue(idNumber)
            newUser.child("namePeolpe").setValue(namePeople)
            newUser.child("id_user").setValue(id_user)
            newUser.child("name_user").setValue(name_user)
            newUser.child("title").setValue(title)
            newUser.child("date").setValue(date2)
        }catch (e:Exception){

        }
    }

    fun delete (nameHelp : String , type: String, idNumber: String, date: String){

        database.child("notification/$nameHelp/$type/$idNumber/$date").removeValue()
    }

}