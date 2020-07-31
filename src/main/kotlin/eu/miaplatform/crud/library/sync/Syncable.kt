package eu.miaplatform.crud.library.sync

import eu.miaplatform.crud.library.enums.State
import java.io.Serializable

interface Syncable : Serializable {

    fun getObjectId() : String?
    fun getObjectUpdatedAt() : String?
    fun getObjectCreatedAt() : String?
    fun getObjectCreatorId() : String?
    fun getObjectUpdaterId() : String?
    fun getObjectState() : String?
    fun setObjectState(newState: State)
    fun getObjectDbId() : Long
    fun setObjectDbId(id: Long)
}