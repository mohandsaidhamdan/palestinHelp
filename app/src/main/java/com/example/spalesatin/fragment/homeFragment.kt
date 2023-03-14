package com.example.spalesatin.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.bumptech.glide.Glide
import com.example.spalesatin.R
import com.example.spalesatin.detaliess.add.add_pepole
import com.example.spalesatin.detaliess.details
import com.example.spalesatin.detaliess.details_2
import com.example.spalesatin.list_frsgment.list_aske
import com.example.spalesatin.list_mrsh.list_admin_m
import com.example.spalesatin.list_mrsh.list_user_m
import com.example.spalesatin.list_mrsh.search
import com.example.spalesatin.tr.tr_admin
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*


// TODO: Rename parameter arguments, choose names that match
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

@SuppressLint("StaticFieldLeak")
lateinit var text: TextView

@SuppressLint("StaticFieldLeak")
lateinit var v: View
lateinit var name: String


class homeFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    @SuppressLint("CutPasteId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_home, container, false)
        onStop()




        val shard = requireContext().getSharedPreferences("user", AppCompatActivity.MODE_PRIVATE)
        val id = shard.getString("id", "false").toString()
        val user = shard.getString("user", "false").toString()
        val title = shard.getString("title", "false").toString()



     // add helps people in realtime Firebase
        // c - > user
        // A - > admin
        v.btn_aske_helps.setOnClickListener {
            if (user.equals("A"))
                startActivity(Intent(context, tr_admin::class.java))
            else
                Toast.makeText(activity, "عليك ترقية حسابك للوصول لهذه الخدمة", Toast.LENGTH_LONG).show()
        }


        // add people in realtime Firebase
        v.btnAddPeople.setOnClickListener {
            val i = Intent(context, add_pepole::class.java)
            startActivity(i)
        }

        // view people in realtime in firebase
        v.btnAllPeople.setOnClickListener {
            val i = Intent(context, details::class.java)
            startActivity(i)
        }



       // view helps in realtime in firebase
        v.btnAllHelps.setOnClickListener {
            val i = Intent(context, list_aske::class.java)
            startActivity(i)

        }

        // button to list view all help users
        v.btn_add_help_ListAll.setOnClickListener {
       if (user.equals("A")) {
           val i = Intent(context, list_admin_m::class.java)
           startActivity(i)

            }else{
           val i = Intent(context, list_admin_m::class.java)
           startActivity(i)
            }

        }

        // add help one people
        v.btnHelpOnePeople.setOnClickListener {
            val i = Intent(context, details_2::class.java)
            startActivity(i)
        }





        // Searche in database in number people
        v.btnSearch.setOnClickListener {
            val i = Intent(context, search::class.java)
            startActivity(i)

        }
















        return v
    }


}

