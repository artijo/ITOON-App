package com.project.itoon

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

data class OptionsList(val name:String,val option: String)

private fun prepareOptionsList(): MutableList<OptionsList>{
    val optionsList = mutableListOf<OptionsList>()
    optionsList.add(OptionsList(name = "เมื่อเร็วๆนี้", option = "Recently"))
    optionsList.add(OptionsList(name = "รายการที่กดถูกใจ", option = "Favorite"))
    optionsList.add(OptionsList(name = "ซื้อแล้ว", option = "Bought"))
    optionsList.add(OptionsList(name = "ครีเอเตอร์", option = "Creator"))
    optionsList.add(OptionsList(name = "คอมเมนท์", option = "Comment"))
    return optionsList
}


@Composable
private fun ItemLayout(optionsList: OptionsList,context: Context= LocalContext.current.applicationContext){
    Card(shape = RoundedCornerShape(size = 12.dp), elevation = CardDefaults.cardElevation(
        defaultElevation = 4.dp
    )) {
        Box (modifier = Modifier
            .fillMaxSize()
            .clickable {
                Toast
                    .makeText(context, optionsList.option, Toast.LENGTH_SHORT)
                    .show()
            }
            .padding(8.dp)){
            Text(text = "${optionsList.name}", modifier = Modifier.size(size = 36.dp))
        }
    }
}

@Composable
fun MyTopLazyRow(){
    val optionsList = prepareOptionsList()
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(space = 12.dp), 
        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 12.dp)){

    }
}