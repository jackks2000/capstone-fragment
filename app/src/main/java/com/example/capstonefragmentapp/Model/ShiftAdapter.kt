package com.example.capstonefrontend.Model

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import kotlinx.android.synthetic.main.item_shift.view.*

import android.content.DialogInterface
import android.provider.CalendarContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.capstonefragmentapp.R
import com.example.capstonefragmentapp.ViewModel.ShiftViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.type.Color
import kotlinx.coroutines.delay


/**
 * @author Jack Klein Schiphorst
 */
class ShiftAdapter(private val items: ArrayList<Shift>, private val context: Context, private val viewModel: ShiftViewModel) :
    RecyclerView.Adapter<ShiftAdapter.ViewHolder>() {



    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun dataBind(shift: Shift) {
            itemView.ShiftNameTV.text = shift.name
            itemView.StartTimeTV.text = shift.startTime
            itemView.EndTimeTV.text = shift.endTime

            itemView.btnGetShift.setOnClickListener{
                showDialog(shift, itemView)
            }
        }
    }

    private fun showDialog(shift: Shift, itemView: View){
        // Late initialize an alert dialog object
        lateinit var dialog: AlertDialog

        var replied: Boolean = false

        // Initialize a new instance of alert dialog builder object
        val builder = AlertDialog.Builder(context)

        // Set a title for alert dialog
        builder.setTitle("Get shift")

        // Set a message for alert dialog
        builder.setMessage("Are you sure you want to sign up for this shift?")

        // On click listener for dialog buttons
        val dialogClickListener = DialogInterface.OnClickListener{ _, which ->
            when(which){
                DialogInterface.BUTTON_POSITIVE -> {
                    println("YES")
                    shift.employeeEmail = FirebaseAuth.getInstance().currentUser!!.email
                    println("SHIFT ${shift.employeeEmail}")
                    viewModel.updateShift(shift)
                    itemView.btnGetShift.isEnabled = false
                    itemView.btnGetShift.isClickable = false
                }
                DialogInterface.BUTTON_NEGATIVE -> {
                    println("CANCEL")
                }
                DialogInterface.BUTTON_NEUTRAL -> println("CANCEL")
            }
        }


        // Set the alert dialog positive/yes button
        builder.setPositiveButton("YES",dialogClickListener)

        // Set the alert dialog negative/no button
        builder.setNegativeButton("CANCEL",dialogClickListener)

        // Set the alert dialog neutral/cancel button


        // Initialize the AlertDialog using builder object
        dialog = builder.create()

        // Finally, display the alert dialog
        dialog.show()

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(com.example.capstonefragmentapp.R.layout.item_shift, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.dataBind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }
}