package com.carles.carleskotlin.common.view

import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.View.GONE
import android.view.View.VISIBLE
import com.carles.carleskotlin.R
import kotlinx.android.synthetic.main.layout_progressbar.*

abstract class BaseActivity : AppCompatActivity() {

    protected var alertDialog: AlertDialog? = null
    protected abstract val layoutResourceId: Int
    protected abstract fun initViews()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransition(R.anim.slide_in_from_right_to_left, R.anim.slide_out_from_right_to_left)
        setContentView(layoutResourceId)
        initViews()
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.slide_in_from_left_to_right, R.anim.slide_out_from_left_to_right)
    }

    protected fun showProgress() {
        progressbar_layout?.visibility = VISIBLE
    }

    protected fun hideProgress() {
        progressbar_layout?.visibility = GONE
    }

    protected fun showError(messageId: Int, onRetry: (() -> Unit)? =  null) {
        showError(getString(messageId), onRetry)
    }

    protected fun showError(message: String?, onRetry: (() -> Unit)? = null) {
        hideProgress()
        if (isFinishing) return

        val isShowing = alertDialog?.isShowing ?: false
        if (!isShowing) {
            val alertDialogBuilder = AlertDialog.Builder(this).setMessage(message)
            if (onRetry != null) {
                alertDialogBuilder.setCancelable(false)
                    .setPositiveButton(R.string.error_retry) { _, _ -> onRetry() }
            }
            alertDialogBuilder.create().show()
        }
    }
}