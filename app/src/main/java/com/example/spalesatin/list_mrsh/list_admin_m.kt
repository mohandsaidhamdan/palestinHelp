package com.example.spalesatin.list_mrsh

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.*
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.adapter.myAdaoterhelppelpeTa
import com.example.spalesatin.MainActivity
import com.example.spalesatin.R
import com.example.spalesatin.model2.helpsTa
import com.example.spalesatin.model2.people
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.database.*
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_list_admin_m.*
import kotlinx.android.synthetic.main.item_cap10.view.*
import java.util.*
import kotlin.collections.ArrayList


class list_admin_m : AppCompatActivity() {

    var arrayList:ArrayList<helpsTa> = ArrayList()
    var filterList:ArrayList<helpsTa> = ArrayList()
    lateinit var db: FirebaseFirestore
    var Adapter: FirestoreRecyclerAdapter<helpsTa, personeItem>? = null

    @SuppressLint("MissingInflatedId")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_admin_m)
        val progressDialog = ProgressDialog(this)




        db = Firebase.firestore

        val shard = getSharedPreferences("user", AppCompatActivity.MODE_PRIVATE)
        val id = shard.getString("id", "false").toString()
        val user = shard.getString("user", "false").toString()
        val title = shard.getString("title", "false").toString()




        if (user.equals("A")) {
            val query = db.collection("notification")
            val option =
                FirestoreRecyclerOptions.Builder<helpsTa>().setQuery(query, helpsTa::class.java).build()

            Adapter = object : FirestoreRecyclerAdapter<helpsTa, personeItem>(option)  {
                override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): personeItem {
                    var view = LayoutInflater.from(this@list_admin_m)
                        .inflate(R.layout.item_cap10, parent, false)
                    return personeItem(view)
                }

                override fun onBindViewHolder(holder: personeItem, position: Int, model: helpsTa) {
                    holder.name.text = model.namePeople
                    holder.des.text = model.idNumber
                    holder.situation.text = model.type
                    holder.marid.text = model.nameHelp
                    holder.numbur.text = model.date
                    holder.title.text = model.title
                    holder.worker.text =model.name_user
                    holder.btnadd.setOnClickListener {
                        val dat = Calendar.getInstance()
                        val year = dat.get(Calendar.YEAR)
                        val MONTH = dat.get(Calendar.MONTH) + 1
                        val day = dat.get(Calendar.DAY_OF_MONTH)
                        val date2 = "$year/$MONTH/$day"

                        databeasRealtime().addPerssoneRealtime(model.nameHelp ,model.type,model.idNumber,model.namePeolpe , model.id_user
                            , model.name_user,model.title,model.date,date2 ,"تم الإستلام")

                        databeasRealtime().getidDelete(model.id_user , model.idNumber ,model.date , model.type)



                    }
                    holder.buttonNo.setOnClickListener {
                        val dilalog = AlertDialog.Builder(this@list_admin_m)
                        dilalog.setTitle("رفض الترشيح")
                        dilalog.setMessage("هل انت متأكد من  رفض الترشيح ؟؟")
                        dilalog.setPositiveButton("نعم") { dialog, which ->
                            // Do something when the positive button is clicked
                            databeasRealtime().getidDelete(model.id_user , model.idNumber ,model.date , model.type)
                            dialog.dismiss()

                        }
                        dilalog.setNegativeButton("لا") { dialog, which ->
                            // Do something when the negative button is clicked
                            dialog.dismiss()

                        }
                        dilalog.show()

                    }
                }




            }

            listView2.layoutManager = LinearLayoutManager(this)
            listView2.adapter = Adapter
        }else{
            val titles = "مسجد $title"
            val query = db.collection("notification").whereEqualTo("title", titles)
            val option =
                FirestoreRecyclerOptions.Builder<helpsTa>().setQuery(query, helpsTa::class.java).build()

            Adapter = object : FirestoreRecyclerAdapter<helpsTa, personeItem>(option)  {
                override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): personeItem {
                    var view = LayoutInflater.from(this@list_admin_m)
                        .inflate(R.layout.item_cap10, parent, false)
                    return personeItem(view)
                }

                override fun onBindViewHolder(holder: personeItem, position: Int, model: helpsTa) {
                    holder.name.text = model.namePeople
                    val name = model.namePeople

                    holder.des.text = model.idNumber
                    holder.situation.text = model.type
                    holder.marid.text = model.nameHelp
                    holder.numbur.text = model.date
                    holder.title.text = model.title
                    holder.worker.text =model.name_user
                    holder.btnadd.setOnClickListener {
                        val dat = Calendar.getInstance()
                        val year = dat.get(Calendar.YEAR)
                        val MONTH = dat.get(Calendar.MONTH) + 1
                        val day = dat.get(Calendar.DAY_OF_MONTH)
                        val date2 = "$year/$MONTH/$day"

                        databeasRealtime().addPerssoneRealtime(model.nameHelp ,model.type , model.idNumber ,name, model.id_user
                            , model.name_user,model.title,model.date,date2 ,"تم الإستلام")

                        databeasRealtime().getidDelete(model.id_user , model.idNumber ,model.date , model.type)



                    }
                    holder.buttonNo.setOnClickListener {
                        val dilalog = AlertDialog.Builder(this@list_admin_m)
                        dilalog.setTitle("رفض الترشيح")
                        dilalog.setMessage("هل انت متأكد من  رفض الترشيح ؟؟")
                        dilalog.setPositiveButton("نعم") { dialog, which ->
                            // Do something when the positive button is clicked
                            databeasRealtime().getidDelete(model.id_user , model.idNumber ,model.date , model.type)
                            dialog.dismiss()

                        }
                        dilalog.setNegativeButton("لا") { dialog, which ->
                            // Do something when the negative button is clicked
                            dialog.dismiss()

                        }
                        dilalog.show()

                    }
                }




            }

            listView2.layoutManager = LinearLayoutManager(this)
            listView2.adapter = Adapter
        }




        progressDialog.show()
        progressDialog.setContentView(R.layout.progress_dialog)
        progressDialog.window!!.setBackgroundDrawableResource(com.firebase.ui.auth.R.color.fui_transparent)

        Handler().postDelayed({
            progressDialog.dismiss()
        }, 2000)




    }

    class personeItem(view: View) : RecyclerView.ViewHolder(view) {
        var name = itemView.text_name
        var des = itemView.text_name2
        var numbur = itemView.text1
        var marid = itemView.text2
        var situation = itemView.text_name3
        var worker = itemView.text4
        var btnadd = itemView.btnadd
        var buttonNo = itemView.btNo
        var title = itemView.text_titile
    }

    override fun onStart() {
        super.onStart()
        Adapter!!.startListening()

    }

    override fun onStop() {
        super.onStop()
        Adapter!!.stopListening()
    }



    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.home_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.home ->{
                val i = Intent(this, MainActivity::class.java)
                startActivity(i)
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }



    class databeasRealtime {
        val db = FirebaseFirestore.getInstance()
// helpsTarshPeople


        fun addPerssoneRealtime(
            nameHelp: String,
            type: String,
            idNumber: String,
            namePeople: String,
            id_user: String,
            name_user: String,
            title: String,
            datetr: String,
            dateOK: String,
            hala: String) {
// Create a new user with a first and last name
            val persone = hashMapOf(
                "nameHelp" to nameHelp,
                "type" to type,
                "idNumber" to idNumber,
                "namePeople" to namePeople,
                "id_user" to id_user,
                "name_user" to name_user,
                "title" to title,
                "date" to datetr,
                "dateOK" to dateOK,
                "hala" to hala,
            )
            val databases = FirebaseDatabase.getInstance()
            val myRef = databases.getReference("helpsTarshPeoples")

            // create a new user with auto-generated ID
            val newUser = myRef.push()
            // helpsTa
            newUser.setValue(persone)

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
}

