package com.example.spalesatin.detaliess.add

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import com.example.spalesatin.MainActivity
import com.example.spalesatin.R
import com.example.spalesatin.fragment.v
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_add_pepole.*

class add_pepole : AppCompatActivity() {
    var database: DatabaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://palestain-51792-default-rtdb.firebaseio.com/")

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_pepole)


        val halaMar  = findViewById<AutoCompleteTextView>(R.id.autoText)
        val  helps = findViewById<AutoCompleteTextView>(R.id.autoText2)
        val  Title = findViewById<AutoCompleteTextView>(R.id.Title)
        val  Worke = findViewById<AutoCompleteTextView>(R.id.Worke)
        val  halaEconomy = findViewById<AutoCompleteTextView>(R.id.halaEconomy)
        val  halaSocial = findViewById<AutoCompleteTextView>(R.id.halaSocial)
        val  home = findViewById<AutoCompleteTextView>(R.id.home)

        val ArrayhalaMar = arrayOf("سليم" , "مصاب" , "")
        val Arrayhelps = arrayOf("المنحة القطرية" , "شؤوون إجتماعية" , "غير مستفيد")
        val ArrayTitle = arrayOf("مسجد المجاهدين" , "مسجد فلسطين" , "مسجد السلام")
        val ArrayWorke = arrayOf("موضف" , "عمل خاص" , "لا يعمل")
        val ArrayhalaEconomy = arrayOf("أ" , "ب" , "ج")
        val ArrayhalaSocial = arrayOf("متزوج" , "أعزب" , "مطلق" )
        val Arrayhome = arrayOf("ملك" , "إيجار")
        val Arrayhuman = arrayOf("ذكر" , "إنثى")


        val adpter : ArrayAdapter<String> = ArrayAdapter(v.context , R.layout.drop_dwon , ArrayhalaMar)
        val adpter2 : ArrayAdapter<String> = ArrayAdapter(v.context , R.layout.drop_dwon , Arrayhelps)
        val adpter3 : ArrayAdapter<String> = ArrayAdapter(v.context , R.layout.drop_dwon , ArrayTitle)
        val adpter4 : ArrayAdapter<String> = ArrayAdapter(v.context , R.layout.drop_dwon , ArrayWorke)
        val adpter5 : ArrayAdapter<String> = ArrayAdapter(v.context , R.layout.drop_dwon , ArrayhalaEconomy)
        val adpter6 : ArrayAdapter<String> = ArrayAdapter(v.context , R.layout.drop_dwon , ArrayhalaSocial)
        val adpter7 : ArrayAdapter<String> = ArrayAdapter(v.context , R.layout.drop_dwon , Arrayhome)
        val adpter8 : ArrayAdapter<String> = ArrayAdapter(v.context , R.layout.drop_dwon , Arrayhuman)


        halaMar.setAdapter(adpter)
        helps.setAdapter(adpter2)
        Title.setAdapter(adpter3)
        Worke.setAdapter(adpter4)
        halaEconomy.setAdapter(adpter5)
        halaSocial.setAdapter(adpter6)
        home.setAdapter(adpter7)
        human.setAdapter(adpter8)




        //btn add people
        btn_add_peeplo.setOnClickListener {
            val halaMar = halaMar.text.toString()
            val helps = helps.text.toString()
            val Title = Title.text.toString()
            val Worke = Worke.text.toString()
            val halaEconomy = halaEconomy.text.toString()
            val halaSocial = halaSocial.text.toString()
            val halahuman = human.text.toString()
            val home = home.text.toString()
            val name = textname.text.toString()
            val numberID = number_id.text.toString()
            val phone = number_phone.text.toString()
            val numberFamly = number_famly.text.toString()

         //   if (name.isEmpty() || numberID.isEmpty() || phone.isEmpty() || numberFamly.isEmpty() ||
            //    halaMar.isEmpty() || helps.isEmpty() || Title.isEmpty() || Worke.isEmpty()
          //      || halaEconomy.isEmpty() || halaSocial.isEmpty() || home.isEmpty()
          //  ) {
         //       Toast.makeText(this@add_pepole, "كل الحقول مطلوبة", Toast.LENGTH_SHORT).show()
      //      } else {


                database.child("people")
                    .addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            if (snapshot.hasChild(phone)) {
                                Toast.makeText(
                                    this@add_pepole,
                                    "رقم الجوال مستخدم من قبل",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else {
                                // add data user in realtime Firebase
                                database.child("people").child(numberID).child("name")
                                    .setValue(name)
                                database.child("people").child(numberID).child("halaMar")
                                    .setValue(halaMar)
                                database.child("people").child(numberID).child("helps")
                                    .setValue(helps)
                                database.child("people").child(numberID).child("Title")
                                    .setValue(Title)
                                database.child("people").child(numberID).child("Worke")
                                    .setValue(Worke)
                                database.child("people").child(numberID).child("halaEconomy")
                                    .setValue(halaEconomy)
                                database.child("people").child(numberID).child("halaSocial")
                                    .setValue(halaSocial)
                                database.child("people").child(numberID).child("home")
                                    .setValue(home)
                                database.child("people").child(numberID).child("numberID")
                                    .setValue(numberID)
                                database.child("people").child(numberID).child("phone")
                                    .setValue(phone)
                                database.child("people").child(numberID).child("numberFamly")
                                    .setValue(numberFamly)
                                database.child("people").child(numberID).child("halahuman")
                                    .setValue(halahuman)

                                Toast.makeText(
                                    this@add_pepole,
                                    "تم  الإضافة بنجاح",
                                    Toast.LENGTH_SHORT
                                ).show()
                                //startActivity(Intent(this@add_pepole, MainActivity::class.java))
                              // finish()
                            }
                        }

                        override fun onCancelled(error: DatabaseError) {
                            Log.e("firebase", error.message)
                            Toast.makeText(this@add_pepole, "حاول لاحقا", Toast.LENGTH_SHORT).show()
                        }


                    })

     //       }
        }


    }




















    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.home_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.home -> {
                val i = Intent(this, MainActivity::class.java)
                startActivity(i)
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }


   /* fun writeExcel() {

        val queue = Volley.newRequestQueue(this)
        val url =
            "https://script.google.com/macros/s/AKfycbxnpJvqF21y-p7cZSJz2rybbBPQWgplxa8bpgv7L5Ae-MBF3W6f/exec"


        val stringRequest = object : StringRequest(
            Request.Method.POST, url,
            Response.Listener {
                Toast.makeText(this@add_pepole, it.toString(), Toast.LENGTH_SHORT).show()

            },
            Response.ErrorListener {
                Toast.makeText(this@add_pepole, it.toString(), Toast.LENGTH_SHORT).show()
            }) {

            val name = textname.text.toString()
            val number_id_2 = number_id.text.toString()
            val number_famly_2 = number_famly.text.toString()
            val phone_2 = number_phone.text.toString()

            val spierxp = spierp.selectedItem.toString()

            @Throws(AuthFailureError::class)
            override fun getParams(): MutableMap<String, String> {
                val params = HashMap<String, String>()
                params["name"] = name
                params["title"] = number_id_2
                params["greeneed"] = number_id_2
                params["idumber"] = number_famly_2
                params["typesex"] = number_famly_2
                params["maritalstatus"] = number_id_2
                params["numberFamley"] = phone_2
                params["phone"] = number_id_2
                params["qatar"] = spierxp
                return params
            }

        }

        queue.add(stringRequest)
    }*/
}