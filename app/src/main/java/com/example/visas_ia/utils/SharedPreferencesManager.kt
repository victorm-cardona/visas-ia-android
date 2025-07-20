package com.example.visas_ia.utils

import android.content.Context
import android.content.SharedPreferences

class SharedPreferencesManager(context: Context) {
    
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(
        PREF_NAME, Context.MODE_PRIVATE
    )
    
    companion object {
        private const val PREF_NAME = "VisasIAPrefs"
        private const val KEY_USER_LOGGED_IN = "user_logged_in"
        private const val KEY_USER_EMAIL = "user_email"
        private const val KEY_USER_NAME = "user_name"
        private const val KEY_USER_ID = "user_id"
    }
    
    fun saveUserSession(email: String, name: String) {
        val editor = sharedPreferences.edit()
        editor.putBoolean(KEY_USER_LOGGED_IN, true)
        editor.putString(KEY_USER_EMAIL, email)
        editor.putString(KEY_USER_NAME, name)
        editor.putString(KEY_USER_ID, email) // Usar email como ID temporal
        editor.apply()
    }
    
    fun isUserLoggedIn(): Boolean {
        return sharedPreferences.getBoolean(KEY_USER_LOGGED_IN, false)
    }
    
    fun getUserEmail(): String? {
        return sharedPreferences.getString(KEY_USER_EMAIL, null)
    }
    
    fun getUserName(): String? {
        return sharedPreferences.getString(KEY_USER_NAME, "Usuario")
    }
    
    fun getUserId(): String? {
        return sharedPreferences.getString(KEY_USER_ID, null)
    }
    
    fun clearUserSession() {
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }
} 