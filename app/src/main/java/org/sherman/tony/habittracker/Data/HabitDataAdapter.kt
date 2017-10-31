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
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import java.util.*


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
        val mHabit = habit.trim().capitalize()
        values.put(KEY_HABIT_NAME, mHabit)
        values.put(KEY_HABIT_ACTIVE,1)

        // Open an initial entry
        db.insert(TABLE_HABITS,null,values)
        Log.d(DEBUG,"Habits database updated")
    }

    fun habitExists(habitName: String):Boolean{
        var db: SQLiteDatabase = readableDatabase
        val mHabitName = habitName.trim().capitalize()
        var habit = Habit()
        var cursor: Cursor = db.query(TABLE_HABITS, arrayOf(KEY_HABIT_ID, KEY_HABIT_NAME, KEY_HABIT_ACTIVE),
                KEY_HABIT_NAME+"=?", arrayOf(mHabitName), null, null, null)

        return cursor.moveToFirst()
    }

    fun readHabit(habitName:String):Habit{
        var db: SQLiteDatabase = readableDatabase
        val mHabitName = habitName.trim().capitalize()
        var habit = Habit()
        var cursor: Cursor = db.query(TABLE_HABITS, arrayOf(KEY_HABIT_ID, KEY_HABIT_NAME, KEY_HABIT_ACTIVE),
                KEY_HABIT_NAME+"=?", arrayOf(mHabitName), null, null, null)

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
                Log.d(DEBUG,habit.habitID.toString())
            }while (cursor.moveToNext())
        }
        return list
    }


    //TODO - Remove debug before production
    fun printList(list:ArrayList<Habit>){
        for (i in 0..list.size - 1){
            Log.d(DEBUG, "${list[i].habitName} has ID ${list[i].habitID}")
        }
    }
    fun dropHabit(habit: Habit){
        var db: SQLiteDatabase = writableDatabase
        val recordID = habit.habitID

        db.delete(TABLE_HABITS, KEY_HABIT_ID+"=?", arrayOf(recordID.toString()))
    }

    fun createActivity(habit: String){
        var db: SQLiteDatabase = writableDatabase
        var values = ContentValues()

        val habitObj = readHabit(habit)
        var activity = Activity()

        activity.activityDate = Calendar.getInstance().timeInMillis
        activity.activityHabitKey = habitObj.habitID

        values.put(KEY_DATE, activity.activityDate)
        values.put(KEY_ACTIVEHABIT_ID,activity.activityHabitKey)

        db.insert(TABLE_ACTIVITIES,null, values)
    }

    fun createActivityObj(activity: Activity){
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


    fun readActivityList(habitName: String):ArrayList<Activity> {
        var db: SQLiteDatabase = readableDatabase
        var habit = Habit()



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
        var activityList = ArrayList<Activity>()

        cursor = db.rawQuery(QUERY_STR2,null)
        if (cursor.moveToFirst()){
            do {
                var activity = Activity()

                activity.activityDate = cursor.getLong(cursor.getColumnIndex(KEY_DATE))
                activity.activityHabitKey = cursor.getInt(cursor.getColumnIndex(KEY_ACTIVEHABIT_ID))
                activity.activityID = cursor.getInt(cursor.getColumnIndex(KEY_ACTIVITY_ID))

                activityList.add(activity)
            }while(cursor.moveToNext())
        }
        return activityList
    }

    fun activityCount(habit: String):Int{
        var activityList:ArrayList<Activity>

        activityList = readActivityList(habit)

        return activityList.size
    }

    fun sienfeldCount(habit: String):Int {
        var activityList:ArrayList<Activity>

        activityList = readActivityList(habit)
        activityList.sortByDescending { it.activityDate }

        val elapsedList = buildElapsedList(activityList)

        return sienfeldNumber(elapsedList)
    }

    fun sienfeldNumber(elapsedList: ArrayList<Int>): Int {
        var counter = 0
        var sum = 0
        if (elapsedList.isEmpty()){
            return 0
        } else {
            do{
                sum++
                counter++
            }while (elapsedList[counter]==1 && (counter < elapsedList.size - 1))
        }
        return sum
    }

    fun buildElapsedList(activityList:ArrayList<Activity>):ArrayList<Int>{
        var elapsedList = ArrayList<Int>()
        if(activityList.size > 1){
            for(i in 0..activityList.size-2){
                val d1 = daysElapsed(activityList[i].activityDate!!)
                val d2 = daysElapsed(activityList[i+1].activityDate!!)
                val gap = (d1-d2).toInt()
                elapsedList.add(gap)
            }
        }
        return elapsedList
    }

    fun daysElapsed(timeStamp:Long):Long {
        val epochDay = (timeStamp/ TRUNCATE_TO_TODAY).toLong()
        val dateStart = LocalDate.of(2017,1,1)
        val dateNow = LocalDate.ofEpochDay(epochDay)
        Log.d(DEBUG,"Time in Epoch Days = $epochDay which is date $dateNow")

        return dateStart.until(dateNow, ChronoUnit.DAYS)
    }

    fun createRandomActivities() {
        val min = 1
        val max = 30
        val recordNumber = 25
        val habitsList = readHabits()

        var rand  = Random()

        for (i in 0..habitsList.size-1){
            for(j in 1..recordNumber){
                var activity = Activity()
                var date = Calendar.getInstance()
                var randomNumber = rand.nextInt( max - min + 1) + min
                activity.activityHabitKey = habitsList[i].habitID
                date.add(Calendar.DATE, -randomNumber)
                activity.activityDate = date.timeInMillis
                createActivityObj(activity)
                Log.d(DEBUG, "Created ${habitsList[i].habitName} with key ${activity.activityHabitKey} at time ${activity.activityDate}")
            }
        }
        Log.d(DEBUG,"$recordNumber random records created")
    }
}