package com.example.kotlinloginregister

import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText

import androidx.fragment.app.Fragment
import android.content.Intent
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import kotlinx.android.synthetic.main.fragment_reset_password.*

class ResetPasswordFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_reset_password, container, false)

        val buttonCancel = view.findViewById<MaterialButton>(R.id.res_pass_btn_cancel)
        val buttonAccept = view.findViewById<MaterialButton>(R.id.res_pass_btn_accept)
        val buttonSignUp = view.findViewById<MaterialButton>(R.id.res_pass_btn_sign_up)

        buttonCancel.setOnClickListener {
            activity!!.findViewById<CustomViewPager>(R.id.fragment_page).currentItem = 1
            reset()
        }

        buttonSignUp.setOnClickListener {
            activity!!.findViewById<CustomViewPager>(R.id.fragment_page).currentItem = 0
            reset()
        }

        buttonAccept.setOnClickListener {
            if (isEMailValid() && isPasswordValid() && areSimilar()) {
                activity!!.findViewById<CustomViewPager>(R.id.fragment_page).currentItem = 1

                sendEmail(res_pass_email_et.text.toString(), res_pass_password_et.text.toString())

                activity!!.findViewById<TextInputEditText>(R.id.log_email_et).setText(res_pass_email_et.text.toString())
                activity!!.findViewById<EditText>(R.id.tmp_mail).setText(res_pass_email_et.text.toString())
                activity!!.findViewById<EditText>(R.id.tmp_pass).setText(res_pass_password_et.text.toString())

                reset()
            } else {
                if (!isEMailValid()) {
                    if (res_pass_email_et.text!!.isEmpty()) res_pass_email_l.error = getString(R.string.error_email_none)
                    else res_pass_email_l.error = getString(R.string.error_email)
                }
                else res_pass_email_l.error = null

                if (!isPasswordValid()) {
                    when (res_pass_password_et.text.toString().length) {
                        0 -> res_pass_password_l.error = getString(R.string.error_password_none)
                        else -> res_pass_password_l.error = getString(R.string.error_password_short)
                    }
                }
                else res_pass_password_l.error = null

                if (!areSimilar()) {
                    if (res_pass_password_repeat_et.text!!.isEmpty()) res_pass_password_repeat_l.error = getString(R.string.error_repeat_password_none)
                    else res_pass_password_repeat_l.error = getString(R.string.error_repeat_password)
                }
                else res_pass_password_repeat_l.error = null
            }
        }
        return view
    }

    private fun isEMailValid() : Boolean {
        return !TextUtils.isEmpty(res_pass_email_et.text.toString()) && Patterns.EMAIL_ADDRESS.matcher(res_pass_email_et.text.toString()).matches() && res_pass_email_et.text!!.isNotEmpty()
    }

    private fun isPasswordValid() : Boolean {
        return res_pass_password_et.text.toString().length >= 8
    }

    private fun areSimilar() : Boolean {
        return res_pass_password_repeat_et.text.toString() == res_pass_password_et.text.toString()
    }

    private fun reset() {
        res_pass_email_et.setText("")
        res_pass_password_et.setText("")
        res_pass_password_repeat_et.setText("")
    }

    private fun sendEmail(to: String, newPassword: String) {
        val email = Intent(Intent.ACTION_SEND)
        email.putExtra(Intent.EXTRA_EMAIL, arrayOf(to))
        email.putExtra(Intent.EXTRA_SUBJECT, R.string.text_your_password)
        email.putExtra(Intent.EXTRA_TEXT, newPassword)

        email.type = "message/rfc822"

        startActivity(Intent.createChooser(email, getString(R.string.text_select_email_client)))
    }
}