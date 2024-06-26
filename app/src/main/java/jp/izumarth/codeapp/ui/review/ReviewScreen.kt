package jp.izumarth.codeapp.ui.review

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import jp.izumarth.codeapp.activity.BeerActivity
import jp.izumarth.codeapp.model.Beer
import jp.izumarth.codeapp.model.Review
import jp.izumarth.codeapp.model.ReviewSelectValue
import jp.izumarth.codeapp.ui.widgets.InfoDialog
import jp.izumarth.codeapp.ui.widgets.LargeDropdownMenu

@Composable
fun ReviewScreen(
    beerItem: Beer,
    review: Review?,
    onLaunch: () -> Unit,
    viewModel: ReviewViewModel = hiltViewModel()
) {
    val reviewState = viewModel.reviewState
    val activity = LocalContext.current as BeerActivity

    LaunchedEffect(Unit) {
        onLaunch()
        viewModel.onLaunch(
            beerItem = beerItem,
            review = review,
        )
    }

    ReviewContent(
        review = reviewState,
        onChangeReview = { value, reviewItem ->
            viewModel.onChangeReview(value, reviewItem)
        },
        onAddReview = {
            viewModel.onAddReview()
            activity.finish()
        },
    )
}

@Composable
private fun ReviewContent(
    review: Review?,
    onChangeReview: (Int, ReviewSelectValue) -> Unit,
    onAddReview: () -> Unit,
) {
    review?.let {
        ReviewLoaded(
            review = review,
            onChangeReview = onChangeReview,
            onAddReview = onAddReview,
        )
    } ?: CircularProgressIndicator()
}

@Composable
private fun ReviewLoaded(
    review: Review,
    onChangeReview: (Int, ReviewSelectValue) -> Unit,
    onAddReview: () -> Unit,
) {

    Column(
        modifier = Modifier
            .padding(horizontal = 12.dp)
            .fillMaxSize(),
    ) {
        Box(
            modifier = Modifier
                .weight(0.9f),
        ) {
            LazyColumn {
                items(ReviewSelectValue.entries) { reviewSelectValue ->
                    ReviewDropDownMenu(
                        title = reviewSelectValue.label,
                        description = reviewSelectValue.description,
                        sliderPosition = reviewSelectValue.toSelectValue(review),
                        valueRanges = reviewSelectValue.values,
                        onValueChange = {
                            onChangeReview(it, reviewSelectValue)
                        },
                    )
                }
            }
        }


        Box(
            modifier = Modifier
                .weight(0.1f),
        ) {
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                onClick = onAddReview,
                contentPadding = PaddingValues(
                    start = 20.dp,
                    top = 12.dp,
                    end = 20.dp,
                    bottom = 12.dp
                )
            ) {
                Text(
                    "Review!",
                    style = MaterialTheme.typography.bodyLarge,
                )
            }
        }
    }
}

@Composable
private fun ReviewDropDownMenu(
    title: String,
    description: String,
    sliderPosition: Float,
    onValueChange: (Int) -> Unit,
    valueRanges: List<String>,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                modifier = Modifier
                    .weight(0.5f),
                text = title,
                style = MaterialTheme.typography.titleSmall,
                textAlign = TextAlign.Center,
            )

            DescriptionDialogue(
                description = description,
            )

            var selectedIndex by remember { mutableIntStateOf(sliderPosition.toInt()) }
            LargeDropdownMenu(
                modifier = Modifier
                    .weight(1f),
                label = title,
                items = valueRanges,
                selectedIndex = selectedIndex,
                onItemSelected = { index, _ ->
                    selectedIndex = index
                    onValueChange(index)
                },
            )
        }
    }
}

@Composable
private fun DescriptionDialogue(
    description: String,
) {
    val openDialog = remember { mutableStateOf(false) }

    IconButton(onClick = { openDialog.value = true }) {
        Icon(
            imageVector = Icons.Filled.Info,
            contentDescription = null,
            modifier = Modifier.size(16.dp),
            tint = MaterialTheme.colorScheme.secondary,
        )
    }

    if (openDialog.value) {
        InfoDialog(
            desc = description,
            onDismiss = { openDialog.value = false }
        )
    }
}

private fun ReviewSelectValue.toSelectValue(review: Review): Float = when (this) {
    ReviewSelectValue.Color
    -> review.color.toFloat()

    ReviewSelectValue.Transparency
    -> review.transparency.toFloat()

    ReviewSelectValue.Carbonation
    -> review.carbonation.toFloat()

    ReviewSelectValue.HopsFlavor
    -> review.hopsFlavor.toFloat()

    ReviewSelectValue.MaltFlavor
    -> review.maltFlavor.toFloat()

    ReviewSelectValue.EsterFlavor
    -> review.esterFlavor.toFloat()

    ReviewSelectValue.Bitterness
    -> review.bitterness.toFloat()

    ReviewSelectValue.Sweetness
    -> review.sweetness.toFloat()

    ReviewSelectValue.Body
    -> review.body.toFloat()

    ReviewSelectValue.AfterTaste
    -> review.afterTaste.toFloat()

    ReviewSelectValue.TotalRate
    -> review.totalRate.toFloat()
}

@Preview(showBackground = true)
@Composable
fun ReviewScreenPreview() {
    ReviewLoaded(
        review = Review(name = "beer preview"),
        onChangeReview = { _, _ -> },
        onAddReview = {},
    )
}