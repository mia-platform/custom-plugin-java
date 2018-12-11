package eu.miaplatform.crud.library.sync

import java.util.*

interface DatabaseSync {

    fun <T: Syncable> delete(obj: T)
    fun <T: Syncable> delete(objects: ArrayList<T>)

    fun <T: Syncable> insert(obj: T)
    fun <T: Syncable> insert(objects: ArrayList<T>)

    fun <T: Syncable> update(obj: T)
    fun <T: Syncable> update(objects: ArrayList<T>)

    fun getObjectsToPush(classes: ArrayList<Class<out Syncable>>): ArrayList<out Syncable>
    fun getObjectsToUpdate(classes: ArrayList<Class<out Syncable>>): ArrayList<out Syncable>
    fun getObjectsToChangeState(classes: ArrayList<Class<out Syncable>>): ArrayList<out Syncable>

    fun <T: Syncable> getMaxUpdatedAt(clazz: Class<T>): Date?

}