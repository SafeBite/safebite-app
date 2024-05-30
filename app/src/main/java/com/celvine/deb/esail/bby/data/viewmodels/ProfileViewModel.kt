package com.celvine.deb.esail.bby.data.viewmodels

import androidx.compose.runtime.mutableStateListOf
import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import com.celvine.deb.esail.bby.api.ApiConfig
import com.celvine.deb.esail.bby.data.model.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileViewModel(context: Context) : ViewModel() {
    private val _selectedIconIds = mutableStateListOf<Int>()
    val selectedIconIds: List<Int> get() = _selectedIconIds
    fun updateUsers(
        request: UpdateUserRequest,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        val apiService = ApiConfig.instanceRetrofit
            val updateUserCall: Call<UpdateUserProfileRespone> = apiService.updateUser( request)

            updateUserCall.enqueue(object : Callback<UpdateUserProfileRespone> {
                override fun onResponse(call: Call<UpdateUserProfileRespone>, response: Response<UpdateUserProfileRespone>) {
                    if (response.isSuccessful) {
                        val responModel = response.body()
                        onSuccess.invoke()
                        if (responModel != null) {
                            Log.d("Update Users", "Update Users successful")
                        }
                    } else {
                        onError.invoke("Update Users error")
                    }
                }
                override fun onFailure(call: Call<UpdateUserProfileRespone>, t: Throwable) {
                    onError.invoke("Update Users failed")
                }
            })
    }

    fun addIconId(id: Int) {
        if (id !in _selectedIconIds) {
            _selectedIconIds.add(id)
        }
    }

    fun removeIconId(id: Int) {
        _selectedIconIds.remove(id)
    }

    fun setInitialSelectedIcons(allergenIds: List<Int>) {
        _selectedIconIds.clear()
        _selectedIconIds.addAll(allergenIds)
    }
}
