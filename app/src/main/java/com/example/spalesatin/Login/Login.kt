package com.example.spalesatin.Login

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Toast
import com.example.spalesatin.MainActivity
import com.example.spalesatin.R
import com.google.firebase.database.*
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.login.*

class Login : AppCompatActivity(){
    var database: DatabaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://palestain-51792-default-rtdb.firebaseio.com/")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        // button in Sigin acount user
        btn_sigin.setOnClickListener{
            val i = Intent(this , Sigin::class.java)
            startActivity(i)
        }



          // button in login acount user
        btn_login.setOnClickListener {
            val phone = text_name.text.toString()
            val password = textinputLayoutedittext.text.toString()


            if (phone.isEmpty() || password.isEmpty()){
                if (phone.isEmpty()){
                    Toast.makeText(this, "أدخل  اسم المستخدم", Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(this, "ادخل كلمة السر", Toast.LENGTH_SHORT).show()
                }

            }else{
                // check usernamae and password

                LoginRealTimeFireBase(phone , password)

            }

        }

        
    }


    fun LoginRealTimeFireBase(phone:String , password :String){
        database.child("user").addListenerForSingleValueEvent(object : ValueEventListener {
            @SuppressLint("SuspiciousIndentation")
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.hasChild(phone)){
                    val getpassword = snapshot.child(phone).child("password").getValue(String::class.java)
                    val getche = snapshot.child(phone).child("che").getValue(String::class.java)
                    val getuser = snapshot.child(phone).child("user").getValue(String::class.java)
                    val getTilte = snapshot.child(phone).child("title").getValue(String::class.java)
                    val getname = snapshot.child(phone).child("name").getValue(String::class.java)
                    if (getpassword!!.equals(password)){
                        if (getche.equals("true")){
                            startActivity(Intent(this@Login , MainActivity::class.java))
                            finish()
                            Toast.makeText(this@Login, "تم التسجيل بنجاح", Toast.LENGTH_SHORT).show()

                            // Checking is active
                            var shard =  getSharedPreferences("user", MODE_PRIVATE)
                            val edit = shard.edit()
                                edit.putString("id" , phone)
                                edit.putString("isactive" , getche)
                                edit.putString("che" , "true")
                                edit.putString("user" , getuser)
                                edit.putString("title" , getTilte)
                                edit.putString("name" , getname)
                                edit.apply()

                        }else if (getche.equals("false")){
                            Toast.makeText(this@Login, "الحساب قيد المراجعة", Toast.LENGTH_SHORT).show()
                            error.setText("الحساب قيد المراجعة")
                            error.visibility = View.VISIBLE
                            error.setBackgroundColor(Color.BLUE)
                        }else if(getche.equals("r")){
                            error.visibility = View.VISIBLE
                            error.setBackgroundColor(Color.RED)
                            error.setText("تم رفض طلب التسجيل بالموقع")
                        }



                    }else{
                        Toast.makeText(this@Login, "خطأ في كلمة السر أو الإيميل", Toast.LENGTH_SHORT).show()
                        error.visibility = View.VISIBLE
                        error.setBackgroundColor(Color.RED)
                        error.setText("خطأ في كلمة السر أو الإيميل")
                    }
                }else{
                    Toast.makeText(this@Login, "خطأ في كلمة السر أو الإيميل", Toast.LENGTH_SHORT).show()
                    error.visibility = View.VISIBLE
                    error.setBackgroundColor(Color.RED)
                    error.setText("خطأ في كلمة السر أو الإيميل")
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("firebase", error.message)
                Toast.makeText(this@Login, "حاول لاحقا", Toast.LENGTH_SHORT).show()
            }


        })
    }

}

