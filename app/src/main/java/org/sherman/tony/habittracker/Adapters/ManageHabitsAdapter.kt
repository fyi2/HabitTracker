package org.sherman.tony.habittracker.Adapters


import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import org.sherman.tony.habittracker.Models.Habit
import org.sherman.tony.habittracker.R

/**
 * Created by Admin on 10/29/2017.
 */
class ManageHabitsAdapter(var listOfHabits:ArrayList<Habit>,val context: Context):
        RecyclerView.Adapter<ManageHabitsAdapter.ViewHolder>(){

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder!!.bindView(listOfHabits[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder{
        val view = LayoutInflater.from(context)
                .inflate(R.layout.habit_list_file, parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listOfHabits.size
    }

    inner class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){

        var habitText = itemView.findViewById<TextView>(R.id.habitNameID)

        fun bindView(habit:Habit){
            habitText.text = habit.habitName.toString()
        }
    }
}