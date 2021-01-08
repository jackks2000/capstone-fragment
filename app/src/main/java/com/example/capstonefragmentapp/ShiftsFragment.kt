package com.example.capstonefragmentapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.capstonefragmentapp.ViewModel.ShiftViewModel
import com.example.capstonefrontend.Model.Shift
import com.example.capstonefrontend.Model.ShiftAdapter
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_shifts.*

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class ShiftsFragment : Fragment() {

    private val viewModel: ShiftViewModel by viewModels()

    private var shifts = arrayListOf<Shift>()
    private lateinit var shiftAdapter: ShiftAdapter

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_shifts, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (FirebaseAuth.getInstance().currentUser!!.email!!.contains("@admin")){
            btnCreateShift.visibility = View.VISIBLE
        }else{
            btnCreateShift.visibility = View.INVISIBLE
        }
        initRv()
        initObserver()

//        get arguments
        val day: Int = arguments?.get("day") as Int
        val month: Int = arguments?.get("month") as Int
        val year: Int = arguments?.get("year") as Int
        viewModel.getShiftsFromDate(day, month, year)
        println("Day $day")
        println("Month $month")
        println("Year $year")


        btnBackToCalendar.setOnClickListener {
            findNavController().navigate(R.id.action_shiftsFragment_to_firstFragment)
        }

        btnCreateShift.setOnClickListener{
            //add arguments to navigation
            val args = Bundle()
            args.putInt("year", year)
            args.putInt("month", month)
            args.putInt("day", day)
            findNavController().navigate(R.id.action_shiftsFragment_to_createShiftFragment, args)
        }


    }

    private fun initRv(){
        shiftAdapter = ShiftAdapter(shifts, requireContext(), viewModel)

        AvailableShiftsRV.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        AvailableShiftsRV.adapter = shiftAdapter

    }

    private fun initObserver(){
        viewModel.shifts.observe(viewLifecycleOwner, {
            shifts.clear()
            shifts.addAll(it)
            shiftAdapter.notifyDataSetChanged()
        })
    }
}