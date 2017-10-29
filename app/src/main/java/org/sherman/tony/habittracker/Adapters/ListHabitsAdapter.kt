package org.sherman.tony.habittracker.Adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import org.sherman.tony.habittracker.Data.DEBUG
import org.sherman.tony.habittracker.Models.Habit
import org.sherman.tony.habittracker.R


/**
 * Created by Admin on 10/29/2017.
 */
class ListHabitsAdapter(val list:ArrayList<Habit>, context: Context): RecyclerView.Adapter<ListHabitsAdapter.ViewHolder>() {

    val mContext = context

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(mContext)
                .inflate(R.layout.habit_list_file, parent, false)

        return ViewHolder(view, mContext, list)
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder?.bindViews(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }


    inner class ViewHolder(itemView:View, context: Context, list: ArrayList<Habit>): RecyclerView.ViewHolder(itemView), View.OnClickListener {

        var mContext = context
        var mList = list

        var habitName = itemView.findViewById<TextView>(R.id.habitNameID)
        var deleteButton = itemView.findViewById<ImageButton>(R.id.habitDeleteButton)
        var doneButton = itemView.findViewById<ImageButton>(R.id.habitActiveButton)

        fun bindViews(habit: Habit){
            habitName.text = habit.habitName

            deleteButton.setOnClickListener(this)
            doneButton.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            var mPosition:Int = adapterPosition
            var habit = mList[mPosition]

            Log.d(DEBUG, habitName.text.toString())

            when(v!!.id){
                deleteButton.id -> Log.d(DEBUG,"delete button clicked")
                doneButton!!.id -> Log.d(DEBUG,"done button clicked")
            }
        }

    }
}