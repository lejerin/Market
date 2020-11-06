package com.example.shopping.data.network

import com.example.shopping.data.model.LoginResponse
import retrofit2.Call
import retrofit2.Response
import java.io.IOException

abstract class SafeApiRequest {

//    suspend fun <T: Any> apiRequest(call: suspend () -> Response<T>) : T{
//        val response = call.invoke()
//        if(response.isSuccessful){
//            return response.body()!!
//        }else{
//            System.out.println("오류" + response.code().toString())
////            throw  ApiException(
////                response.code().toString()
////            )
//            return response.code()
//        }
//
//    }

    suspend fun<T : Any> apiRequest(call: suspend()-> Response<T>) : Output<T>{
        val response = call.invoke()
        return if (response.isSuccessful)
            Output.Success(response.body()!!)
        else
            Output.Error(IOException(response.message()))
    }
}

class ApiException(message: String) : IOException(message)

sealed class Output<out T : Any>{
    data class Success<out T : Any>(val output : T) : Output<T>()
    data class Error(val exception: Exception)  : Output<Nothing>()
}



