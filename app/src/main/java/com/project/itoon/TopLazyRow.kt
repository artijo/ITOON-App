package com.project.itoon

sealed class TopLazyRow(val route:String,val name:String) {
    object Recently:TopLazyRow(route = "recently_screen",name = "เมื่อเร็วๆนี้")

    object  Like:TopLazyRow(route = "like_screen", name = "รายการที่ถูกใจ")

    object Bought:TopLazyRow(route = "bought_screen", name = "ซื้อแล้ว")

    object Creator:TopLazyRow(route = "creator_screen", name = "ครีเอเตอร์")

    object Comment:TopLazyRow(route = "comment_screen", name = "คอมเมนต์")

}