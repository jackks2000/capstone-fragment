package com.example.capstonefragmentapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.capstonefragmentapp.ViewModel.ShiftViewModel
import com.example.capstonefrontend.Model.Shift
import kotlinx.android.synthetic.main.fragment_create_shift.*

class CreateShiftFragment: Fragment() {
    private val viewModel: ShiftViewModel by viewModels()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_shift, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //            get arguments
        val day: Int = arguments?.get("day") as Int
        val month: Int = arguments?.get("month") as Int
        val year: Int = arguments?.get("year") as Int



        btnBackToShifts.setOnClickListener {
            val args = Bundle()
            args.putInt("year", year)
            args.putInt("month", month)
            args.putInt("day", day)
            findNavController().navigate(R.id.action_createShiftFragment_to_shiftsFragment, args)
        }

        btnFinishCreateShift.setOnClickListener{
            val name = etShiftName.text.toString()
            val startTime = etShiftStartTime.text.toString()
            val endTime = etShiftEndTime.text.toString()
            val employee = etShiftOptionalEmployee.text.toString()
            val school = etShiftSchoolName.text.toString()
            viewModel.createShift(Shift(name + day + month + year + school, name, startTime, endTime, day,month,year, employee, school))

            //        add arguments to navigation
            val args = Bundle()
            args.putInt("year", year)
            args.putInt("month", month)
            args.putInt("day", day)

            println("Day $day")
            println("Month $month")
            println("Year $year")

            findNavController().navigate(R.id.action_createShiftFragment_to_shiftsFragment, args)
        }
    }
}