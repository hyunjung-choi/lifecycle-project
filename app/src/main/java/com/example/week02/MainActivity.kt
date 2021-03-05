package com.example.week02

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.week02.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    val SP_NAME = "my_sp_storage"

    private lateinit var binding:ActivityMainBinding // xml 파일을 클래스로 만들어 줌

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater) // ActivityMainBinding 클래스로 인스턴스 생성
        val view = binding.root //root == LinearLayout
        setContentView(view) // root 전체를 view에 담아서 setContentView에 넣는다. => 화면이 메모리로 올라간다.

        binding.checkBoxRemember.setOnClickListener {
            var checkRemember = binding.checkBoxRemember.isChecked // 아이디 저장 check
            writeSharedPreferenceCheck("check", checkRemember)
        }

        binding.btnMainLogin.setOnClickListener {
            val edtId = binding.edtMainId.text.toString() // 사용자 ID
            val edtPw = binding.edtMainPassword.text.toString() // 사용자 비밀번호

            // ID가 3글자 미만이면
            if(edtId!!.length < 3){
                showIdDialog()
            }
            // ID가 3글자 이상이면 SP에 저장
            else{
                writeSharedPreference("id", edtId)
                writeSharedPreference("pw", edtPw)
                Log.d("로그인SP", "아이디: $edtId 비밀번호: $edtPw")

                var intent = Intent(this, SecondActivity::class.java) // 클래스 명을 정확히 명시해줌 => 명시적 인텐트
                intent.putExtra("data", edtId)
                startActivity(intent)
            }

        }

    }

    fun writeSharedPreference(key:String, value:String){
        val sp = getSharedPreferences(SP_NAME, MODE_PRIVATE)
        val editor = sp.edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun writeSharedPreferenceCheck(key:String, value: Boolean){
        val sp = getSharedPreferences(SP_NAME, MODE_PRIVATE)
        val editor = sp.edit()
        editor.putBoolean(key, value)
        editor.apply()
    }

    fun readSharedPreference(key:String): String{
        val sp = getSharedPreferences(SP_NAME, MODE_PRIVATE)
        return sp.getString(key, "") ?: ""
    }

    fun showIdDialog(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("아이디를 3글자 이상 입력해주세요!")
        builder.setPositiveButton("확인", null)
        val dialogView = layoutInflater.inflate(R.layout.dialog_id, null)
        builder.show()
    }

    override fun onStart() {
        super.onStart()
        var checkRemember = binding.checkBoxRemember.isChecked
        if(checkRemember){
            val id = readSharedPreference("id")
            binding.edtMainId.setText(id)
        }
//        Toast.makeText(this,"onStart",Toast.LENGTH_SHORT).show()
    }

    override fun onResume() {
        super.onResume()
//        Toast.makeText(this,"onResume",Toast.LENGTH_SHORT).show()
    }

    override fun onPause() {
        super.onPause()
//        Toast.makeText(this,"onPause",Toast.LENGTH_SHORT).show()
    }

    override fun onStop() {
        super.onStop()
        // editText 빈칸으로 만들기
        binding.edtMainId.setText("")
        binding.edtMainPassword.setText("")
//        Toast.makeText(this,"onStop",Toast.LENGTH_SHORT).show()
    }

    override fun onRestart() {
        super.onRestart()
//        Toast.makeText(this,"onRestart",Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        val sp = getSharedPreferences(SP_NAME, MODE_PRIVATE)
        val editor = sp.edit()
        editor?.remove("id")?.apply()
        editor?.remove("pw")?.apply()
        editor?.remove("check")?.apply()

//        val id = readSharedPreference("id")
//        val pw = readSharedPreference("pw")
//        val check = readSharedPreference("check")
//        Log.d("로그인SP", "아이디: $id 비밀번호: $pw 아이디저장: $check")
//        Toast.makeText(this,"onDestroy",Toast.LENGTH_SHORT).show()
    }
}