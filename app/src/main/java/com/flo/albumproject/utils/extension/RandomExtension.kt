package com.flo.albumproject.utils.extension

import android.graphics.Color
import kotlin.random.Random

fun Random.generateColor(): Int {
    return Color.argb(255, nextInt(256), nextInt(256), nextInt(256))
}