package com.adl.ujiancrud.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_absent.view.*
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*


class AbsentViewHolder(view:View):RecyclerView.ViewHolder(view) {
    val tanggal = view.lblTanggal
    val masuk = view.lblMasuk
    val keluar = view.lblKeluar


    fun bindData(adapter:AbsensiAdapter, position:Int){

        var calendar = Calendar.getInstance()
        val formatDate = "HH:mm:ss"
        val sdf = SimpleDateFormat(formatDate, Locale.US)
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH)

        val formatDate2 = "dd MMM"
        val sdf2 = SimpleDateFormat(formatDate2, Locale.US)

        val defaultZoneId: ZoneId = ZoneId.systemDefault()

        val ltglMasuk = LocalDateTime.parse(adapter.data.get(position)?.tglMasuk,formatter)
        val dtglMasuk = Date.from(ltglMasuk.atZone(defaultZoneId).toInstant())
        val tglMasuk = sdf.format(dtglMasuk)

        val ltglTanggal = LocalDateTime.parse(adapter.data.get(position)?.tglMasuk,formatter)
        val dtglTanggal = Date.from(ltglTanggal.atZone(defaultZoneId).toInstant())
        val tglTanggal = sdf2.format(dtglTanggal)



        if (adapter.data.get(position)?.tglKeluar?.equals("0000-00-00 00:00:00") == true){
            keluar.setText("-")
        }else{
            val ltglKeluar = LocalDateTime.parse(adapter.data.get(position)?.tglKeluar,formatter)
            val dtglKeluar = Date.from(ltglKeluar.atZone(defaultZoneId).toInstant())
            val tglKeluar = sdf.format(dtglKeluar)
            keluar.setText(tglKeluar)
        }







        masuk.setText(tglMasuk)

        tanggal.setText(tglTanggal)


    }

}