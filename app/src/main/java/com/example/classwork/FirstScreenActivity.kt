package com.example.classwork

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp

class FirstScreenActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DrawScreen()
        }
    }
    @Composable
    fun DrawScreen() {
        val context = LocalContext.current

        Column {
            val authorName = stringResource(R.string.author_name)
            val groupNumber = stringResource(R.string.group_number)

            Text(text = authorName, Modifier.padding(top = 64.dp, start = 8.dp,), fontFamily = FontFamily.Serif)
            Text(text = groupNumber, Modifier.padding(8.dp), fontFamily = FontFamily.Serif)

            Button(onClick = {
                val intent = Intent(context, SecondScreenActivity::class.java).apply {
                    putExtra("TEXT_NAME", authorName)
                    putExtra("TEXT_GROUP", groupNumber)
                }
                context.startActivity(intent)
            }, Modifier.padding(top = 16.dp), colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray)) {
                Text("To second screen", color = colorResource(R.color.custom_gray))
            }
        }
    }
}