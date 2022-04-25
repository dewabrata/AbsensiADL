package com.adl.absensiadl

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.adl.absensiadl.repository.ResponseLogin
import com.adl.absensiadl.services.RetrofitConfig
import kotlinx.android.synthetic.main.login_activity.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)

        btnLogin.setOnClickListener({
            login()
        })

    }


    fun login(){

        RetrofitConfig().getAbsen().login("username",txtUsername.text.toString()).enqueue(object: Callback<ResponseLogin>{
            override fun onResponse(call: Call<ResponseLogin>, response: Response<ResponseLogin>) {
                val username = (response.body() as ResponseLogin).data?.tblUserAbsen?.get(0)?.username.toString()
                val password = (response.body() as ResponseLogin).data?.tblUserAbsen?.get(0)?.password.toString()

                if(txtUsername.text.toString() == username && txtPassword.text.toString() == password){

                    //simpan username as cookies
                    val editor:SharedPreferences.Editor = getSharedPreferences("absen",Context.MODE_PRIVATE).edit()
                    editor.putString("username",username)
                    editor.apply()

                    val intent : Intent = Intent(applicationContext,MainMenu::class.java)
                    startActivity(intent)
                }else{
                    Toast.makeText(this@LoginActivity,"Maaaf username atau password salah",
                        Toast.LENGTH_LONG).show()

                }


            }

            override fun onFailure(call: Call<ResponseLogin>, t: Throwable) {
                Toast.makeText(this@LoginActivity,t.localizedMessage.toString(),
                    Toast.LENGTH_LONG).show()
            }

        })
    }
} 