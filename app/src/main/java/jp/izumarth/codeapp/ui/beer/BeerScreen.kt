package jp.izumarth.codeapp.ui.beer

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
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
import jp.izumarth.codeapp.model.Beer

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
    when (uiState) {
        is BeerUiState.Loading ->
            CircularProgressIndicator()

        is BeerUiState.NotFound ->
            // TODO: Error Screen
            CircularProgressIndicator()

        is BeerUiState.Loaded ->
            Column {
                BeerBody(
                    beerItem = uiState.beerItem,
                )
            }
    }
}

@Composable
fun BeerTop(
    beerItem: Beer,
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
                .height(400.dp)
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
                    beerItem.beerStyle,
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
                .padding(horizontal = 16.dp)
                .height(56.dp),
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                beerItem.name,
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
            )
        }
    }
}

@Composable
fun BeerBody(
    beerItem: Beer,
) {
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState()),
    ) {
            BeerTop(
                beerItem = beerItem,
            )
            BasicInfo(
                beerItem = beerItem,
            )
            Description(
                beerItem = beerItem,
            )
            OtherInformation(
                beerItem = beerItem,
            )
            Spacer(modifier = Modifier.height(64.dp))
    }
}

@Composable
fun BasicInfo(
    beerItem: Beer,
) {
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
    ) {
        InfoColumn("Alcohol", "${beerItem.alcohol}%")
        InfoColumn("Volume", "${beerItem.volume}ml")
        InfoColumn("Country", beerItem.country)
    }
}

@Composable
fun InfoColumn(
    title: String,
    text: String
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = title, color = Color.Gray)
        Text(text = text, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun Description(
    beerItem: Beer,
) {
    Text(
        text = beerItem.description,
        fontWeight = FontWeight.Medium,
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)
    )
}

@Composable
fun OtherInformation(
    beerItem: Beer,
) {
    Text(
        text = "その他情報",
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Blue)
            .padding(horizontal = 8.dp),
        color = Color.White,
        fontWeight = FontWeight.Bold,
    )

    val displayInformation = listOf(
        "原材料" to beerItem.materials.joinToString("\n"),
        "ホップ" to beerItem.hop.joinToString("\n"),
    )

    displayInformation.forEach { (title, information) ->
        OtherInformationItem(title, information)
        OtherInformationDivider()
    }
}

@Composable
fun OtherInformationItem(
    title: String,
    information: String,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(
            text = title,
            modifier = Modifier
                .weight(0.2f)
                .padding(8.dp),
            fontWeight = FontWeight.Bold,
        )

        Text(
            text = information,
            modifier = Modifier
                .weight(0.8f)
                .padding(8.dp),
        )
    }
}

@Composable
private fun OtherInformationDivider(
) {
Divider(
    modifier = Modifier
        .fillMaxWidth()
        .padding(start = 8.dp)
)
}