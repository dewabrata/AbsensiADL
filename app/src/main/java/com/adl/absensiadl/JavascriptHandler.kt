package com.adl.absensiadl

import android.content.Context
import android.util.Log
import android.webkit.JavascriptInterface
import android.widget.Toast

class JavascriptHandler {

    lateinit var context:Context
    constructor(context: Context){
        this.context = context
    }
    @JavascriptInterface
    fun  showToast(data:String){
        Toast.makeText(context,data,Toast.LENGTH_LONG).show()
    }
}