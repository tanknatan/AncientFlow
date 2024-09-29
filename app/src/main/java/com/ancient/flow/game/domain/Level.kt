package com.ancient.flow.game.domain

enum class Level(val firstTimeLimit: Int, val secondTimeLimit: Int, val number: Int) {
    LEVEL_1(30, 35,1),
    LEVEL_2(30, 35,2),
    LEVEL_3(25, 30,3),
    LEVEL_4(25, 30,4),
    LEVEL_5(20, 25,5),
    LEVEL_6(20, 25,6),
    LEVEL_7(15, 20,7),
    LEVEL_8(15, 20,8),
    LEVEL_9(10, 15,9),
    LEVEL_10(10, 15,10),
    LEVEL_11(5, 10,11),
    LEVEL_12(5, 10,12);
}
