package com.project.itoon

sealed class BottomBar(val route:String,val name:String,val icon: Int){
    object Favorite: BottomBar(route = "favoritepage_screen", name = "เฉพาพคุณ", icon = R.drawable.favorite)
    object MyCartoon: BottomBar(route = "mycartoonpage_screen", name = "การ์ตูนของฉัน", icon = R.drawable.user)
    object Coin: BottomBar(route = "coinpage_screen", name = "เติมเหรียญ", icon = R.drawable.coin)
    object ETC: BottomBar(route = "etcpage_screen", name = "อื่นๆ", icon = R.drawable.etc)
}