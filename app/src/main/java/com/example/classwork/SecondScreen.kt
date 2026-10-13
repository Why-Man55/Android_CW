package com.example.classwork

import android.content.ClipData
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.ClipEntry
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import coil.compose.AsyncImage
import kotlinx.coroutines.launch

@Composable
fun SecondScreen(email:String, password: String, onBack: () -> Unit) {
    val successUrl = ""
    val errorUrl = ""

    val clipboard = LocalClipboardManager.current
    val currentPassword = generatePassword()

    var text by remember { mutableStateOf("") }
    var showText by remember { mutableStateOf(false) }
    Column() {
        if (password == currentPassword){
            AsyncImage(
                model = successUrl,
                contentDescription = stringResource(R.string.image_success_login)
            )
        } else {
            AsyncImage(model = errorUrl,
                contentDescription = stringResource(R.string.image_error_login)
            )
        }

        TextField (
            value = text,
            onValueChange = {
                text = if (showText) {
                    it
                } else {
                    "*".repeat(it.length)
                }
            }
        )

        Row {
            Button(onClick = {
                showText = !showText
            }) {
                Text(stringResource(R.string.button_show_text))
            }

            Button(onClick = {onBack()}) {
                Text(stringResource(R.string.button_back))
            }

            Button(onClick = {
                clipboard.setText(AnnotatedString(text))
            }) {
                Text(stringResource(R.string.button_copy))
            }

        }
    }
}

fun generatePassword(): String {
    val letters = ('a'..'z') + ('A'..'Z')
    val digits = ('0'..'9')
    val allChars = letters + digits

    val password = mutableListOf(
        letters.random(),
        digits.random()
    )

    repeat(6) {
        password.add(allChars.random())
    }

    return password.shuffled().joinToString("")
}