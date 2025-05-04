package com.ablashchuk.xkcdviewer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.room.Room
import com.ablashchuk.xkcdviewer.data.access.ApiService
import com.ablashchuk.xkcdviewer.data.access.ComicDatabase
import com.ablashchuk.xkcdviewer.data.datasource.LocalComicDataSource
import com.ablashchuk.xkcdviewer.data.datasource.RemoteComicDataSource
import com.ablashchuk.xkcdviewer.data.repository.ComicRepository
import com.ablashchuk.xkcdviewer.ui.view.MainScreen
import com.ablashchuk.xkcdviewer.ui.viewmodel.MainViewModel
import com.ablashchuk.xkcdviewer.ui.viewmodel.MainViewModelFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //TODO replace with DI

        // Setup Retrofit
        val retrofit = Retrofit.Builder()
            .baseUrl("https://xkcd.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val api = retrofit.create(ApiService::class.java)

        // Setup Room
        val db = Room.databaseBuilder(
            applicationContext,
            ComicDatabase::class.java,
            "comic_db"
        ).build()
        val dao = db.favoriteComicDao()

        // Create data sources
        val remoteComicDataSource = RemoteComicDataSource(api)
        val localComicDataSource = LocalComicDataSource(dao)

        // Create repository
        val repository = ComicRepository(remoteComicDataSource, localComicDataSource)

        // Create ViewModel using a factory
        val factory = MainViewModelFactory(repository)
        val viewModel: MainViewModel by viewModels { factory }

        setContent {
            MainScreen(viewModel)
        }
    }
}
