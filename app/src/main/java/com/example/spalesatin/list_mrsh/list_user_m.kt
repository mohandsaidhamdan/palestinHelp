package com.example.spalesatin.list_mrsh

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.spalesatin.MainActivity
import com.example.spalesatin.R
import com.example.spalesatin.model2.people
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_list_user_m.*
import kotlinx.android.synthetic.main.item_item_item.view.*


class list_user_m : AppCompatActivity() {
    lateinit var db : FirebaseFirestore
    var Adapter: FirestoreRecyclerAdapter<people, userall>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_user_m)
        db = Firebase.firestore
        geta()
    }

    fun geta(){
        var shard =  getSharedPreferences("acount", MODE_PRIVATE)
        val id_user =  shard.getString("id" , "false")
        var title =  shard.getString("title" , "فلسطين")

        val t = " مسجد $title"
        db = Firebase.firestore
        val query =
            db.collection("rash_1")
                .whereEqualTo("title", t)
                .whereEqualTo("hala", "1")
                .orderBy("name_type_help")
        val option =
            FirestoreRecyclerOptions.Builder<people>().setQuery(query, people::class.java).build()

        Adapter = object : FirestoreRecyclerAdapter<people, userall>(option) {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): userall {
                var view = LayoutInflater.from(this@list_user_m)
                    .inflate(R.layout.item_item_item, parent, false)
                return userall(view)
            }

            override fun onBindViewHolder(holder: userall, position: Int, model: people) {
               //  holder.name.text = model.name_help
             //   holder.number_famly.text = model.name_type_help
                holder.type_name.text ="model.name_type_help"

                var year = "model.year"
                var day =" model.day"

                var month =" model.month.toInt() + 1"


                val date = "$day / $month / $year"
                holder.situation.text = date
                val id_user2 = "model.id_user.toString()"

                db.collection("peoples")
                    .get()
                    .addOnSuccessListener {des ->
                    for (doc in des){
                        var id_number = "model.id_number"
                        val id  = doc.getString("number_id")
                        if (id.equals(id_number)){
                            val name = doc.getString("name")
                            val phone = doc.getString("phone")
                            val title = doc.getString("title")
                            val number_famly = doc.getString("number_famly")
                            val marid = doc.getString("marid")
                            holder.name.text =name
                            holder.phone.text =phone
                            holder.spierp.text =title
                            holder.number.text =id
                            holder.number_famly.text =number_famly
                            holder.marid.text =marid
                        }
                    }
                    }


                db.collection("user")
                    .get()
                    .addOnSuccessListener {des ->
                        for (doc in des){
                            val id  = doc.id
                            //Toast.makeText(this@list_user_m, "$id", Toast.LENGTH_SHORT).show()
                            if (id.equals(id_user2)){
                                val name = doc.getString("name")
                                holder.name_tr.text =name
                            }
                        }
                    }

                //    holder.date.text ="10/1/2022"
            //    holder.tr.setText("إلغاء الترشيح")

                holder.card.setOnClickListener {
                    Toast.makeText(this@list_user_m, "ok", Toast.LENGTH_SHORT).show()
                    var id = "model.id_user"
                    db.collection("pe")
                        .whereEqualTo("number_id", "$id")
                        .get()
                        .addOnSuccessListener { des ->
                            for (doc in des) {
                                var id_update = doc.id
                                val name = intent.getStringExtra("name")
                                update(id_update, "1")
                                Toast.makeText(this@list_user_m, "تم إلغاء الترشيح", Toast.LENGTH_SHORT).show()
                            }
                        }

                }
                var number = 1

                holder.btn_onclicke.setOnClickListener {
                    if (number == 1){
                        Toast.makeText(this@list_user_m, "تم الترشيح", Toast.LENGTH_SHORT).show()
                        db.collection("peoples")
                            .get()
                            .addOnSuccessListener {des ->
                                for (doc in des){
                                    var id_number = "model.id_number"
                                    val id  = doc.getString("number_id")
                                    if (id.equals(id_number)){
                                        val name = doc.getString("name")
                                        val phone = doc.getString("phone")
                                        val title = doc.getString("title")
                                        val number_famly = doc.getString("number_famly")
                                        val marid = doc.getString("marid")
                                        holder.name.text =name
                                        holder.phone.text =phone
                                        holder.spierp.text =title
                                        holder.number.text =id
                                        holder.number_famly.text =number_famly
                                        holder.marid.text =marid
                                        val name_type_helpe ="model.name_type_help"
                                        val name_helpe ="model.name_help"
                                        val id_user2 = "model.id_user.toString()"
                                        addcuser(name.toString(),id.toString(),name_helpe,name_type_helpe,day,month.toString(),year, id_user2)
                                        db.collection("rash_1")
                                            .whereEqualTo("title", t)
                                            .whereEqualTo("id_number", id_number)
                                            .whereEqualTo("name_type_help", name_type_helpe)
                                            .whereEqualTo("day", day)
                                            .get()
                                            .addOnSuccessListener {des ->
                                                for (doc in des){
                                                    val id= doc.id
                                                    update2(id)
                                                }
                                            }

                                        holder.btn_onclicke.setImageDrawable(ContextCompat.getDrawable(this@list_user_m,R.drawable.ic_baseline_clear_24))
                                        number = 2
                                    }
                                }
                            }
                    }else if (number == 2){
                        val id_user2 = "model.id_user.toString()"
                        Toast.makeText(this@list_user_m, "تم إلغاء الترشيح", Toast.LENGTH_SHORT).show()
                        db.collection("peoples")
                            .get()
                            .addOnSuccessListener {des ->
                                for (doc in des){
                                    var id_number = "model.id_number"
                                    val id  = doc.getString("number_id")
                                    if (id.equals(id_number)){
                                        val name = doc.getString("name")
                                        val phone = doc.getString("phone")
                                        val title = doc.getString("title")
                                        val number_famly = doc.getString("number_famly")
                                        val marid = doc.getString("marid")
                                        holder.name.text =name
                                        holder.phone.text =phone
                                        holder.spierp.text =title
                                        holder.number.text =id
                                        holder.number_famly.text =number_famly
                                        holder.marid.text =marid
                                        val name_type_helpe ="=model.name_type_help"
                                        val name_helpe ="model.name_help"
                                        val id_user2 = "model.id_user.toString()"
                                        val m = month.toInt()+1
                                        db.collection("rash_2")
                                            .whereEqualTo("id_user",id_user2)
                                            .whereEqualTo("number_id",id_number)
                                            .whereEqualTo("day",day)
                                            .whereEqualTo("month",month.toString())
                                            .whereEqualTo("year",year)
                                            .whereEqualTo("name_help",name_type_helpe)
                                            .get()
                                            .addOnSuccessListener { des ->
                                                for (doc in des) {
                                                       val id = doc.id
                                                    delete(id)
                                                }
                                            }

                                        db.collection("rash_1")
                                            .whereEqualTo("title", t)
                                            .whereEqualTo("id_number", id_number)
                                            .whereEqualTo("name_type_help", name_type_helpe)
                                            .whereEqualTo("day", day)
                                            .get()
                                            .addOnSuccessListener {des ->
                                                for (doc in des){
                                                    val id= doc.id
                                                    update3(id)
                                                }
                                            }

                                        holder.btn_onclicke.setImageDrawable(ContextCompat.getDrawable(this@list_user_m,R.drawable.ic_baseline_check_24))
                                        number = 1
                                    }
                                }
                            }
                    }


                }

            }
        }

        rrecc.layoutManager = LinearLayoutManager(this)
        rrecc.adapter = Adapter
    }



    class userall(view: View) : RecyclerView.ViewHolder(view) {

        var name = itemView.text_name
        var name_tr = itemView.text_name2
        var type_name = itemView.text_name3
        var marid = itemView.text_name4
        var number_famly = itemView.text_name5
        var situation = itemView.text_name6
        var phone = itemView.text_name7
        var spierp = itemView.text_name8
        var number = itemView.text_name9
        var card = itemView.text_name3
        var btn_onclicke = itemView.imageView
    }

    override fun onStart() {
        super.onStart()
        Adapter!!.startListening()
    }

    override fun onStop() {
        super.onStop()
        Adapter!!.stopListening()
    }


    fun addtrash_1(name :String , id_user :String , id_number :String  , hala :String ,title :String){
        var data = hashMapOf( "name" to name , "id_user" to id_user , "title" to title ,"id_number" to id_number ,"hala" to hala)
        db.collection("rash_1")
            .add(data)
    }

    fun getall(id_number :String , id_user: String , title: String){
        db.collection("rash_1")
            .whereEqualTo("id_number",id_number)
            .whereEqualTo("id_user", id_user)
            .whereEqualTo("title", title)
            .whereEqualTo("hala","1")
            .get()
            .addOnSuccessListener { des ->
                for (doc in des){
                    val id = doc.id
                    val shard2 =  getSharedPreferences("tr_1", MODE_PRIVATE)
                    val edit =shard2.edit()
                    edit.putString("id" , id)
                        .apply()
                }
            }

    }

    fun update2(old_id:String){
        val users = HashMap<String, Any>()
        users["hala"] = "2"
        db.collection("rash_1").document(old_id)
            .update(users)


    }
    fun update3(old_id:String){
        val users = HashMap<String, Any>()
        users["hala"] = "1"
        db.collection("rash_1").document(old_id)
            .update(users)


    }

    fun delete(old_id:String){
        db.collection("rash_2").document(old_id)
            .delete()
    }



    private fun update(oldId: String,hala : String){
        val users = HashMap<String, Any>()
        /*  var m =1
          i+=m*/
        users["hala"] = hala
        // users["$i"] = name
        db.collection("pe").document(oldId)
            .update(users)
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


    fun addcuser(
        name: String,
        number_id: String,
        name_type_helpe: String,
        name_help: String,
        day: String,
        month: String,
        year : String,
        id_user2 : String
    ) {
        val user = hashMapOf(
            "name" to name,
            "number_id" to number_id,
            "name_type_helpe" to name_type_helpe,
            "name_help" to name_help,
            "day" to day,
            "month" to month,
            "year" to year,
            "id_user" to id_user2
        )
        db.collection("rash_2")
            .add(user)
            .addOnSuccessListener { doc ->
                Log.e("TAG", "  id ${doc.id} تمت العملية بنجاح")
            }


    }
}
