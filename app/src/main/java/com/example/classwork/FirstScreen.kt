package com.example.classwork

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp

@Composable
fun FirstScreen(onNavigateToSecondScreen: (email: String, password: String) -> Unit) {

    var emailText by remember { mutableStateOf("") }
    var passwordText by remember { mutableStateOf("") }
    var isEmailEmpty by remember { mutableStateOf(false) }
    var isPasswordEmpty by remember { mutableStateOf(false) }
    var isTestsGood by remember {mutableStateOf(false)}

    val validation = {
        isEmailEmpty = emailText.isEmpty()
        isPasswordEmpty = passwordText.isEmpty()
        isTestsGood = (emailText.matches(("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$").toRegex()) &&
                passwordText.length >= 8 && passwordText.contains(""".*[a-zA-Z].*""") && passwordText.contains(""".*[0-9].*"""))

        if (isEmailEmpty && isPasswordEmpty && isTestsGood) {
            onNavigateToSecondScreen(emailText, passwordText)
        }
    }

    Column() {
        if (isEmailEmpty) {
            Text(text = stringResource(R.string.email_error), color = androidx.compose.ui.graphics.Color.Red)
        }

        TextField(
            value = emailText,
            onValueChange = { emailText = it},
            label = {Text(stringResource(R.string.email_placeholder))},
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            modifier = Modifier.padding(bottom = 16.dp, start = 16.dp)
        )
        if (isPasswordEmpty) {
            Text(text = stringResource(R.string.email_error), color = androidx.compose.ui.graphics.Color.Red)
        }

        TextField(
            value = "*".repeat(passwordText.length),
            onValueChange = { passwordText = it},
            label = {Text(stringResource(R.string.password_placeholder))},
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(
                onDone = { validation() }
            ),
            modifier = Modifier.padding(bottom = 16.dp)
        )

        if (!isTestsGood) {
            Text(text = stringResource(R.string.button_tests_error), color = androidx.compose.ui.graphics.Color.Red)
        }

        Button(onClick = {
            validation()
         },) {
            Text(stringResource(R.string.button_enter))
        }
    }
}