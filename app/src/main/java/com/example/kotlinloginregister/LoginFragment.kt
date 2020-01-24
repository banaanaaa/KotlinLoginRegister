package com.example.kotlinloginregister

import android.content.Intent
import android.os.Bundle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import androidx.fragment.app.Fragment
import com.google.android.material.button.MaterialButton
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_login.view.*

class LoginFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_login, container, false)

        view.log_btn_sign_up.setOnClickListener {
            activity!!.findViewById<CustomViewPager>(R.id.fragment_page).currentItem = 0
            reset()
        }

        view.log_btn_reset_pass.setOnClickListener {
            activity!!.findViewById<CustomViewPager>(R.id.fragment_page).currentItem = 2
            reset()
        }

        view.log_btn_accept.setOnClickListener {
            if (log_email_et.text.toString() == activity!!.tmp_mail.text.toString() && log_password_et.text.toString() == activity!!.tmp_pass.text.toString()) {
                val intent = Intent(activity, PlugActivity::class.java)
                startActivity(intent)

                Toast.makeText(activity, R.string.notice_logged_in, Toast.LENGTH_LONG).show()

                reset()
            } else
                Toast.makeText(activity, R.string.notice_sign_in_error, Toast.LENGTH_LONG).show()
        }
        return view
    }

    private fun reset() {
        log_email_et.setText("")
        log_password_et.setText("")
    }
}
