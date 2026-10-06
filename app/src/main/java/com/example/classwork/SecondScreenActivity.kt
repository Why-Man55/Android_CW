package com.example.classwork

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

class SecondScreenActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val receivedName = intent.getStringExtra("TEXT_NAME") ?: getString(R.string.extra_error)
        val receivedGroup = intent.getStringExtra("TEXT_GROUP") ?: getString(R.string.extra_error)

//        enableEdgeToEdge()
        setContent {
            Text("$receivedName/$receivedGroup", Modifier.padding(top = 16.dp, start = 16.dp))
        }
    }
}