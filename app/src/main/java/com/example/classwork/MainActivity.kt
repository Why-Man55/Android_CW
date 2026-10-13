package com.example.classwork

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppNav()
        }
    }
}

@Composable
fun AppNav() {
    val navController = rememberNavController()

    NavHost (
        navController = navController,
        startDestination = First
    ) {
        composable<First> {
            FirstScreen (
                onNavigateToSecondScreen = { email, password ->
                    navController.navigate(Second(email = email, password = password))
                }
            )
        }

        composable<Second> { entry ->
            val args = entry.toRoute<Second>()
            SecondScreen (
                email = args.email,
                password = args.password,
                onBack = {navController.popBackStack()}
            )
        }
    }
}