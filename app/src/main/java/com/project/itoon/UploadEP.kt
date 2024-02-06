package com.project.itoon

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UploadEP() {

    var title by remember {
        mutableStateOf("")
    }

    //Upload Mutiple Image
    var selectImages by remember { mutableStateOf(listOf<Uri>()) }
    val galleryLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetMultipleContents()) {
            selectImages = it
        }

    //Upload Single Image
    var selectImageCover by remember { mutableStateOf<Uri?>(null) }
    val galleryLauncher2 =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) {
            selectImageCover = it
        }

    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)) {
        //Upload Single Image
        Button(
            onClick = { galleryLauncher2.launch("image/*") },
            modifier = Modifier
                .wrapContentSize()
                .padding(10.dp)
                .height(100.dp)
                .background(
                    color = Color.Gray,
                    shape = RoundedCornerShape(8.dp),
                ),
            colors = ButtonDefaults.buttonColors(Color.Gray),
            border = BorderStroke(2.dp, Color.Black)
        ) {
            Text(
                text = "อัปโหลดรูปภาพ",
                color = Color.White,
                style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold)
            )
        }

        Column {
            Text(text = "ชื่อเรื่อง: เรื่องไรสักอย่าง")
        }
        Column {
            Text(text = "ชื่อตอน")
            Column {
                BasicTextField(value = title, onValueChange = {title=it},modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        border = BorderStroke(1.dp, color = Color.Red),
                        shape = RoundedCornerShape(10.dp)
                    )
                    .padding(20.dp))
            }
        }

        Column {
            Text(text = "อัปโหลดไฟล์")
            Button(
                onClick = { galleryLauncher.launch("image/*") },
                modifier = Modifier
                    .wrapContentSize()
                    .padding(10.dp)
                    .height(100.dp)
                    .width(400.dp)
                    .background(
                        color = Color.Gray,
                        shape = RoundedCornerShape(8.dp),
                    ),
                colors = ButtonDefaults.buttonColors(Color.Gray),
                border = BorderStroke(2.dp, Color.Black)
            ) {
                Text(
                    text = "อัปโหลดรูปภาพ",
                    color = Color.White,
                    style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold)
                )
            }
        }

        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
            Button(onClick = { /*TODO*/ }, modifier = Modifier
                .width(129.dp)
                .align(Alignment.CenterHorizontally), colors = ButtonDefaults.buttonColors(
                Color(184,0,0)
            )) {
                Text(text = "เผยแพร่", color = Color.White)
            }
        }
}
}