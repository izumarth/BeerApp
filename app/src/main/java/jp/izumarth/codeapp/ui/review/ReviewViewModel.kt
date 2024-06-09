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
import jp.izumarth.codeapp.model.ReviewSelectValue
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
        reviewItem: ReviewSelectValue,
    ) {
        viewModelScope.launch {
            reviewState = when (reviewItem) {
                ReviewSelectValue.Color
                    -> reviewState?.copy(color = value)

                ReviewSelectValue.Transparency
                    -> reviewState?.copy(transparency = value)

                ReviewSelectValue.Carbonation
                    -> reviewState?.copy(carbonation = value)

                ReviewSelectValue.HopsFlavor
                    -> reviewState?.copy(hopsFlavor = value)

                ReviewSelectValue.MaltFlavor
                    -> reviewState?.copy(maltFlavor = value)

                ReviewSelectValue.EsterFlavor
                    -> reviewState?.copy(esterFlavor = value)

                ReviewSelectValue.Bitterness
                    -> reviewState?.copy(bitterness = value)

                ReviewSelectValue.Sweetness
                    -> reviewState?.copy(sweetness = value)

                ReviewSelectValue.Body
                    -> reviewState?.copy(body = value)

                ReviewSelectValue.AfterTaste
                    -> reviewState?.copy(afterTaste = value)

                ReviewSelectValue.TotalRate
                    -> reviewState?.copy(totalRate = value)
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
}