package com.example.spalesatin.tr

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_tr_admin.*
import kotlinx.android.synthetic.main.activity_tr_admin.view.*
import kotlinx.android.synthetic.main.login.*
import java.util.*

class tr_admin : AppCompatActivity() {
    var database: DatabaseReference = FirebaseDatabase.getInstance()
        .getReferenceFromUrl("https://palestain-51792-default-rtdb.firebaseio.com/")

    @SuppressLint("MissingInflatedId", "SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tr_admin)

        // spinner

        val halaMar = findViewById<AutoCompleteTextView>(R.id.autoText)
        val helps = findViewById<AutoCompleteTextView>(R.id.autoText2)
        val helps2 = findViewById<AutoCompleteTextView>(R.id.autoText4)
        val Worke = findViewById<AutoCompleteTextView>(R.id.Worke)
        val halaEconomy = findViewById<AutoCompleteTextView>(R.id.halaEconomy)
        val halaEconomy2 = findViewById<AutoCompleteTextView>(R.id.halaEconomy2)
        val halaSocial = findViewById<AutoCompleteTextView>(R.id.halaSocial)

        inputHalaEc.visibility = View.GONE
        helps22.visibility = View.GONE

        val ArrayhalaMar = arrayOf("سليم", "مصاب", "غير مطلوب")
        val Arrayhelps = arrayOf("المنحة القطرية", "شؤوون إجتماعية", "غير مستفيد", "إضافة خيار أخر")
        val Arrayhelps2 = arrayOf("المنحة القطرية", "شؤوون إجتماعية", "غير مستفيد")
        val ArrayTitle = arrayOf("مسجد المجاهدين", "مسجد فلسطين", "مسجد السلام")
        val ArrayWorke = arrayOf("موضف", "عمل خاص", "لا يعمل")
        val ArrayhalaEconomy = arrayOf("جيد", "متوسط", "صعب", "إضافة خيار أخر")
        val ArrayhalaEconomy2 = arrayOf("جيد", "متوسط", "صعب")
        val ArrayhalaSocial = arrayOf("متزوج", "أعزب", "مطلق", "غير مطلوب")


        val adpter: ArrayAdapter<String> = ArrayAdapter(v.context, R.layout.drop_dwon, ArrayhalaMar)
        val adpter2: ArrayAdapter<String> = ArrayAdapter(v.context, R.layout.drop_dwon, Arrayhelps)
        val adpter9: ArrayAdapter<String> = ArrayAdapter(v.context, R.layout.drop_dwon, Arrayhelps2)
        val adpter3: ArrayAdapter<String> = ArrayAdapter(v.context, R.layout.drop_dwon, ArrayTitle)
        val adpter4: ArrayAdapter<String> = ArrayAdapter(v.context, R.layout.drop_dwon, ArrayWorke)
        val adpter5: ArrayAdapter<String> =
            ArrayAdapter(v.context, R.layout.drop_dwon, ArrayhalaEconomy)
        val adpter6: ArrayAdapter<String> =
            ArrayAdapter(v.context, R.layout.drop_dwon, ArrayhalaSocial)
        val adpter8: ArrayAdapter<String> =
            ArrayAdapter(v.context, R.layout.drop_dwon, ArrayhalaEconomy2)


        halaMar.setAdapter(adpter)
        helps.setAdapter(adpter2)
        helps2.setAdapter(adpter9)
        Worke.setAdapter(adpter4)
        halaEconomy.setAdapter(adpter5)
        halaSocial.setAdapter(adpter6)
        halaEconomy2.setAdapter(adpter8)

        halaEconomy.setOnItemClickListener(object : AdapterView.OnItemClickListener {
            override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                if (halaEconomy.text.toString().equals("إضافة خيار أخر")) {
                    inputHalaEc.visibility = View.VISIBLE
                }

            }

        })
        helps.setOnItemClickListener(object : AdapterView.OnItemClickListener {
            override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                if (helps.text.toString().equals("إضافة خيار أخر")) {
                    helps22.visibility = View.VISIBLE
                }

            }

        })


        // add data realtime fierbase
        btn_add_peeplo.setOnClickListener {
            val halaMar = halaMar.text.toString()
            val helps = helps.text.toString()
            val Worke = Worke.text.toString()
            val halaEconomy = halaEconomy.text.toString()
            val halaSocial = halaSocial.text.toString()
            val helps2 = helps2.text.toString()
            val halaEconomy2 = halaEconomy2.text.toString()

            // get data in input user
            val name = texname.text.toString()
            val type = type_name.text.toString()
            val number = numbers.text.toString()
            val firstNumberPeople = number_f.text.toString()
            val scendNumberPeople = number_s.text.toString()
            val numberM = n_m.text.toString()
            val numberS = n_s.text.toString()
            val numberF = n_p.text.toString()
            val hala = "0"


            if(numbers.text.toString().isEmpty() || n_m.text.toString().isEmpty()|| n_s.text.toString().isEmpty()|| n_p.text.toString().isEmpty()){
                Toast.makeText(this, "يجب ان تدخل عدد المساعدات و حصص المساجد", Toast.LENGTH_SHORT).show()
            }else{
                val sumNumber = n_m.text.toString().toInt() + n_s.text.toString().toInt() + n_p.text.toString().toInt()
                val numberAll = numbers.text.toString().toInt()
                if (numberAll >= sumNumber){
                    val dat = Calendar.getInstance()
                    val year = dat.get(Calendar.YEAR)
                    val MONTH = dat.get(Calendar.MONTH) + 1
                    val day = dat.get(Calendar.DAY_OF_MONTH)
                    val date = "$year/$MONTH/$day"

                    val databases = FirebaseDatabase.getInstance()
                    val myRef = databases.getReference("helps")

                    // create a new user with auto-generated ID
                    val newUser = myRef.push()

                    val help = hashMapOf(
                        "name" to name,
                        "date" to date,
                        "halaMar" to halaMar,
                        "type" to type,
                        "firstNumberPeople" to firstNumberPeople,
                        "number" to number,
                        "Worke" to Worke,
                        "helps" to helps,
                        "scendNumberPeople" to scendNumberPeople,
                        "halaEconomy2" to halaEconomy2,
                        "helps2" to helps2,
                        "halaSocial" to halaSocial,
                        "halaEconomy" to halaEconomy,
                        "hala" to hala,
                        "numberS" to numberS,
                        "numberF" to numberF,
                        "numberM" to numberM,
                        "numberM" to numberM
                    )


                    newUser.setValue(help)
                    Toast.makeText(this@tr_admin, "تم الإضافة", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this@tr_admin, MainActivity::class.java))
                    finish()
                }else{
                    Toast.makeText(this@tr_admin, "يجب ان يكون حصص المساجد اقل او تساوي عدد المساعدات", Toast.LENGTH_LONG).show()
                }

            }





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

}