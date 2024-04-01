package com.matttax.foodordering.network.cache

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.matttax.foodordering.network.model.FoodResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream

object FoodItemsSerializer : Serializer<FoodResponse> {

	override val defaultValue: FoodResponse
		get() = FoodResponse(emptyList())

	override suspend fun readFrom(input: InputStream): FoodResponse {
		try {
			return Json.decodeFromString(
				deserializer = FoodResponse.serializer(),
				string = input.readBytes().decodeToString()
			)
		} catch (exception: SerializationException) {
			throw CorruptionException("Error occurred during decoding the storage", exception)
		}
	}

	override suspend fun writeTo(t: FoodResponse, output: OutputStream) {
		withContext(Dispatchers.IO) {
			output.write(
				Json.encodeToString(serializer = FoodResponse.serializer(), value = t).toByteArray()
			)
		}
	}
}
