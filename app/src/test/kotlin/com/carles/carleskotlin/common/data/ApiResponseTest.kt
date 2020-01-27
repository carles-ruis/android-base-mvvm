package com.carles.carleskotlin.common.data

import com.carles.carleskotlin.common.model.ApiEmptyResponse
import com.carles.carleskotlin.common.model.ApiErrorResponse
import com.carles.carleskotlin.common.model.ApiResponse
import com.carles.carleskotlin.common.model.ApiSuccessResponse
import io.mockk.every
import io.mockk.mockk
import org.junit.Test
import retrofit2.Response
import java.lang.RuntimeException

class ApiResponseTest {

    val response : Response<String> = mockk(relaxed = true)

    @Test
    fun create_serverErrorResponse() {
        assert(ApiResponse.create<String>(Throwable()).errorMessage == "unknown error" )
        assert(ApiResponse.create<String>(RuntimeException("nice exception")).errorMessage == "nice exception")
    }

    @Test
    fun create_serverSuccessResponse() {
        every { response.isSuccessful } returns false
        assert(ApiResponse.create(response) is ApiErrorResponse<String>)

        every { response.isSuccessful } returns true
        every { response.code() } returns 204
        assert(ApiResponse.create(response) is ApiEmptyResponse<String>)

        every { response.code() } returns 200
        every { response.body() } returns "hello"
        assert((ApiResponse.create(response) as ApiSuccessResponse).body == "hello")
    }

}