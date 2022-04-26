package com.adl.absensiadl

import android.Manifest
import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.webkit.PermissionRequest
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.adl.absensiadl.R
import kotlinx.android.synthetic.main.activity_web_view.*
import pub.devrel.easypermissions.EasyPermissions
import pub.devrel.easypermissions.EasyPermissions.PermissionCallbacks


class WebViewActivity  : AppCompatActivity(), PermissionCallbacks {

    private val TAG = "TEST"
    private var mPermissionRequest: PermissionRequest? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_web_view)
        val webViewClient = WebViewClient()
        webView.webViewClient = webViewClient
        webView.settings.javaScriptEnabled = true
        webView.settings.domStorageEnabled = true
        webView.settings.setSupportZoom(false)
        webView.settings.allowFileAccess = true
        webView.settings.allowContentAccess = true
        webView.settings.mediaPlaybackRequiresUserGesture = false

        //myWebView.setWebViewClient(new WebViewClient());
        webView.webChromeClient = object : WebChromeClient() {
            // Grant permissions for cam
            override fun onPermissionRequest(request: PermissionRequest) {
                Log.i(TAG, "onPermissionRequest")
                mPermissionRequest = request
                val requestedResources: Array<String> = request.getResources()
                for (r in requestedResources) {
                    if (r == PermissionRequest.RESOURCE_VIDEO_CAPTURE) {
                        // In this sample, we only accept video capture request.
                        val alertDialogBuilder: AlertDialog.Builder = AlertDialog.Builder(this@WebViewActivity)
                            .setTitle("Allow Permission to camera")
                            .setPositiveButton("Allow",
                                DialogInterface.OnClickListener { dialog, which ->
                                    dialog.dismiss()
                                    mPermissionRequest!!.grant(arrayOf<String>(PermissionRequest.RESOURCE_VIDEO_CAPTURE))
                                    Log.d(TAG, "Granted")

                                })
                            .setNegativeButton("Deny",
                                DialogInterface.OnClickListener { dialog, which ->
                                    dialog.dismiss()
                                    mPermissionRequest!!.deny()
                                    Log.d(TAG, "Denied")
                                })
                        val alertDialog: AlertDialog = alertDialogBuilder.create()
                        alertDialog.show()
                        break
                    }
                }
            }

            override fun onPermissionRequestCanceled(request: PermissionRequest?) {
                super.onPermissionRequestCanceled(request)
                Toast.makeText(this@WebViewActivity, "Permission Denied", Toast.LENGTH_SHORT).show()
            }
        }
        if (hasCameraPermission()) {
            webView.loadUrl("https://dewabrata.github.io/cupangdetect")
          //  setContentView(myWebView)
        } else {
            EasyPermissions.requestPermissions(
                this,
                "This app needs access to your camera so you can take pictures.",
                REQUEST_CAMERA_PERMISSION,
                *PERM_CAMERA
            )
        }

        webView.loadUrl("https://dewabrata.github.io/cupangdetect")
    }

    private fun hasCameraPermission(): Boolean {
        return EasyPermissions.hasPermissions(this@WebViewActivity, *PERM_CAMERA)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this@WebViewActivity)
    }

    override fun onPermissionsGranted(requestCode: Int, perms: List<String>) {}
    override fun onPermissionsDenied(requestCode: Int, perms: List<String>) {}

    companion object {
        private const val REQUEST_CAMERA_PERMISSION = 1
        private val PERM_CAMERA = arrayOf<String>(Manifest.permission.CAMERA)
    }
}