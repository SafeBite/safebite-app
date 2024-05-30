package com.celvine.deb.esail.bby.helper

import android.content.Context
import android.util.Log

class SessionManager(context: Context) {

    private val prefs = context.getSharedPreferences("my_prefs", Context.MODE_PRIVATE)

    fun saveAuthToken(token: String, expiryTime: Long) {
        val editor = prefs.edit()
        editor.putString("auth_token", token)
        editor.putLong("expiry_time", expiryTime)
        editor.apply()
        Log.d("SessionManager", "Saved Auth Token: $token, Expiry Time: $expiryTime")
    }

    fun saveRefreshToken(refreshToken: String) {
        val editor = prefs.edit()
        editor.putString("refresh_token", refreshToken)
        editor.apply()
        Log.d("SessionManager", "Saved Refresh Token: $refreshToken")
    }

    fun fetchAuthToken(): String? {
        val token = prefs.getString("auth_token", null)
        Log.d("SessionManager", "Fetched Auth Token: $token")
        return token
    }

    fun fetchRefreshToken(): String? {
        val refreshToken = prefs.getString("refresh_token", null)
        Log.d("SessionManager", "Fetched Refresh Token: $refreshToken")
        return refreshToken
    }

    fun fetchAccessTokenExpiry(): Long {
        val expiryTime = prefs.getLong("expiry_time", 0)
        Log.d("SessionManager", "Fetched Access Token Expiry Time: $expiryTime")
        return expiryTime
    }

    fun clearSession() {
        val editor = prefs.edit()
        editor.remove("auth_token")
        editor.remove("refresh_token")
        editor.remove("expiry_time")
        editor.apply()
        Log.d("SessionManager", "Session cleared")
    }
}





