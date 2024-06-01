package jp.izumarth.codeapp.ui.review

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import jp.izumarth.codeapp.model.Beer
import jp.izumarth.codeapp.model.Review

@Composable
fun ReviewScreen(
    beerItem: Beer,
    review: Review?,
    viewModel: ReviewViewModel = hiltViewModel()
) {
    val reviewState = viewModel.reviewState

    LaunchedEffect(Unit) {
        viewModel.onLaunch(
            beerItem = beerItem,
            review = review,
        )
    }

    ReviewContent(
        review = reviewState,
        onChangeReview = { value, reviewItem ->
            viewModel.onChangeReview(value, reviewItem)
        }
    )
}

@Composable
private fun ReviewContent(
    review: Review?,
    onChangeReview: (Int, ReviewViewModel.ReviewItem) -> Unit,
) {
    review?.let {
        ReviewLoaded(
            review = review,
            onChangeReview = onChangeReview,
        )
    } ?: CircularProgressIndicator()
}

@Composable
private fun ReviewLoaded(
    review: Review,
    onChangeReview: (Int, ReviewViewModel.ReviewItem) -> Unit,
) {
    Column(
        modifier = Modifier
            .padding(horizontal = 8.dp),
    ) {
        ReviewSlider(
            title = "苦味",
            description = "モルトが弱い場合は苦く、強い場合はあまり感じません。モルトとホップのバランスが良いと甘味と苦味双方感じることができます。",
            sliderPosition = review.bitterness.toFloat(),
            onValueChange = {
                onChangeReview(it.toInt(), ReviewViewModel.ReviewItem.Bitterness)
            },
        )

        ReviewSlider(
            title = "甘味",
            description = "フルーツビールやランビックでは甘味を強く感じます。それ以外でも直接的ではないですがモルトからコクに通じる甘味が形成されます。",
            sliderPosition = review.sweetness.toFloat(),
            onValueChange = {
                onChangeReview(it.toInt(), ReviewViewModel.ReviewItem.Sweetness)
            },
        )
    }
}


@Composable
private fun ReviewSlider(
    title: String,
    description: String,
    sliderPosition: Float,
    onValueChange: (Float) -> Unit,
    valueRange: ClosedFloatingPointRange<Float> = 1f..5f,
    steps: Int = 5,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleSmall,
        )

        Text(
            text = description,
            style = MaterialTheme.typography.bodySmall,
        )

        Slider(
            value = sliderPosition,
            onValueChange = onValueChange,
            valueRange = valueRange,
            steps = steps,
        )
    }
}

