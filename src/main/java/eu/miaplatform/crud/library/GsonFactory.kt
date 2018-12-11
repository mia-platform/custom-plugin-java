package eu.miaplatform.crud.library

import com.google.gson.ExclusionStrategy
import com.google.gson.FieldAttributes
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import eu.miaplatform.crud.library.annotations.SkipSerialisation


class GsonFactory {

    companion object {
        fun newGsonInstance(): Gson {
            return GsonBuilder()
                    .enableComplexMapKeySerialization()
                    .addSerializationExclusionStrategy(object : ExclusionStrategy {
                        override fun shouldSkipClass(clazz: Class<*>?): Boolean {
                            return false
                        }

                        override fun shouldSkipField(f: FieldAttributes?): Boolean {
                            return f?.getAnnotation(SkipSerialisation::class.java) != null
                        }
                    })
                    .create()
        }
    }
}