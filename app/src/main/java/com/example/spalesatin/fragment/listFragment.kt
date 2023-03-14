package com.example.spalesatin.fragment

import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import com.example.spalesatin.Login.Login
import com.example.spalesatin.R
import com.example.spalesatin.list_frsgment.*
import com.example.spalesatin.title_main
import com.example.spalesatin.tr.aske_admin
import com.example.spalesatin.tr.orderOk

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
var shard: SharedPreferences? = null

/**
 * A simple [Fragment] subclass.
 * Use the [listFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class listFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

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

         v= inflater.inflate(R.layout.fragment_list, container, false)


        val sign_out = v.findViewById<TextView>(R.id.sigou)
        val btn_acount = v.findViewById<TextView>(R.id.button6)
        val btn_tr = v.findViewById<TextView>(R.id.button7)
        val btn_ok = v.findViewById<TextView>(R.id.btn_ok)



        onStop()


        shard =  requireContext().getSharedPreferences("user", AppCompatActivity.MODE_PRIVATE)
        val user = shard!!.getString("user" , "b")


        sign_out.setOnClickListener {
            var shard =   requireActivity().getSharedPreferences("user", AppCompatActivity.MODE_PRIVATE)
            val edit = shard!!.edit()
            edit.putString("che" , "false").apply()
            val i = Intent(this.context , Login::class.java)
            requireActivity().startActivity(i)
            requireActivity().finish()
        }

        btn_ok.setOnClickListener {
            val i = Intent(this.context , orderOk::class.java)
            startActivity(i)

        }
        btn_acount.setOnClickListener {
            val i = Intent(this.context , acount_fragment::class.java)
            startActivity(i)
        }


        if (user.equals("a")|| user.equals("A")){
            btn_tr.setText("ترقية الحسابات")
           // btn_tr.visibility  = GONE
        }


        btn_tr.setOnClickListener {
            if (user.equals("b") || user.equals("B")){
                    val i = Intent(this.context , aske_admin::class.java)
                    startActivity(i)
            }else  if (user.equals("a")|| user.equals("A")){
                    val i = Intent(this.context , tr_admin::class.java)
                    startActivity(i)

            }

        }


        return v
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment listFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            listFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}