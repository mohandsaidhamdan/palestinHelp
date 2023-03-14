package com.example.spalesatin

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.spalesatin.model2.helpFinishPeople
import com.google.firebase.database.*
import jxl.Workbook
import jxl.WorkbookSettings
import jxl.write.Label
import jxl.write.WritableWorkbook
import kotlinx.android.synthetic.main.activity_sigin.*
import kotlinx.android.synthetic.main.activity_write_excel2.*
import kotlinx.android.synthetic.main.item_add2.*
import java.io.File
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class WriteExcel : AppCompatActivity() {
    var workbook: WritableWorkbook? = null
    lateinit var database: DatabaseReference

    @SuppressLint("NewApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_write_excel2)
        startDate.visibility = View.GONE
        endDate.visibility = View.GONE



        chbetwen.setOnCheckedChangeListener { _, ch ->
            if (ch) {
                startDate.visibility = View.VISIBLE
                endDate.visibility = View.VISIBLE
                chall.isChecked = false
                chmin.isChecked = false

            } else {
                startDate.visibility = View.GONE
                endDate.visibility = View.GONE
            }
        }
        chmin.setOnCheckedChangeListener { _, ch ->
            if (ch) {
                chall.isChecked = false
                chbetwen.isChecked = false
                startDate.visibility = View.VISIBLE
                endDate.visibility = View.GONE
            } else {
                startDate.visibility = View.GONE
                endDate.visibility = View.GONE
            }
        }
        chall.setOnCheckedChangeListener { _, ch ->
            if (ch) {
                chbetwen.isChecked = false
                chmin.isChecked = false
                startDate.visibility = View.GONE
                endDate.visibility = View.GONE
            }
        }

        database = FirebaseDatabase.getInstance().getReference("helpsTarshPeoples")


        btncreateFileExcel.setOnClickListener {

            val daystart = dayStart.selectedItem.toString()
            val monthSatrt = monthStart.selectedItem.toString()
            val yearStart = yearStart.selectedItem.toString()
            val dateStart = "$daystart-$monthSatrt-$yearStart"

            val dayend = dayend.selectedItem.toString()
            val monthend = monthend.selectedItem.toString()
            val yearend = yearend.selectedItem.toString()
            val dateEnd = "$yearend - $monthend - $dayend"

            val idNumber = text_idNumber.text.toString()
            val nameFile = text_name_file.text.toString()
            if (idNumber.isNotEmpty() && nameFile.isNotEmpty()) {
                createExcelSheet(nameFile, idNumber, dateStart, dateEnd)
            } else {
                Toast.makeText(this, "كل الجقول مطلوبة", Toast.LENGTH_SHORT).show()
            }
        }

    }


    fun createExcelSheet(nameFile: String, idNumber: String, DateStart: String, dateEnd: String) {

        if (nameFile.isEmpty()) {
            Toast.makeText(this, "أدخل إسم الملف", Toast.LENGTH_SHORT).show()
        } else {
            val csvFile = "$nameFile.xls"
            val futureStudioIconFile = File(
                Environment
                    .getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                    .toString() + "/" + csvFile
            )
            val wbSettings = WorkbookSettings()
            wbSettings.locale = Locale("en", "EN")
            try {
                workbook = Workbook.createWorkbook(futureStudioIconFile, wbSettings)
                createFirstSheet(idNumber, DateStart, dateEnd)

                with(workbook) {
                    this!!.write()
                    close()
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(this, "فشل الإنشاء ", Toast.LENGTH_SHORT).show()
            }
        }


    }

    fun createFirstSheet(idNumber: String, DateStart: String, dateEnd: String) {
        val listdata: MutableList<helpFinishPeople> = ArrayList()

        try {

            val query: Query = database.orderByChild("nameHelp")
            query.addValueEventListener(object : ValueEventListener {
                @RequiresApi(Build.VERSION_CODES.O)
                @SuppressLint("NotifyDataSetChanged")
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (snapshot in snapshot.children) {
                        val getNameHelp = snapshot.child("nameHelp").getValue(String::class.java)
                        val getNamePeople =
                            snapshot.child("namePeople").getValue(String::class.java)
                        val getType = snapshot.child("type").getValue(String::class.java)
                        val getidNumber = snapshot.child("idNumber").getValue(String::class.java)
                        val dateStr = snapshot.child("date").getValue(String::class.java)


                        //  Toast.makeText(this@WriteExcel, "date == $dateStr", Toast.LENGTH_SHORT).show()
                        try {
                            val date = dateStr.toString()
                            val date1 = LocalDate.parse(date)
                            val date2 = LocalDate.parse("2022-03-15")

                            if (date1.compareTo(date2) < 0) {
                                Toast.makeText(this@WriteExcel, "$date1 is before $date2", Toast.LENGTH_SHORT).show()
                            } else if (date1.compareTo(date2) > 0) {
                                println("$date1 is after $date2")
                                Toast.makeText(this@WriteExcel, "$date1 is after $date2", Toast.LENGTH_SHORT).show()
                            } else {
                                println("$date1 is equal to $date2")
                            }
                        } catch (_: Exception) {

                        }



                        if (getidNumber.equals(idNumber)) {
                            textView13.text = DateStart
//                                    val date = dateStr.toString()
//                                    val date1 = LocalDate.parse(date)
//                                    val date2 = LocalDate.parse(DateStart)
//                                    if(date1.compareTo(date2) > 0) {
//                                        val users = snapshot.getValue(helpFinishPeople::class.java)
//                                        if (users != null) {
//                                            listdata.add(users)
//                                            textView13.text = users.date
//                                        }
//
//                                }
                            }

                        }



                }

                override fun onCancelled(error: DatabaseError) {
                }

            })

            /*  val db = DataBaseHelper(this)
              val name_sum = intent.getStringExtra("name_sum")
              val search = intent.getStringExtra("search").toString()
              val data = db.login_help_all2(month_f , month_s)*/

            //var da: MutableList<help_all> = ArrayList()

            var i = 0
            /*   while (i < data.size){
                   listdata.add(data[i])
                   i++
               }*/
//             Toast.makeText(this, "تم الإنشاء بنجاح", Toast.LENGTH_SHORT).show()


            val sheet = workbook!!.createSheet("sheet1", 0)
            // column and row title
            sheet.addCell(Label(0, 0, "الإسم "))
            sheet.addCell(Label(1, 0, "رقم الهوية"))
            sheet.addCell(Label(2, 0, "الجهة المانحة للمساعدة"))
            sheet.addCell(Label(3, 0, "نوع المساعدة"))
            sheet.addCell(Label(4, 0, "تاريخ المساعدة"))
            for (i in listdata.indices) {
                sheet.addCell(Label(0, i + 1, "listdata[i"))
                sheet.addCell(Label(1, i + 1, "listdata[i].getnumber_id()"))
                sheet.addCell(Label(2, i + 1, " listdata[i].getname_type_helpe()"))
                sheet.addCell(Label(3, i + 1, "listdata[i].gettype_name()"))
                sheet.addCell(Label(4, i + 1, "listdata[i].getdate()"))
            }

        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(this, "فشل الإنشاء ", Toast.LENGTH_SHORT).show()
        }
    }

}