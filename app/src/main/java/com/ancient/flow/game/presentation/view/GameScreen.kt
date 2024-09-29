package com.ancient.flow.game.presentation.view

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.paint
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.Color.Sphere.Challenge.gamecolor.data.SoundManager
import com.ancient.flow.game.R
import com.ancient.flow.game.domain.Level
import com.ancient.flow.game.presentation.navigation.OutlinedText
import com.ancient.flow.game.presentation.navigation.Screen
import kotlinx.coroutines.delay


@Composable
fun GameScreen(
    level: Level,
    onLevelSelect: () -> Unit,
    restartGame: () -> Unit,
    onNextLevel: () -> Unit,
    onNext: (Screen) -> Unit


) {
    // List of image resources for the squares
    val squareImages = listOf(
        R.drawable.square_1, // Replace with your drawable resources
        R.drawable.square_2,
        R.drawable.square_3,
        R.drawable.square_4,
        R.drawable.square_5,
        R.drawable.square_6,
        R.drawable.square_7,
        R.drawable.square_8,
        R.drawable.square_9
    )
    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences("LevelPreferences", Context.MODE_PRIVATE)
    val levelManager = LevelManager(sharedPreferences)
    // Track the original and shuffled states
    val originalImages by remember { mutableStateOf(generateRandomSquares(squareImages)) }
    var shuffledImages by remember { mutableStateOf(originalImages) }
    var isShuffled by remember { mutableStateOf(false) }
    var gameResult by remember { mutableStateOf("") }
    val gradient = Brush.horizontalGradient(
        colors = listOf(Color(0xFFE6DD44), Color(0xFFD55976)) // черный и фиолетовый цвет
    )
    // Track selected squares for swapping
    var selectedIndices by remember { mutableStateOf(listOf<Int>()) }

    // Timers
    var countdownTime by remember { mutableStateOf(level.firstTimeLimit) } // First timer of 5 seconds
    var secondTimerTime by remember { mutableStateOf(level.secondTimeLimit) } // Second timer of 10 seconds
    var isFirstTimerRunning by remember { mutableStateOf(true) }
    var isSecondTimerRunning by remember { mutableStateOf(false) }

    var gameEnd by remember { mutableStateOf(false) }
    var gameWon by remember { mutableStateOf(false) }

    // First countdown timer (starts immediately when the screen appears)
    LaunchedEffect(isFirstTimerRunning) {
        if (isFirstTimerRunning) {
            for (i in countdownTime downTo 1) {
                delay(1000L)
                countdownTime = i - 1
                if (!isFirstTimerRunning) break // If manual "Mix" is pressed, stop this timer
            }
            if (isFirstTimerRunning) {
                isFirstTimerRunning = false
                shuffledImages = originalImages.shuffled()
                isShuffled = true
                isSecondTimerRunning = true
            }
        }
    }

    // Second countdown timer (starts after Mix button is pressed)
    LaunchedEffect(isSecondTimerRunning) {
        if (isSecondTimerRunning) {
            for (i in secondTimerTime downTo 1) {
                delay(1000L)
                secondTimerTime = i - 1
            }
            isSecondTimerRunning = false
            if (shuffledImages != originalImages) {
                gameWon = false
                gameEnd = true
                gameWon = false
                gameResult = "You lost! Time ran out and the array is incorrect."
            }
        }
    }
    if (gameEnd || gameWon) {
        if (gameWon) levelManager.unlockNextLevel(level.ordinal + 1)
        GameEndScreen(
            isWin = gameWon,
            levels = level,
            onMenuClicked = onLevelSelect,
            restartGame = restartGame,
            onNextLevel = onNextLevel
        )
    } else {
        Box(
            modifier = Modifier
                .fillMaxSize()

        ) {
            // Фоновое изображение
            Image(
                painter = painterResource(id = R.drawable.background), // Замените на ваше фоновое изображение
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp, bottom = 0.dp, start = 16.dp, end = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.back),
                        contentDescription = "Back",
                        modifier = Modifier
                            .size(68.dp)
                            .clickable(

                                onClick = {
                                    SoundManager.playSound()
                                    onLevelSelect()
                                }
                            )
                    )

                    Image(
                        painter = painterResource(id = R.drawable.optionsbutton),
                        contentDescription = "Options",
                        modifier = Modifier
                            .size(68.dp)
                            .clickable(
                                onClick = {
                                    SoundManager.playSound()
                                    onNext(Screen.OptionScreen)
                                }
                            )
                    )
                }
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
                        text = "Level ${level.number}",
                        outlineColor = Color.Black,
                        fillColor = Color.White,
                        fontSize = 50.sp
                    )
                }

                Box(
                    modifier = Modifier
                        .size(450.dp),
                    contentAlignment = Alignment.Center
                ) {

                    Image(
                        painter = painterResource(id = R.drawable.gamebg), // Replace with your background resource
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.size(350.dp),

                        )
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CrossPattern(shuffledImages, selectedIndices, isShuffled) { index ->
                            // Handle square selection
                            if (isShuffled) {
                                if (selectedIndices.size < 2 && !selectedIndices.contains(index)) {
                                    selectedIndices = selectedIndices + index
                                }
                                if (selectedIndices.size == 2) {
                                    // Perform the swap
                                    shuffledImages =
                                        swapSquares(
                                            shuffledImages,
                                            selectedIndices[0],
                                            selectedIndices[1]
                                        )
                                    selectedIndices = listOf() // Clear the selection
                                }
                            }
                        }
                    }

                }

                // Cross pattern with 5 squares

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Slider(
                        value = if(isFirstTimerRunning){countdownTime.toFloat()} else secondTimerTime.toFloat(),
                        onValueChange = {},
                        valueRange = 0f..60f,
                        colors = SliderDefaults.colors(
                            thumbColor = Color.White,
                            activeTrackColor = Color.White, // убираем цвет стандартного трека
                            inactiveTrackColor = Color.Transparent // убираем цвет стандартного трека
                        ),
                        modifier = Modifier
                            //.weight(2f)
                            .drawBehind {
                                val trackWidth = size.width
                                val trackHeight = size.height / 4f
                                val topLeft = Offset(0f, center.y - trackHeight / 2f)
                                val size = Size(trackWidth, trackHeight)
                                drawRoundRect(
                                    brush = gradient,
                                    topLeft = topLeft,
                                    size = size,
                                    cornerRadius = CornerRadius(10f, 10f)
                                )
                            },  // Делаем слайдер шире
                    )


                }

                // Display timer or game result
