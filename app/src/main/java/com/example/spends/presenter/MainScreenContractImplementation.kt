package com.example.spends.presenter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.spends.data.local.Spend
import com.example.spends.data.local.SpendDao
import com.example.spends.data.local.SpendsDatabase
import com.example.spends.view.MainActivity
import com.example.spends.view.MainScreenContract
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainScreenContractImplementation( private var mainView: MainActivity)
    : MainScreenContract.Presenter,MainScreenContract.Model.OnFinishedListener
{

    lateinit var db: SpendsDatabase

    private lateinit var dao: SpendDao


    override fun addSpend(amount: String, description: String, img: String)= GlobalScope.launch(Dispatchers.IO) {

        try {
            db = SpendsDatabase(mainView)
                    /*Room.databaseBuilder(mainView, SpendsDatabase::class.java, "Spends-Database.db").
            fallbackToDestructiveMigration().build()*/
            dao = db.getSpendDao()
            dao.addSpend(Spend(amount,description,img))

        }
        catch (e : Exception)
        {
           e.printStackTrace()
        }



    }


    private val _last20SpendsLiveData = MutableLiveData<List<Spend>>()
    val last20SpendsLiveData: LiveData<List<Spend>>
        get() = _last20SpendsLiveData


    fun getLast20Spends() = GlobalScope.launch (Dispatchers.IO){
        //signinView = MainScreenContractImpl(this)

        try {
            db = SpendsDatabase(mainView)
                /*Room.databaseBuilder(mainView, SpendsDatabase::class.java, "Spends-Database.db").
            fallbackToDestructiveMigration().build()*/
            dao = db.getSpendDao()
            //_last20SpendsLiveData.value = dao.getLast20Spends()


             var list : List<Spend> = dao.getLast20Spends()
            withContext(Dispatchers.Main)
            {
                onFinished(list)

            }
        }
        catch (e:Exception)
        {
            e.printStackTrace()
        }

    }

    override fun onFinished(list: List<Spend>) {
        if (mainView != null) {
            mainView!!.setString(list)
        }
    }


}