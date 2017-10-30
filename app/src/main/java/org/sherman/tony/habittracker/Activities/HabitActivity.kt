package org.sherman.tony.habittracker.Activities

import android.content.DialogInterface
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.support.v4.content.ContextCompat.startActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_habit.*
import kotlinx.android.synthetic.main.habit_list_file.*
import kotlinx.android.synthetic.main.habit_pop_up.view.*
import org.sherman.tony.habittracker.Adapters.ListHabitsAdapter
import org.sherman.tony.habittracker.Data.DEBUG
import org.sherman.tony.habittracker.Data.EDIT_HABITS
import org.sherman.tony.habittracker.Data.HabitDataAdapter
import org.sherman.tony.habittracker.Models.Habit
import org.sherman.tony.habittracker.R

class HabitActivity : AppCompatActivity() {
    var adapter: ListHabitsAdapter? = null
    private var habitList: ArrayList<Habit>? = null
    private var habitListItems: ArrayList<Habit>? = null
    private var layoutManager: RecyclerView.LayoutManager? = null

    var dbHandler:HabitDataAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_habit)

        loadRecycler()
    }

    //TODO - Remove debug before production
    fun printList(list:ArrayList<Habit>){
        for (i in 0..list.size - 1){
            Log.d(DEBUG, "${list[i].habitName} has ID ${list[i].habitID}")
        }
    }

    fun addHabit(view: View) {
        var db = HabitDataAdapter(applicationContext)

        // pop up a dialog
        var promptView:View = layoutInflater.inflate(R.layout.habit_pop_up, null)
        var builder:AlertDialog.Builder =AlertDialog.Builder(this)
        builder.setView(promptView)
        builder.setTitle("Create a Habit")
        builder.setNeutralButton("CANCEL", DialogInterface.OnClickListener({
            dialog, which -> loadRecycler()
        }))
        builder.setPositiveButton("OK", DialogInterface.OnClickListener(){
            dialog, which ->
            var habitField = promptView.editText.text.toString()
            if(!db.habitExists(habitField)){
                Log.d(DEBUG,"Creating new habit")
                db.createHabit(habitField)
            }
            loadRecycler()
        })
        builder.show()
    }

    fun loadMain(view: View) {
        var intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    fun loadRecycler(){

        dbHandler = HabitDataAdapter(this)
        habitList = ArrayList<Habit>()
        habitListItems = ArrayList()
        layoutManager = LinearLayoutManager(this)
        adapter = ListHabitsAdapter(habitListItems!!,this, EDIT_HABITS)

        // Set up the RecyclerView
        habitListRecyclerViewID.layoutManager = layoutManager
        habitListRecyclerViewID.adapter = adapter

        // Load the habits
        habitList = dbHandler!!.readHabits()

        habitList!!.reverse()

        for (h in habitList!!.iterator()){
            var habit = Habit()
            habit.habitName = h.habitName
            habit.habitActive = h.habitActive
            habit.habitID = h.habitID
            if(habit.habitActive == 1) {
                habitListItems!!.add(habit)
            }
        }
        adapter!!.notifyDataSetChanged()
        //printList(habitListItems!!)


    }
}
