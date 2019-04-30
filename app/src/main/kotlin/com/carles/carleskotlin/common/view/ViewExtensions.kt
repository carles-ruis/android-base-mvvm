package com.carles.carleskotlin.common.view

import android.support.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.ViewGroup
import com.carles.carleskotlin.R

fun ViewGroup.inflate(@LayoutRes layoutRes: Int) = LayoutInflater.from(context).inflate(layoutRes, this, false)
