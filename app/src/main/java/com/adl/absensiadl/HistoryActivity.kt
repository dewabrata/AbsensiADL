package com.adl.absensiadl

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.adl.absensiadl.repository.ResponseAbsen
import com.adl.absensiadl.repository.ResponseLogin
import com.adl.absensiadl.repository.TblAbsenItem
import com.adl.absensiadl.services.RetrofitConfig
import com.adl.ujiancrud.adapter.AbsensiAdapter
import kotlinx.android.synthetic.main.activity_history.*
import kotlinx.android.synthetic.main.login_activity.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HistoryActivity : AppCompatActivity() {
    lateinit var lstAbsent :List<TblAbsenItem?>
    lateinit var absentAdapter :AbsensiAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)
        loadData()
    }


    fun loadData(){
        RetrofitConfig().getAbsen().getAllAbsen().enqueue(object:
            Callback<ResponseAbsen> {
            override fun onResponse(call: Call<ResponseAbsen>, response: Response<ResponseAbsen>) {


                lstAbsent = response.body()?.data?.tblAbsen!!

                absentAdapter = ArrayList(lstAbsent)?.let { AbsensiAdapter(it) }
                recycleAbsent.apply {
                    layoutManager = LinearLayoutManager(this@HistoryActivity)
                    adapter = absentAdapter
                }



            }

            override fun onFailure(call: Call<ResponseAbsen>, t: Throwable) {
                Toast.makeText(this@HistoryActivity,t.localizedMessage.toString(),
                    Toast.LENGTH_LONG).show()
            }

        })
    }
}