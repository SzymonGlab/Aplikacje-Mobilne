package com.example.hangman

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private var word = ""
    private val usedIndexes = mutableListOf<Int>()
    private val usedLetters = mutableListOf<String>()
    private val pictures = mutableListOf<Int>()
    private var letterIndexes = mutableListOf<Int>()
    private var pictureNumber = 0
    private var winFlag = false
    private var loseFlag = false
    private var words =  Array<String>(8) { "it = $it" }
    private var r = Random


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadPictures()
        resetPicture()
        words = resources.getStringArray(R.array.words)
        reset()
        Log.d("xD", word)
    }

    private fun loadPictures() {
        pictures.add(R.drawable.one)
        pictures.add(R.drawable.two)
        pictures.add(R.drawable.three)
        pictures.add(R.drawable.four)
        pictures.add(R.drawable.five)
        pictures.add(R.drawable.six)
        pictures.add(R.drawable.seven)
    }

    private fun wordDisplayer() {

        var newWord = ""

        for (i in 0 until word.length) {

            if (usedIndexes.contains(i)) {
                newWord += " " + word[i] + " "
                continue
            }

            if (letterIndexes.contains(i)) {
                usedIndexes.add(i)
                newWord += userInputField.text.toString()
            } else {
                newWord += " _ "
            }
        }

        wordTV.text = newWord
        resetLetterIndexes()
    }

    private fun resetLetterIndexes() {
        letterIndexes.clear()
    }

    private fun reset() {

        winFlag = false
        loseFlag = false
        resetLastGameFields()
        findNewWord()
        var underScores = ""
        for (i in 1..word.length) {
            underScores += " _ "
        }

        wordTV.text = underScores
    }

    private fun resetLastGameFields() {
        pictureNumber = 0
        resetPicture()
        resetUsedLetters()
        resetUsedIndexes()
        updateUsedLetters()
    }

    private fun resetUsedIndexes() {
        usedIndexes.clear()
    }

    private fun resetUsedLetters() {
        usedLetters.clear()
    }

    private fun findNewWord() {
        word = words[r.nextInt(8)]
        // words.random()
    }

    private fun resetPicture() {
        imageView.setImageResource(pictures[0])
    }

    private fun checkInput(): Boolean {

        val input = userInputField.text.toString()

        if (input == "") {
            Toast.makeText(this, "Choose a letter!", Toast.LENGTH_SHORT).show()
            return false
        }
                                                                                                                        //
        for(i in usedIndexes){
            if(word[i] == input.single()){
                Toast.makeText(this, "Choose other letter!", Toast.LENGTH_SHORT).show()
                return false
            }
        }

        if (word.contains(input, true)) {

            for (i in 0 until word.length) {
                if (word[i] == input.single()) {
                    letterIndexes.add(i)
                }
            }
        } else {
            nextPicture()
            usedLetters.add(userInputField.text.toString())
            updateUsedLetters()
        }
        return true
    }

    private fun nextPicture() {
        pictureNumber++
        if (pictureNumber == 7) {
            pictureNumber = 0
        }
        imageView.setImageResource(pictures[pictureNumber])
    }


    fun checkClick(view: View) {

        if (winFlag || loseFlag) {
            reset()
            changeButton()
            return
        }

        if (!checkInput()) {
            return
        }
        wordDisplayer()
        if (checkWin()) {
            winFlag = true
            displayWin()
        } else if(checkLose()){
            loseFlag = true
        }

        changeButton()
        clearInput()
    }

    private fun changeButton() {
        if (winFlag || loseFlag) {
            checkButton.text = "RESTART"
        } else {
            checkButton.text = "CHECK"
        }
    }

    private fun displayWin() {
        imageView.setImageResource(R.drawable.win)
    }

    private fun checkLose(): Boolean {

        if(pictureNumber==6){
            loseFlag = true
        }
    return false
    }


    private fun checkWin(): Boolean {
        for (i in 0 until word.length) {
            if (!usedIndexes.contains(i)) {
                return false
            }
        }
        return true
    }

    private fun clearInput() {
        userInputField.setText("")
    }

    private fun updateUsedLetters() {
        var letters = ""

        usedLetters.forEach { usedLetter -> letters += " $usedLetter" }
        findViewById<TextView>(R.id.usedLetters).text = letters
    }
}
