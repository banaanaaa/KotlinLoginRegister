package com.example.kotlinloginregister

import android.content.Intent
import android.os.Bundle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_main.*

class LoginFragment : Fragment() {

    private var eMail: EditText? = null
    private var password: EditText? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_login, container, false)

        eMail = view.findViewById(R.id.log_EMail) as EditText
        password = view.findViewById(R.id.log_Password) as EditText

        val buttonSignUp = view.findViewById(R.id.button_sign_up) as Button
        val buttonResetPassword = view.findViewById(R.id.button_reset_password) as Button
        val buttonAccept = view.findViewById(R.id.log_btn_Accept) as Button

        buttonSignUp.setOnClickListener {
            activity!!.findViewById<CustomViewPager>(R.id.fragment_page).currentItem = 0
        }

        buttonResetPassword.setOnClickListener {
            activity!!.findViewById<CustomViewPager>(R.id.fragment_page).currentItem = 2
        }

        buttonAccept.setOnClickListener {
            if (checkValid()) {
                val intent = Intent(activity, PlugActivity::class.java)
                startActivity(intent)

                Toast.makeText(activity, "Logged in successfully", Toast.LENGTH_LONG).show()

                eMail!!.setText("")
                password!!.setText("")
            } else
                Toast.makeText(activity, "Wrong email or password", Toast.LENGTH_LONG).show()
        }

        return view
    }

    private fun checkValid() : Boolean {
        return eMail!!.text.toString() == activity!!.tmp_mail.text.toString()  && password!!.text.toString() == activity!!.tmp_pass.text.toString()
    }
}
