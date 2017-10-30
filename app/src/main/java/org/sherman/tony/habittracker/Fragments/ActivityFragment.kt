package org.sherman.tony.habittracker.Fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.activity_habit.*
import kotlinx.android.synthetic.main.fragment_activity.*
import org.sherman.tony.habittracker.Adapters.ListHabitsAdapter
import org.sherman.tony.habittracker.Data.CHECK_HABITS
import org.sherman.tony.habittracker.Data.HabitDataAdapter
import org.sherman.tony.habittracker.Models.Habit

import org.sherman.tony.habittracker.R


/**
 * A simple [Fragment] subclass.
 */
class ActivityFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater!!.inflate(R.layout.fragment_activity, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        var adapter: ListHabitsAdapter? = null
        var habitList: ArrayList<Habit>? = null
        var habitListItems: ArrayList<Habit>? = null
        var layoutManager: RecyclerView.LayoutManager? = null

        var dbHandler:HabitDataAdapter? = null
        dbHandler = HabitDataAdapter(context)
        habitList = ArrayList<Habit>()
        habitListItems = ArrayList()
        layoutManager = LinearLayoutManager(context)
        adapter = ListHabitsAdapter(habitListItems!!,context, CHECK_HABITS)

        // Set up the RecyclerView
        activityFragmentRecyclerID.layoutManager = layoutManager
        activityFragmentRecyclerID.adapter = adapter

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

}// Required empty public constructor
