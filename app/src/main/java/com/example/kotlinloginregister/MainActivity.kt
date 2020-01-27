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
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_login.*
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

    fun logFragmentSignUpClick(v: View) {
        fragment_page.currentItem = 0
        resetLog()
    }

    fun logFragmentResetPassClick(v: View) {
        fragment_page.currentItem = 2
        resetLog()
    }

    fun logFragmentAcceptClick(v: View) {
        if (log_email_et.text.toString() == this.tmp_mail.text.toString() && log_password_et.text.toString() == this.tmp_pass.text.toString()) {
            val intent = Intent(this, PlugActivity::class.java)
            startActivity(intent)
            Toast.makeText(this, R.string.notice_logged_in, Toast.LENGTH_LONG).show()

            resetLog()
        } else
            Toast.makeText(this, R.string.notice_sign_in_error, Toast.LENGTH_LONG).show()
    }

    fun regFragmentSignInClick(v: View) {
        fragment_page.currentItem = 1
        resetReg()
    }

    fun regFragmentAcceptClick(v: View) {
        if (isEMailValid(v) && isLoginValid() && isPasswordValid(v) && areSimilar(v)) {
            log_email_et.setText(reg_email_et.text.toString())
            tmp_mail.setText(reg_email_et.text.toString())
            tmp_pass.setText(reg_password_et.text.toString())

            resetReg()

            fragment_page.currentItem = 1
        } else {
            if (!isEMailValid(v)) {
                if (reg_email_et.text!!.isEmpty()) reg_email_l.error =
                    getString(R.string.error_email_none)
                else reg_email_l.error = getString(R.string.error_email)
            } else reg_email_l.error = null

            if (!isLoginValid()) {
                when (reg_login_et.text.toString().length) {
                    0 -> reg_login_l.error = getString(R.string.error_login_none)
                    else -> reg_login_l.error = getString(R.string.error_login_short)
                }
            } else reg_login_l.error = null

            if (!isPasswordValid(v)) {
                when (reg_password_et.text.toString().length) {
                    0 -> reg_password_l.error = getString(R.string.error_password_none)
                    else -> reg_password_l.error = getString(R.string.error_password_short)
                }
            } else reg_password_l.error = null

            if (!areSimilar(v)) {
                if (reg_password_repeat_et.text!!.isEmpty()) reg_password_repeat_l.error =
                    getString(R.string.error_repeat_password_none)
                else reg_password_repeat_l.error = getString(R.string.error_repeat_password)
            } else reg_password_repeat_l.error = null
        }
    }

    fun resFragmentCancelClick(v: View) {
        fragment_page.currentItem = 1
        resetRes()
    }

    fun resFragmentSignUpClick(v: View) {
        fragment_page.currentItem = 0
        resetRes()
    }

    fun resFragmentAcceptClick(v: View) {
        if (isEMailValid(v) && isPasswordValid(v) && areSimilar(v)) {
            fragment_page.currentItem = 1

            sendEmail(res_pass_email_et.text.toString(), res_pass_password_et.text.toString())

            log_email_et.setText(res_pass_email_et.text.toString())
            tmp_mail.setText(res_pass_email_et.text.toString())
            tmp_pass.setText(res_pass_password_et.text.toString())

            resetRes()
        } else {
            if (!isEMailValid(v)) {
                if (res_pass_email_et.text!!.isEmpty()) res_pass_email_l.error = getString(R.string.error_email_none)
                else res_pass_email_l.error = getString(R.string.error_email)
            }
            else res_pass_email_l.error = null

            if (!isPasswordValid(v)) {
                when (res_pass_password_et.text.toString().length) {
                    0 -> res_pass_password_l.error = getString(R.string.error_password_none)
                    else -> res_pass_password_l.error = getString(R.string.error_password_short)
                }
            }
            else res_pass_password_l.error = null

            if (!areSimilar(v)) {
                if (res_pass_password_repeat_et.text!!.isEmpty()) res_pass_password_repeat_l.error = getString(R.string.error_repeat_password_none)
                else res_pass_password_repeat_l.error = getString(R.string.error_repeat_password)
            }
            else res_pass_password_repeat_l.error = null
        }
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
            R.id.res_pass_btn_accept -> bool = Patterns.EMAIL_ADDRESS.matcher(res_pass_email_et.text.toString()).matches() &&
                    res_pass_email_et.text!!.isNotEmpty()
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

    private fun resetLog() {
        log_email_et.setText("")
        log_password_et.setText("")
    }

    private fun resetReg() {
        reg_email_et.setText("")
        reg_login_et.setText("")
        reg_password_et.setText("")
        reg_password_repeat_et.setText("")
    }

    private fun resetRes() {
        res_pass_email_et.setText("")
        res_pass_password_et.setText("")
        res_pass_password_repeat_et.setText("")
    }
}