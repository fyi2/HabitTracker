package org.sherman.tony.habittracker.Activities

import android.content.DialogInterface
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.habit_pop_up.view.*
import org.sherman.tony.habittracker.Adapters.ListHabitsAdapter
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

        dbHandler = HabitDataAdapter(this)
        habitList = ArrayList<Habit>()
        habitListItems = ArrayList()
        layoutManager = LinearLayoutManager(this)
        adapter = ListHabitsAdapter(habitListItems!!,this)

    }

    fun addHabit(view: View) {
        var popUpResult:String?=null
        var db = HabitDataAdapter(applicationContext)

        // pop up a dialog
        var promptView:View = layoutInflater.inflate(R.layout.habit_pop_up, null)
        var builder:AlertDialog.Builder =AlertDialog.Builder(this)
        builder.setView(promptView)
        builder.setTitle("Create a Habit")
        builder.setNeutralButton("CANCEL", DialogInterface.OnClickListener({
            dialog, which -> Toast.makeText(this,"Boo",Toast.LENGTH_LONG).show()
        }))
        builder.setPositiveButton("OK", DialogInterface.OnClickListener(){
            dialog, which ->
            var habitField = promptView.editText.text.toString()
            db.createHabit(habitField)
            Toast.makeText(this,habitField,Toast.LENGTH_LONG).show()

        })

        builder.show()

    }

    fun deleteHabit(view: View) {

    }
}
