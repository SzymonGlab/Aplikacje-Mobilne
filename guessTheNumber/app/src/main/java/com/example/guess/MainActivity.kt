package com.example.guess

import android.annotation.SuppressLint
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    private var r = Random()
    private var currentRand = 0
    private var userNumber = 0
    private var pointsCounter = 0
    private var attemptsCounter = 0
    private var record = 0
    private var winFlag = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        newRandomNumber()
    }

    fun newRandomNumber() {
        currentRand = r.nextInt(100)
        updateCounters()
    }

    fun checkGuess() {

        var sign = ""

        when {
            userNumber == currentRand -> {
                sign = "="
                goodGuess()
            }
            userNumber > currentRand -> {
                sign = ">"
                badGuess()
            }
            userNumber < currentRand -> {
                sign = "<"
                badGuess()
            }
        }

        findViewById<TextView>(R.id.sign).text = sign
    }

    fun buttonClick(view: View) {

        if (winFlag) {
            winFlag = false
            findViewById<Button>(R.id.bGuess).text = "Guess!"
            findViewById<TextView>(R.id.randNumb).text = "?"
            restart()
        } else if (checkUserInput()) {
            checkGuess()
            updateCounters()
        }
    }

    fun updateCounters() {
        findViewById<TextView>(R.id.points).text = "Points: $pointsCounter"
        findViewById<TextView>(R.id.attempts).text = "Attempt No.: $attemptsCounter"
        findViewById<TextView>(R.id.record).text = "Best guess: $record"
    }

    fun checkUserInput(): Boolean {

        if(findViewById<EditText>(R.id.userInput).text.toString() == ""){
            Toast.makeText(this, "Insert a number!", Toast.LENGTH_LONG).show()
            return false
        }

        userNumber = findViewById<EditText>(R.id.userInput).text.toString().toInt()
        if (userNumber > 100 || userNumber < 0) {
            Toast.makeText(this, "Input number from [0,100]", Toast.LENGTH_LONG).show()
            return false
        }
        return true
    }

    fun resetAttemptsNumber() {
        if (attemptsCounter < record || record == 0) {
            record = attemptsCounter
        }
        attemptsCounter = 0
    }

    fun goodGuess() {

        winFlag = true
        pointsCounter++
        attemptsCounter++

        findViewById<TextView>(R.id.title).text = "Congratulations!"
        findViewById<TextView>(R.id.randNumb).text = "$currentRand"
        findViewById<Button>(R.id.bGuess).text = "Let's get another point!"
    }

    fun badGuess() {
        findViewById<TextView>(R.id.title).text = "Try again ..."
        attemptsCounter++
    }

    fun restart() {
        resetAttemptsNumber()
        findViewById<TextView>(R.id.title).text = "Take a guess ..."
        findViewById<TextView>(R.id.userInput).text = ""
        newRandomNumber()
    }
}
