package com.ablashchuk.xkcdviewer.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ablashchuk.xkcdviewer.data.repository.ComicRepository

/** @author Blashchuk Anton */
class MainViewModelFactory(private val repository: ComicRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(repository) as T
    }
}