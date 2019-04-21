package com.example.todoer

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView

class MyArrayAdapter(context: Context, var data: ArrayList<ListElement>) :
    ArrayAdapter<ListElement>(context, R.layout.list_item, data) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view = convertView
        var backgroundColor: String

        if (view == null) {
            val inflater = LayoutInflater.from(context)
            view = inflater.inflate(R.layout.list_item, parent, false)
        }
        if (data[position].done) {
            backgroundColor = "#4cd370"

        } else {
            backgroundColor = "#e2bb1b"
        }
        view?.findViewById<LinearLayout>(R.id.list_element)?.setBackgroundColor(Color.parseColor(backgroundColor))

        view!!.findViewById<TextView>(R.id.title).text = data[position].TODO
        view.findViewById<TextView>(R.id.time).text = displayCorrectTime(data[position].hour, data[position].minute)
        view.findViewById<TextView>(R.id.date).text = displayCorrectDate(data[position].day, data[position].month, data[position].year)

        when {
            data[position].groupType == "work" -> view!!.findViewById<ImageView>(R.id.group).setImageResource(R.drawable.ic_work)
            data[position].groupType == "chill" -> view!!.findViewById<ImageView>(R.id.group).setImageResource(R.drawable.ic_chill)
            data[position].groupType == "home" -> view!!.findViewById<ImageView>(R.id.group).setImageResource(R.drawable.ic_home)
            data[position].groupType == "school" -> view!!.findViewById<ImageView>(R.id.group).setImageResource(R.drawable.ic_school)
            data[position].groupType == "none" -> view!!.findViewById<ImageView>(R.id.group).setImageResource(R.drawable.ic_none)
        }

        if (data[position].priority) {
            view.findViewById<ImageView>(R.id.priority).setImageResource(R.drawable.ic_priority)
        } else {
            view.findViewById<ImageView>(R.id.priority).setImageResource(0)
        }
        return view
    }

    fun displayCorrectTime(hour: Int, minute: Int): String {

        var minuteString = minute.toString()
        var hourString = hour.toString()

        if (minute < 10) {
            minuteString = "0$minute"
        }
        when {
            hour < 10 -> hourString = "0$hour"
            hour > 23 -> {
                hourString = "00"
            }
        }
        return "$hourString:$minuteString"
    }

    fun displayCorrectDate(day: Int, month: Int, year: Int): String {
        var dayString = day.toString()
        var monthString = month.toString()

        if (day < 10) {
            dayString = "0$dayString"
        }
        if (month < 10) {
            monthString = "0$month"
        }

        return "$dayString/$monthString/$year"
    }
}