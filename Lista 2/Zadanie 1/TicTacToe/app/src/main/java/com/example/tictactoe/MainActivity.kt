package com.example.tictactoe

import android.content.res.Configuration
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity() {


    private var p1points = 0
    private var p2points = 0
    private var r = Random()
    private var p1move = false
    private var p2move = false
    private var win = false
    private var bot = false
    private val clickedButtons = ArrayList<Button>()
    private val fields = Array(5) { IntArray(5) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lPlayer1.text = "X's\n points"
        lPlayer2.text = "O's\n points"

        resetGame()
        chooseStartPlayer()
        displayTurn()

        switch1.setOnClickListener {
            p1move = true
            p2move = false
            clearFields()
            resetFieldsArray()
            displayTurn()
            bot = switch1.isChecked
        }
    }


    private fun chooseStartPlayer() {

        p1move = false
        p2move = false

        when {
            bot -> {
                p1move = true
                p2move = false
            }
            r.nextBoolean() -> p1move = true
            else -> p2move = true
        }
    }

    private fun updateButton(btn: Button) {
        if (p1move) {
            btn.text = "X"
        } else {
            btn.text = "O"
        }
    }

    fun bClick(view: View) {

        val btn: Button = findViewById(view.id)

        btn.isClickable = false
        updateMove(btn)
        updateFieldsArray(btn)
        clickedButtons.add(btn)

        if (checkTie() || checkWin(btn)) {
            if (checkWin(btn)) {
                updatePoints()
            } else if (checkTie()) {
                Toast.makeText(this, "It's a tie!", Toast.LENGTH_LONG).show()
            }
            win = true
            displayWin()
            blockFields()
            return
        }
        if (bot) {
            botMove(btn)
        } else {
            changeTurn()
        }
    }

    private fun botMove(btn: Button) {

        val x = getBtnX(btn)
        val y = getBtnY(btn)
        var ctr1 = 0
        var ctr2 = 0
        var ctr3 = 0
        var ctr4 = 0

        for (i in 0..3) {
            if (fields[x][i] != 0 && fields[x][i] == fields[x][i + 1]) {
                ctr1++
            }
            if (fields[x][i] != 0 && fields[i][y] == fields[i + 1][y]) {
                ctr2++
            }
        }
        for (i in 0..4) {
            if (fields[x][i] != 0 && fields[x][y] == fields[i][i]) {
                ctr3++
            }
            if (fields[x][i] != 0 && fields[x][y] == fields[4 - i][i]) {
                ctr4++
            }
        }

        var i = 0
        var btn: Button
        var block = false
        var randomX = r.nextInt(5)
        var randomY = r.nextInt(5)

        while (randomX < 0 || randomY < 0 || fields[randomX][randomY] == 1 || fields[randomX][randomY] == 2) {
            randomX = r.nextInt(5)
            randomY = r.nextInt(5)
        }

        btn = grid.getChildAt(randomX * 5 + randomY) as Button

        when {
            ctr1 > 2 -> {
                while ((fields[x][i] == 1 || fields[x][i] == 2) && i < 4) {
                    i++
                }
                if (fields[x][i] == 2 || fields[x][i] == 1) {
                    btn = grid.getChildAt(randomX * 5 + randomY) as Button
                } else {
                    btn = grid.getChildAt(5 * x + i) as Button
                    fields[x][i] = 2
                    block = true
                }
            }
            ctr2 > 2 -> {
                while ((fields[i][y] == 1 || fields[i][y] == 2) && i < 4) {
                    i++
                }

                if (fields[i][y] == 2 || fields[i][y] == 1) {
                    btn = grid.getChildAt(randomX * 5 + randomY) as Button
                } else {
                    btn = grid.getChildAt(5 * i + y) as Button
                    fields[i][y] = 2
                    block = true
                }

            }
            ctr3 > 3 -> {
                while ((fields[i][i] == 1 || fields[i][i] == 2) && i < 4) {
                    i++
                }
                if (fields[i][i] == 2 || fields[i][i] == 1) {
                    btn = grid.getChildAt(randomX * 5 + randomY) as Button
                } else {
                    btn = grid.getChildAt(5 * i + i) as Button
                    fields[i][i] = 2
                    block = true
                }
            }
            ctr4 > 3 -> {
                while ((1 == fields[4 - i][i] || 2 == fields[4 - i][i]) && i < 4) {
                    i++
                }
                if (fields[4 - i][i] == 2 || 1 == fields[4 - i][i]) {
                    btn = grid.getChildAt(randomX * 5 + randomY) as Button
                } else {
                    btn = grid.getChildAt(5 * i + i) as Button
                    fields[4 - i][i] = 2
                    block = true
                }
            }
        }

        if (!block) {
            fields[randomX][randomY] = 2
        }
        btn.text = "O"
        btn.isClickable = false

        if (checkWin(btn)) {
            win = true
            addBotPoint()
            displayWin()
            blockFields()
        } else if (checkTie()) {
            displayTie()
        }
    }

    private fun addBotPoint(){
        p2points++
        lplayer2Points.text = p2points.toString()
    }

    private fun displayTie() {
        win = true
        displayWin()
        Toast.makeText(this, "It's a tie!", Toast.LENGTH_LONG).show()
    }


    private fun checkTie(): Boolean {
        var ct = 0
        for (i in 0..4) {
            for (j in 0..4) {
                if (fields[i][j] == 1 || fields[i][j] == 2) {
                    ct++
                }
            }
        }
        if (ct == 25) {
            return true
        }
        return false
    }

    private fun blockFields() {
        for (i in 0..24) {
            val btn = grid.getChildAt(i) as Button
            btn.isClickable = false
        }
    }

    private fun updatePoints() {
        if (p1move) {
            p1points++
            lplayer1Points.text = p1points.toString()
        } else if (bot) {
            return
        } else {
            p2points++
            lplayer2Points.text = p2points.toString()
        }
    }

    private fun updateMove(btn: Button) {
        updateButton(btn)
        updateFieldsArray(btn)
    }

    private fun updateFieldsArray(btn: Button) {

        val x = getBtnX(btn)
        val y = getBtnY(btn)

        if (p1move) {
            fields[x][y] = 1
        } else
            fields[x][y] = 2
    }

    fun bReset(view: View) {

        if (win) {
            win = !win
            reset.text = "RESET"
            clearFields()
            resetFieldsArray()
        } else
            resetGame()
    }

    private fun resetGame() {

        clearFields()
        resetPoints()
        resetFieldsArray()
    }

    private fun resetPoints() {

        p1points = 0
        p2points = 0

        lplayer1Points.text = p1points.toString()
        lplayer2Points.text = p2points.toString()
    }

    private fun resetFieldsArray() {
        for (i in 0..4) {
            for (j in 0..4) {
                fields[i][j] = 0
            }
        }
    }

    private fun clearFields() {

        for (i in 0..24) {
            val btn = grid.getChildAt(i) as Button
            btn.isClickable = true
            btn.text = ""
        }
    }

    private fun displayTurn() {

        if (p1move) {
            lTurn.text = "X"
        } else
            lTurn.text = "O"
    }

    private fun displayWin() {
        reset.text = "PLAY AGAIN"
    }

    private fun checkWin(btn: Button): Boolean {

        val x = getBtnX(btn)
        val y = getBtnY(btn)

        var ctr1 = 0
        var ctr2 = 0
        var ctr3 = 0
        var ctr4 = 0

        for (i in 0..3) {

            if (fields[x][i] == fields[x][i + 1]) {
                ctr1++
            }
            if (fields[i][y] == fields[i + 1][y]) {
                ctr2++
            }
        }

        for (i in 0..4) {
            if (fields[x][y] == fields[i][i]) {
                ctr3++
            }
            if (fields[x][y] == fields[4 - i][i]) {
                ctr4++
            }
        }

        if (ctr1 == 4 || ctr2 == 4 || ctr3 == 5 || ctr4 == 5) {
            return true
        }

        return false
    }

    private fun getBtnX(btn: Button): Int {

        var button: Button
        var j: Int
        var k = 0
        for (i in 0..24) {
            j = i % 5
            button = grid.getChildAt(i) as Button
            if (button == btn) {
                return k
            }
            if (j == 4) {
                k++
            }
        }
        return -1
    }

    private fun getBtnY(btn: Button): Int {

        var button: Button
        var j: Int
        var k = 0
        for (i in 0..24) {
            j = i % 5
            button = grid.getChildAt(i) as Button
            if (button == btn) {
                return j
            }
            if (j == 4) {
                k++
            }
        }
        return -1
    }

    private fun changeTurn() {
        if (p1move) {
            lTurn.text = "O"
            p2move = true
            p1move = false
        } else {
            lTurn.text = "X"
            p1move = true
            p2move = false
        }
    }
}




