package com.example.spends.view

import com.example.spends.data.remote.ImageResponse
import com.example.spends.others.Resource

interface ImagePickContract
{
    interface View
    {
        //fun updateViewOnAdd(toDoList: List<ToDo?>?)

        //fun getLast20Spends(list: List<Spend>)


        // method to set random
        // text on the TextView
        fun setString(list: ImageResponse)

    }

    interface Presenter
    {

      suspend  fun searchForImage( str : String): Resource<ImageResponse>

    }

    interface Model {
            fun onFinished(list: ImageResponse)


    }

}