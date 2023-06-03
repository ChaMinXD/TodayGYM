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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        alarmManager=requireContext().getSystemService(ALARM_SERVICE) as AlarmManager?
        binding= FragmentSettingBinding.inflate(inflater,container,false)
        init()

        return binding.root
    }
    fun init(){
        Log.d("alarm",App.prefs.getString("alarmONOFF","OFF"))

        binding.alarmSwitch.isChecked = App.prefs.getString("alarmONOFF","OFF")!="OFF"
        if(binding.alarmSwitch.isChecked){
            binding.alarmTimeGroup.visibility=View.VISIBLE
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
                App.prefs.setString("alarmONOFF","ON")

            }
            else{
                binding.alarmTimeGroup.visibility=View.INVISIBLE
                App.prefs.setString("alarmONOFF","OFF")
                cancelAlarm()
            }
        }

        binding.alarmSetBtn.setOnClickListener {
            hour=binding.alarmTimepicker.hour
            min=binding.alarmTimepicker.minute
            setAlarm(hour,min)
            Log.d("alarm",hour.toString()+min.toString())
            Toast.makeText(requireContext(), "알람 설정이 완료되었습니다 .", Toast.LENGTH_SHORT).show()

        }

    }
    fun showGoalDialog(){
        val goalDialog= GoalDialog()
        goalDialog.show(requireFragmentManager(),"routineDelDialog")
    }
    private fun cancelAlarm() {
        val receiverIntent = Intent(requireContext(), AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(requireContext(), 0, receiverIntent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
        alarmManager?.cancel(pendingIntent)
    }

    private fun setAlarm(hour: Int, minute: Int) {
        //옵션값에 따라서, 푸시 설정이 되지 않을 수 있도록 함
        //AlarmReceiver에 값 전달
        val receiverIntent = Intent(requireContext(), AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(requireContext(), 0, receiverIntent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)

        //alarm 등록 전, 이전 push cancel
        alarmManager?.cancel(pendingIntent)

        // Set the alarm to start at time and minute

        val calendar: Calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
            set(Calendar.SECOND, 0)

            if (timeInMillis <= System.currentTimeMillis()) {
                add(Calendar.DAY_OF_MONTH, 1)
            }
        }

        alarmManager?.setRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY,
            pendingIntent
        )
    }

}