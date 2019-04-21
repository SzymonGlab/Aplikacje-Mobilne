package com.example.todoer

import android.app.Activity
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.ImageButton
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_add_task.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class AddTask : AppCompatActivity() {

    lateinit var date : String
    var day = 0
    var month =0
    var year =0
    var group = "none"
    var hour = 0
    var minute = 0
    var priority = false
    val calendar = Calendar.getInstance()!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_task)


        year = calendar.get(Calendar.YEAR)
        month = calendar.get(Calendar.MONTH)+1
        day = calendar.get(Calendar.DAY_OF_MONTH)
        hour = calendar.get(Calendar.HOUR_OF_DAY)
        minute = calendar.get(Calendar.MINUTE)

        dateTF.setText(displayCorrectDate(day,month,year))
        timeTF.setText(displayCorrectTime(hour,minute))

        pickTimeButton.setOnClickListener{

            val timePicker = TimePickerDialog(this,TimePickerDialog.OnTimeSetListener { view, mHour, mMinute ->
                hour = mHour
                minute = mMinute
                timeTF.setText(displayCorrectTime(hour,minute))
            }, minute,hour, true)
            timePicker.show()
        }
        priorityB.setOnCheckedChangeListener { buttonView, isChecked ->
            priority = isChecked
        }
        pickDateButton.setOnClickListener {
            val dpd1 = DatePickerDialog(this, DatePickerDialog.OnDateSetListener{ view, mYear, mMonth, mDay ->
                day = mDay
                month = mMonth+1
                year = mYear
                dateTF.setText(displayCorrectDate(mDay,mMonth+1,mYear))
                }, year, month, day)
            dpd1.show()
        }
    }

    fun createTODO (view: View){

        if(group.isEmpty()){
            Toast.makeText(this@AddTask, "Select group!", Toast.LENGTH_SHORT).show()
            return
        }

        val todo = todoTF.text.toString()
        date =  dateTF.text.toString()
        val time = timeTF.text.toString()
        val listElement = ListElement(todo, group, priority, minute,hour,day,month,year, false)
        val intent = Intent(this, MainActivity::class.java)
        intent.apply { putExtra("ListItem", listElement)}
        setResult(Activity.RESULT_OK,intent)
        finish()
    }

    fun addGroup(view:View){
        val btn: ImageButton = findViewById(view.id)
        when (btn) {
            workGroup -> group = "work"
            chillGroup -> group = "chill"
            schoolGroup -> group = "school"
            homeGroup -> group = "home"
        }
    }

    fun todayClick(view : View){
        val today = LocalDate.now()
        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        val formatted = today.format(formatter);
        date = formatted.toString()
        dateTF.setText(date)
    }

    fun plusHourClick(view: View){
        hour++

        if(hour>23){
            hour =0
        }

        timeTF.setText(displayCorrectTime(hour,minute))
    }

    fun displayCorrectTime(hour : Int,minute : Int): String{

        var minuteString = minute.toString()
        var hourString = hour.toString()

        if(minute<10){
            minuteString = "0$minute"
        }
        when {
            hour<10 -> hourString = "0$hour"
            hour>23 -> {
                hourString = "00"
            }
        }
        return "$hourString:$minuteString"
    }

    fun displayCorrectDate(day : Int, month: Int, year : Int) : String{

        var dayString = day.toString()
        var monthString = month.toString()

        if(day<10){
            dayString = "0$dayString"
        }
        if(month<10){
            monthString = "0$month"
        }

        return "$dayString/$monthString/$year"
    }
}
