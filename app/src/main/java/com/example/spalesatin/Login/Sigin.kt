package com.example.spalesatin.Login


import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.spalesatin.R
import com.google.firebase.database.*
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_sigin.*

class Sigin : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sigin)

          // button in Sigin acount
        btn_sigin_n.setOnClickListener {
            // get name user
          var  name = text_name.text.toString()
            // get phone user
          var  phone = text_phone.text.toString()
            // get password user
          var  password = text_password.text.toString()
            // get confirm password user
          var  confirm_password = text_confirm_password.text.toString()

            // if data is empty
            if (name.isEmpty() || phone.isEmpty() || password.isEmpty() || confirm_password.isEmpty()) {
                if (name.isEmpty()) {
                    Toast.makeText(this, "أدخل الإسم", Toast.LENGTH_SHORT).show()
                    } else
                        if (phone.isEmpty()) {
                            Toast.makeText(this, "أدخل رقم الجوال", Toast.LENGTH_SHORT).show()
                        } else
                            if (password.isEmpty()) {
                                Toast.makeText(this, "أدخل كلمة السر", Toast.LENGTH_SHORT).show()
                            } else
                                if (confirm_password.isEmpty()) {
                                    Toast.makeText(this, "أدخل تأكيد كلمة السر", Toast.LENGTH_SHORT)
                                        .show()
                                }
                // if data is not empty
            } else {
                // check password equals confirm password
                if (password.equals(confirm_password)) {
                    // get title user
                    val title = spinnertitle.selectedItem.toString()

              // realtime Firebase in Sigin
                    databeasRealtime().Regster(this ,title , phone , name , password)
                } else {
                    Toast.makeText(this, "كلمة السر غير متطابقة", Toast.LENGTH_SHORT).show()
                }

            }
        }


    }
class databeasRealtime{
    var database: DatabaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://palestain-51792-default-rtdb.firebaseio.com/")

    fun Regster(context2 : Context ,title :String , phone : String , name : String , password : String){
        database.child("user").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.hasChild(phone)){
                    Toast.makeText(context2, "رقم الجوال مستخدم من قبل", Toast.LENGTH_SHORT).show()
                }else{
                    // add data user in realtime Firebase
                    database.child("user").child(phone).child("name").setValue(name)
                    database.child("user").child(phone).child("title").setValue(title)
                    database.child("user").child(phone).child("password").setValue(password)
                    database.child("user").child(phone).child("che").setValue("false")
                    database.child("user").child(phone).child("phone").setValue(phone)
                    // user A ==> admin
                    // user b ==> user
                    database.child("user").child(phone).child("user").setValue("b")

                    Toast.makeText(context2, "تم التسجيل بنجاح", Toast.LENGTH_SHORT).show()
                    context2.startActivity(Intent(context2 , Login::class.java))
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("firebase", error.message)
                Toast.makeText(context2, "حاول لاحقا", Toast.LENGTH_SHORT).show()
            }


        })
    }

}

}