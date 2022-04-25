package com.adl.absensiadl.services

import com.adl.absensiadl.repository.ResponseAbsen
import com.adl.absensiadl.repository.ResponseLogin
import com.adl.absensiadl.repository.ResponsePostData
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface ILogin {

    @Headers("X-Api-Key:6D83551EAC167A26DC10BB7609EA9AEF")
    @GET("api/tbl_user_absen/all")
    fun login (@Query("field")  field:String, @Query("filter") filter:String ) : Call<ResponseLogin>

    @Headers("X-Api-Key:6D83551EAC167A26DC10BB7609EA9AEF")
    @GET("api/tbl_absen/all")
    fun getId (@Query("field")  field:String, @Query("filter") filter:String ) : Call<ResponseAbsen>

    @Headers("X-Api-Key:6D83551EAC167A26DC10BB7609EA9AEF")
    @GET("api/tbl_absen/all")
    fun getAllAbsen () : Call<ResponseAbsen>


    @Multipart
    @Headers("X-Api-Key:6D83551EAC167A26DC10BB7609EA9AEF")
    @POST("api/tbl_absen/add")
    fun addDataAndImage(@Part("username") username: RequestBody, @Part("tgl_masuk") tgl_masuk: RequestBody,
                        @Part("tgl_keluar") tgl_keluar: RequestBody, @Part("gps") gps: RequestBody,
                        @Part file: MultipartBody.Part):Call<ResponsePostData>

    @FormUrlEncoded
    @Headers("X-Api-Key:6D83551EAC167A26DC10BB7609EA9AEF")
    @POST("api/tbl_absen/update")
    fun updateCheckout(@Field("id") id:String,@Field("username") username:String,
                    @Field("tgl_keluar") tgl_keluar:String):Call<ResponsePostData>



}