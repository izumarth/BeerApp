package jp.izumarth.codeapp.ui.beerList

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.izumarth.codeapp.data.repository.BeerRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BeerListViewModel @Inject constructor(
    private val beerRepository: BeerRepository
) : ViewModel() {

    var state: BeerListUiState by mutableStateOf(BeerListUiState.Loading)
    fun onLaunch() {
        viewModelScope.launch {
            beerRepository.getBeers().let {
                state = BeerListUiState.Loaded(
                    beers = it
                )
            }
        }
    }
}