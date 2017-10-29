package org.sherman.tony.habittracker.Data

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

import org.sherman.tony.habittracker.Data.*
import org.sherman.tony.habittracker.Models.Activity
import org.sherman.tony.habittracker.Models.Habit


class HabitDataAdapter(context: Context): SQLiteOpenHelper(context,DATABASE_NAME, null,DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase?) {
        //SQL - Structured Query Language
        db?.execSQL(CREATE_ACTIVITY_TABLE)
        db?.execSQL(CREATE_HABIT_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS " + TABLE_ACTIVITIES)
        db?.execSQL("DROP TABLE IF EXISTS " + TABLE_HABITS)
        //create table again
        onCreate(db)
    }


    fun getHabitCount(habit: String): Int {
        var db: SQLiteDatabase = readableDatabase

        var countQuery = "SELECT * FROM "+ TABLE_HABITS
        var cursor: Cursor = db.rawQuery(countQuery, null)

        return cursor.count
    }

    fun getActivityCount(habit: String): Int {
        var db: SQLiteDatabase = readableDatabase

        var countQuery = "SELECT * FROM "+ TABLE_ACTIVITIES
        var cursor: Cursor = db.rawQuery(countQuery, null)

        return cursor.count
    }

    fun findLastN(id: Int): Int {
        return 0
    }
    //CRUD
    fun createHabit(habit:String) {
        var db: SQLiteDatabase = writableDatabase
        var values = ContentValues()
        // create a habit table entry
        values.put(KEY_HABIT_NAME, habit)
        values.put(KEY_HABIT_ACTIVE,1)

        // Open an initial entry
        db.insert(TABLE_HABITS,null,values)
        Log.d(DEBUG,"Habits database updated")
    }

    fun readHabit(habitName:String):Habit{
        var db: SQLiteDatabase = readableDatabase
        var habit = Habit()
        var cursor: Cursor = db.query(TABLE_HABITS, arrayOf(KEY_HABIT_ID, KEY_HABIT_NAME, KEY_HABIT_ACTIVE),
                KEY_HABIT_NAME+"=?", arrayOf(habitName), null, null, null)

        if (cursor.moveToFirst()){

            habit.habitID = cursor.getInt(cursor.getColumnIndex(KEY_HABIT_ID))
            habit.habitName = cursor.getString(cursor.getColumnIndex(KEY_HABIT_NAME))
            habit.habitActive = cursor.getInt(cursor.getColumnIndex(KEY_HABIT_ACTIVE))
        }
        return habit
    }


    fun readHabits():ArrayList<Habit>{
        var db: SQLiteDatabase = readableDatabase
        var list:ArrayList<Habit> = ArrayList()
        val QUERY_STR = "SELECT * FROM "+ TABLE_HABITS
        var cursor: Cursor = db.rawQuery(QUERY_STR,null)

        if (cursor.moveToFirst()){
            do {
                var habit = Habit()
                habit.habitID = cursor.getInt(cursor.getColumnIndex(KEY_HABIT_ID))
                habit.habitName = cursor.getString(cursor.getColumnIndex(KEY_HABIT_NAME))
                habit.habitActive = cursor.getInt(cursor.getColumnIndex(KEY_HABIT_ACTIVE))
                list.add(habit)
            }while (cursor.moveToNext())
        }
        return list
    }

    fun deleteHabit(habit: String) {
        // Set habit to inactive
    }

    fun createActivity(activity: Activity){
        var db: SQLiteDatabase = writableDatabase
        var values = ContentValues()

        values.put(KEY_DATE, activity.activityDate)
        values.put(KEY_ACTIVEHABIT_ID,activity.activityHabitKey)

        db.insert(TABLE_ACTIVITIES,null, values)

    }

    fun readActivity(position: Int): Activity{
        var db: SQLiteDatabase = readableDatabase
        var activity = Activity()

        var cursor: Cursor = db.query(TABLE_ACTIVITIES, arrayOf(KEY_ACTIVITY_ID, KEY_ACTIVEHABIT_ID, KEY_DATE),
                KEY_ACTIVITY_ID+"=?", arrayOf(position.toString()), null, null, null)

        if (cursor.moveToFirst()){

            activity.activityID = cursor.getInt(cursor.getColumnIndex(KEY_ACTIVITY_ID))
            activity.activityHabitKey = cursor.getInt(cursor.getColumnIndex(KEY_ACTIVEHABIT_ID))
            activity.activityDate = cursor.getLong(cursor.getColumnIndex(KEY_DATE))
        }
        return activity
    }


    fun readLatestActivity(habitName: String):Activity {
        var db: SQLiteDatabase = readableDatabase
        var habit = Habit()
        var activity = Activity()
        var activityDate:Long = 0

        // Read the habit matching String
        val QUERY_STR1 = "SELECT * FROM " + TABLE_HABITS+" WHERE $KEY_HABIT_NAME="+"'$habitName'"
        var cursor: Cursor = db.rawQuery(QUERY_STR1, null)

        if (cursor.moveToFirst()){

            habit.habitID = cursor.getInt(cursor.getColumnIndex(KEY_HABIT_ID))
            habit.habitName = cursor.getString(cursor.getColumnIndex(KEY_HABIT_NAME))
            habit.habitActive = cursor.getInt(cursor.getColumnIndex(KEY_HABIT_ACTIVE))
            Log.d(DEBUG,"Found habit ${habit.habitName}")
        } else {
            Log.d(DEBUG,"No habit found")
        }
        // Return the latest
        val QUERY_STR2 = "SELECT * FROM "+ TABLE_ACTIVITIES+" WHERE $KEY_ACTIVEHABIT_ID="+habit.habitID

        activity.activityDate = 0L
        cursor = db.rawQuery(QUERY_STR2,null)
        if (cursor.moveToFirst()){
            do {
                activityDate = cursor.getLong(cursor.getColumnIndex(KEY_DATE))
                Log.d(DEBUG,"FOUND ${activity.activityDate}")
                if(activityDate > activity.activityDate!!){
                    activity.activityDate = activityDate
                    activity.activityHabitKey = cursor.getInt(cursor.getColumnIndex(KEY_ACTIVEHABIT_ID))
                    activity.activityID = cursor.getInt(cursor.getColumnIndex(KEY_ACTIVITY_ID))
                    Log.d(DEBUG,"reviewing ${activity.activityDate}")
                    activityDate = activity.activityDate!!
                }
            } while(cursor.moveToNext())
        }
        return activity
    }

}