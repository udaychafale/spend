package com.example.spends.di

import android.content.Context
import androidx.room.Room
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.spends.R
import com.example.spends.adapter.SpendsAdapter
import com.example.spends.data.local.SpendsDatabase
import com.example.spends.data.remote.PixabayAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule
{

    @Singleton
    @Provides
    fun provideShoppingItemDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(context, SpendsDatabase::class.java, "Spends-Database.db").build()

    @Singleton
    @Provides
    fun provideShoppingDao(
        database: SpendsDatabase
    ) = database.getSpendDao()

    @Singleton
    @Provides
    fun provideGlideInstance(
        @ApplicationContext context: Context
    ) = Glide.with(context).setDefaultRequestOptions(
        RequestOptions()
            .placeholder(R.drawable.ic_image)
            .error(R.drawable.ic_image)
    )

    @Singleton
    @Provides
    fun providePixabayApi(): PixabayAPI {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://pixabay.com")
            .build()
            .create(PixabayAPI::class.java)
    }

    @Singleton
    @Provides
    fun provideSpendsAdapter(@ApplicationContext context: Context)= SpendsAdapter(context)
}