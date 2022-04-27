package com.mapsearch.repositories_impl

import com.mapsearch.repositories.dto.HubsDto
import com.mapsearch.repositories_impl.mappers.mapToDomain
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class MapperSearchTest {
    @Test
    fun `invoke mapper when convert api to domain POSITIVE CASE`() {
        val response = responseSearchValid.mapToDomain()
        val result = listOf(
            HubsDto(
                id = "122",
                name = "Test",
                lat = 81.00000,
                lng = 14.00000
            ),
            HubsDto(
                id = "156",
                name = "Test2",
                lat = 51.00000,
                lng = 14.00000
            )
        )
        assert(response.deepEquals(result))
    }

    @Test
    fun `invoke mapper when convert api to domain EMPTY CASE`() {
        val response = responseSearchEmpty.mapToDomain()
        val result = listOf<HubsDto>()
        assert(response.deepEquals(result))
    }

    private fun List<HubsDto>.deepEquals(other: List<HubsDto>): Boolean {
        return this.size == other.size && this.mapIndexed { index, productDto ->
            productDto == other[index]
        }.all { it }
    }
}