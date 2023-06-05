package com.example.TodayGYM.Setting

import android.app.AlarmManager
import android.app.Application
import android.app.PendingIntent
import android.content.Context
import android.content.Context.ALARM_SERVICE
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import com.example.TodayGYM.DB.RoutineEntity
import com.example.TodayGYM.Dialog.GoalDialog
import com.example.TodayGYM.Dialog.RoutineDelDialog
import com.example.TodayGYM.MainActivity
import com.example.TodayGYM.R
import com.example.TodayGYM.Sharedprefs.App
import com.example.TodayGYM.databinding.FragmentSettingBinding
import java.util.*
internal var alarmManager: AlarmManager? = null
class SettingFragment : Fragment() {
    lateinit var binding:FragmentSettingBinding
    companion object {
        const val REQUEST_CODE = 101
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentSettingBinding.inflate(inflater,container,false)

        val alarmManager = binding.root.context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val pendingIntent = Intent(binding.root.context, AlarmReceiver::class.java).let {
            it.putExtra("code", REQUEST_CODE)
            it.putExtra("count", 10)
            PendingIntent.getBroadcast(binding.root.context, REQUEST_CODE, it, 0)
        }
        Log.d("alarm",App.prefs.getString("alarmONOFF","OFF"))

        binding.alarmSwitch.isChecked = App.prefs.getString("alarmONOFF","OFF")!="OFF"
        if(binding.alarmSwitch.isChecked){
            binding.alarmTimeGroup.visibility=View.VISIBLE
            binding.alarmOnTimeTextview.text=App.prefs.getString("alarmtime","없음")
        }
        else{
            binding.alarmTimeGroup.visibility=View.INVISIBLE
        }
        var hour:Int
        var min:Int
        binding.goalView.setOnClickListener {
            showGoalDialog()
        }
        binding.alarmTimepicker.setOnTimeChangedListener { timePicker, c_hour, c_min ->
            hour=c_hour
            min=c_min
        }
        binding.alarmSwitch.setOnCheckedChangeListener { compoundButton, b ->
            if(b){
                binding.alarmTimeGroup.visibility=View.VISIBLE
                binding.alarmOnTimeTextview.text=App.prefs.getString("alarmtime","없음")
                App.prefs.setString("alarmONOFF","ON")

            }
            else{
                binding.alarmTimeGroup.visibility=View.INVISIBLE
                App.prefs.setString("alarmONOFF","OFF")
                App.prefs.setString("alarmtime","없음")
                alarmManager.cancel(pendingIntent)
            }
        }

        binding.alarmSetBtn.setOnClickListener {
            hour=binding.alarmTimepicker.hour
            min=binding.alarmTimepicker.minute
            val calendar=Calendar.getInstance().apply {
                timeInMillis=System.currentTimeMillis()
                set(Calendar.HOUR_OF_DAY, hour)
                set(Calendar.MINUTE, min)
            }
            alarmManager.setInexactRepeating(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                AlarmManager.INTERVAL_DAY,
                pendingIntent
            )
            var alarmtime:String
            if(hour>=12){
                if(hour==12){
                    alarmtime="오후 "+hour.toString()+"시 "+min.toString()+"분"
                }
                else {
                    hour %= 12
                    alarmtime = "오후 " + hour.toString() + "시 " + min.toString() + "분"
                }
            }
            else{
                alarmtime="오전 "+hour.toString()+"시 "+min.toString()+"분"

            }
            var alarm=
            App.prefs.setString("alarmtime", alarmtime)
            Log.d("alarm",hour.toString()+min.toString())
            binding.alarmOnTimeTextview.text=App.prefs.getString("alarmtime","없음")

            Toast.makeText(requireContext(), "알람 설정이 완료되었습니다 .", Toast.LENGTH_SHORT).show()

        }

        return binding.root
    }

    fun showGoalDialog(){
        val goalDialog= GoalDialog()
        goalDialog.show(requireFragmentManager(),"routineDelDialog")
    }
}