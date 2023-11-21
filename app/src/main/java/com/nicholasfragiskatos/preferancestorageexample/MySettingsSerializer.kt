package com.nicholasfragiskatos.preferancestorageexample

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.google.protobuf.InvalidProtocolBufferException
import java.io.InputStream
import java.io.OutputStream

object MySettingsSerializer : Serializer<MySettings> {
    override val defaultValue: MySettings = MySettings.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): MySettings {
        try {
            return MySettings.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("cannot read proto.", exception)
        }
    }

    override suspend fun writeTo(t: MySettings, output: OutputStream) {
        t.writeTo(output)
    }

}

