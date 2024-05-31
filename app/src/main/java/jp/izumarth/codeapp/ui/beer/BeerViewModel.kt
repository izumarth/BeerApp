package jp.izumarth.codeapp.ui.beer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.izumarth.codeapp.data.repository.BeerRepository
import jp.izumarth.codeapp.data.repository.ReviewRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class BeerViewModel @Inject constructor(
    private val beerRepository: BeerRepository,
    private val reviewRepository: ReviewRepository,
) : ViewModel() {
    private val _beerNameFlow = MutableStateFlow<String?>(null)

    private val _beerFlow = _beerNameFlow
        .filterNotNull()
        .map { beerRepository.getBeerItem(it) }

    private val _reviewFlow = _beerNameFlow
        .filterNotNull()
        .map { reviewRepository.getReview(it) }

    val uiStateFlow = combine(
        _beerFlow,
        _reviewFlow,
    ) { beer, review ->

        beer?.let {
            BeerUiState.Loaded(
                beerItem = beer,
                review = review,
            )
        } ?: BeerUiState.NotFound
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), BeerUiState.Loading)

    fun onLaunch(
        beerName: String,
    ) {
        _beerNameFlow.value = beerName
    }
}