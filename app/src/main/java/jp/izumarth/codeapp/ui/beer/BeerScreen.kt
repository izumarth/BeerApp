package jp.izumarth.codeapp.ui.beer

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import jp.izumarth.codeapp.R

val AppBarCollapsedHeight = 56.dp
val AppBarExpendedHeight = 400.dp

@Composable
fun BeerScreen(
    viewModel: BeerViewModel = hiltViewModel()
) {
    val uiState = viewModel.state

    LaunchedEffect(Unit) {
        viewModel.onLaunch()
    }

    BeerContent(
        uiState = uiState,
    )
}

@Composable
fun BeerContent(
    uiState: BeerUiState
) {
    val scrollState = rememberLazyListState()

    when (uiState) {
        is BeerUiState.Loading ->
            CircularProgressIndicator()

        is BeerUiState.NotFound ->
            // TODO: Error Screen
            CircularProgressIndicator()

        is BeerUiState.Loaded ->
            Column {
                BeerBody(scrollState)
            }
    }
}

@Composable
fun BeerTop(
    scrollState: LazyListState,
) {
    val Transparent = Color(0x00FFFFFF)
    val White = Color(0xFFFFFFFF)


    val Shapes = Shapes(
        small = RoundedCornerShape(12.dp),
        medium = RoundedCornerShape(20.dp),
        large = RoundedCornerShape(24.dp)
    )

    Column(
        modifier = Modifier
            .background(Color.White)
    ) {
        Box(
            Modifier
                .height(AppBarExpendedHeight)
        ) {
            Image(
                painter = painterResource(id = R.drawable.duvel),
                contentDescription = null,
                contentScale = ContentScale.FillHeight,
                modifier = Modifier.fillMaxSize()
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colorStops = arrayOf(
                                Pair(0.4f, Transparent),
                                Pair(1f, White)
                            )
                        )
                    )
            )

            Row(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalAlignment = Alignment.Bottom
            ) {
                Text(
                    "Belgian Ale",
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier
                        .clip(Shapes.small)
                        .background(LightGray)
                        .padding(vertical = 6.dp, horizontal = 16.dp)
                )
            }
        }

        Column(
            Modifier
                .fillMaxWidth()
                .height(AppBarCollapsedHeight),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                "duvel",
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
            )
        }
    }
}

@Composable
fun BeerBody(scrollState: LazyListState) {
    LazyColumn(
        state = scrollState
    ) {
        item {
            BeerTop(scrollState)
            BasicInfo()
            Description()
            Description()
        }
    }
}

@Composable
fun BasicInfo() {
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
    ) {
        InfoColumn(R.drawable.sports_bar, "8.5%")
        InfoColumn(R.drawable.style, "330ml")
        InfoColumn(R.drawable.map, "Belgium")
    }
}

@Composable
fun InfoColumn(@DrawableRes iconResource: Int, text: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(
            painter = painterResource(id = iconResource),
            contentDescription = null,
            tint = Color.Blue,
            modifier = Modifier.height(48.dp)
        )
        Text(text = text, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun Description() {
    Text(
        text = "独自の酵母と長い熟成期間、瓶内2次発酵を駆使した、 1918年から続くオリジナルのレシピ。\n" +
                "繊細な香りと絶妙な苦みを備えたゴールデン・エールは、 あまりの魅力から「悪魔」と名付けられた。\n" +
                "厄介なのは、その誘惑から逃れるのはひどく困難ということ。 もちろん試してみるのは自由だがー。",
        fontWeight = FontWeight.Medium,
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)
    )
}

