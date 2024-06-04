package jp.izumarth.codeapp.ui.review

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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

@Composable
fun ReviewScreen(
    beerItem: Beer,
    review: Review?,
    viewModel: ReviewViewModel = hiltViewModel()
) {
    val reviewState = viewModel.reviewState
    val activity = LocalContext.current as BeerActivity

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
            .padding(horizontal = 8.dp)
            .verticalScroll(rememberScrollState()),
    ) {
        ReviewSelectValue.entries.forEach { reviewSelectValue ->
            ReviewSlider(
                title = reviewSelectValue.label,
                description = reviewSelectValue.description,
                sliderPosition = reviewSelectValue.toSelectValue(review),
                valueRanges = reviewSelectValue.values,
                onValueChange = {
                    onChangeReview(it, reviewSelectValue)
                },
            )
        }

        Button(
            onClick = onAddReview,
            contentPadding = PaddingValues(
                start = 20.dp,
                top = 12.dp,
                end = 20.dp,
                bottom = 12.dp
            )
        ) {
            Icon(
                Icons.Filled.Edit,
                contentDescription = "edit",
                modifier = Modifier.size(ButtonDefaults.IconSize),
            )
            Spacer(Modifier.size(ButtonDefaults.IconSpacing))
            Text("edit")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ReviewSlider(
    title: String,
    description: String,
    sliderPosition: Float,
    onValueChange: (Int) -> Unit,
    valueRanges: List<String>,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        Row (
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleSmall,
                textAlign = TextAlign.Center,
            )

            DescriptionDialogue(
                description = description,
            )
        }

        ReviewSegmentedButton(
            options = valueRanges,
            value = sliderPosition.toInt(),
            onClick = onValueChange,
        )
    }
}

@Composable
private fun DescriptionDialogue(
    description: String,
) {
    val openDialog = remember { mutableStateOf(false) }

    IconButton(onClick =  { openDialog.value = true }) {
        Icon(
            imageVector = Icons.Filled.Info,
            contentDescription = null,
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReviewSegmentedButton(
    options: List<String>,
    value: Int,
    onClick: (Int) -> Unit,
) {
    SingleChoiceSegmentedButtonRow(
        modifier = Modifier
            .padding(horizontal = 8.dp)
            .height(32.dp)
            .fillMaxWidth(),
    ) {
        options.forEachIndexed { index, label ->
            SegmentedButton(
                modifier = Modifier
                    .fillMaxHeight(),
                colors = SegmentedButtonDefaults.colors().copy(
                    activeContainerColor = MaterialTheme.colorScheme.secondary,
                    activeContentColor = MaterialTheme.colorScheme.onPrimary,
                ),
                shape = SegmentedButtonDefaults.itemShape(
                    index = index,
                    count = options.size,
                    baseShape = RoundedCornerShape(6.dp),
                ),
                onClick = { onClick(index) },
                selected = index == value
            ) {
                Text(
                    text = label,
                    modifier = Modifier
                        .fillMaxHeight(),
                )
            }
        }

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