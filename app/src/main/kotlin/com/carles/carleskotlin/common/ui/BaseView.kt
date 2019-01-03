package com.carles.carleskotlin.common.ui

import android.content.Context

interface BaseView {
    fun getContext() : Context
    fun showProgress()
    fun hideProgress()
    fun showError(messageId: Int, onRetry: (() -> Unit)? = null)
}