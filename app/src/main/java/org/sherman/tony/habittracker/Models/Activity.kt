package org.sherman.tony.habittracker.Models

class Activity() {
    var activityID: Int? = null
    var activityDate:Long? = null
    var activityHabitKey: Int? = null

    constructor(activityID:Int, activityDate:Long, activityHabitKey:Int): this() {
        this.activityID = activityID
        this.activityDate = activityDate
        this.activityHabitKey = activityHabitKey

    }

}