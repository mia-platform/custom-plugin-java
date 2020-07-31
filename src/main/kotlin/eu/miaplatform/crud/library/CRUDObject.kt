package eu.miaplatform.crud.library

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object CRUDObject {

        /**
         * The ID _ kEY.
         */
        internal var ID_KEY = "_id"
        /**
         * The UPDATED _ aT _ kEY.
         */
        internal var UPDATED_AT_KEY = "updatedAt"
        /**
         * The UPDATER _ iD _ kEY.
         */
        internal var UPDATER_ID_KEY = "updaterId"
        /**
         * The CREATED _ aT _ kEY.
         */
        internal var CREATED_AT_KEY = "createdAt"
        /**
         * The CREATOR _ iD _ kEY.
         */
        internal var CREATOR_ID_KEY = "creatorId"
        /**
         * The STATE _ kEY.
         */
        internal var STATE_KEY = "__STATE__"

        val CollectionDefaultProperties = arrayOf(ID_KEY, UPDATED_AT_KEY, UPDATER_ID_KEY, CREATED_AT_KEY, CREATOR_ID_KEY, STATE_KEY)

        fun toISO8601(date: Date): String {
            val tz = TimeZone.getTimeZone("UTC")
            val df = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS")
            df.timeZone = tz
            return df.format(date)
        }

        fun fromISO8601UTC(dateStr: String): Date? {
            val tz = TimeZone.getTimeZone("UTC")
            val df = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS")
            df.timeZone = tz

            try {
                return df.parse(dateStr)
            } catch (e: ParseException) {
                e.printStackTrace()
            }

            return null
        }

}