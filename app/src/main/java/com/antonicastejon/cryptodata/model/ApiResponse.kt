package com.antonicastejon.cryptodata.model

import retrofit2.Response

class ApiResponse<T>(response: Response<T>?, error: Throwable? = null) {

    val code: Int = response?.code() ?: 500
    val body: T?
    val errorMessage: String?
    val isSuccessful = code in 200..299

    init {
        if (response != null) {
            if (response.isSuccessful) {
                body = response.body()
                errorMessage = null
            } else {
                body = null
                errorMessage = response.errorBody()?.string() ?: response.message()
            }
        } else {
            body = null
            errorMessage = error?.message ?: ""
        }
    }
}