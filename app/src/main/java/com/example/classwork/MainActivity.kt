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
        startDestination = Cats
    ) {
        composable<Cats> {
            CatsScreen (
                onNavigateToCatInfo = { name, image, info ->
                    navController.navigate(CatInfo(name, image, info))
                }
            )
        }

        composable<CatInfo> { entry ->
            val args = entry.toRoute<CatInfo>()
            CatInfoScreen (
                name = args.name,
                image = args.image,
                info = args.info)}
        }
    }
