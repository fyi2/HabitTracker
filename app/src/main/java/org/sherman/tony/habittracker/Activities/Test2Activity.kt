package org.sherman.tony.habittracker.Activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import kotlinx.android.synthetic.main.activity_test2.*
import org.sherman.tony.habittracker.Data.HabitDataAdapter
import org.sherman.tony.habittracker.R

class Test2Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test2)

        val intent = this.intent
        val test = intent.getStringExtra("test")
        val readHabit = "readHabit"
        val readActivity = "readActivity"
        val readLatestActivity = "readLatestActivity"
        var db = HabitDataAdapter(applicationContext)

        when (test) {
            readHabit -> {
                val habit = db.readHabit("Test Habit")
                activityNameID.text = habit.habitName.toString()
                activityCompletedID.text = habit.habitID.toString()
                dateCompletedID.text = habit.habitActive.toString()
            }
            readActivity -> {
                val position = intent.getIntExtra("activityID", 1)
                val activity = db.readActivity(position)
                activityNameID.text = activity.activityID.toString()
                activityCompletedID.text = activity.activityHabitKey.toString()
                dateCompletedID.text = activity.activityDate.toString()
            }
            /*
            TODO

            readLatestActivity -> {
                val habitName = intent.getStringExtra("habitName")
                val activity = db.readLatestActivity(habitName)
                activityNameID.text = activity.activityID.toString()
                activityCompletedID.text = activity.activityHabitKey.toString()
                dateCompletedID.text = activity.activityDate.toString()
            } */
        }
    }

    fun gotoTesting(view: View) {
        val intent = Intent(this, TestActivity::class.java)
        startActivity(intent)
    }
}
