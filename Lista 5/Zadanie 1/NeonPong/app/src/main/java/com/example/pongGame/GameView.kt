package com.example.pongGame

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Resources
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView

class GameView(context: Context, attributeSet: AttributeSet) : SurfaceView(context, attributeSet),
    SurfaceHolder.Callback {

    private val thread: GameThread

    private var pinkC = PointsColor("pink")
    private var cyanC = PointsColor("cyan")

    private var ball: Ball? = null
    private var paddle1: Paddle? = null
    private var paddle2: Paddle? = null

    private var touchedX: Int = 0
    private var touchedY: Int = 0

    private var longestRally: Int = 0

    private var pointsColor1: Paint
    private var pointsColor2: Paint

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("gameInfo", Context.MODE_PRIVATE)

    private val editor: SharedPreferences.Editor = sharedPreferences.edit()


    private val screenWidth = Resources.getSystem().displayMetrics.widthPixels
    private val screenHeight = Resources.getSystem().displayMetrics.heightPixels

    init {
        holder.addCallback(this)
        thread = GameThread(holder, this)
        pointsColor1 = cyanC.pointsC
        pointsColor2 = pinkC.pointsC
    }


    override fun surfaceChanged(surfaceHolder: SurfaceHolder, i: Int, i1: Int, i2: Int) {}

    override fun surfaceDestroyed(holder: SurfaceHolder?) {
        editor.putInt("longestRally", longestRally)
        editor.putInt("ballX", ball!!.x)
        editor.putInt("ballY", ball!!.y)
        editor.putInt("ballVelocityX", ball!!.xVelocity)
        editor.putInt("ballVelocityY", ball!!.yVelocity)
        editor.putInt("currentRally", ball!!.currentRally)
        editor.putInt("p1position", paddle1!!.x)
        editor.putInt("p1points", paddle1!!.points)
        editor.putInt("p2position", paddle2!!.x)
        editor.putInt("p2points", paddle2!!.points)
        editor.apply()
        thread.setRunning(false)
        thread.join()
    }

    override fun surfaceCreated(holder: SurfaceHolder?) {

        ball = Ball(BitmapFactory.decodeResource(resources, R.drawable.bball))
        if (screenWidth > 1500) {
            paddle1 = Paddle(BitmapFactory.decodeResource(resources, R.drawable.bar_cyan2), 1)
            paddle2 = Paddle(BitmapFactory.decodeResource(resources, R.drawable.bar_purple2), 2)
        } else {
            paddle1 = Paddle(BitmapFactory.decodeResource(resources, R.drawable.bar_cyan3), 1)
            paddle2 = Paddle(BitmapFactory.decodeResource(resources, R.drawable.bar_purple3), 2)
        }

        longestRally = sharedPreferences.getInt("longestRally", 0)

        if (sharedPreferences.getBoolean("pause", false)) {
            loadGame()
        }

        thread.setRunning(true)
        thread.start()
    }

    private fun loadGame() {
        ball!!.x = sharedPreferences.getInt("ballX", 0)
        ball!!.y = sharedPreferences.getInt("ballY", 0)
        ball!!.xVelocity = sharedPreferences.getInt("ballVelocityX", 0)
        ball!!.yVelocity = sharedPreferences.getInt("ballVelocityY", 0)
        ball!!.currentRally = sharedPreferences.getInt("currentRally", 0)
        paddle1!!.points = sharedPreferences.getInt("p1points", 0)
        paddle1!!.x = sharedPreferences.getInt("p1position", 0)
        paddle2!!.points = sharedPreferences.getInt("p2points", 0)
        paddle2!!.x = sharedPreferences.getInt("p2position", 0)
    }

    fun update() {
        val paddle1Rect = Rect(paddle1!!.x, paddle1!!.y, paddle1!!.x + paddle1!!.width, paddle1!!.y + paddle1!!.height)
        val paddle2Rect = Rect(paddle2!!.x, paddle2!!.y, paddle2!!.x + paddle2!!.width, paddle2!!.y + paddle2!!.height)
        val ballRect = Rect(ball!!.x, ball!!.y, ball!!.x + ball!!.size, ball!!.y + ball!!.size)

        if (paddle1Rect.intersect(ballRect)) {
            ball!!.updateVelocity()
            ball!!.yVelocity = -ball!!.yVelocity
            ball!!.currentRally++
        } else if (paddle2Rect.intersect(ballRect)) {
            ball!!.updateVelocity()
            ball!!.yVelocity = -ball!!.yVelocity
            ball!!.currentRally++
        } else if (ball!!.y < 0) {
            paddle2!!.points++
            ball!!.reset(false)
        } else if (ball!!.y > screenHeight - ball!!.size) {
            paddle1!!.points++
            ball!!.reset(true)
        }

        if (ball!!.currentRally > longestRally) {
            longestRally = ball!!.currentRally
        }

        ball!!.update()
        paddle1!!.update()
        paddle2!!.update()
    }

    override fun draw(canvas: Canvas) {
        super.draw(canvas)
        ball!!.draw(canvas)
        paddle1!!.draw(canvas)
        paddle2!!.draw(canvas)

        drawGameInfo(canvas)
        holder.unlockCanvasAndPost(canvas)
    }

    private fun drawGameInfo(canvas: Canvas) {

        val net = Paint()
        net.setARGB(100, 255, 255, 255)
        net.strokeWidth = 15f

        canvas.save()
        canvas.rotate(180f, screenWidth / 2f, screenHeight / 2f)
        pointsColor1.textSize = 100f
        canvas.drawText(
            "${paddle1!!.points}",
            screenWidth - 0.15f * screenWidth,
            screenHeight / 2f + 0.1f * screenHeight,
            pointsColor1
        )
        pointsColor1.textSize = 50f
        canvas.drawText(
            "LONGEST RALLY: $longestRally",
            0.05f * screenWidth,
            screenHeight / 2f + 0.08f * screenHeight,
            pointsColor1
        )
        canvas.restore()
        canvas.drawLine(0f, screenHeight / 2f, screenWidth.toFloat(), screenHeight / 2f, net)
        pointsColor2.textSize = 100f
        canvas.drawText(
            "${paddle2!!.points}",
            screenWidth - 0.15f * screenWidth,
            screenHeight / 2f + 0.1f * screenHeight,
            pointsColor2
        )
        pointsColor2.textSize = 50f
        canvas.drawText(
            "LONGEST RALLY: $longestRally",
            0.05f * screenWidth,
            screenHeight / 2f + 0.08f * screenHeight,
            pointsColor2
        )
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {

        val pointerCount = event.pointerCount

        for (i in 0 until pointerCount) {

            touchedX = event.getX(i).toInt()
            touchedY = event.getY(i).toInt()

            if (touchedY < height / 2) {
                paddle1!!.x = touchedX - (paddle2!!.image.width / 2)
                paddle1!!.update()
            } else {
                paddle2!!.x = touchedX - (paddle1!!.image.width / 2)
                paddle2!!.update()
            }
        }
        return true
    }
}