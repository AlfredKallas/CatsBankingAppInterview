package com.example.catsbankingapp.data.serializer

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
object BooleanAsIntSerializer : KSerializer<Boolean> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("BooleanAsInt", PrimitiveKind.INT)
    override fun serialize(encoder: Encoder, value: Boolean) {
        // Convert true -> 1, false -> 0
        encoder.encodeInt(if (value) 1 else 0)
    }
    override fun deserialize(decoder: Decoder): Boolean {
        // Convert 1 -> true, anything else (like 0) -> false
        val intValue = decoder.decodeInt()
        return intValue == 1
    }
}