//                if (isFirstTimerRunning) {
//
//                    TextButton(onClick = {
//                        // Manual "Mix" before the first timer ends
//                        isFirstTimerRunning = false
//                        shuffledImages = originalImages.shuffled()
//                        isShuffled = true
//                        isSecondTimerRunning = true
//                    }) {
//
//                    }
//                } else if (isSecondTimerRunning) {
//                    Text(text = "Finish in $secondTimerTime seconds...")
//                }
//                if (isShuffled) {
//                    TextButton(onClick = {
//                        if (shuffledImages == originalImages) {
//                            gameWon = true
//                            gameEnd = true
//
//                            isSecondTimerRunning = false
//                        } else {
//                            gameWon = false
//                            gameEnd = true
//
//                            isSecondTimerRunning = false
//                        }
//                    }) {
//
//                    }
//                }
                Box(
                    modifier = Modifier
                        .size(130.dp, 50.dp)
                        .clickable(
                            onClick = {
                                if (isFirstTimerRunning) {
                                    isFirstTimerRunning = false
                                    shuffledImages = originalImages.shuffled()
                                    isShuffled = true
                                    isSecondTimerRunning = true
                                } else {
                                    if (shuffledImages == originalImages) {
                                        gameWon = true
                                        gameEnd = true

                                        isSecondTimerRunning = false
                                    } else {
                                        gameWon = false
                                        gameEnd = true

                                        isSecondTimerRunning = false
                                    }
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
                        text = if (isFirstTimerRunning) {
                            "Mix"
                        } else {
                            "Finish"
                        },
                        outlineColor = Color.Red,
                        fillColor = Color.White,
                        fontSize = 20.sp // Немного уменьшенный размер текста
                    )
                }
            }
        }
    }
}

// Function to randomly select 5 squares from the list
fun generateRandomSquares(allImages: List<Int>): List<Int> {
    return allImages.shuffled().take(5)
}

// Function to swap two squares in the list
fun swapSquares(images: List<Int>, index1: Int, index2: Int): List<Int> {
    val mutableImages = images.toMutableList()
    val temp = mutableImages[index1]
    mutableImages[index1] = mutableImages[index2]
    mutableImages[index2] = temp
    return mutableImages.toList()
}

@Composable
fun CrossPattern(
    images: List<Int>,
    selectedIndices: List<Int>,
    isShuffled: Boolean,
    onSquareClick: (Int) -> Unit
) {
    Box(
        modifier = Modifier.size(300.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            SquareBox(
                imageRes = images[0],
                isSelected = selectedIndices.contains(0),
                isShuffled = isShuffled,
                onClick = { onSquareClick(0) }) // Top
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                SquareBox(
                    imageRes = images[1],
                    isSelected = selectedIndices.contains(1),
                    isShuffled = isShuffled,
                    onClick = { onSquareClick(1) }) // Left
                Spacer(modifier = Modifier.size(50.dp)) // Space in the middle
                SquareBox(
                    imageRes = images[2],
                    isSelected = selectedIndices.contains(2),
                    isShuffled = isShuffled,
                    onClick = { onSquareClick(2) }) // Right
            }
            SquareBox(
                imageRes = images[3],
                isSelected = selectedIndices.contains(3),
                isShuffled = isShuffled,
                onClick = { onSquareClick(3) }) // Bottom
        }
        SquareBox(
            imageRes = images[4],
            isSelected = selectedIndices.contains(4),
            isShuffled = isShuffled,
            onClick = { onSquareClick(4) },
            modifier = Modifier.align(Alignment.Center)
        ) // Center
    }
}

@Composable
fun SquareBox(
    imageRes: Int,
    isSelected: Boolean,
    isShuffled: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Image(
        painter = painterResource(id = imageRes),
        contentDescription = null,
        modifier = modifier
            .size(50.dp)
            .background(
                if (isSelected) Color.Gray else Color.Transparent,
                shape = RoundedCornerShape(12.dp)
            )
            .clickable(enabled = isShuffled) { onClick() }, // Disable clicking before shuffle
        contentScale = ContentScale.Crop
    )
}