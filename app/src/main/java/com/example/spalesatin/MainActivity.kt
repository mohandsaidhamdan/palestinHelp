package com.example.spalesatin

import android.Manifest
import android.annotation.SuppressLint
import android.app.DownloadManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.core.app.NotificationCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.spalesatin.Login.Login

import com.example.spalesatin.fragment.*
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.database.*
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import jxl.write.WritableWorkbook
import kotlinx.android.synthetic.main.activity_details.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_add.*
import kotlinx.android.synthetic.main.item_add2.*
import java.util.*

class MainActivity : AppCompatActivity() {

    lateinit var id: String
    lateinit var name: String
    lateinit var db: FirebaseFirestore
  var workbook: WritableWorkbook? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        db = Firebase.firestore



      // create fragment
        fragmenCreate()

        var database: DatabaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://palestain-51792-default-rtdb.firebaseio.com/")


database.child("helps").addListenerForSingleValueEvent(object : ValueEventListener {
    @SuppressLint("SuspiciousIndentation")
    override fun onDataChange(snapshot: DataSnapshot) {
var count =0
        for ( snapshot in snapshot.children){
            val id =  getSharedPreferences("user", MODE_PRIVATE).getString("id" , "")
            val getHala = snapshot.child("$id").getValue(String::class.java)
            val getType = snapshot.child("type").getValue(String::class.java)
            val newKey = snapshot.key

            if (!(getHala == "true")){
               database.child("helps").child("$newKey").child("$id").setValue("true")
                Notification(" طلب ترشيح أسماء جديد" , "نوع المساعدة : $getType " , count)
                count ++

            }
        }
    }

    override fun onCancelled(error: DatabaseError) {
        TODO("Not yet implemented")
    }
})













    }

    fun Notification(title : String , massge : String ,  notificationId : Int){
        // Create a notification channel
        val channelId = "my_channel_id"
        val channelName = "My Channel Name"
        val importance = NotificationManager.IMPORTANCE_HIGH
        val channel = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel(channelId, channelName, importance)
        } else {
            TODO("VERSION.SDK_INT < O")
        }

// Create a notification
        val notificationBuilder =
            NotificationCompat.Builder(this@MainActivity, channelId)
                .setSmallIcon(android.R.drawable.ic_menu_compass).setContentTitle(title)
                .setContentText(massge).setPriority(NotificationCompat.PRIORITY_HIGH)

// Show the notification
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
        notificationManager.notify(notificationId, notificationBuilder.build())
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.home_menu3, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.home -> {
                val intent = Intent(DownloadManager.ACTION_VIEW_DOWNLOADS)
                startActivity(intent)
            }
            R.id.tr -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                    checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                ) {
                    requestPermissions(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 1)
                } else {
                    startActivity(Intent(this , WriteExcel::class.java))
                    //your code
                 //   createExcelSheet()
                }

            }
        }
        return super.onOptionsItemSelected(item)
    }




    // create fragment MainActivity
    fun fragmenCreate(){
        var adapter = myadapter(this)
        viewbager.adapter = adapter
        val taboutminiter = TabLayoutMediator(Tablayout, viewbager, object : TabLayoutMediator.TabConfigurationStrategy {
            override fun onConfigureTab(tab: TabLayout.Tab, position: Int) {
                when (position) {
                    0 -> tab.setIcon(R.drawable.ic_baseline_home_24)
                     1 -> tab.setIcon(R.drawable.ic_baseline_notifications_24)
                  //  1 -> tab.setIcon(R.drawable.ic_baseline_wysiwyg_24)
                   2 -> tab.setIcon(R.drawable.ic_baseline_menu_24)

                }
            }

        })

        taboutminiter.attach()
    }

    class myadapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
        override fun getItemCount(): Int {
            return totalcount
        }

        override fun createFragment(position: Int): Fragment {
            var fragment = Fragment()
            when (position) {
                0 -> fragment = homeFragment()
                1-> fragment = searcheFragment()
                2 -> fragment = listFragment()

            }
            return fragment
        }

    }

    companion object {
        val totalcount =3
    }

}


