package com.example.pongGame
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF

    class Paddle(var image: Bitmap, playerNumber: Int) {
        var x: Int = 0
        var y: Int = 0
        var width: Int = 0
        var height: Int = 0
        var points : Int = 0

        private val screenWidth = Resources.getSystem().displayMetrics.widthPixels
        private val screenHeight = Resources.getSystem().displayMetrics.heightPixels

        init {
            reset()
            x = screenWidth/2

            y = if(playerNumber == 1){
                25
            } else {
                screenHeight - 40
            }

            width = image.width
            height = image.height
        }

        fun draw(canvas: Canvas) {
            canvas.drawBitmap(image,x.toFloat(),y.toFloat(),null)
        }

        fun update() {
            if (x > screenWidth - image.width) {
                x = screenWidth - width
            } else if (x < 0){
                x = 0
            }

        }

        fun reset() {
            points = 0
        }
    }