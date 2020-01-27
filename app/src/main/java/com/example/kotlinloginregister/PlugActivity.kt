package com.example.kotlinloginregister

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_plug.*

class PlugActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_plug)

        button_log_out.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)

            Toast.makeText(this, R.string.notice_logged_out, Toast.LENGTH_SHORT).show()
        }
    }
}
