package com.example.spends.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.spends.adapter.ImageAdapter
import com.example.spends.data.remote.ImageResponse
import com.example.spends.databinding.ActivityImagePickBinding
import com.example.spends.presenter.ImagePickContractImplementation
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_image_pick.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class ImagePickActivity() : AppCompatActivity(),ImagePickContract.View
{


    @Inject
    lateinit var imageAdapter : ImageAdapter




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // setContentView(R.layout.activity_main)

        var binding: ActivityImagePickBinding = ActivityImagePickBinding.inflate(layoutInflater)

        // binding.root returns the root layout,
        // which is activity_main.xml file itself
        setContentView(binding.root)

        setupRecyclerView()

        // creating object of Presenter interface in Contract
        var presenter: ImagePickContract.Presenter? = null

        // instantiating object of Presenter Interface
        presenter = ImagePickContractImplementation(this)


        var job: Job? = null



        binding.etSearch.addTextChangedListener { editable ->
            job?.cancel()
            job = lifecycleScope.launch {
                delay(300L)
                editable?.let {
                    if(editable.toString().isNotEmpty()) {
                        presenter.searchForImage(editable.toString())
                    }
                }
            }
        }


        imageAdapter.setOnItemClickListener {
            presenter.setCurImageUrl(it)
            val intent = Intent(this, MainActivity::class.java)
            // start your next activity
            intent.putExtra("imgurl",it)
            startActivity(intent)
        }

    }

    private fun subscribeToObservers() {

    }

    private fun setupRecyclerView() {
        rvImages.apply {
            adapter = imageAdapter
            layoutManager = GridLayoutManager(this@ImagePickActivity, 4)
        }
    }

    override fun setString(result: ImageResponse) {

        val url = result.hits.map { imageResult ->imageResult.previewURL  }
        imageAdapter.images = url ?: listOf()
        progressBar.visibility = View.GONE
    }



}
