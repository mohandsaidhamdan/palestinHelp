package com.example.spalesatin.detaliess


import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.adapter.myAdaoterAddHelps
import com.example.spalesatin.MainActivity
import com.example.spalesatin.R
import com.example.spalesatin.model2.people
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_tr_admin.*
import kotlinx.android.synthetic.main.ditlels.*
import kotlinx.android.synthetic.main.login.*
import java.util.Calendar
import kotlin.error


class details_2 : AppCompatActivity() {
    var database: DatabaseReference = FirebaseDatabase.getInstance()
        .getReferenceFromUrl("https://palestain-51792-default-rtdb.firebaseio.com/")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ditlels)

        val calendar = Calendar.getInstance()
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val month = calendar.get(Calendar.MONTH) + 1
        val year = calendar.get(Calendar.YEAR)
        val date = "$day / $month / $year"

        textdate.setText(date)


   // button add helps on people in realtime firebase
        btn_add_helps.setOnClickListener {
            val numberid = numberId.text.toString()
            val name = text_name_helps.text.toString()
            val type = text_type.text.toString()

            if (numberid.isEmpty() || name.isEmpty() || type.isEmpty()) {
                Toast.makeText(this@details_2, "كل الحقول مطلوبة", Toast.LENGTH_SHORT).show()
            } else {
                database.child("people")
                    .addListenerForSingleValueEvent(object : ValueEventListener {
                        @SuppressLint("SuspiciousIndentation")
                        override fun onDataChange(snapshot: DataSnapshot) {
                            if (snapshot.hasChild(numberid)) {
                                var getname = snapshot.child(numberid).child("name")
                                    .getValue(String::class.java)
                                val shard =
                                    getSharedPreferences("user", AppCompatActivity.MODE_PRIVATE)
                                val id = shard.getString("id", "false").toString()
                                val user = shard.getString("user", "false").toString()
                                val title = shard.getString("title", "false").toString()
                                Toast.makeText(this@details_2, "تم الإضافة", Toast.LENGTH_SHORT)
                                    .show()
                                database.child("helpsTarshPeople").child(numberid).child("dateTa")
                                    .setValue(date)
                                database.child("helpsTarshPeople").child(numberid).child("hala")
                                    .setValue("2")
                                database.child("helpsTarshPeople").child(numberid)
                                    .child("nameHelps").setValue(name)
                                database.child("helpsTarshPeople").child(numberid).child("numberID")
                                    .setValue(numberid)
                                database.child("helpsTarshPeople").child(numberid)
                                    .child("titleUser").setValue(title)
                                database.child("helpsTarshPeople").child(numberid)
                                    .child("typeHelps").setValue(type)
                                database.child("helpsTarshPeople").child(numberid).child("user")
                                    .setValue(id)
                                database.child("helpsTarshPeople").child(numberid).child("name")
                                    .setValue(getname)
                                startActivity(Intent(this@details_2 , MainActivity::class.java))
                                finish()
                            } else {
                                Toast.makeText(this@details_2, "رقم الهوية خطأ", Toast.LENGTH_SHORT)
                                    .show()
                            }
                        }

                        override fun onCancelled(error: DatabaseError) {
                            Log.e("firebase", error.message)
                            Toast.makeText(this@details_2, "حاول لاحقا", Toast.LENGTH_SHORT).show()
                        }


                    })
            }
        }


    }


}
