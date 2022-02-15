package com.example.sendwavecodingtest.data.model

import com.example.sendwavecodingtest.data.dto.ExchangeRateDto
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import org.json.JSONObject
import java.lang.reflect.Type

class ConverterFactory : JsonDeserializer<List<ExchangeRateDto>> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): ArrayList<ExchangeRateDto> {
        return returnJSONArrayList(json, ExchangeRateDto::class.java, context)
    }

    private fun returnJSONArrayList(
        json: JsonElement?, type: Type?, ctx:
        JsonDeserializationContext?
    ): ArrayList<ExchangeRateDto> {
        val list = arrayListOf<ExchangeRateDto>()
        json?.let { json ->
            val `object` = JSONObject(json.toString())
            val it = `object`.keys()
            while (it.hasNext()) {
                val key = it.next()
                try {
                    val object1 = `object`.getString(key)
                    list.add(
                        ExchangeRateDto(
                            emptyList(),
                            key,
                            object1.toDouble()
                        )
                    )
                } catch (e: Throwable) {
                    e.printStackTrace()
                }
            }
        }
        return list
    }
}
