package jp.izumarth.codeapp.data.repository

import jp.izumarth.codeapp.data.local.RawDataSource
import jp.izumarth.codeapp.model.Beer
import javax.inject.Inject

class BeerRepository @Inject constructor(
    private val source: RawDataSource
) {
    suspend fun getBeers(): List<Beer> = source.getBeers()

    suspend fun getBeerItem(name: String): Beer? {
       return source.getBeers().firstOrNull { it.name == name }
    }
}