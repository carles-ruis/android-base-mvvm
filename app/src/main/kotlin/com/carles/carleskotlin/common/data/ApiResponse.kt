package com.carles.carleskotlin.common.data

import retrofit2.Response

/**
 * Common class used by API responses.
 * @param <T> the type of the response object
</T> */
@Suppress("unused") // T is used in extending classes
sealed class ApiResponse<in T> {

    companion object {
        fun <T> create(error: Throwable) = ApiErrorResponse<T>(error.message ?: "unknown error")

        fun <T> create(response: Response<T>) = if (response.isSuccessful) {
            if (response.body() == null || response.code() == 204) ApiEmptyResponse<T>() else ApiSuccessResponse(response.body())
        } else {
            ApiErrorResponse(response.errorBody()?.string() ?: response.message())
        }
    }
}

/**
 * separate class for HTTP 204 responses so that we can make ApiSuccessResponse's body non-null.
 */
class ApiEmptyResponse<T> : ApiResponse<T>()
data class ApiSuccessResponse<T>(val body: T) : ApiResponse<T>()
data class ApiErrorResponse<T>(val errorMessage: String) : ApiResponse<T>()
