package com.project.itoon.Setting

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class SharedPreferencesManager(context: Context) {
    companion object{
        private const val KEY_IS_LOGGED_IN ="is_logged_in"
        private const val KEY_USER_EMAIL ="user_email"
    }
    private val preferences: SharedPreferences =
        context.getSharedPreferences("MyPreferences",Context.MODE_PRIVATE)
    var isLoggedIn:Boolean
        get() = preferences.getBoolean(KEY_IS_LOGGED_IN,false)
        set(value) = preferences.edit().putBoolean(KEY_IS_LOGGED_IN,value).apply()

    var userEmail : String?
        get() = preferences.getString(KEY_USER_EMAIL,null)
        set(value) = preferences.edit().putString(KEY_USER_EMAIL,value).apply()
    fun clearUserAll(){
        preferences.edit { remove(KEY_IS_LOGGED_IN) }
        preferences.edit{remove(KEY_USER_EMAIL)}
    }
    fun clearUserlogin(){
        preferences.edit{remove(KEY_IS_LOGGED_IN)}
    }

}