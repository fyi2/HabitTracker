package org.sherman.tony.habittracker.Models



class Habit() {
    var habitName:String? = null
    var habitID:Int? = null
    var habitActive:Int?=null


    constructor(habitName: String, habitID:Int,habitActive:Int): this() {
        this.habitName = habitName
        this.habitID = habitID
        this.habitActive = habitActive
    }
}