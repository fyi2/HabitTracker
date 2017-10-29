package org.sherman.tony.habittracker.Fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import org.sherman.tony.habittracker.R


/**
 * A simple [Fragment] subclass.
 */
class ReportFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val textView = TextView(activity)
        textView.setText(R.string.hello_blank_fragment)
        return textView
    }

}// Required empty public constructor
