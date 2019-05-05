package com.example.pongGame

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Canvas
import android.util.Log
import kotlin.random.Random

class Ball(var image: Bitmap) {
    var x: Int = 0
    var y: Int = 0
    var size: Int = 0
    var xVelocity: Int = 0
    var yVelocity: Int = 0
    var currentRally = 0
    private val screenWidth = Resources.getSystem().displayMetrics.widthPixels
    private val screenHeight = Resources.getSystem().displayMetrics.heightPixels

    init {
        size = image.height
        reset(Random.nextBoolean())
    }

    fun draw(canvas: Canvas) {
        canvas.drawBitmap(image, x.toFloat(), y.toFloat(), null)
    }

    fun update() {

        x += (xVelocity)
        y += (yVelocity)

        if (x <= 0 || x + size >= screenWidth) {
            xVelocity = -xVelocity
        }
    }

    fun updateVelocity() {
        if (xVelocity < 0) {
            xVelocity -= 1
        } else {
            xVelocity += 1
        }

        if (yVelocity < 0) {
            yVelocity -= 1
        } else {
            yVelocity += 1
        }
    }

    fun reset(player1won: Boolean) {
        x = screenWidth / 2
        y = screenHeight / 2
        currentRally = 0

        if (player1won) {
            xVelocity = 10
            yVelocity = 10
        } else {
            xVelocity = -10
            yVelocity = -10
        }
    }
}