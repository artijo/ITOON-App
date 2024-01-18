package com.project.itoon

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun UploadForm(){
    var title by remember {
        mutableStateOf("")
    }
    var description by remember {
        mutableStateOf("")
    }
    var file by remember {
        mutableStateOf("")
    }
    var cat1 by remember {
        mutableStateOf("")
    }
    var cat2 by remember {
        mutableStateOf("")
    }
    var selectImages by remember { mutableStateOf(listOf<Uri>()) }
    val galleryLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetMultipleContents()) {
            selectImages = it
        }
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)) {
        Column {
            Button(
                onClick = { galleryLauncher.launch("image/*") },
                modifier = Modifier
                    .wrapContentSize()
                    .padding(10.dp)
            ) {
                Text(text = "Pick Image From Gallery")
            }
        }
        Text(text = "ประเภท")
        Column {
            Row {
                cat1 = DropDownCatgory()
                Spacer(modifier = Modifier.width(25.dp))
                cat2 = DropDownCatgory()
            }
        }
        Text(text = "ชื่อเรื่อง")
        Column {
            BasicTextField(value = title, onValueChange = {title=it},modifier = Modifier
                .fillMaxWidth()
                .border(
                    border = BorderStroke(1.dp, color = Color.Red),
                    shape = RoundedCornerShape(10.dp)
                )
                .padding(20.dp))
        }
        Text(text = "เรื่องย่อ")
        Column {
            BasicTextField(
                value = description,
                onValueChange = { description = it }, modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .border(
                        border = BorderStroke(1.dp, color = Color.Red),
                        shape = RoundedCornerShape(10.dp)
                    )
            )
        }
        Text(text = "อีเมล")
        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
            Button(onClick = { /*TODO*/ }, modifier = Modifier
                .width(129.dp)
                .align(Alignment.CenterHorizontally), colors = ButtonDefaults.buttonColors(
                Color(184,0,0)
            )) {
                Text(text = "ต่อไป", color = Color.White)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropDownCatgory():String{
    val catgoryList = listOf(
        "เลือกประเภท",
        "ดราม่า",
        "แฟนตาซี",
        "โรแมนติก",
        "ย้อนเวลา"
    )
    var expanded by remember { mutableStateOf(false) }
    var catselect by remember {
        mutableStateOf(catgoryList[0])
    }
    val keyboardController = LocalSoftwareKeyboardController.current
    ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = {expanded = !expanded}, modifier = Modifier.clickable { keyboardController?.hide() }) {
        OutlinedTextField(value = catselect, onValueChange = {}, textStyle = TextStyle.Default.copy(fontSize = 12.sp), readOnly = true, label = { Text(
            text = "ประเภท"
        )
        }, trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)}, colors = ExposedDropdownMenuDefaults.textFieldColors(),modifier = Modifier
            .width(150.dp)
            .menuAnchor()
            .clickable { keyboardController?.hide() })
        ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            catgoryList.forEach { selectionOption ->
                DropdownMenuItem(text = { Text(selectionOption) },
                    onClick = {
                        catselect = selectionOption
                        expanded = false
                    }, contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding)
            }
        }
    }
    return catselect
}

