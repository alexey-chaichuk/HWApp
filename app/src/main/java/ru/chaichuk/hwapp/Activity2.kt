package ru.chaichuk.hwapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class Activity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_2)
    }

    fun onAct2Act3Click(view: View) {
        startActivity(Intent(this, Activity3::class.java))
    }

    fun onAct2MActClick(view: View) {
        startActivity(Intent(this, MainActivity::class.java))
    }
}