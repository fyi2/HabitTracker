package org.sherman.tony.habittracker.Data

// Database (SQLite)

val DATABASE_VERSION : Int = 1
val DATABASE_NAME: String = "seinfeld.db"
val TABLE_HABITS: String = "jerry"
val TABLE_ACTIVITIES:String = "kramer"

// STATION table columns names
val KEY_ACTIVITY_ID: String = "id"          // Activity primary key
val KEY_ACTIVEHABIT_ID: String = "habit_id"     // Activity habit ID
val KEY_DATE: String = "done_date"          // Date Activity done
val KEY_HABIT_NAME: String = "habit_name"         // Habit name
val KEY_HABIT_ACTIVE: String = "habit_active"    // Is this habit currently active
val KEY_HABIT_ID:String = "id"              // Habit primary key

// SQL Commands
val CREATE_HABIT_TABLE = "CREATE TABLE "+ TABLE_HABITS + " ( "+ KEY_HABIT_ID + " INTEGER PRIMARY KEY, "+
        KEY_HABIT_ACTIVE+" INTEGER, "+
        KEY_HABIT_NAME+" TEXT );"

val CREATE_ACTIVITY_TABLE = "CREATE TABLE "+ TABLE_ACTIVITIES + " ( "+ KEY_ACTIVITY_ID + " INTEGER PRIMARY KEY, "+
        KEY_ACTIVEHABIT_ID+" INTEGER, "+
        KEY_DATE+" LONG ) ;"

val DROP_TABLE_HABITS = "DROP TABLE IF EXISTS " + TABLE_HABITS
val DROP_TABLE_ACTIVIES = "DROP TABLE IF EXISTS " + TABLE_ACTIVITIES

val SELECT_ALL_HABITS = "SELECT * FROM " + TABLE_HABITS
val SELECT_ALL_ACTIVITIES = "SELECT * FROM " + TABLE_ACTIVITIES



// Constants
val DEBUG = "DEBUG ===>>> "
