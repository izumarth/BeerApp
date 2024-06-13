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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import jp.izumarth.codeapp.activity.BeerActivity
import jp.izumarth.codeapp.model.Beer
import jp.izumarth.codeapp.ui.review.ReviewScreen
import jp.izumarth.codeapp.utils.getDrawableId

@Composable
fun BeerScreen(
    beerName: String,
    viewModel: BeerViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiStateFlow.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.onLaunch(
            beerName = beerName
        )
    }

    BeerContent(
        uiState = uiState.value,
    )
}

@Composable
fun BeerContent(
    uiState: BeerUiState,
) {
    when (uiState) {
        is BeerUiState.Loading ->
            CircularProgressIndicator()

        is BeerUiState.NotFound ->
            // TODO: Error Screen
            CircularProgressIndicator()

        is BeerUiState.Loaded ->
            LoadedScreen(
                uiState = uiState,
            )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoadedScreen(
    uiState: BeerUiState.Loaded,
) {
    val activity = LocalContext.current as BeerActivity
    val navController = rememberNavController()
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val canBack by remember(currentBackStackEntry) {
        derivedStateOf {
            navController.previousBackStackEntry != null
        }
    }

    var isEnableFab by remember { mutableStateOf(true) }

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    if (canBack) {
                        IconButton(onClick = navController::popBackStack) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = null,
                            )
                        }
                    } else {
                        IconButton(onClick = { activity.finish() }) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = null,
                            )
                        }
                    }
                },
                title = { Text(uiState.beerItem.name) },
            )
        },
        floatingActionButton = {
            if (isEnableFab) {
                ExtendedFloatingActionButton(
                    onClick = {
                        navController.navigate(BeerRoute.Review.destination)
                    },
                    icon = {
                        Icon(
                            Icons.Filled.Edit,
                            contentDescription = null,
                        )
                    },
                    text = {
                        Text(text = "Review")
                    },
                )
            }
        }
    ) {
        NavHost(
            navController = navController,
            startDestination = BeerRoute.Detail.destination,
            modifier = Modifier.padding(it),
        ) {
            composable(BeerRoute.Detail.destination) {
                isEnableFab = true
                BeerDetailContent(
                    beerItem = uiState.beerItem,
                )
            }

            composable(BeerRoute.Review.destination) {
                ReviewScreen(
                    beerItem = uiState.beerItem,
                    review = uiState.review,
                    onLaunch = {
                        isEnableFab = false
                    },
                )
            }
        }
    }
}

@Composable
fun BeerDetailContent(
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
fun BeerTop(
    beerItem: Beer,
) {
    val drawableId = getDrawableId(imageName = beerItem.imageName)

    Column(
        modifier = Modifier
            .background(Color.White)
    ) {
        Box(
            Modifier
                .height(400.dp)
        ) {
            Image(
                painter = painterResource(id = drawableId),
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
                                Pair(0.4f, Color(0x00FFFFFF)),
                                Pair(1f, Color(0xFFFFFFFF))
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
                        .clip(RoundedCornerShape(12.dp))
                        .background(MaterialTheme.colorScheme.tertiaryContainer.copy(alpha = 0.5f))
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
fun BasicInfo(
    beerItem: Beer,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp),
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

        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            InfoColumn("IBU", beerItem.ibu?.toString())
            InfoColumn("EBC", beerItem.ebc?.toString())
        }
    }
}

@Composable
fun InfoColumn(
    title: String,
    text: String?,
) {
    val label = text ?: "-"

    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.secondary,
        )

        Text(
            text = label,
            fontWeight = FontWeight.Bold,
        )
    }
}

@Composable
fun Description(
    beerItem: Beer,
) {
    Text(
        text = beerItem.description,
        style = MaterialTheme.typography.labelLarge,
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)
    )
}

@Composable
fun OtherInformation(
    beerItem: Beer,
) {
    val displayInformation = listOf(
        "原材料" to beerItem.materials.joinToString("\n"),
        "ホップ" to beerItem.hop.joinToString("\n"),
    )

    OtherInformationDivider()

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
            style = MaterialTheme.typography.labelMedium,
        )

        Text(
            text = information,
            modifier = Modifier
                .weight(0.8f)
                .padding(8.dp),
            style = MaterialTheme.typography.bodyMedium,
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
            .padding(vertical = 1.dp),
        thickness = (0.5).dp,
        color = MaterialTheme.colorScheme.secondary.copy(
            alpha = 0.5f,
        ),
    )
}