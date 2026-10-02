package com.example.classwork

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext // ОБЯЗАТЕЛЬНО: для получения context в Compose
import androidx.compose.ui.res.stringResource

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

            Text(text = authorName)
            Text(text = groupNumber)

            Button(onClick = {
                val intent = Intent(context, SecondScreenActivity::class.java).apply {
                    putExtra("TEXT_NAME", authorName)
                    putExtra("TEXT_GROUP", groupNumber)
                }
                context.startActivity(intent)
            }) {
                Text("To second screen")
            }
        }
    }
}