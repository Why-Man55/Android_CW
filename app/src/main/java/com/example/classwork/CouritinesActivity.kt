package com.example.classwork

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.roundToInt
import kotlin.random.Random
import kotlin.time.Duration.Companion.milliseconds

class CouritinesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Screen()
        }
    }

    @Composable
    fun Screen(){
        var sliderPosition by remember { (mutableFloatStateOf(1f)) }
        var progress by remember {mutableFloatStateOf(0f)}

        val coroutineScope = rememberCoroutineScope()

        Column(verticalArrangement = Arrangement.Top) {
            LinearProgressIndicator(
                progress = { progress },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )

            Text("Количество корутинов")

            Slider (
                value = sliderPosition,
                onValueChange = {
                    sliderPosition = it.roundToInt().toFloat()
                },
                valueRange = 1f..10f,
                steps = 8
            )

            Button (
                onClick = {
                    progress = 0f
                    val n = sliderPosition.roundToInt()
                    coroutineScope.launch {
                        for (i in 1..n) {
                            letsWait()
                            progress = i.toFloat() / n
                        }
                    }
                }
            ) {
                Text("Последовательно")
            }

            Button (
                onClick = {
                    progress = 0f
                    val n = sliderPosition.roundToInt()
                    var completed = 0
                    for (i in 1..n) {
                        coroutineScope.launch {
                            delay(Random.nextLong(2000L, 5000L).milliseconds)
                            completed++
                            progress = completed.toFloat() / n
                        }
                    }

                }
            ) {
                Text("Параллельно")
            }

            Button (
                onClick = {
                    progress = 0f
                    coroutineScope.coroutineContext.cancelChildren()
                }
            ) {
                Text("Отменить")
            }
        }
    }

    suspend fun letsWait(){
        //IDE очень хотела чтобы я сделал так...
        delay(2000L.milliseconds)
    }
}