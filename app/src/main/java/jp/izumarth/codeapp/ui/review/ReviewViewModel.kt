package jp.izumarth.codeapp.ui.review

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.izumarth.codeapp.data.repository.ReviewRepository
import jp.izumarth.codeapp.model.Beer
import jp.izumarth.codeapp.model.Review
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReviewViewModel @Inject constructor(
    private val reviewRepository: ReviewRepository,
) : ViewModel() {

    var reviewState: Review? by mutableStateOf(null)
    fun onLaunch(
        beerItem: Beer,
        review: Review?,
    ) {
        viewModelScope.launch {
            reviewState = review ?: Review(beerItem.name)
        }
    }

    fun onChangeReview(
        value: Int,
        reviewItem: ReviewItem,
    ) {
        viewModelScope.launch {
            reviewState = when (reviewItem) {
                ReviewItem.Bitterness -> reviewState?.copy(bitterness = value)
                ReviewItem.Sweetness -> reviewState?.copy(sweetness = value)
            }
        }
    }

    fun onAddReview() {
        viewModelScope.launch {
            reviewState?.let {
                reviewRepository.replace(it)
            }
        }
    }

    enum class ReviewItem {
        Bitterness,
        Sweetness,
    }
}