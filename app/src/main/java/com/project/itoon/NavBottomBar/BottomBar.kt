package com.project.itoon.NavBottomBar

import com.project.itoon.R


sealed class BottomBar(val route:String,val name:String,val icon: Int){
    object Favorite: BottomBar(route = "favoritepage_screen", name = "เฉพาพคุณ", icon = R.drawable.favorite)
    object MyCartoon: BottomBar(route = "mycartoonpage_screen", name = "การ์ตูนของฉัน", icon = R.drawable.user)
    object Coin: BottomBar(route = "coinpage_screen", name = "เติมเหรียญ", icon = R.drawable.coin)
    object ETC: BottomBar(route = "etcpage_screen", name = "อื่นๆ", icon = R.drawable.etc)
    object FirstPage: BottomBar(route = "firstpage_screen", name = "หน้าหลัก", icon = R.drawable.logo)

    object SearchPage:BottomBar(route = "searchpage_screen" ,name ="ค้นหา", icon = R.drawable.search_interface_symbol)
    object RegcreatorPage:BottomBar(route = "regcreator_screen" ,name ="สมัครเป็น Creator", icon = R.drawable.writer)
}