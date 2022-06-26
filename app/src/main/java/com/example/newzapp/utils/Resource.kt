package com.example.newzapp.utils

sealed class Resource<T>(
    val myData: T? = null,
    val currMessage: String?= null
){
    class SuccessCL<T>(data: T): Resource<T>(data)
    class ErrorCL<T>(message: String, data: T? = null): Resource<T>(data, message)
    class LoadingCL<T>: Resource<T>()
}