package com.example.spalesatin.detaliess

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.Menu
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.spalesatin.adapter.myAdaoterTr
import com.example.spalesatin.MainActivity
import com.example.spalesatin.R
import com.example.spalesatin.adapter.checkBoxArrau
import com.example.spalesatin.model2.helps
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_details.*
import kotlinx.android.synthetic.main.activity_list_all_user_add_help.*
import kotlinx.android.synthetic.main.activity_tr_admin.*
import kotlinx.android.synthetic.main.login.*

class ListAllUserAddHelp : AppCompatActivity() {
    lateinit var recyclerView: RecyclerView
    lateinit var myAdaoter: myAdaoterTr
    var arrayList: ArrayList<helps> = ArrayList()


    var database: DatabaseReference = FirebaseDatabase.getInstance()
        .getReferenceFromUrl("https://palestain-51792-default-rtdb.firebaseio.com/")

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_all_user_add_help)

        val progressDialog = ProgressDialog(this)

        recyclerView = findViewById(R.id.listView2)
        recyclerView.setHasFixedSize(true)



        recyclerView.layoutManager = LinearLayoutManager(this)
        myAdaoter = myAdaoterTr(this, arrayList)

        recyclerView.adapter = myAdaoter
        btn_save.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
//            checkBoxArrau.put(0 ,false)
            Toast.makeText(this, "تم حفظ التعديلات بنجاح", Toast.LENGTH_SHORT).show()
            finish()
        }




        val nameHelps = intent.getStringExtra("name").toString()
        val type = intent.getStringExtra("type").toString()
        val number = intent.getStringExtra("number").toString()
        val numberFamly = intent.getStringExtra("numberFamly").toString()
        val date = intent.getStringExtra("date").toString()
        val halaEconomy = intent.getStringExtra("halaEconomy").toString()
        val halaSocial = intent.getStringExtra("halaSocial").toString()
        val Worke = intent.getStringExtra("Worke").toString()
        val helps = intent.getStringExtra("helps").toString()
        val numberF = intent.getStringExtra("numberF").toString()
        val numberM = intent.getStringExtra("numberM").toString()
        val numberS = intent.getStringExtra("numberS").toString()
        val hala = intent.getStringExtra("hala").toString()
        val firstNumberPeople = intent.getStringExtra("firstNumberPeople").toString()
        val scendNumberPeople = intent.getStringExtra("scendNumberPeople").toString()
        LoginRealTimeFireBase(
            nameHelps, helps, halaEconomy, Worke, halaSocial, number, type,
            scendNumberPeople, firstNumberPeople, date, hala, numberF, numberM, numberS
        )

        progressDialog.show()
        progressDialog.setContentView(R.layout.progress_dialog)
        progressDialog.window!!.setBackgroundDrawableResource(com.firebase.ui.auth.R.color.fui_transparent)

        Handler().postDelayed({
            progressDialog.dismiss()
//            startActivity(Intent(this , MainActivity::class.java))
//            finish()
        }, 3000)

    }

    fun LoginRealTimeFireBase(
        nameHelps: String,
        help: String,
        halaEconomy: String,
        Worke: String,
        halaSocial: String,
        number: String,
        type: String,
        scendNumberPeople: String,
        firstNumberPeople: String,
        date: String,
        hala: String,
        numberF: String,
        numberM: String,
        numberS: String
    ) {
        database.child("helps").addListenerForSingleValueEvent(object : ValueEventListener {
            @SuppressLint("SuspiciousIndentation")
            override fun onDataChange(snapshot: DataSnapshot) {
                //   if (snapshot.hasChild(nameHelps)) {
                for (snapshot in snapshot.children) {
                    val data = snapshot.getValue(helps::class.java)
                    val helpData = data!!.helps
                    val halaEconomyData = data.halaEconomy
                    val halaSocialData = data.halaSocial
                    val WorkeData = data.Worke
                    val numberData = data.number
                    val typeData = data.type
                    val halaData = data.hala
                    val dateData = data.date
                    val halaMarData = data.halaMar
                    val numberFData = data.numberF
                    val numberMData = data.numberM
                    val numberSData = data.numberS
                    val halaEconomy2 = data.halaEconomy2
                    val helps2 = data.helps2


                    val firstNumberPeopleData = data.firstNumberPeople
                    val scendNumberPeopleData = data.scendNumberPeople

                    if (helpData.equals(help) && halaEconomyData.equals(halaEconomy) &&
                        WorkeData.equals(Worke) && halaSocialData.equals(halaSocial) &&
                        halaSocialData.equals(halaSocial) &&
                        numberData.equals(number) && typeData.equals(type) &&
                        firstNumberPeopleData.equals(firstNumberPeople) && scendNumberPeopleData.equals(
                            scendNumberPeople
                        ) && numberFData.equals(numberF) &&
                        halaData.equals(hala) && dateData.equals(date) &&
                        numberMData.equals(numberM) && numberSData.equals(numberS)
                    ) {

                        database.child("people").addListenerForSingleValueEvent(object :
                            ValueEventListener {
                            override fun onDataChange(snapshot: DataSnapshot) {
                                for (snapshot in snapshot.children) {
                                    val data = snapshot.getValue(helps::class.java)

                                    val phalaSocialUSer = data!!.halaSocial
                                    val pWorkeUSer = data!!.Worke
                                    val phalaEconomyUSer = data!!.halaEconomy
                                    val phalaMarUSer = data!!.halaMar
                                    val phelpsUSer = data!!.helps
                                    val pnumberFamlyUSer = data!!.numberFamly


                                    var shard =
                                        getSharedPreferences("user", MODE_PRIVATE)
                                    val titlePeople =
                                        shard.getString("title", "").toString()

                                    val title = data.Title

                                    if (title == "مسجد $titlePeople") {
                                      if (firstNumberPeople.isNotEmpty() && scendNumberPeople.isNotEmpty()){
                                          if (pnumberFamlyUSer.toInt() >= firstNumberPeopleData.toInt() && pnumberFamlyUSer.toInt() <= scendNumberPeopleData.toInt()) {
                                             if(phalaEconomyUSer.equals(halaEconomy) ||phalaEconomyUSer.equals(halaEconomy2)|| halaEconomy.isNotEmpty()){
                                             if (halaSocial.isNotEmpty() || phalaSocialUSer == halaSocial ) {
                                                 arrayList.add(data)
                                                 myAdaoter.notifyDataSetChanged()
                                             }
                                             }
                                          }
                                      }else{
                                          if (pnumberFamlyUSer.toInt() >= firstNumberPeopleData.toInt() && pnumberFamlyUSer.toInt() <= scendNumberPeopleData.toInt()) {
                                              if(phalaEconomyUSer.equals(halaEconomy) ||phalaEconomyUSer.equals(halaEconomy2)|| halaEconomy.isNotEmpty()){
                                                  if (halaSocial.isNotEmpty() || phalaSocialUSer == halaSocial ) {
                                                      arrayList.add(data)
                                                      myAdaoter.notifyDataSetChanged()
                                                  }
                                              }
                                          }
                                      }



                                    }
                                }

                            }

                            override fun onCancelled(error: DatabaseError) {
                                TODO("Not yet implemented")
                            }

                        })


                    }

                }

            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("firebase", error.message)
                Toast.makeText(this@ListAllUserAddHelp, "حاول لاحقا", Toast.LENGTH_SHORT).show()
            }


        })





    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.search_item , menu)
        val menuItem=menu!!.findItem(R.id.serchview)
        val searchview = menuItem.actionView as androidx.appcompat.widget.SearchView
        searchview.maxWidth = Int.MAX_VALUE

        searchview.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                myAdaoter!!.filter.filter(p0)

                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {

                myAdaoter!!.filter.filter(p0)
                return true
            }

        })
        return true
    }

}