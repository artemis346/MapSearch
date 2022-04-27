package com.mapsearch.repositories_impl

import com.mapsearch.repositories.dto.HubsDto
import com.mapsearch.repositories_impl.mappers.mapToDomain
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class MapperHubsTest {
    @Test
    fun `invoke mapper when convert api to domain POSITIVE CASE`() {
        val response = responseHubsValid.mapToDomain()
        val result = listOf(
            HubsDto(
                id = "hub12",
                name = "Test",
                lat = 81.00000,
                lng = 14.00000
            ),
            HubsDto(
                id = "hub13",
                name = "Test2",
                lat = 51.00000,
                lng = 14.00000
            )
        )
        assert(response.deepEquals(result))
    }

    @Test
    fun `invoke mapper when convert api to domain EMPTY CASE`() {
        val response = responseHubsEmpty.mapToDomain()
        val result = listOf<HubsDto>()
        assert(response.deepEquals(result))
    }

    @Test
    fun `invoke mapper when convert api to domain NULL CASE`() {
        val response = responseHubsNull.mapToDomain()
        val result = listOf<HubsDto>()
        assert(response.deepEquals(result))
    }

    private fun List<HubsDto>.deepEquals(other: List<HubsDto>): Boolean {
        return this.size == other.size && this.mapIndexed { index, productDto ->
            productDto == other[index]
        }.all { it }
    }
}