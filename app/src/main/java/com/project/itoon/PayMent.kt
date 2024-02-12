package com.project.itoon

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.project.itoon.ui.theme.ITOONTheme

@Preview(showBackground = true)
@Composable
fun PaymentPreview() {
    ITOONTheme {
        PaymentPage()
    }
}

@Composable
fun PaymentPage(){
    var showMasterCard by remember{ mutableStateOf(false) }
    var showPromtPay by remember{ mutableStateOf(false) }
    var textFieldCreditID by remember{ mutableStateOf("") }
    var textFieldName by remember{ mutableStateOf("") }
    var textFieldExpireDate by remember{ mutableStateOf("") }
    var textFieldCVV by remember{ mutableStateOf("") }
    Column (modifier = Modifier
        .fillMaxSize()
        .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                modifier = Modifier
                    .border(
                        width = 2.dp,
                        color = Color.Red,
                        shape = RoundedCornerShape(10.dp)
                    )
                    .padding(top = 25.dp, bottom = 25.dp, start = 100.dp, end = 100.dp),
                textAlign = TextAlign.Center,
                text = "เหรียญของฉัน : 20.00",
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 22.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Box (
                modifier = Modifier
                    .clickable(onClick = {
                        showMasterCard = true
                    })
            ){
                Column(
                    modifier = Modifier
                        .border(
                            width = 2.dp,
                            color = Color.Red,
                            shape = RoundedCornerShape(10.dp)
                        )
                ) {
                    Image(
                        painter = painterResource(R.drawable.mastercard),
                        contentDescription = null,
                        contentScale = ContentScale.FillHeight,
                        modifier = Modifier
                            .size(100.dp)
                    )
                    Text(text = "Master Card",
                        color = Color.Black,
                        fontSize = 15.sp,
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(top = 12.dp)
                    )
                }
            }
            Box {
                Column(
                    modifier = Modifier
                        .border(
                            width = 2.dp,
                            color = Color.Red,
                            shape = RoundedCornerShape(10.dp)
                        )
                ) {
                    Image(
                        painter = painterResource(R.drawable.wallet),
                        contentDescription = null,
                        contentScale = ContentScale.FillHeight,
                        modifier = Modifier
                            .size(100.dp)
                    )
                    Text(text = "True Wallet",
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(top = 12.dp))
                }
            }
            Box(
                modifier = Modifier
                    .clickable(onClick = {
                        showPromtPay = true
                    })
            ) {
                Column (
                    modifier = Modifier
                        .border(
                            width = 2.dp,
                            color = Color.Red,
                            shape = RoundedCornerShape(10.dp)
                        )
                ){
                    Image(
                        painter = painterResource(R.drawable.promtpay),
                        contentDescription = null,
                        contentScale = ContentScale.FillHeight,
                        modifier = Modifier
                            .size(100.dp)
                    )
                    Text(text = "Promt Pay",
                        color = Color.Black,
                        fontSize = 15.sp,
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(top = 12.dp)
                    )

                }
            }
            if (showMasterCard){
                AlertDialog(
                    onDismissRequest = { showMasterCard = false },
                    title = { Text(text = "MasterCard") },
                    text = {
                        Column {
                            OutlinedTextField(
                                value = textFieldCreditID,
                                onValueChange = {textFieldCreditID = it},
                                label = { Text(text = "Enter your Credit ID")},
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                            )
                            OutlinedTextField(
                                value = textFieldName,
                                onValueChange = { textFieldName = it},
                                label = { Text(text = "Enter your Name")}
                            )
                            OutlinedTextField(
                                value = textFieldExpireDate,
                                onValueChange = { textFieldExpireDate = it},
                                label = { Text(text = "Enter Expire Date")}
                            )
                            OutlinedTextField(
                                value = textFieldCVV,
                                onValueChange = {textFieldCVV = it},
                                label = { Text(text = "Enter CVV") },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                            )
                        }
                    },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                showMasterCard = false
                                textFieldCreditID = ""
                                textFieldName = ""
                                textFieldExpireDate = ""
                                textFieldCVV = ""
                            }
                        ) {
                            Text(text = "Save")
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = {
                            showMasterCard = false
                            textFieldCreditID = ""
                            textFieldName = ""
                            textFieldExpireDate = ""
                            textFieldCVV = ""}
                        ) {
                            Text(text = "Cancel")
                        }
                    }
                )
            }
            if (showPromtPay){
                AlertDialog(
                    onDismissRequest = { showPromtPay = false },
                    title = { Text(text = "PromtPay",
                        modifier = Modifier.padding(start = 60.dp))},
                    text = {
                        Column {
                            Image(painter = painterResource(R.drawable.promtpay),
                                contentDescription = null,
                                contentScale = ContentScale.Fit,
                                modifier = Modifier.size(250.dp))
                        }
                    },
                    confirmButton = {
                        TextButton(onClick = {showPromtPay = false}
                        ) {
                            Text(text = "เสร็จสิ้น")
                        }
                    },
                    dismissButton = {}
                )
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 22.dp)
        ) {
            Text(
                text = "กรุณาเลือกแพ็กเกจที่ต้องการเติมเงิน ผ่านช่องทาง \n" +
                        "'True Wallet'(1 บาท = 0.5 เหรียญ)",
                fontSize = 15.sp
            )
        }
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 22.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Column(
                modifier = Modifier
                    .border(
                        width = 2.dp,
                        color = Color.Red,
                        shape = RoundedCornerShape(10.dp)
                    )
                    .width(115.dp)
                    .height(100.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "10")
                    Spacer(modifier = Modifier.padding(start = 5.dp))
                    Icon(painter = painterResource(id = R.drawable.coin),
                        contentDescription = null,
                        tint = Color.Black,
                        modifier = Modifier
                            .size(15.dp))
                }
                    Text(
                        textAlign = TextAlign.Center,
                        text = "(20 บาท)",
                        fontWeight = FontWeight.Bold,
                        fontSize = 10.sp
                    )
            }
            Column(
                modifier = Modifier
                    .border(
                        width = 2.dp,
                        color = Color.Red,
                        shape = RoundedCornerShape(10.dp)
                    )
                    .width(115.dp)
                    .height(100.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "25")
                    Spacer(modifier = Modifier.padding(start = 5.dp))
                    Icon(painter = painterResource(id = R.drawable.coin),
                        contentDescription = null,
                        tint = Color.Black,
                        modifier = Modifier
                            .size(15.dp))
                }
                Text(
                    textAlign = TextAlign.Center,
                    text = "(50 บาท)",
                    fontWeight = FontWeight.Bold,
                    fontSize = 10.sp
                )
            }
            Column(
                modifier = Modifier
                    .border(
                        width = 2.dp,
                        color = Color.Red,
                        shape = RoundedCornerShape(10.dp)
                    )
                    .width(115.dp)
                    .height(100.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "50")
                    Spacer(modifier = Modifier.padding(start = 5.dp))
                    Icon(painter = painterResource(id = R.drawable.coin),
                        contentDescription = null,
                        tint = Color.Black,
                        modifier = Modifier
                            .size(15.dp))
                }
                Text(
                    textAlign = TextAlign.Center,
                    text = "(100 บาท)",
                    fontWeight = FontWeight.Bold,
                    fontSize = 10.sp
                )
            }
        }
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 22.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Column(
                modifier = Modifier
                    .border(
                        width = 2.dp,
                        color = Color.Red,
                        shape = RoundedCornerShape(10.dp)
                    )
                    .width(115.dp)
                    .height(100.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "150")
                    Spacer(modifier = Modifier.padding(start = 5.dp))
                    Icon(painter = painterResource(id = R.drawable.coin),
                        contentDescription = null,
                        tint = Color.Black,
                        modifier = Modifier
                            .size(15.dp))
                }
                Text(
                    textAlign = TextAlign.Center,
                    text = "(300 บาท)",
                    fontWeight = FontWeight.Bold,
                    fontSize = 10.sp
                )
            }
            Column(
                modifier = Modifier
                    .border(
                        width = 2.dp,
                        color = Color.Red,
                        shape = RoundedCornerShape(10.dp)
                    )
                    .width(115.dp)
                    .height(100.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "250")
                    Spacer(modifier = Modifier.padding(start = 5.dp))
                    Icon(painter = painterResource(id = R.drawable.coin),
                        contentDescription = null,
                        tint = Color.Black,
                        modifier = Modifier
                            .size(15.dp))
                }
                Text(
                    textAlign = TextAlign.Center,
                    text = "(500 บาท)",
                    fontWeight = FontWeight.Bold,
                    fontSize = 10.sp
                )
            }
            Column(
                modifier = Modifier
                    .border(
                        width = 2.dp,
                        color = Color.Red,
                        shape = RoundedCornerShape(10.dp)
                    )
                    .width(115.dp)
                    .height(100.dp)
            ){
                Text(
                    modifier = Modifier
                        .padding(top = 37.dp,start = 22.dp),
                    textAlign = TextAlign.Center,
                    text = "กำหนดเอง",
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp
                )
            }
        }
        Column(
            modifier = Modifier
                .padding(top = 100.dp),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(onClick = {

            },
                colors = ButtonDefaults.buttonColors(Color(80, 200, 120))) {
                Text(
                    modifier = Modifier
                        .width(100.dp)
                        .height(45.dp)
                        .padding(top = 10.dp),
                    textAlign = TextAlign.Center,
                    text = "ยืนยัน",
                    fontWeight = FontWeight.Bold,
                    fontSize = 17.sp
                )
            }
        }
    }
}