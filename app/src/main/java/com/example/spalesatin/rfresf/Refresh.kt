package com.example.spalesatin.rfresf

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.spalesatin.R
import com.example.spalesatin.list_mrsh.list_admin_m

class Refresh : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_refresh)

            val i = Intent(this, list_admin_m::class.java)
        startActivity(i)
        finish()
    }
}