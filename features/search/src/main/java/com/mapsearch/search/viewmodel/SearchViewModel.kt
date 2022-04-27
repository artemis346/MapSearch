package com.mapsearch.search.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mapsearch.repositories.ISearchRepository
import com.mapsearch.repositories.ISelectedRepository
import com.mapsearch.search.mapper.mapToItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: ISearchRepository,
    private val selectedRepository: ISelectedRepository
) : ViewModel() {

    private val listData =
        MutableStateFlow<SearchUiState>(SearchUiState.Initial)
    val uiState: StateFlow<SearchUiState> = listData

    companion object {
        const val SEARCH_DEBOUNCE = 1000L
        const val MIN_QUERY_LENGTH = 2
    }

    fun selectItem(id: String) {
        viewModelScope.launch {
            flow {
                selectedRepository.selectItem(id)
                emit(Unit)
            }.flowOn(Dispatchers.IO)
                .collect {
                listData.value = SearchUiState.ItemSelected
            }
        }
    }

    fun startSearchWithDelay(query: String) {
        viewModelScope.launch {
            flowOf(query)
                .dropWhile {
                    it.length < MIN_QUERY_LENGTH
                }
                .debounce(SEARCH_DEBOUNCE)
                .collect {
                    repository.searchByQuery(query = query)
                        .onStart {
                            listData.value = SearchUiState.Loading
                        }
                        .map { list ->
                            list.map { it.mapToItem() }
                        }
                        .catch { ex ->
                            ex.printStackTrace()
                            listData.value = SearchUiState.Error(ErrorState.ERROR_LOADING)
                        }
                        .collect {
                            listData.value = SearchUiState.Success(it)
                        }
                }
        }
    }
}