package com.example.TodayGYM

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.TodayGYM.Sharedprefs.App
import com.example.TodayGYM.databinding.ActivitySelectBinding

class SelectActivity : AppCompatActivity() {
    lateinit var binding:ActivitySelectBinding
    lateinit var type:String//팔,가슴,등,하체,어깨,유산소
    lateinit var place:String//홈트레이닝 , 헬스장
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivitySelectBinding.inflate(layoutInflater)
        init()
        setContentView(binding.root)
    }
    private fun init(){
        binding.exerciseArmBtn.setOnClickListener {
            binding.selectGroup1.visibility=View.GONE
            binding.selectGroup2.visibility=View.VISIBLE
            type="팔"
        }
        binding.exerciseBackBtn.setOnClickListener {
            binding.selectGroup1.visibility=View.GONE
            binding.selectGroup2.visibility=View.VISIBLE
            type="등"
        }
        binding.exerciseBreastBtn.setOnClickListener {
            binding.selectGroup1.visibility=View.GONE
            binding.selectGroup2.visibility=View.VISIBLE
            type="가슴"
        }
        binding.exerciseLegBtn.setOnClickListener {
            binding.selectGroup1.visibility=View.GONE
            binding.selectGroup2.visibility=View.VISIBLE
            type="하체"
        }
        binding.exerciseShoulderBtn.setOnClickListener {
            binding.selectGroup1.visibility=View.GONE
            binding.selectGroup2.visibility=View.VISIBLE
            type="어깨"
        }
        binding.exerciseBtn.setOnClickListener {
            val intent= Intent(this,MainActivity::class.java)
            type="유산소"
            place="유산소"
            App.prefs.setString("type",type)
            App.prefs.setString("place",place)

            startActivity(intent)
        }
        binding.backTextview.setOnClickListener {
            binding.selectGroup1.visibility=View.VISIBLE
            binding.selectGroup2.visibility=View.GONE
        }
        binding.healthBtn.setOnClickListener {
            place="헬스장"
            App.prefs.setString("type",type)
            App.prefs.setString("place",place)
            val intent=Intent(this,MainActivity::class.java)

            startActivity(intent)
        }
        binding.hometrainBtn.setOnClickListener {
            place="홈트레이닝"
            App.prefs.setString("type",type)
            App.prefs.setString("place",place)
            val intent=Intent(this,MainActivity::class.java)
            startActivity(intent)
        }
    }
}