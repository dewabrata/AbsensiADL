package com.adl.absensiadl

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main_menu.*

class MainMenu : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)
        val status = getSharedPreferences("absen", Context.MODE_PRIVATE).getString("status","")!!

        if(!status.equals("")){
            txtStatus.setText("CHECK OUT")
        }else{
            txtStatus.setText("CHECK IN")
        }

        btnCheckin.setOnClickListener({

            val intent : Intent = Intent(applicationContext,CheckinActivity::class.java)
            startActivity(intent)

        })

        btnHistory.setOnClickListener({
            val intent : Intent = Intent(applicationContext,HistoryActivity::class.java)
            startActivity(intent)
        })

        btnClose.setOnClickListener({
            finish()
        })

    }


    override fun onResume() {
        super.onResume()
        val status = getSharedPreferences("absen", Context.MODE_PRIVATE).getString("status","")!!

        if(!status.equals("")){
            txtStatus.setText("CHECK OUT")
        }else{
            txtStatus.setText("CHECK IN")
        }
    }

}