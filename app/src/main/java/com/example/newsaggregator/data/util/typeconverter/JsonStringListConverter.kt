package com.example.newsaggregator.data.util.typeconverter

import androidx.room.TypeConverter
import kotlinx.serialization.json.Json

class JsonStringListConverter {
    @TypeConverter
    fun fromString(string: String): List<String> {
        return Json.decodeFromString<List<String>>(string)
    }

    @TypeConverter
    fun toString(list: List<String>): String {
        return Json.encodeToString(list)
    }
}