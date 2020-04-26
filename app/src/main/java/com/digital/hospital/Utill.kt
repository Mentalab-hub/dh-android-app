package com.digital.hospital

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import android.net.Uri
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity

/**
 * @CreatedBy Ali Ahsan
 *
 *         Author Email: this.aliahsan@gmail.com
 *         Created on: 26/04/20
 */

/**
 * Check Internet Connection
 *
 */

// Check for network connections
fun checkAvailableNetwork(activity: AppCompatActivity):Boolean{
    val connectivityManager=activity.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val networkInfo=connectivityManager.activeNetworkInfo
    return  networkInfo!=null && networkInfo.isConnected
}

fun callDialog(activity: FragmentActivity) {

    val dialog = AlertDialog.Builder(activity)
    dialog.setCancelable(true)
    val view: View = activity.layoutInflater.inflate(R.layout.custom_dialog, null)

    dialog.setView(view)

    val call = view.findViewById(R.id.btnCall) as TextView

    dialog.create().let {

        it.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        call.setOnClickListener { _ ->

            makeCall(activity)

            it.dismiss()
        }

        it.show()
    }
}

private fun makeCall(activity: FragmentActivity)
{
    val phone = activity.getString(R.string.default_contact_text)
    val intent = Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null))
    activity.startActivity(intent)
}