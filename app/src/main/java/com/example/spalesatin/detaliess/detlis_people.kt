package com.example.spalesatin.detaliess

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.example.spalesatin.MainActivity
import com.example.spalesatin.R
import kotlinx.android.synthetic.main.activity_detiles_peplos_on.*
import kotlinx.android.synthetic.main.activity_tr_admin.*
import java.util.*


class detlis_people : AppCompatActivity() {
    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detiles_peplos_on)
      val name = intent.getStringExtra("name").toString()
      val type =intent.getStringExtra("type").toString()
      val number =intent.getStringExtra("number").toString()
      val date =intent.getStringExtra("date").toString()
      val halaEconomy =intent.getStringExtra("halaEconomy").toString()
      val halaSocial =intent.getStringExtra("halaSocial").toString()
      val helps =intent.getStringExtra("helps").toString()
      val Worke =intent.getStringExtra("Worke").toString()
      val hala =intent.getStringExtra("hala").toString()


        text_name_user.setText(name)
        text_number_id.setText(type)
        text_date.setText(number)
        //tex_name.setText(intent.getStringExtra("numberFamly").toString())
        type_1name.setText(date)
        number_h.setText(halaEconomy)
        number_h2.setText(halaSocial)
        number_h3.setText(helps)
        number_h4.setText(Worke)


        btn_add_helps.setOnClickListener {
            if (hala.equals("1")){
                Toast.makeText(this, "هذه المساعدة منتهية", Toast.LENGTH_SHORT).show()
            }else{
                val i = Intent(this@detlis_people , details_2::class.java)
                i.putExtra("name" , name)
                i.putExtra("type" , type)
                i.putExtra("number" , number)
                i.putExtra("date" , date)
                i.putExtra("halaEconomy" , halaEconomy)
                i.putExtra("halaSocial" , halaSocial)
                i.putExtra("helps" , helps)
                i.putExtra("Worke" , Worke)
                i.putExtra("numberF" , Worke)
                i.putExtra("numberM" , Worke)
                i.putExtra("numberS" , Worke)
                i.putExtra("firstNumberPeople" , Worke)
                i.putExtra("scendNumberPeople" , Worke)
                startActivity(i)
                finish()
            }

        }


    }

}