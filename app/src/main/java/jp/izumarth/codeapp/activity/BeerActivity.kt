package jp.izumarth.codeapp.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContract
import androidx.core.os.bundleOf
import dagger.hilt.android.AndroidEntryPoint
import jp.izumarth.codeapp.ui.beer.BeerScreen
import jp.izumarth.codeapp.ui.theme.BeerTheme

@AndroidEntryPoint
class BeerActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val extras = intent.extras
            ?.let { Extras.from(it) }
            ?: return finish()

        setContent {
            BeerTheme {
                BeerScreen(
                    beerName = extras.beerName,
                )
            }
        }
    }

    data class Extras(
        val beerName: String,
    ) {
        fun toBundle(): Bundle =
            bundleOf(
                EXTRA_BEER_NAME to beerName,
            )

        companion object {
            private const val EXTRA_BEER_NAME = "beer_name"

            fun from(extras: Bundle): Extras? {
                val beerName = extras.getString(EXTRA_BEER_NAME) ?: return null

                return Extras(beerName)
            }
        }
    }

    class Launcher : ActivityResultContract<Extras, Unit>() {
        override fun createIntent(context: Context, input: Extras): Intent =
            Intent(context, BeerActivity::class.java)
                .putExtras(input.toBundle())

        override fun parseResult(resultCode: Int, intent: Intent?) =
            Unit
    }
}