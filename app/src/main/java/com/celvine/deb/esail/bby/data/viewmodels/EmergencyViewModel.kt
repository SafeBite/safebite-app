package com.celvine.deb.esail.bby.data.viewmodels

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.celvine.deb.esail.bby.data.model.HospitalModel
import com.celvine.deb.esail.bby.data.sources.HospitalData


class EmergencyViewModel() : ViewModel()  {
    fun makePhoneCall(context: Context, phoneNumber: String) {
        val intent = Intent(Intent.ACTION_DIAL).apply {
            data = Uri.parse("tel:$phoneNumber")
        }
        context.startActivity(intent)
    }

    private val _hospitals = mutableStateOf(HospitalData.data)
    val hospitals: State<List<HospitalModel>> = _hospitals
}