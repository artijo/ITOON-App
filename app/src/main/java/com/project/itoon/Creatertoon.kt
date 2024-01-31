package com.project.itoon

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter

data class Creatertoon(
    var name : String,
    var image : String,
    var creater : String
)

private fun CreatercartoonList(): MutableList<Creatertoon>{
    val cartoonlist = mutableListOf<Creatertoon>()
    cartoonlist.add(Creatertoon("หัวหน้าคิม","https://swebtoon-phinf.pstatic.net/20211221_30/1640093928915giUxr_JPEG/0M_details.jpg?type=crop540_540","Artijo"))
    cartoonlist.add(Creatertoon("หัวหน้าคิม","https://swebtoon-phinf.pstatic.net/20211221_30/1640093928915giUxr_JPEG/0M_details.jpg?type=crop540_540","Artijo"))
    cartoonlist.add(Creatertoon("หัวหน้าคิม","https://swebtoon-phinf.pstatic.net/20211221_30/1640093928915giUxr_JPEG/0M_details.jpg?type=crop540_540","Artijo"))
    return cartoonlist
}
@Composable
private fun Itemcolumn(
    cartoonlist:Creatertoon,
    context: Context =LocalContext.current.applicationContext
){
   Card(modifier = Modifier
       .padding(horizontal = 5.dp, vertical = 8.dp)
       .fillMaxWidth()
       .height(120.dp),
       colors = CardDefaults.cardColors(containerColor = Color.White),
       elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
       shape = RoundedCornerShape(corner = CornerSize(10.dp)),
   ) {
Row(
    Modifier
        .fillMaxWidth()
//        .fillMaxHeight()
        .height((Dp(120f)))
        .padding(10.dp)
) {
Image(painter = rememberAsyncImagePainter(cartoonlist.image),
    contentDescription = cartoonlist.name,
    modifier = Modifier
        .width(150.dp)
        .height(150.dp)
    )

    Text(text = "${cartoonlist.name}\n" + "${cartoonlist.creater}", fontSize = 15.sp,modifier = Modifier.weight(1f))
    IconButton(onClick = { /*TODO*/ },
    modifier = Modifier.padding(start = 20.dp),
    ) {
       Icon(imageVector = Icons.Default.Cancel, contentDescription = null )
    }
}

   }

}

@Composable
fun MyLazyLayout(){
    val cartoonlist = CreatercartoonList()
    Column(
        modifier = Modifier
            .border(5.dp, Color.Black)
            .fillMaxWidth()
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,

    ){
        Column (
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center){
            Text(text = "การ์ตูนของฉัน",Modifier.padding(top = 30.dp), fontWeight = FontWeight.Bold)
            LazyColumn(verticalArrangement = Arrangement.spacedBy(space = 24.dp),
                contentPadding = PaddingValues(all = 22.dp)
            ){
                items(cartoonlist){
                        item-> Itemcolumn(cartoonlist = item)
                }
            }
        }


    }
}

