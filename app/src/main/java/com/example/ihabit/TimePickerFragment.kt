package com.example.ihabit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.TimePicker
import androidx.fragment.app.Fragment
import com.example.ihabit.activity.AddHabitActivity

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [timePickerFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class timePickerFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_time_picker, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val back = view.findViewById<TextView>(R.id.timerBack)
        val timePicker = view.findViewById<TimePicker>(R.id.timePicker)
        var AM_PM = "AM"
        var selectHour = "AM 08:00"
        timePicker.setOnTimeChangedListener { _, hourOfDay, minute ->
            var hour=""
            var min=""
            if(hourOfDay<10){
                hour="0$hourOfDay"
            }else{hour=hourOfDay.toString()}
            if(minute<10){
                min="0$minute"
            }else{min=minute.toString()}
            selectHour = "$hour:$min"
        }
        back.setOnClickListener {

            if (activity is AddHabitActivity) {
                (activity as AddHabitActivity).sendHourString(selectHour)
            }
            val tmp = activity?.supportFragmentManager
            tmp?.beginTransaction()?.remove(this)?.commit()
        }


    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment timePickerFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            timePickerFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}