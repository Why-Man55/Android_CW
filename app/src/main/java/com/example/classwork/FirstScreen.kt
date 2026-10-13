package com.example.classwork

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CatsScreen(onNavigateToCatInfo: (name: String, image: String, info: String) -> Unit) {

    val cats = listOf(Cat("Мейн-кун", "https://cataas.com/cat", "крупная, мощная кошка с густой шерстью и дружелюбным характером"),
        Cat("Британская короткошерстная", "https://cataas.com/cat", "спокойный, независимый питомец с плюшевой шерстью, отлично подходящий для квартиры"),
        Cat("Шотландская вислоухая", "https://cataas.com/cat", "ласковая кошка с характерными загнутыми вперед ушками и высоким интеллектом"),
        Cat("Рэгдолл", "https://cataas.com/cat", "невероятно мягкая и послушная кошка, которая полностью расслабляется на руках у хозяина"),
        Cat("Сибирская кошкаун", "https://cataas.com/cat", "сильная, энергичная и здоровая порода с пышной шерстью, которая часто считается гипоаллергенной"),
        Cat("Сфинкс", "https://cataas.com/cat", "умная, преданная и активная лысая кошка, любящая внимание и контакт с человеком"),
        Cat("Сиамская кошка", "https://cataas.com/cat", " грациозная, общительная и голосистая кошка с яркими голубыми глазами и окрасом колор-пойнт"),
        Cat("Русская голубая", "https://cataas.com/cat", "сдержанная, тихая и умная кошка с серебристо-голубой шерстью и зелеными глазами"),
        Cat("Персидская кошка", "https://cataas.com/cat", "спокойное длинношерстное животное с аристократичной и размеренной манерой поведения"),
        Cat("Абиссинская кошкан", "https://cataas.com/cat", " — активная, игривая и любознательная короткошерстная кошка с тикированным окрасом"))

    Column {
        Text("Подборка котиков:", color = Color.Magenta, fontSize = 24.sp)

        LazyColumn {
            items(cats) { cat ->
                Card(
                    modifier = Modifier
                        .padding(start = 8.dp, top = 16.dp)
                        .clickable {
                            onNavigateToCatInfo(cat.name, cat.image, cat.info)
                        }
                ) {
                    Text(text = cat.name, fontSize = 16.sp)
                }
            }
        }
    }
}