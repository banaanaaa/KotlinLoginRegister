package com.example.kotlinloginregister

import android.os.Bundle

import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText

import androidx.fragment.app.Fragment
import com.google.android.material.textfield.TextInputEditText
import kotlinx.android.synthetic.main.fragment_register.*
import kotlinx.android.synthetic.main.fragment_register.view.*

class RegisterFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_register, container, false)

        view.reg_btn_sign_in.setOnClickListener {
            activity!!.findViewById<CustomViewPager>(R.id.fragment_page).currentItem = 1
            reset()
        }

        view.reg_btn_accept.setOnClickListener {
            if (isEMailValid() && isLoginValid() && isPasswordValid() && areSimilar()) {
                activity!!.findViewById<TextInputEditText>(R.id.log_email_et).setText(reg_email_et.text.toString())
                activity!!.findViewById<EditText>(R.id.tmp_mail).setText(reg_email_et.text.toString())
                activity!!.findViewById<EditText>(R.id.tmp_pass).setText(reg_password_et.text.toString())

                reset()

                activity!!.findViewById<CustomViewPager>(R.id.fragment_page).currentItem = 1
            } else {
                if (!isEMailValid()) {
                    if (reg_email_et.text!!.isEmpty()) reg_email_l.error = getString(R.string.error_email_none)
                    else reg_email_l.error = getString(R.string.error_email)
                }
                else reg_email_l.error = null

                if (!isLoginValid()) {
                    when (reg_login_et.text.toString().length) {
                        0 -> reg_login_l.error = getString(R.string.error_login_none)
                        else -> reg_login_l.error = getString(R.string.error_login_short)
                    }
                }
                else reg_login_l.error = null

                if (!isPasswordValid()) {
                    when (reg_password_et.text.toString().length) {
                        0 -> reg_password_l.error = getString(R.string.error_password_none)
                        else -> reg_password_l.error = getString(R.string.error_password_short)
                    }
                }
                else reg_password_l.error = null

                if (!areSimilar()) {
                    if (reg_password_repeat_et.text!!.isEmpty()) reg_password_repeat_l.error = getString(R.string.error_repeat_password_none)
                    else reg_password_repeat_l.error = getString(R.string.error_repeat_password)
                }
                else reg_password_repeat_l.error = null
            }
        }
        return view
    }

    private fun isEMailValid() : Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(reg_email_et.text.toString()).matches()
    }

    private fun isLoginValid() : Boolean {
        return reg_login_et.text.toString().length >= 4
    }

    private fun isPasswordValid() : Boolean {
        return reg_password_et.text.toString().length >= 8
    }

    private fun areSimilar() : Boolean {
        return reg_password_repeat_et.text.toString() == reg_password_et.text.toString()
    }

    private fun reset() {
        reg_email_et.setText("")
        reg_login_et.setText("")
        reg_password_et.setText("")
        reg_password_repeat_et.setText("")
    }
}