package com.celvine.deb.esail.bby.data.viewmodels

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.celvine.deb.esail.bby.data.model.HospitalModel
import com.celvine.deb.esail.bby.data.repositories.HospitalRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class EmergencyViewModel(private val hospitalRepository: HospitalRepository) : ViewModel() {

    fun makePhoneCall(context: Context, phoneNumber: String) {
        val intent = Intent(Intent.ACTION_DIAL).apply {
            data = Uri.parse("tel:$phoneNumber")
        }
        context.startActivity(intent)
    }

    private val _hospitals = MutableStateFlow<List<HospitalModel>>(emptyList())
    val hospitals: StateFlow<List<HospitalModel>> = _hospitals

    init {
        fetchHospitals()
    }

    private fun fetchHospitals() {
        viewModelScope.launch {
            hospitalRepository.getHospital().collect { hospitalList ->
                _hospitals.value = hospitalList
            }
        }
    }
}
