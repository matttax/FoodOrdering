package com.matttax.foodordering.network.cache

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.matttax.foodordering.network.model.CategoriesResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream

object CategoriesSerializer : Serializer<CategoriesResponse> {

	override val defaultValue: CategoriesResponse
		get() = CategoriesResponse(emptyList())

	override suspend fun readFrom(input: InputStream): CategoriesResponse {
		try {
			return Json.decodeFromString(
				deserializer = CategoriesResponse.serializer(),
				string = input.readBytes().decodeToString()
			)
		} catch (exception: SerializationException) {
			throw CorruptionException("Error occurred during decoding the storage", exception)
		}
	}

	override suspend fun writeTo(t: CategoriesResponse, output: OutputStream) {
		withContext(Dispatchers.IO) {
			output.write(
				Json.encodeToString(serializer = CategoriesResponse.serializer(), value = t).toByteArray()
			)
		}
	}
}
