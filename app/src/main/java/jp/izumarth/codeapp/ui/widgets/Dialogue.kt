package jp.izumarth.codeapp.ui.widgets

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import jp.izumarth.codeapp.R

@Composable
fun InfoDialog(
    desc: String = "",
    onDismiss: () -> Unit,
) {
    Dialog(
        onDismissRequest = onDismiss,
    ) {
        Box(
            Modifier
                .size(300.dp)
        ) {
            Box(
                Modifier
                    .padding(top = 16.dp)
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(10.dp),
                    )
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .padding(top = 32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(
                        desc,
                        style = MaterialTheme.typography.bodyMedium,
                    )

                    Spacer(modifier = Modifier.weight(1.0f))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                    ) {
                        Button(
                            onClick = onDismiss,
                            shape = RoundedCornerShape(10.dp),
                        ) {
                            Text(
                                "ok",
                                style = TextStyle(color = MaterialTheme.colorScheme.onPrimary),
                            )
                        }
                    }
                }

            }
            InfoHeader(
                Modifier
                    .size(36.dp)
                    .align(Alignment.TopCenter)
                    .border(
                        border = BorderStroke(
                            width = 4.dp,
                            color = MaterialTheme.colorScheme.onPrimary,
                        ),
                        shape = CircleShape,
                    )
            )
        }
    }
}

@Composable
private fun InfoHeader(modifier: Modifier) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.info))
    val progress by animateLottieCompositionAsState(
        composition = composition,
        speed = 2.5f,
    )
    LottieAnimation(
        composition = composition,
        progress = { progress },
        modifier = modifier,
    )
}

@Preview(showBackground = true)
@Composable
fun DialogPreview() {
    InfoDialog(
        desc = "klafjosdifjasldfjaosdijfaosiedjfaosidfjnaosidjfaosdijfoasd",
        onDismiss = {}
    )
}