package com.project.itoon.cartoonPage

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.project.itoon.ui.theme.ITOONTheme

@Composable
fun CartoonThisChapter(){
    Column(
        Modifier
            .fillMaxSize()
    ){

    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ITOONTheme {
        CartoonThisChapter()
    }
}
