package com.example.spalesatin.fragment

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.ProgressDialog
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.os.Build
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.spalesatin.adapter.myAdaoterNotification
import com.example.spalesatin.R
import com.example.spalesatin.model2.helps
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
@SuppressLint("StaticFieldLeak")
lateinit var VIEW44: View

lateinit var recyclerView: RecyclerView
@SuppressLint("StaticFieldLeak")
lateinit var myAdaoter: myAdaoterNotification
var arrayList: ArrayList<helps> = ArrayList()

//var Adapter3: FirestoreRecyclerAdapter<People_add_model,searcheFragment.alli>? = null

class searcheFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    var notificationId = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        VIEW44 = inflater.inflate(R.layout.fragment_searche, container, false)
        recyclerView = VIEW44.findViewById<RecyclerView>(R.id.recViewNotification)
        val progressDialog = ProgressDialog(activity)
        recyclerView.layoutManager = LinearLayoutManager(activity)

        val shard = requireActivity().getSharedPreferences("user", AppCompatActivity.MODE_PRIVATE)
        val user = shard.getString("user", "false").toString()

            myAdaoter = myAdaoterNotification(requireActivity(), arrayList)
            recyclerView.adapter = myAdaoter


        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("helps")

try {
    myRef.addValueEventListener(object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {


            for (userSnapshot in snapshot.children) {

                val help = userSnapshot.getValue(helps::class.java)
                val hala = help!!.hala
                if (hala == "0"){
                    if (help != null) {
                        arrayList.add(help)
                    }
                }
            }

            myAdaoter.notifyDataSetChanged()
            if(arrayList.size > 0){
                progressDialog.dismiss()
            }

        }

        override fun onCancelled(error: DatabaseError) {
        }

    })

}catch (e:Exception){}


// prograss Dialog


        progressDialog.show()
        progressDialog.setContentView(R.layout.progress_dialog)
        progressDialog.window!!.setBackgroundDrawableResource(com.firebase.ui.auth.R.color.fui_transparent)

        Handler().postDelayed({
            progressDialog.dismiss()
        }, 5000)




        return VIEW44


    }


}
