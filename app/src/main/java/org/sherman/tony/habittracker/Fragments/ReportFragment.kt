package org.sherman.tony.habittracker.Fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import org.sherman.tony.habittracker.R


/**
 * A simple [Fragment] subclass.
 */
class ReportFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater!!.inflate(R.layout.fragment_report, container, false)
    }

}// Required empty public constructor
