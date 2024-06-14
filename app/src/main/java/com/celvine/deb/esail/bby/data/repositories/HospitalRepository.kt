package com.celvine.deb.esail.bby.data.repositories

import com.celvine.deb.esail.bby.data.model.HospitalModel
import com.celvine.deb.esail.bby.data.sources.HospitalData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class HospitalRepository {
    fun getHospital(): Flow<List<HospitalModel>> {
        return flowOf(HospitalData.data)
    }

    companion object {
        @Volatile
        private var instance: HospitalRepository? = null

        fun getInstance(): HospitalRepository = instance ?: synchronized(this) {
            HospitalRepository().apply {
                instance = this
            }
        }
    }
}
