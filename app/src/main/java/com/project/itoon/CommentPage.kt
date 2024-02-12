package com.project.itoon

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.project.itoon.ui.theme.ITOONTheme

@Preview(showBackground = true)
@Composable
fun CommentPreview() {
    ITOONTheme {
        CommentPage()
    }
}

@Composable
fun CommentPage(){
    var textFieldComment by remember{ mutableStateOf("") }
    Column {
        Row (
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ){
            Box(
                modifier = Modifier
                    .padding(start = 15.dp,top = 15.dp)
            ) {
                Image(
                    painter = painterResource(R.drawable.user),
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .clip(CircleShape)
                        .border(
                            width = 2.dp,
                            color = Color.Black,
                            shape = RoundedCornerShape(100.dp)
                        )
                        .size(50.dp)
                        .padding(top = 10.dp)
                )
            }
            Spacer(modifier = Modifier .padding(start = 25.dp, top = 100.dp))
            OutlinedTextField(
                value = textFieldComment,
                onValueChange = {textFieldComment = it},
                label = { Text(text = "Comment") },
                trailingIcon = { Icon(imageVector = Icons.Default.Edit,
                    contentDescription = null)},
                modifier = Modifier
                    .width(180.dp)
                    .height(60.dp)
                    .padding(top = 10.dp)
            )
        }
        Spacer(modifier = Modifier .padding(top = 50.dp))
        Row (
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ){
            Box(
                modifier = Modifier
                    .padding(start = 12.dp,top = 15.dp)
            ) {
                Image(
                    painter = painterResource(R.drawable.user),
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .clip(CircleShape)
                        .border(
                            width = 2.dp,
                            color = Color.Black,
                            shape = RoundedCornerShape(100.dp)
                        )
                        .size(50.dp)
                        .padding(top = 10.dp)
                )
            }
        }
    }
}