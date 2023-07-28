package com.flo.albumproject.data.api

import android.util.Log
import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import com.squareup.moshi.ToJson
import okio.Buffer
import org.json.JSONException
import org.json.JSONObject

class JsonMoshiAdapter: JsonAdapter<JSONObject>() {

    @FromJson
    override fun fromJson(reader: JsonReader): JSONObject? {
        // Here we're expecting the JSON object, it is processed as Map<String, Any> by Moshi
        return (reader.readJsonValue() as? Map<*, *>)?.let { data ->
            try {
                JSONObject(data)
            } catch (e: JSONException) {
                Log.e("#http", "Something went wrong with the ${this::class.java}", e)
                return null
            }
        }
    }

    @ToJson
    override fun toJson(writer: JsonWriter, value: JSONObject?) {
        value?.let { writer.value(Buffer().writeUtf8(value.toString())) }
    }
}