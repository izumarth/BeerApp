package jp.izumarth.codeapp.data.local

import android.content.Context
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.qualifiers.ApplicationContext
import jp.izumarth.codeapp.R
import jp.izumarth.codeapp.model.Beer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RawDataSource @Inject constructor(
    @ApplicationContext private val context: Context,
){
    private val gson = GsonBuilder()
        .create()

    suspend fun getBeers(): List<Beer> =
        withContext(Dispatchers.IO) {
            getJson(RawDataKey.BeerItem)
        }

    private suspend inline fun <reified T> getJson(key: RawDataKey): T =
        readFile(key)
            .let { gson.fromJson(it, object : TypeToken<T>() {}.type) }

    private fun readFile(key: RawDataKey): String =
        context.resources
            .openRawResource(key.resId)
            .bufferedReader()
            .use { it.readText() }

    private enum class RawDataKey(
        val resId: Int,
    ) {
        BeerItem(R.raw.beer_item)
    }
}