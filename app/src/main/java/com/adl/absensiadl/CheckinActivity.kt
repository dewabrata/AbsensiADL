package com.adl.absensiadl

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.adl.absensiadl.repository.ResponseAbsen
import com.adl.absensiadl.repository.ResponseLogin
import com.adl.absensiadl.repository.ResponsePostData
import com.adl.absensiadl.services.RetrofitConfig
import com.anirudh.locationfetch.EasyLocationFetch
import com.anirudh.locationfetch.GeoLocationModel
import com.github.drjacky.imagepicker.ImagePicker
import kotlinx.android.synthetic.main.activity_checkin.*
import kotlinx.android.synthetic.main.activity_main_menu.*
import kotlinx.android.synthetic.main.login_activity.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.text.SimpleDateFormat
import java.util.*


class CheckinActivity : AppCompatActivity() {
    lateinit var photoURI: Uri
    lateinit var geoLocationModel : GeoLocationModel
    val isstatus:Boolean = false //false checkin , true checkout
    lateinit var status:String

    private val cameraLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                val uri = it.data?.data!!

                photo.setImageURI(uri)
                photoURI = uri

                txtGPS.setText("${geoLocationModel.lattitude} | ${geoLocationModel.longitude} ")
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkin)

        status = getSharedPreferences("absen", Context.MODE_PRIVATE).getString("status","")!!



        geoLocationModel = (EasyLocationFetch(this@CheckinActivity).locationData)







        btnCamera.setOnClickListener({
            cameraLauncher.launch(
                ImagePicker.with(this)
                    .crop()
                    .cameraOnly()
                    .maxResultSize(480, 800, true)
                    .createIntent()
            )

        })

        btnCheckIn.setOnClickListener({
            if(status.equals("")){
                if(btnCheckIn.text.toString().equals("Done")){
                    val intent : Intent = Intent(applicationContext,MainMenu::class.java)
                    startActivity(intent)
                    finish()

                }else {
                    checkin()
                }
            }else{
                checkout()
            }
              })

    }


    fun createRB(data:String): RequestBody {
        return RequestBody.create(MultipartBody.FORM,data)
    }

    fun uploadImage(uri:Uri,param:String): MultipartBody.Part {
        val file: File = File(uri.path)
        val rb: RequestBody =  file.asRequestBody("image/jpeg".toMediaTypeOrNull())

        return MultipartBody.Part.createFormData(param,file.name,rb)

    }


    fun checkin(){

        var calendar = Calendar.getInstance()
        val formatDate = "yyyy-MM-dd hh:mm:ss"
        val sdf = SimpleDateFormat(formatDate, Locale.US)
        val nowdate = sdf.format(calendar.time)

        val username = getSharedPreferences("absen", Context.MODE_PRIVATE).getString("username","not found")!!



        if (!username.equals("not found")) {

            RetrofitConfig().getAbsen().addDataAndImage(
                createRB(username), createRB(nowdate.toString()),
                createRB(""), createRB(txtGPS.text.toString()),
                uploadImage(photoURI, "foto")

            ).enqueue(
                object : Callback<ResponsePostData> {
                    override fun onResponse(
                        call: Call<ResponsePostData>,
                        response: Response<ResponsePostData>
                    ) {
                        Toast.makeText(
                            this@CheckinActivity, (response.body() as ResponsePostData).message,
                            Toast.LENGTH_LONG
                        ).show()

                        photo.setImageResource(R.drawable.ok)

                        if(!isstatus) {
                            val editor: SharedPreferences.Editor =
                                getSharedPreferences("absen", Context.MODE_PRIVATE).edit()
                            editor.putString("status", nowdate)
                            editor.apply()
                            getId()



                        }
                    }

                    override fun onFailure(call: Call<ResponsePostData>, t: Throwable) {
                        Log.e("error post data", t.localizedMessage.toString())
                        photo.setImageResource(R.drawable.close)
                        btnCheckIn.setText("RE-UPLOAD")
                    }

                }
            )
        }else{

            Toast.makeText(
                this@CheckinActivity, "Error load username",
                Toast.LENGTH_LONG).show()
        }

    }

    fun checkout(){

        var calendar = Calendar.getInstance()
        val formatDate = "yyyy-MM-dd hh:mm:ss"
        val sdf = SimpleDateFormat(formatDate, Locale.US)
        val checkout = sdf.format(calendar.time)

        val id = getSharedPreferences("absen", Context.MODE_PRIVATE).getString("id","not found")!!


        val username = getSharedPreferences("absen", Context.MODE_PRIVATE).getString("username","not found")!!



        if (!username.equals("not found")) {

            RetrofitConfig().getAbsen().updateCheckout(
                id, username,
                checkout
                ).enqueue(
                object : Callback<ResponsePostData> {
                    override fun onResponse(
                        call: Call<ResponsePostData>,
                        response: Response<ResponsePostData>
                    ) {
                        Toast.makeText(
                            this@CheckinActivity, (response.body() as ResponsePostData).message,
                            Toast.LENGTH_LONG
                        ).show()


                            val editor: SharedPreferences.Editor =
                                getSharedPreferences("absen", Context.MODE_PRIVATE).edit()
                            editor.putString("status", "")
                        editor.putString("id", "")
                            editor.apply()

                        finish()




                    }

                    override fun onFailure(call: Call<ResponsePostData>, t: Throwable) {
                        Log.e("error post data", t.localizedMessage.toString())
                    }

                }
            )
        }else{
            Toast.makeText(
                this@CheckinActivity, "Error load username",
                Toast.LENGTH_LONG).show()
        }

    }


    fun getId(){
        status = getSharedPreferences("absen", Context.MODE_PRIVATE).getString("status","")!!
        RetrofitConfig().getAbsen().getId("tgl_masuk",status).enqueue(object: Callback<ResponseAbsen>{
            override fun onResponse(call: Call<ResponseAbsen>, response: Response<ResponseAbsen>) {



                    //simpan username as cookies
                    val editor:SharedPreferences.Editor = getSharedPreferences("absen",Context.MODE_PRIVATE).edit()
                    editor.putString("id",response.body()?.data?.tblAbsen?.get(0)?.id)
                    editor.apply()

                btnCheckIn.setText("Done")



            }

            override fun onFailure(call: Call<ResponseAbsen>, t: Throwable) {
                Toast.makeText(this@CheckinActivity,t.localizedMessage.toString(),
                    Toast.LENGTH_LONG).show()
            }

        })
    }
}