package com.example.spends.view

import com.example.spends.data.local.Spend
import com.example.spends.data.remote.ImageResponse
import kotlinx.coroutines.Job

interface MainScreenContract
{

    interface View
    {
        //fun updateViewOnAdd(toDoList: List<ToDo?>?)

        //fun getLast20Spends(list: List<Spend>)


        // method to set random
        // text on the TextView
        fun setString(list: List<Spend>)

    }

    interface Presenter
    {

        fun addSpend(amount: String, description: String, img: String): Job

    }

    interface Model {
        // nested interface to be
        interface OnFinishedListener {
            // function to be called
            // once the Handler of Model class
            // completes its execution
            fun onFinished(list: List<Spend>)
        }

    }
}