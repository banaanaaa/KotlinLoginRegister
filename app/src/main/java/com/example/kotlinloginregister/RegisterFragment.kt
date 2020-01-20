package com.example.kotlinloginregister

import android.os.Bundle

import android.text.TextUtils
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

import androidx.fragment.app.Fragment

class RegisterFragment : Fragment() {

    private var eMail: EditText? = null
    private var login: EditText? = null
    private var password: EditText? = null
    private var repeatPassword: EditText? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_register, container, false)

        eMail = view.findViewById(R.id.reg_EMail) as EditText
        login = view.findViewById(R.id.reg_Login) as EditText
        password = view.findViewById(R.id.reg_Password) as EditText
        repeatPassword = view.findViewById(R.id.reg_RepeatPassword) as EditText

        val buttonSignIn = view.findViewById(R.id.button_sign_in) as Button
        val buttonAccept = view.findViewById(R.id.reg_btn_Accept) as Button

        buttonSignIn.setOnClickListener {
            activity!!.findViewById<CustomViewPager>(R.id.fragment_page).currentItem = 1
        }

        buttonAccept.setOnClickListener {
            if (isEMailValid() && isLoginValid() && isPasswordValid() && areSimilar()) {
                eMail!!.setBackgroundResource(R.drawable.field_valid)
                login!!.setBackgroundResource(R.drawable.field_valid)
                password!!.setBackgroundResource(R.drawable.field_valid)
                repeatPassword!!.setBackgroundResource(R.drawable.field_valid)
                activity!!.findViewById<CustomViewPager>(R.id.fragment_page).currentItem = 1
                activity!!.findViewById<EditText>(R.id.log_EMail).setText(eMail!!.text.toString())
                activity!!.findViewById<EditText>(R.id.tmp_mail).setText(eMail!!.text.toString())
                activity!!.findViewById<EditText>(R.id.tmp_pass).setText(password!!.text.toString())
                reset()
            } else {
                var tmp = ""
                if (!isEMailValid()) {
                    eMail!!.setBackgroundResource(R.drawable.field_none_valid)
                    tmp += "Incorrect spelling of the email address"
                } else
                    eMail!!.setBackgroundResource(R.drawable.field_valid)

                if (!isLoginValid()) {
                    login!!.setBackgroundResource(R.drawable.field_none_valid)
                    when (tmp == "") {
                        true -> tmp += "Short login (must be more than 4 characters)"
                        false -> tmp += "\nShort login (must be more than 4 characters)"
                    }
                } else
                    login!!.setBackgroundResource(R.drawable.field_valid)

                if (!isPasswordValid()) {
                    password!!.setBackgroundResource(R.drawable.field_none_valid)
                    when (tmp == "") {
                        true -> tmp += "Short password (must be more than 8 characters)"
                        false -> tmp += "\nShort password (must be more than 8 characters)"
                    }
                } else
                    password!!.setBackgroundResource(R.drawable.field_valid)

                if (!areSimilar()) {
                    repeatPassword!!.setBackgroundResource(R.drawable.field_none_valid)
                    when (tmp == "") {
                        true -> tmp += "Passwords are different"
                        false -> tmp += "\nPasswords are different"
                    }
                } else
                    repeatPassword!!.setBackgroundResource(R.drawable.field_valid)

                Toast.makeText(activity, tmp, Toast.LENGTH_LONG).show()
            }
        }

        return view
    }

    private fun isEMailValid() : Boolean {
        return !TextUtils.isEmpty(eMail!!.text.toString()) && Patterns.EMAIL_ADDRESS.matcher(eMail!!.text.toString()).matches()
    }

    private fun isLoginValid() : Boolean {
        return login!!.text.toString().length >= 4
    }

    private fun isPasswordValid() : Boolean {
        return password!!.text.toString().length >= 8
    }

    private fun areSimilar() : Boolean {
        return password!!.text.toString() == repeatPassword!!.text.toString()
    }

    private fun reset() {
        eMail!!.setText("")
        login!!.setText("")
        password!!.setText("")
        repeatPassword!!.setText("")
    }
}