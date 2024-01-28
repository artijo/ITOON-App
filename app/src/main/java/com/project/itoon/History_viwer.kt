package com.project.itoon

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


data class Menurow(val menu : String)
private fun pepareMunuRow():MutableList<Menurow>{
    val op = mutableListOf<Menurow>()
    op.add(Menurow(menu="เมื่อเร็วๆนี้"))
    op.add(Menurow(menu="รายการที่ถูกใจ"))
    op.add(Menurow(menu="ซื้อแล้ว"))
    op.add(Menurow(menu="ครีเอเตอร์"))
    op.add(Menurow(menu="คอมเมนท์"))
    return op
}
@Composable
fun MenuLazyrow(){
    val op = pepareMunuRow()
    Row(
        modifier = Modifier
            .border(1.dp,Color.Gray)
            .fillMaxWidth()
    ) {
        LazyRow(horizontalArrangement = Arrangement.spacedBy(space = 12.dp),
            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 8.dp),
                    modifier = Modifier
                    .border(1.dp,Color.Gray)
            .fillMaxWidth()
        ){
            items(op){
                    item -> MenuLayout(op = item)
            }
        }
    }
    
}

@Composable
fun MenuLayout(
    op:Menurow
){
    Card(
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
    Row {
        Text(text = op.menu)
    }
    }

}

