package com.example.classwork

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun SecondScreen(email: String, password: String, onBack: () -> Unit) {
    val successUrl = stringResource(R.string.success_url)
    val errorUrl = stringResource(R.string.error_url)
    val clipboard = LocalClipboardManager.current
    var currentPassword by remember { mutableStateOf(PasswordRepository.secretPassword) }

    var isPasswordVisible by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (password == currentPassword) {
            AsyncImage(
                model = successUrl,
                contentDescription = "Success",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
            )
        } else {
            AsyncImage(
                model = errorUrl,
                contentDescription = "Error",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        TextField(
            value = currentPassword,
            onValueChange = { text ->
                currentPassword = text
                PasswordRepository.secretPassword = text
            },
            visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(
                onClick = { isPasswordVisible = !isPasswordVisible },
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = if (isPasswordVisible) stringResource(R.string.button_hide_text) else stringResource(R.string.button_show_text),
                    maxLines = 1
                )
            }

            Button(
                onClick = { onBack() },
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = stringResource(R.string.button_back),
                    maxLines = 1
                )
            }

            Button(
                onClick = { clipboard.setText(AnnotatedString(currentPassword)) },
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = stringResource(R.string.button_copy),
                    maxLines = 1
                )
            }
        }
    }
}

object PasswordRepository {
    var secretPassword = generatePassword()
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
