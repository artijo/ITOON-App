package com.project.itoon.firstpageapi

sealed class FirstPageSealClass(
    val route : String,
    val name : String
){
    object FirstPage: FirstPageSealClass(route = "firstPage_screen", name="หน้าหลัก")
    object Genre: FirstPageSealClass(route = "genre_screen", name="ประเภท")


}
