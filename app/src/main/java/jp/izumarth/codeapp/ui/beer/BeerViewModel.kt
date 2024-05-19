package jp.izumarth.codeapp.ui.beer

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
class BeerViewModel @Inject constructor(
    private val beerRepository: BeerRepository
) : ViewModel() {

    var state: BeerUiState by mutableStateOf(BeerUiState.Loading)

    fun onLaunch() {
        viewModelScope.launch {
            beerRepository.getBeerItem("duvel")?.let {
                state = BeerUiState.Loaded(
                    beerItem = it
                )
            }
        }
    }
}