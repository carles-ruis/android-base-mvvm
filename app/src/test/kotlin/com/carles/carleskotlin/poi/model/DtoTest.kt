package com.carles.carleskotlin.poi.model

import com.carles.carleskotlin.emptyPoiList
import com.carles.carleskotlin.poi.domain.Poi
import com.carles.carleskotlin.poiDetailResponseDto
import com.carles.carleskotlin.poiListResponseDto
import com.carles.carleskotlin.poiResponseDto
import org.assertj.core.api.Assertions
import org.junit.Test
import java.util.*

class DtoTest {

    @Test
    fun poiListResponseDto_toModel() {
        Assertions.assertThat(emptyPoiList.toModel()).isEqualTo(Collections.emptyList<Poi>())
        with(poiListResponseDto.toModel()) {
            Assertions.assertThat(size == poiListResponseDto.list!!.size && get(0).id == poiListResponseDto.list!!.get(0).id).isTrue()
        }
    }

    @Test
    fun poiResponseDto_toModel() {
        with(poiResponseDto.toModel()) {
            Assertions.assertThat(id == poiResponseDto.id && title == poiResponseDto.title && geocoordinates == poiResponseDto
                    .geocoordinates).isTrue()
        }
    }

    @Test
    fun poiDetailResponseDto_toModel() {
        with(poiDetailResponseDto.toModel()) {
            Assertions.assertThat(id == poiDetailResponseDto.id && transport == poiDetailResponseDto.transport && email ==
                    poiDetailResponseDto.email && phone == poiDetailResponseDto.phone).isTrue()
        }
        val dtoWithNulls = poiDetailResponseDto.copy(transport = "", email = "null", phone = "undefined")
        with(dtoWithNulls.toModel()) {
            Assertions.assertThat(transport == null && email == null && phone == null).isTrue()
        }
    }
}