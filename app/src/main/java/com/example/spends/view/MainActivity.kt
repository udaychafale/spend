package com.example.spends.view

import android.R
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.spends.adapter.SpendsAdapter
import com.example.spends.data.local.Spend
import com.example.spends.data.local.SpendDao
import com.example.spends.data.local.SpendsDatabase
import com.example.spends.data.remote.ImageResponse
import com.example.spends.databinding.ActivityMainBinding
import com.example.spends.presenter.MainScreenContractImplementation
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.view.*
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity()  : AppCompatActivity() ,MainScreenContract.View {


    lateinit var db: SpendsDatabase

    private lateinit var dao: SpendDao

    @Inject
    lateinit var  spendsAdapter: SpendsAdapter

    private lateinit var ss :String

    // creating object of Presenter interface in Contract
    //var presenter: MainScreenContract.Presenter? = null

    var presenter: MainScreenContractImplementation? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)




        var binding: ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)

        // binding.root returns the root layout,
        // which is activity_main.xml file itself
        setContentView(binding.root)

         /*val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.add(binding.framelayout.id, ImagePickFragment())
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
*/

        /*// when app is initially opened the Fragment 1 should be visible
        supportFragmentManager.beginTransaction().apply {
            replace(binding.framelayout.id, ExampleFragment1())
            addToBackStack(null)
            commit()
        }*/


       // setFragment(ImagePickFragment())



        try {
            if(intent.extras != null)
            {
                ss = intent.getStringExtra("imgurl").toString()

            }
            else
            {
                ss=""
            }
        }
        catch (e: java.lang.Exception)
        {
            e.printStackTrace()
        }



        // instantiating object of Presenter Interface
        presenter = MainScreenContractImplementation(this)




        binding.button2.setOnClickListener {
            var ite = binding.editTextItem.text.toString()
            var des = binding.editTextDescription.text.toString()
            var img = ss


            try {
                presenter!!.addSpend(ite,des,img)

            }
            catch (e: Exception)
            {
                 e.printStackTrace()
            }


            presenter!!.getLast20Spends()


        }

        binding.recyclerView.also {
            it.layoutManager = LinearLayoutManager(this)
            it.setHasFixedSize(true)
            it.adapter = spendsAdapter
        }

        if (ss!=null && ss.isNotEmpty())
        {

            val options: RequestOptions = RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.ic_btn_speak_now)
                .error(R.drawable.ic_btn_speak_now)



            Glide.with(this).load(ss).apply(options).into(binding.imageView)


        }
        binding.imageView.setOnClickListener {

            val intent = Intent(this, ImagePickActivity::class.java)
            // start your next activity
            startActivity(intent)
        }

       /* presenter.last20SpendsLiveData.observe(this) { spends ->
            spendsAdapter.spends = spends
        }*/



    }

    override fun onResume() {
        super.onResume()
        presenter?.getLast20Spends()

    }


    override fun setString(string: List<Spend>) {

        var ssss = string
        var dd= string
        spendsAdapter.spends = string


    }

}