package org.sherman.tony.habittracker.Activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.ToggleButton
import kotlinx.android.synthetic.main.activity_test.*
import org.sherman.tony.habittracker.Data.DEBUG
import org.sherman.tony.habittracker.Data.HabitDataAdapter
import org.sherman.tony.habittracker.Models.Activity
import org.sherman.tony.habittracker.Models.Habit
import org.sherman.tony.habittracker.R
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class TestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
    }

    fun generateActivities(view: View) {
    var db = HabitDataAdapter(applicationContext)
        db.createRandomActivities()
    }

    fun dumpActivities(view: View) {
        var db = HabitDataAdapter(applicationContext)
        val habitList = db.readHabits()
        for (i in 0..habitList.size - 1){
            var activitesList = db.readActivityList(habitList[i].habitName.toString())
            //Log.d(DEBUG,"for habit ${habitList[i].habitName.toString()} length of activities list is ${activitesList.size}")
            for (j in 0..activitesList.size - 1){
                Log.d(DEBUG,"J = $j and date is ${activitesList[j].activityDate!!.toLong()}")
            }
        }
    }

    fun addHabitRecord(view: View) {
        var db = HabitDataAdapter(applicationContext)

        val today = Calendar.getInstance().timeInMillis
        val habit = Habit()
        habit.habitID = 0
        habit.habitName = "Test Habit"
        habit.habitActive = 1

        db.createHabit(habit.habitName.toString())
    }

    fun readHabitRecord(view: View) {
        var db = HabitDataAdapter(applicationContext)
        var habit = Habit()

        habit = db.readHabit("Test Habit")
        val intent = Intent(applicationContext, Test2Activity::class.java)
        intent.putExtra("test", "readHabit")
        startActivity(intent)
        finish()
    }

    fun readLatestActivity(view: View) {
        var db = HabitDataAdapter(applicationContext)
        val habitName = "Test Habit"

        val intent = Intent(applicationContext, Test2Activity::class.java)
        intent.putExtra("test", "readLatestActivity")
        intent.putExtra("habitName", habitName)
        startActivity(intent)
        finish()
    }

    fun writeActivity(view: View) {
        var db = HabitDataAdapter(applicationContext)
        var activity = Activity()
        val today: Long = Calendar.getInstance().timeInMillis

        activity.activityHabitKey = 1
        activity.activityDate = today

        db.createActivityObj(activity)
    }

    fun readActivity(view: View) {
        val id = 1
        var intent = Intent(applicationContext, Test2Activity::class.java)
        intent.putExtra("test", "readActivity")
        intent.putExtra("activityID", id)
        startActivity(intent)
        finish()
    }

    fun resetDatabase(view: View) {
        var db = HabitDataAdapter(applicationContext)
        var button: ToggleButton = toggleButton
        var switchState: Boolean = button.isChecked

        if (switchState) {
            Log.d(DEBUG, "Toggle is on.")
        }
    }

    fun readRecord(view: View) {
        var bundle = Bundle()
        val today: Long = Calendar.getInstance().timeInMillis
        Log.d(DEBUG, today.toString())
        val habit = Habit()
        habit.habitID = 0
        habit.habitName = "Test Habit"
        habit.habitActive = 1

        bundle.putInt("done", habit.habitID!!)
        bundle.putString("name", habit.habitName)
        bundle.putInt("date", habit.habitActive!!)

        var intent = Intent(applicationContext, Test2Activity::class.java)
        intent.putExtra("bundle", bundle)
        startActivity(intent)
        finish()
    }

    fun returnHome(view: View) {
        val intent = Intent(applicationContext, MainActivity::class.java)
        startActivity(intent)
    }

    fun humanDate(epoch: Long) : String {

        var calendar: Calendar = Calendar.getInstance()

        val dateFormat: DateFormat = SimpleDateFormat("MM/dd/yyy")

        return dateFormat.format(epoch)
    }
}
