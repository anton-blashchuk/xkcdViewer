package com.ablashchuk.xkcdviewer.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ablashchuk.xkcdviewer.data.repository.ComicRepository

/** [ViewModelProvider.Factory] that is responsible for creating and caching our [MainViewModel]
 * and possibly others later on
 * @author Blashchuk Anton */
class MainViewModelFactory(private val repository: ComicRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(repository) as T
    }
}