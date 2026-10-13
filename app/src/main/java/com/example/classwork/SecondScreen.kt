package com.example.classwork

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

@Composable
fun CatInfoScreen(name: String, image: String, info: String) {

    Column {
        AsyncImage(model = image, contentDescription = "", Modifier.fillMaxWidth())

        Text(name, Modifier.padding(start = 32.dp, top = 16.dp, bottom = 16.dp ), fontSize = 24.sp, fontWeight = FontWeight.Bold)

        Text(info, Modifier.padding(8.dp), fontSize = 16.sp)
    }
}