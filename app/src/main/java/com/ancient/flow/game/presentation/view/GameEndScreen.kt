package com.ancient.flow.game.presentation.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.ancient.flow.game.R
import com.ancient.flow.game.domain.Level
import com.ancient.flow.game.presentation.navigation.OutlinedText


@Composable
fun GameEndScreen(
    isWin: Boolean,
    levels: Level,
    onMenuClicked: () -> Unit,
    onNextLevel: () -> Unit,
    restartGame: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.background), // Замените на ваше фоновое изображение
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {

            Image(
                painter = painterResource(id = R.drawable.svet2), // Замените на ваше фоновое изображение
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                if (isWin) {
                    Image(
                        painter = painterResource(id = R.drawable.svet), // Замените на ваше фоновое изображение
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }


                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(0.dp)
                ) {


                    // Панель с результатами времени и очков
                    Box(
                        modifier = Modifier
                            .padding(10.dp),
                        contentAlignment = Alignment.Center
                    ) {

                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            OutlinedText(
                                text = if (isWin) {
                                    "CONGRATS"
                                } else {
                                    "DEFEAT"
                                },
                                outlineColor = Color(0xFFF3004D),
                                fillColor = Color.White,
                                fontSize = if (isWin) 70.sp else (80).sp
                            )


                            Spacer(modifier = Modifier.height(30.dp))
                            Box(
                                modifier = Modifier

                                    .padding(vertical = 0.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.rec), // Замените на ваш ресурс для кнопки
                                    contentDescription = null,

                                    )
                                OutlinedText(
                                    text = "Level ${levels.number}",
                                    outlineColor = Color.Black,
                                    fillColor = Color.White,
                                    fontSize = 50.sp
                                )
                            }
                            Spacer(modifier = Modifier.height(30.dp))

                            Box(
                                modifier = Modifier
                                    .size(130.dp, 50.dp)
                                    .clickable(
                                        onClick = {
                                            if (isWin) {
                                                onNextLevel(

                                                )
                                            } else {
                                                restartGame(

                                                )
                                            }
                                        }
                                    )
                                    .padding(bottom = 0.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.b_rec), // Фон для кнопки
                                    contentDescription = null,
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .clip(RoundedCornerShape(59.dp)),


                                    contentScale = ContentScale.FillWidth

                                )
                                OutlinedText(
                                    text = if (isWin) {
                                        "Next"
                                    } else {
                                        "Retry"
                                    },
                                    outlineColor = Color.Red,
                                    fillColor = Color.White,
                                    fontSize = 20.sp // Немного уменьшенный размер текста
                                )
                            }
                            Spacer(modifier = Modifier.height(30.dp))
                            Box(
                                modifier = Modifier
                                    .size(130.dp, 50.dp)
                                    .clickable(onClick = onMenuClicked)
                                    .padding(bottom = 0.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.b_rec), // Фон для кнопки
                                    contentDescription = null,
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .clip(RoundedCornerShape(59.dp)),


                                    contentScale = ContentScale.FillWidth

                                )
                                OutlinedText(
                                    text = "Back",
                                    outlineColor = Color.Red,
                                    fillColor = Color.White,
                                    fontSize = 20.sp // Немного уменьшенный размер текста
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

//@Preview
//@Composable
//fun GameEndScreenPreview() {
//    GameEndScreen(isWin = false, levels = Levels.fourth, onMenuClicked = {})
//}


//fun restartGame(navController: NavController, currentDifficulty: Levels) {
//    // Navigate to the game screen with the current difficulty level
//    navController.navigate("game_screen/${currentDifficulty.name}") {
//        // Pop the back stack to clear the previous instance of the game screen
//        popUpTo("game_screen/${currentDifficulty.name}") { inclusive = true }
//    }
//}
//
//fun onNextLevel(navController: NavController, currentDifficulty: Levels) {
//    // Navigate to the game screen with the next difficulty level
//    navController.navigate("game_screen/${currentDifficulty.ordinal + 1}") {
//        // Pop the back stack to clear the previous instance of the game screen
//        popUpTo("game_screen/${currentDifficulty.ordinal + 1}") { inclusive = true }
//    }
//}


