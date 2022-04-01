package com.example.spends.adapter

import android.R
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.spends.data.local.Spend
import com.example.spends.databinding.ItemSpendBinding
import javax.inject.Inject

class SpendsAdapter @Inject constructor(context: Context): RecyclerView.Adapter<SpendsAdapter.SpendViewHolder>() {

    lateinit var context1 :Context
    var spends: List<Spend> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    init {
        context1 = context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = SpendViewHolder(
        ItemSpendBinding.inflate(LayoutInflater.from(parent.context), parent, false)

    )

    override fun onBindViewHolder(holder: SpendViewHolder, position: Int) = with(holder.binding) {
        //textViewDate.text = spends[position].date.toReadableString()
        var des= spends[position].description.toString()
        var amount = spends[position].amount.toString()
        textViewDesc.text = spends[position].description.toString()
        textViewDescOne.text = spends[position].amount.toString()

        var ss= spends[position].img.toString()

        if (ss!=null && ss.isNotEmpty())
        {

            val options: RequestOptions = RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.ic_btn_speak_now)
                .error(R.drawable.ic_btn_speak_now)



            Glide.with(context1).load(ss).apply(options).into(imageView2)


            //binding.imageView.setImageBitmap(ss)
        }
    }

    override fun getItemCount() = spends.size

    fun getItemAtPosition(position: Int) = spends[position]

    inner class SpendViewHolder(val binding: ItemSpendBinding) :
        RecyclerView.ViewHolder(binding.root)
}