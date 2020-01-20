package com.example.kotlinloginregister

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.content.Intent
import android.widget.Toast

class PlugActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_plug)

        val buttonLogOut = findViewById<View>(R.id.button_log_out) as Button

        buttonLogOut.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)

            Toast.makeText(this, "You are logged out of your account", Toast.LENGTH_SHORT).show()
        }
    }
}
