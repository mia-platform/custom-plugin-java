package eu.miaplatform.crud.model

import com.google.gson.annotations.SerializedName
import eu.miaplatform.crud.library.annotations.SkipSerialisation
import eu.miaplatform.crud.library.enums.State
import eu.miaplatform.crud.library.sync.Syncable

open class BaseObject: Syncable {

    @SkipSerialisation
    @SerializedName("_id")
    var id: String? = null

    @SkipSerialisation
    @SerializedName("createdAt")
    var createdAt: String? = null

    @SkipSerialisation
    @SerializedName("updatedAt")
    var updatedAt: String? = null

    @SkipSerialisation
    @SerializedName("creatorId")
    var creatorId: String? = null

    @SkipSerialisation
    @SerializedName("updaterId")
    var updaterId: String? = null

    @SkipSerialisation
    @SerializedName("__STATE__")
    var state: String? = null

    override fun getObjectId(): String? {
        return id
    }

    override fun getObjectState(): String? {
        return state
    }

    override fun setObjectState(newState: State){
        state = newState.value
    }

    override fun getObjectUpdatedAt(): String? {
        return updatedAt
    }

    override fun getObjectCreatedAt(): String? {
        return createdAt
    }

    override fun getObjectCreatorId(): String? {
        return creatorId
    }

    override fun getObjectUpdaterId(): String? {
        return updaterId
    }

    override fun getObjectDbId() : Long {
        return 0
    }

    override fun setObjectDbId(id: Long) {
    }
}