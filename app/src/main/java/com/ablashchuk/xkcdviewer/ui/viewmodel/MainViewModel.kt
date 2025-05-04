package com.ablashchuk.xkcdviewer.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ablashchuk.xkcdviewer.data.model.Comic
import com.ablashchuk.xkcdviewer.data.repository.ComicRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.random.Random

/** [ViewModel] for the [com.ablashchuk.xkcdviewer.ui.view.MainScreen]. Takes in [ComicRepository] to
 * operate, so is a bit trickier to instantiate than usual "val viewModel: MainViewModel by viewModels()"
 * @author Blashchuk Anton */
class MainViewModel(private val repository: ComicRepository) : ViewModel() {

    private val _comic = MutableStateFlow<Comic?>(null)
    val comic: StateFlow<Comic?> = _comic.asStateFlow()

    private val _isFavorite = MutableStateFlow<Boolean>(false)
    private val isFavorite: StateFlow<Boolean> = _isFavorite.asStateFlow()

    /** Index of comic we are showing now */
    private var currentIndex: Int? = null
    /** Index of most recently published comic */
    private var lastIndex: Int? = null


    init {
        viewModelScope.launch {
            //if there are favorites we immediately know some last index(although it would most likely be smaller than actual)
            lastIndex = repository.getFavorites().maxOfOrNull { favorite -> favorite.num }
            loadStartingComic()
        }
    }

    fun nextAvailable() = (lastIndex ?: 0) - (currentIndex ?: 1) > 0 // last is known and current is known and lesser

    fun next() {
        val curIndex = currentIndex!!
        val maxIndex = lastIndex!!
        if (curIndex < maxIndex) {
            currentIndex = curIndex + 1
            loadComic()
        }
    }

    fun previousAvailable() = (currentIndex ?: 1) > 1 //current is not null and not "first"

    fun previous() {
        val curIndex = currentIndex!!
        if ((curIndex) > 1) {
            currentIndex = curIndex - 1
            loadComic()
        }
    }

    fun randomAvailable() = (lastIndex ?: 1) > 1 //last is available, so we know the end of range for random

    fun random() {
        lastIndex?.also {
            currentIndex = Random.nextInt(it)
            loadComic()
        }
    }

    fun firstAvailable() = previousAvailable()

    fun first() {
        if (currentIndex == 1) {
            return
        }
        currentIndex = 1
        loadComic()
    }

    fun lastAvailable() = nextAvailable()

    fun last() {
        lastIndex?.also {
            if (currentIndex == it) {
                return
            }
            currentIndex = it
            loadComic()
        }
    }

    private fun loadComic() {
        viewModelScope.launch {
            currentIndex?.let { curIndex ->
                val result = repository.getComic(curIndex)
                _comic.value = result
                _isFavorite.value = repository.isFavorite(result.num)
            }
        }
    }

    private fun loadStartingComic() {
        viewModelScope.launch {
            val result = repository.getLastComic()
            _comic.value = result
            currentIndex = result.num
            lastIndex = result.num
            _isFavorite.value = repository.isFavorite(result.num)
        }
    }


}