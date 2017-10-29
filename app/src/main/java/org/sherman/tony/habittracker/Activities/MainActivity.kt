package org.sherman.tony.habittracker.Activities

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.MenuItem
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import org.sherman.tony.habittracker.Data.DEBUG
import org.sherman.tony.habittracker.Adapters.TabbedMenuAdapter
import org.sherman.tony.habittracker.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var sectionAdapter: TabbedMenuAdapter

        sectionAdapter = TabbedMenuAdapter(supportFragmentManager)
        viewPagerID.adapter = sectionAdapter
        tabs.setupWithViewPager(viewPagerID)
        tabs.setTabTextColors(Color.WHITE, Color.GREEN)

        val subTitle = "Seinfeld Count:  "+getSeinfeldNumber().toString()
        val toolbarID:Toolbar = findViewById<Toolbar>(R.id.action_bar)
        toolbarID.subtitle = subTitle
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.menu, menu)


        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        // What to do when a specific menu item is selected.

        when(item?.itemId){
            R.id.menuHabitID -> {
                val intent = Intent(applicationContext, HabitActivity::class.java)
                startActivity(intent)
                finish()
            }
            else -> {
                Log.d(DEBUG,"Illegal menu option")
            }
        }
        return super.onOptionsItemSelected(item)
    }


    fun getSeinfeldNumber(): Int {
        return 99
    }

    fun loadTest(view: View) {
        val intent = Intent(applicationContext, TestActivity::class.java)
        startActivity(intent)
    }
}
