package com.adl.absensiadl.repository

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ResponseAbsen(

	@field:SerializedName("total")
	val total: Int? = null,

	@field:SerializedName("data")
	val data: Data? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Boolean? = null
) : Parcelable

@Parcelize
data class TblAbsenItem(

	@field:SerializedName("foto")
	val foto: String? = null,

	@field:SerializedName("tgl_masuk")
	val tglMasuk: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("gps")
	val gps: String? = null,

	@field:SerializedName("tgl_keluar")
	val tglKeluar: String? = null,

	@field:SerializedName("username")
	val username: String? = null
) : Parcelable

@Parcelize
data class Data(

	@field:SerializedName("tbl_absen")
	val tblAbsen: List<TblAbsenItem?>? = null
) : Parcelable
