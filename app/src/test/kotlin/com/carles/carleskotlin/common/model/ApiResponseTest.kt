package com.carles.carleskotlin.common.model

import com.carles.carleskotlin.common.model.ApiEmptyResponse
import com.carles.carleskotlin.common.model.ApiErrorResponse
import com.carles.carleskotlin.common.model.ApiResponse
import com.carles.carleskotlin.common.model.ApiSuccessResponse
import io.mockk.every
import io.mockk.mockk
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue
import org.assertj.core.api.Assertions
import org.junit.Test
import retrofit2.Response
import java.lang.RuntimeException

class ApiResponseTest {

    val response: Response<String> = mockk(relaxed = true)

    @Test
    fun create_error() {
        Assertions.assertThat(ApiResponse.create<String>(Throwable()).errorMessage).isEqualTo("unknown error")
        Assertions.assertThat(ApiResponse.create<String>(RuntimeException("nice exception")).errorMessage).isEqualTo("nice exception")
    }

    @Test
    fun create_success() {
        every { response.isSuccessful } returns false
        Assertions.assertThat(ApiResponse.create(response)).isInstanceOf(ApiErrorResponse::class.java)

        every { response.isSuccessful } returns true
        every { response.code() } returns 204
        Assertions.assertThat(ApiResponse.create(response)).isInstanceOf(ApiEmptyResponse::class.java)

        every { response.code() } returns 200
        every { response.body() } returns "hello"
        Assertions.assertThat((ApiResponse.create(response) as ApiSuccessResponse).body).isEqualTo("hello")
    }

}