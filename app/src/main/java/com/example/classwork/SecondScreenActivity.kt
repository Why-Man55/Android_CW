package com.example.classwork

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Text

class SecondScreenActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val receivedName = intent.getStringExtra("TEXT_NAME") ?: getString(R.string.extra_error)
        val receivedGroup = intent.getStringExtra("TEXT_GROUP") ?: getString(R.string.extra_error)

        enableEdgeToEdge()
        setContent {
            Text("$receivedName/$receivedGroup")
        }
    }
}