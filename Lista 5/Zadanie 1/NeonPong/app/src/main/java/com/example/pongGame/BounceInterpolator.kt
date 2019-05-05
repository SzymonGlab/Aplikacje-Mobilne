package com.example.pongGame

import android.view.animation.Interpolator

class BounceInterpolator(var aplitude : Double, var frequency : Double)  : Interpolator{

    override fun getInterpolation(time: Float): Float {
    return (-1 * Math.pow(Math.E, -time/ aplitude) * Math.cos(frequency * time) + 1).toFloat()
    }
}