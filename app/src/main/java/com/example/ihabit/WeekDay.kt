package com.example.ihabit


class WeekDay(day: String,show:String) {

    val weekDay = day
    val show=show
    var isChoice = false


    fun reverseChoice() {
        if (isChoice) {
            isChoice = false
        }
       else if (!isChoice) {
            isChoice = true
        }
    }

}