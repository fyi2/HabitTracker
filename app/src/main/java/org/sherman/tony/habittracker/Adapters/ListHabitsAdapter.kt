package org.sherman.tony.habittracker.Adapters

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import org.sherman.tony.habittracker.Activities.HabitActivity
import org.sherman.tony.habittracker.Data.CHECK_HABITS
import org.sherman.tony.habittracker.Data.DEBUG
import org.sherman.tony.habittracker.Data.EDIT_HABITS
import org.sherman.tony.habittracker.Data.HabitDataAdapter
import org.sherman.tony.habittracker.Models.Habit
import org.sherman.tony.habittracker.R


class ListHabitsAdapter(val list:ArrayList<Habit>, context: Context, requestType: Int): RecyclerView.Adapter<ListHabitsAdapter.ViewHolder>() {

    val mContext = context
    val mViewType = requestType

    override fun getItemViewType(position: Int): Int {
        val viewRequest = mViewType
        //return super.getItemViewType(position)
        return viewRequest
    }

    override fun getItemId(position: Int): Long {
        return super.getItemId(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType:Int): ViewHolder {
        var view = LayoutInflater.from(mContext)
                .inflate(R.layout.habit_fragment, parent, false)

        when(viewType) {
            EDIT_HABITS -> {
                view = LayoutInflater.from(mContext)
                        .inflate(R.layout.habit_list_file, parent, false)
                return ViewHolder(view, mContext, list,mViewType)
            }
            CHECK_HABITS -> {
                view = LayoutInflater.from(mContext)
                        .inflate(R.layout.habit_fragment, parent, false)
                return ViewHolder(view, mContext, list, mViewType)
            }
        }
        return ViewHolder(view, mContext, list, mViewType)
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {

        holder?.bindViews(list[position])
    }

    override fun getItemCount(): Int {

        return list.size
    }


    inner class ViewHolder(itemView:View, context: Context, list: ArrayList<Habit>, viewType: Int): RecyclerView.ViewHolder(itemView), View.OnClickListener {

        var mContext = context
        var mList = list
        var mViewType = viewType

        var habitName = itemView.findViewById<TextView>(R.id.habitNameID)
        var deleteButton = itemView.findViewById<ImageButton>(R.id.habitDeleteButton)
        var doneButton = itemView.findViewById<ImageButton>(R.id.habitActiveButton)
        var scoreID = itemView.findViewById<TextView>(R.id.habitScoreID)



        fun bindViews(habit: Habit){
            habitName.text = habit.habitName
            if(mViewType == EDIT_HABITS){
                deleteButton.setOnClickListener(this)
            } else{
                doneButton.setOnClickListener(this)
            }

        }

        override fun onClick(v: View?) {
            var dbHandler = HabitDataAdapter(mContext)
            var mPosition:Int = adapterPosition
            var habit = mList[mPosition]
            val habitID = mList[mPosition].habitID

            if (mViewType == EDIT_HABITS) {
                when (v!!.id) {
                    deleteButton.id -> {
                        Log.d(DEBUG, "delete button clicked")
                        // set Habit to not active
                        dbHandler.dropHabit(habit)
                        // Force refresh
                        val intent = Intent(mContext, HabitActivity::class.java)
                        val bundle = Bundle()
                        startActivity(mContext,intent, bundle)
                    }
                    doneButton!!.id -> {
                        doneButton.setImageResource(android.R.drawable.checkbox_on_background)
                    }
                }
            } else {
                doneButton.setImageResource(android.R.drawable.checkbox_on_background)
                // Update Activity Record
                dbHandler.createActivity(habitName.text.toString())
                // Update Habit Siendfeld number
                val sCount = dbHandler.sienfeldCount(habitName.text.toString())
                scoreID.text = sCount.toString()
                // Check for a Sienfeld Day

            }
        }

    }
}