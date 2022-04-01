package com.example.spends.presenter

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.spends.data.remote.ImageResponse
import com.example.spends.data.remote.PixabayAPI
import com.example.spends.others.Event
import com.example.spends.others.Resource
import com.example.spends.view.ImagePickActivity
import com.example.spends.view.ImagePickContract
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ImagePickContractImplementation(private var mainView: ImagePickActivity) :
    ImagePickContract.Presenter,ImagePickContract.Model
{

    private val _curImageUrl = MutableLiveData<String>()
    val curImageUrl: LiveData<String> = _curImageUrl

    private val _images = MutableLiveData<Event<Resource<ImageResponse>>>()
    val images: LiveData<Event<Resource<ImageResponse>>> = _images


    fun setCurImageUrl(url: String) {
        _curImageUrl.postValue(url)
    }

    companion object
    {
        fun providePixabayApi(): PixabayAPI {
            return Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://pixabay.com")
                .build()
                .create(PixabayAPI::class.java)
        }

    }

    override suspend fun searchForImage(str: String) : Resource<ImageResponse>
    {
        return try {
            val response = providePixabayApi().searchForImage(str)
            if(response.isSuccessful) {
                response.body()?.let {
                    onFinished(it)

                    return@let Resource.success(it)
                } ?: Resource.error("An unknown error occured", null)
            } else {
                Resource.error("An unknown error occured", null)
            }
        } catch(e: Exception) {
            Log.e("EXCEPTION", "EXCEPTION:", e)
            Resource.error("Couldn't reach the server. Check your internet connection", null)
        }
    }

    override fun onFinished(list: ImageResponse) {
           mainView.setString(list)
    }



}