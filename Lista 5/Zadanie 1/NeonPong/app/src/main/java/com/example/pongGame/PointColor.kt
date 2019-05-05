package com.example.pongGame

import android.graphics.Paint

class PointsColor (val name : String) : Paint() {

    var pointsC = Paint()

    init {

        pointsC.textSize = 100f
        when (name) {
            "pink" -> pointsC.setARGB(150,255, 25, 151)
            "red" -> pointsC.setARGB(150,221, 8, 47)
            "yellow" -> pointsC.setARGB(150,255, 248, 58)
            "green" -> pointsC.setARGB(150,28, 255, 50)
            "cyan" -> pointsC.setARGB(150,65, 220, 244)
            "blue" -> pointsC.setARGB(150,55, 55, 242)
            "grey" -> pointsC.setARGB(150,160, 160, 160)
            "purple" -> pointsC.setARGB(150,60, 160, 160)
        }
    }
}