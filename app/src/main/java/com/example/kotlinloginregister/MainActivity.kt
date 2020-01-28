package com.example.kotlinloginregister

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Toast

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_login.view.*
import kotlinx.android.synthetic.main.fragment_register.*
import kotlinx.android.synthetic.main.fragment_reset_password.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fragment_page.setPagingEnabled(false)

        val adapterViewPager = MyAdapter(supportFragmentManager)

        fragment_page.adapter = adapterViewPager
        fragment_page.currentItem = 1
    }

    class MyAdapter(fm: FragmentManager) :
        FragmentPagerAdapter(fm, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

        override fun getCount(): Int {
            return 3
        }

        override fun getItem(position: Int): Fragment {
            when (position) {
                0 -> return RegisterFragment()
                1 -> return LoginFragment()
                2 -> return ResetPasswordFragment()
                else -> return RegisterFragment()
            }
        }
    }

    fun forgotPasswordClick(v: View) {
        fragment_page.currentItem = 2
        reset(1)
    }

    fun acceptClick(v: View) {
        when (v.id) {
            R.id.log_btn_accept -> {
                if (log_email_et.text.toString() == this.tmp_mail.text.toString() && log_password_et.text.toString() == this.tmp_pass.text.toString()) {
                    val intent = Intent(this, PlugActivity::class.java)
                    startActivity(intent)
                    Toast.makeText(this, R.string.notice_logged_in, Toast.LENGTH_LONG).show()

                    reset(1)
                } else
                    Toast.makeText(this, R.string.notice_sign_in_error, Toast.LENGTH_LONG).show()
            }
            R.id.reg_btn_accept -> {
                if (isEMailValid(v) && isLoginValid() && isPasswordValid(v) && areSimilar(v)) {
                    regComplete(reg_email_et, reg_password_et)
                    fragment_page.currentItem = 1
                    reset(2)
                } else {
                    setEMailError(v, reg_email_et, reg_email_l)

                    if (!isLoginValid()) {
                        when (reg_login_et.text.toString().length) {
                            0 -> reg_login_l.error = getString(R.string.error_login_none)
                            else -> reg_login_l.error = getString(R.string.error_login_short)
                        }
                    } else reg_login_l.error = null

                    setPasswordError(v, reg_password_et, reg_password_l)
                    setRepeatPasswordError(v, reg_password_repeat_et, reg_password_repeat_l)
                }
            }
            R.id.res_pass_btn_accept -> {
                if (isEMailValid(v) && isPasswordValid(v) && areSimilar(v)) {
                    regComplete(res_pass_email_et, res_pass_password_et)
                    fragment_page.currentItem = 1
                    sendEmail(res_pass_email_et.text.toString(), res_pass_password_et.text.toString())
                    reset(3)
                } else {
                    setEMailError(v, res_pass_email_et, res_pass_email_l)
                    setPasswordError(v, res_pass_password_et, res_pass_password_l)
                    setRepeatPasswordError(v, res_pass_password_repeat_et, res_pass_password_repeat_l)
                }
            }
        }
    }

    fun signInClick(v: View) {
        fragment_page.currentItem = 1
        reset(2)
    }

    fun cancelClick(v: View) {
        fragment_page.currentItem = 1
        reset(3)
    }

    fun signUpClick(v: View) {
        fragment_page.currentItem = 0
        when (v.id) {
            R.id.log_btn_sign_up -> reset(1)
            R.id.res_pass_btn_sign_up ->  reset(3)
        }
    }

    private fun regComplete(e_m: TextInputEditText, e_p: TextInputEditText) {
        log_email_et.setText(e_m.text.toString())
        tmp_mail.setText(e_m.text.toString())
        tmp_pass.setText(e_p.text.toString())
    }

    private fun setEMailError(v: View, e: TextInputEditText, l: TextInputLayout) {
        if (!isEMailValid(v)) {
            if (e.text!!.isEmpty()) l.error = getString(R.string.error_email_none)
            else l.error = getString(R.string.error_email)
        } else l.error = null
    }

    private fun setPasswordError(v: View, e: TextInputEditText, l: TextInputLayout) {
        if (!isPasswordValid(v)) {
            when (e.text.toString().length) {
                0 -> l.error = getString(R.string.error_password_none)
                else -> l.error = getString(R.string.error_password_short)
            }
        } else l.error = null
    }

    private fun setRepeatPasswordError(v: View, e: TextInputEditText, l: TextInputLayout) {
        if (!areSimilar(v)) {
            if (e.text!!.isEmpty()) l.error = getString(R.string.error_repeat_password_none)
            else l.error = getString(R.string.error_repeat_password)
        } else l.error = null
    }

    private fun sendEmail(to: String, newPassword: String) {
        val email = Intent(Intent.ACTION_SEND)
        email.putExtra(Intent.EXTRA_EMAIL, arrayOf(to))
        email.putExtra(Intent.EXTRA_SUBJECT, R.string.text_your_password)
        email.putExtra(Intent.EXTRA_TEXT, newPassword)

        email.type = "message/rfc822"

        startActivity(Intent.createChooser(email, getString(R.string.text_select_email_client)))
    }

    private fun isEMailValid(v: View): Boolean {
        var bool = false
        when (v.id) {
            R.id.reg_btn_accept -> bool = Patterns.EMAIL_ADDRESS.matcher(reg_email_et.text.toString()).matches()
            R.id.res_pass_btn_accept -> bool = Patterns.EMAIL_ADDRESS.matcher(res_pass_email_et.text.toString()).matches()
        }
        return bool
    }

    private fun isLoginValid(): Boolean {
        return reg_login_et.text.toString().length >= 4
    }

    private fun isPasswordValid(v: View): Boolean {
        var bool = false
        when (v.id) {
            R.id.reg_btn_accept -> bool = reg_password_et.text.toString().length >= 8
            R.id.res_pass_btn_accept -> bool = res_pass_password_et.text.toString().length >= 8
        }
        return bool
    }

    private fun areSimilar(v: View): Boolean {
        var bool = false
        when (v.id) {
            R.id.reg_btn_accept -> bool = reg_password_repeat_et.text.toString() == reg_password_et.text.toString()
            R.id.res_pass_btn_accept -> bool = res_pass_password_repeat_et.text.toString() == res_pass_password_et.text.toString()
        }
        return bool
    }

    private fun reset(id: Int) {
        when (id) {
            1 -> {
                log_email_et.setText("")
                log_password_et.setText("")
            }
            2 -> {
                reg_email_et.setText("")
                reg_login_et.setText("")
                reg_password_et.setText("")
                reg_password_repeat_et.setText("")
            }
            3 -> {
                res_pass_email_et.setText("")
                res_pass_password_et.setText("")
                res_pass_password_repeat_et.setText("")
            }
        }
    }
}