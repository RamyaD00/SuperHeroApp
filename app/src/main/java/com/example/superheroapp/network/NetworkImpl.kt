package com.example.superheroapp.network

import android.os.Handler
import android.os.Looper
import com.example.superheroapp.model.HerosResponse
import com.google.gson.Gson
import okhttp3.*
import java.io.IOException
import java.net.URL

class NetworkImpl : NetworkApi {

    override fun getHerosList(jsonResponse: JsonResponse) {

        val urlData = URL("https://akabab.github.io/superhero-api/api/all.json")
        val request = Request.Builder()
        request.url(urlData)

        OkHttpClient().newCall(request.build()).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Handler(Looper.getMainLooper()).post {
                    jsonResponse.onFailure()
                }
            }
            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    try {
                        val responseStr = response.body!!.string()
                        val fromJson = Gson().fromJson(responseStr, Array<HerosResponse>::class.java)
                        Handler(Looper.getMainLooper()).post {
                            jsonResponse.onSuccess(fromJson.toList())
                        }
                    } catch (e: Exception) {
                        Handler(Looper.getMainLooper()).post {
                            jsonResponse.onFailure()
                        }
                    }
                }
            }
        })

    }

}


interface JsonResponse{
    fun onSuccess(list : List<HerosResponse>)
    fun onFailure()
}