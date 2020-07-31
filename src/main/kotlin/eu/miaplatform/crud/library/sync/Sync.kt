package eu.miaplatform.crud.library.sync

import com.google.gson.reflect.TypeToken
import eu.miaplatform.crud.library.*
import eu.miaplatform.crud.library.annotations.CollectionAnnotation
import eu.miaplatform.crud.library.enums.State
import java.util.*
import kotlin.collections.ArrayList

class Sync(private val crud: CRUD, private val db: DatabaseSync) {

    @Throws
    fun pull(vararg clazz: Class<out Syncable>) {
        val classesArrayList = ArrayList<Class<out Syncable>>()
        Collections.addAll<Class<out Syncable>>(classesArrayList, *clazz)
        pull(classesArrayList)
    }

    @Throws
    fun pull(classes: ArrayList<Class<out Syncable>>) {
        for (clazz in classes) {
            val maxUpdatedAt = db.getMaxUpdatedAt(clazz)
            pullCollectionUpdates(clazz, maxUpdatedAt)
        }
    }

    @Throws
    fun push(vararg clazz: Class<out Syncable>) {
        val classesArrayList = ArrayList<Class<out Syncable>>()
        Collections.addAll<Class<out Syncable>>(classesArrayList, *clazz)
        push(classesArrayList)
    }

    @Throws
    fun push(classes: ArrayList<Class<out Syncable>>){

        val objectToPushGrouped = db.getObjectsToPush(classes).groupBy { it.javaClass }
        val objectToUpdateGrouped = db.getObjectsToUpdate(classes).groupBy { it.javaClass }
        val objectToChangeStateGrouped = db.getObjectsToChangeState(classes).groupBy { it.javaClass }

        if(!objectToPushGrouped.isEmpty()) {
            for (data in objectToPushGrouped) {
                pushObjectsInCollection(data.key, data.value as ArrayList<Syncable>)
            }
        }

        if(!objectToUpdateGrouped.isEmpty()) {
            for (data in objectToUpdateGrouped) {
                pushUpdatesInCollection(data.key, data.value as ArrayList<Syncable>)
            }
        }

        if(!objectToChangeStateGrouped.isEmpty()) {
            for (data in objectToChangeStateGrouped) {
                pushChangeStateInCollection(data.key, data.value as ArrayList<Syncable>)
            }
        }

    }

    fun <T: Syncable> toPush(objects: ArrayList<T>) {
        if(!objects.isEmpty()) {
            for (element in objects) {
                element.setObjectState(State.ToPush)
                db.update(element)
            }
        }
    }

    fun <T: Syncable> toPublic(objects: ArrayList<T>) {
        if(!objects.isEmpty()) {
            for (element in objects) {
                val newState = when (element.getObjectState()) {
                    State.Pub.value -> State.Pub
                    State.Draft.value -> State.ToPub
                    State.Trash.value -> State.Trash
                    State.Deleted.value -> State.Deleted
                    else -> State.ToPub
                }

                element.setObjectState(newState)
                db.update(element)
            }
        }
    }

    fun <T: Syncable> toTrash(objects: ArrayList<T>) {
        if(!objects.isEmpty()) {

            for (element in objects) {
                val newState = when (element.getObjectState()) {
                    State.Pub.value -> State.ToTrash
                    State.Draft.value -> State.ToTrash
                    State.Trash.value -> State.Trash
                    State.Deleted.value -> State.ToTrash
                    else -> State.ToTrash
                }

                element.setObjectState(newState)
                db.update(element)
            }
        }
    }

    fun <T: Syncable> toDeleted(objects: ArrayList<T>) {
        if(!objects.isEmpty()) {

            for (element in objects) {
                val newState = when (element.getObjectState()) {
                    State.Pub.value -> State.Pub
                    State.Draft.value -> State.Draft
                    State.Trash.value -> State.ToDeleted
                    State.Deleted.value -> State.Deleted
                    else -> State.ToDeleted
                }

                element.setObjectState(newState)
                db.update(element)
            }
        }
    }

    fun <T: Syncable> toDraft(objects: ArrayList<T>) {
        if(!objects.isEmpty()) {

            for (element in objects) {
                val newState = when (element.getObjectState()) {
                    State.Pub.value -> State.ToDraft
                    State.Draft.value -> State.Draft
                    State.Trash.value -> State.ToDraft
                    State.Deleted.value -> State.Deleted
                    else -> State.ToDraft
                }

                element.setObjectState(newState)
                db.update(element)
            }
        }
    }

    // Private

    private fun  <T: Syncable> pushObjectsInCollection(clazz: Class<T>, objectsToPush: ArrayList<T>) {
        val collectionName = getCollectionNameFromAnnotation(clazz)

        if(collectionName != null && !collectionName.isEmpty()){
            try {
                val post = crud.post(collectionName)
                val ids = post.sync(objectsToPush, null)
                val get = crud.get(collectionName, null)
                if(ids != null && !ids.isEmpty()) {
                    for (id in ids) {
                        val obj = get.sync(id, clazz)
                        if (obj != null) db.insert(obj)
                    }
                }
                db.delete(objectsToPush)
            } catch (e: Exception) {
                throw e
            }
        } else {
            throw CRUDError("No collection name")
        }
    }

