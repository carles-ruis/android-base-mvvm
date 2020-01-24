package com.carles.carleskotlin.common.data

import android.content.SharedPreferences
import com.carles.carleskotlin.R

private val PREFERENCE_EXPIRATION_TIME_PREFIX = "expiration_time_"

fun SharedPreferences.getCacheExpirationTime(className:String?, itemId:String) : Long =
        getLong(PREFERENCE_EXPIRATION_TIME_PREFIX + className + itemId, 0L)

fun SharedPreferences.setCacheExpirationTime(className: String?, itemId: String, timestamp:Long) {
    edit().putLong(PREFERENCE_EXPIRATION_TIME_PREFIX + className + itemId, timestamp).apply()
}

fun Throwable.getMessageId() : Int = if (message == null) R.string.error_server_response else R.string.error_server_response

val <T : Any> T.cacheId : String
    get() = javaClass.simpleName
