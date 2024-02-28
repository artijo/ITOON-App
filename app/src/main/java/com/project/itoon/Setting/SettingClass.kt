package com.project.itoon.Setting

import com.project.itoon.LoginAndSignUp.PageITOON
import java.util.Objects

sealed class SettingClass(val route : String,val name:String) {
    object Setting : SettingClass("Setting_page","ตั้งค่า")
}


