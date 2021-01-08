package com.example.capstonefragmentapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.firebase.ui.auth.AuthUI
import kotlinx.android.synthetic.main.fragment_calendar.*
import java.sql.SQLOutput

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_calendar, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnSignOut.setOnClickListener{
            AuthUI.getInstance()
                .signOut(requireContext())
                .addOnCompleteListener {
                    val intent = Intent(requireContext(), LandingActivity::class.java)
                    startActivity(intent)
                }

        }

        ShiftsCV.setOnDateChangeListener { calendarView, year, month, day ->
            println("Day $day")
            println("Month $month")
            println("Year $year")

            //add arguments to navigation
            val args = Bundle()
            args.putInt("year", year)
            //Add 1 to the zero based index of the month to get the real month number
            args.putInt("month", month + 1)
            args.putInt("day", day)
            findNavController().navigate(R.id.action_firstFragment_to_shiftsFragment, args)
        }

        btnMyShifts.setOnClickListener {
            findNavController().navigate(R.id.action_firstFragment_to_myShiftsFragment)
        }
    }
}