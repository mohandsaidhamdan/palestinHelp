package com.example.spalesatin.splasview

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.lottie.LottieAnimationView
import com.example.spalesatin.Login.Login
import com.example.spalesatin.MainActivity
import com.example.spalesatin.R
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_splasview.*


class SplasView : AppCompatActivity() {
    lateinit var lottieAnimationView: LottieAnimationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splasview)

        lottieAnimationView = findViewById(R.id.lottie)
        lottieAnimationView.animate().translationY((0).toFloat()).setDuration(4000).setStartDelay(1500).withEndAction {
//        lottieAnimationView.animate().setDuration(3000).withEndAction {
            val id_c =  getSharedPreferences("user", MODE_PRIVATE).getString("che" , "false")
            if (id_c == "true"){
                startActivity( Intent(this, MainActivity::class.java))
                finish()
            }else{
                startActivity(Intent(this, Login::class.java))
                finish()
            }
        }



        //  imageView3.alpha = 0f
        /*   imageView3.animate().setDuration(700).alpha(1f).withEndAction {

            var shard =  getSharedPreferences("user", MODE_PRIVATE)
            val id_c =  shard.getString("che" , "false")
            if (id_c == "true"){
                val i = Intent(this, MainActivity::class.java)
                startActivity(i)
                finish()
            }else{
                val i = Intent(this, Login::class.java)
                startActivity(i)
                finish()
            }
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            finish()
        }*/
    }
}