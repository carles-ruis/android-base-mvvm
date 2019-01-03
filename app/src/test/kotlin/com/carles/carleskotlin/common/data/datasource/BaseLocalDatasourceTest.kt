package com.carles.carleskotlin.common.data.datasource

import android.content.SharedPreferences
import com.carles.carleskotlin.common.getCacheExpirationTime
import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class BaseLocalDatasourceTest {

    private lateinit var datasource: BaseLocalDatasource
    private val sharedPreferences: SharedPreferences = mockk(relaxed = true)

    @Before
    fun setup() {
        datasource = object : BaseLocalDatasource(sharedPreferences) {}
    }

    @Test
    fun calculateCacheExpirationTime_shouldReturnFutureTime() {
        assertTrue(datasource.calculateCacheExpirationTime() > System.currentTimeMillis())
    }

    @Test
    fun isExpired_shouldCheckSharedPreferences() {
        datasource.isExpired("someclass", "someid")
        verify { sharedPreferences.getCacheExpirationTime("someclass", "someid") }
    }
}