    private fun  <T: Syncable> pushUpdatesInCollection(clazz: Class<T>, objectsToUpdate: ArrayList<T>) {
        val collectionName = getCollectionNameFromAnnotation(clazz)

        if(collectionName != null && !collectionName.isEmpty()) {
            for (objUpdate in objectsToUpdate) {

                if (objUpdate.getObjectId().isNullOrEmpty()) {
                    throw CRUDError("No glsDriverId for update object")
                }
                try {
                    val patch = crud.patch(collectionName)

                    val jsonObject = GsonFactory.newGsonInstance().toJsonTree(objUpdate).asJsonObject
                    val type = object : TypeToken<Map<String, Any>>() {}.type
                    val map: Map<String, Any> = GsonFactory.newGsonInstance().fromJson(jsonObject, type)

                    for (element in map) {
                        when (element.value) {
                            is String -> {
                                patch.update(element.value as String, element.key)
                            }
                            is Int -> {
                                patch.update(element.value as Int, element.key)
                            }
                            is Long -> {
                                patch.update(element.value as Long, element.key)
                            }
                            is Float -> {
                                patch.update(element.value as Float, element.key)
                            }
                            is Double -> {
                                patch.update(element.value as Double, element.key)
                            }
                            is Date -> {
                                patch.update(CRUDObject.toISO8601(element.value as Date), element.key)
                            }
                        }
                    }
                    patch.sync(objUpdate.getObjectId()!!)
                    db.update(objUpdate)
                } catch (e: Exception) {
                    throw e
                }
            }
        } else {
            throw CRUDError("No collection name")
        }
    }

    private fun  <T: Syncable> pushChangeStateInCollection(clazz: Class<T>, objectsToChangeState: ArrayList<T>) {
        val collectionName = getCollectionNameFromAnnotation(clazz)

        if(collectionName != null && !collectionName.isEmpty()) {
            for (objChangeState in objectsToChangeState) {

                if (objChangeState.getObjectId().isNullOrEmpty() && objChangeState.getObjectState().isNullOrEmpty()) {
                    throw CRUDError("No object glsDriverId or valid state to change state")
                }
                try {
                    val post = crud.post(collectionName)
                    val state = when (objChangeState.getObjectState()) {
                        State.ToPub.value -> State.Pub
                        State.ToDraft.value -> State.Draft
                        State.ToTrash.value -> State.Trash
                        else -> State.Deleted
                    }
                    post.syncChangeState(objChangeState.getObjectId()!!, state)
                    when(state){
                        State.Pub -> {
                            objChangeState.setObjectState(State.Pub)
                            db.update(objChangeState)
                        }
                        else -> db.delete(objChangeState)
                    }
                } catch (e: Exception) {
                    throw e
                }
            }
        } else {
            throw CRUDError("No collection name")
        }
    }

    private fun <T: Syncable> pullCollectionUpdates(clazz: Class<T>, maxUpdatedAt: Date?) {
        val collectionName = getCollectionNameFromAnnotation(clazz)

        if(collectionName != null && !collectionName.isEmpty()) {

            try {
                val getTotal = crud.get(collectionName, null)
                val totalCount = getTotal.syncCount()

                if(totalCount != null && totalCount == 0){
                    return
                }

                val updatedAtQuery = if(maxUpdatedAt != null) QueryBuilder().greater(CRUDObject.UPDATED_AT_KEY, CRUDObject.toISO8601(maxUpdatedAt)) else null
                val overAllGet = crud.get(collectionName, updatedAtQuery)
                overAllGet.state(arrayListOf(State.Pub, State.Draft, State.Deleted, State.Trash))

                val updatesCount = overAllGet.syncCount()

                val objectsToUpdate = ArrayList<T>()

                if(updatesCount != null && updatesCount > 0){
                    val limit = 100
                    var pageIndex = 0
                    do {
                        val getUpdate = crud.get(collectionName, updatedAtQuery)
                        getUpdate.state(arrayListOf(State.Pub, State.Draft, State.Deleted, State.Trash))
                        getUpdate.limit(limit)
                        getUpdate.skip(limit * pageIndex)
                        val resultObjects = getUpdate.sync(clazz)

                        if(resultObjects != null && !resultObjects.isEmpty()){
                            objectsToUpdate.addAll(resultObjects)
                        }

                        pageIndex += 1
                    } while (resultObjects != null && resultObjects.size == limit)
                }

                db.update(objectsToUpdate)

            } catch (e: Exception) {
                throw e
            }
        } else {
            throw CRUDError("No collection name")
        }
    }

    private fun <T: Syncable> getCollectionNameFromAnnotation(clazz: Class<T>): String? {
        val collectionAnnotation: CollectionAnnotation? = clazz.getAnnotation(CollectionAnnotation::class.java)

        var declaredCollectionName: String? = null

        if (collectionAnnotation != null) {
            declaredCollectionName = collectionAnnotation.collectionName
            if (declaredCollectionName == null || declaredCollectionName.isEmpty()) {
                declaredCollectionName = collectionAnnotation.value
            }
        }

        return declaredCollectionName
    }
}