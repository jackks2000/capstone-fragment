package com.example.capstonefragmentapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.capstonefragmentapp.ViewModel.ShiftViewModel
import com.example.capstonefrontend.Model.Shift
import com.example.capstonefrontend.Model.ShiftAdapter
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_my_shifts.*
import kotlinx.android.synthetic.main.fragment_shifts.*
import kotlinx.android.synthetic.main.item_shift.*

class MyShiftsFragment: Fragment() {
    private val viewModel: ShiftViewModel by viewModels()

    private var myShifts = arrayListOf<Shift>()
    private lateinit var myShiftAdapter: ShiftAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_shifts, container, false)

    }

    @SuppressLint("ResourceType")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRv()
        initObserver()

        viewModel.getShiftsFromEmail(FirebaseAuth.getInstance().currentUser!!.email!!)

        btnMyShiftsBackToCalendar.setOnClickListener {
            findNavController().navigate(R.id.action_myShiftsFragment_to_firstFragment)
        }



    }

    private fun initRv(){
        myShiftAdapter = ShiftAdapter(myShifts, requireContext(), viewModel)

        rvMyShifts.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        rvMyShifts.adapter = myShiftAdapter

    }

    private fun initObserver(){
        viewModel.shifts.observe(viewLifecycleOwner, {
            myShifts.clear()
            myShifts.addAll(it)
            myShiftAdapter.notifyDataSetChanged()
        })
    }
}