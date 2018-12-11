package eu.miaplatform.crud.library.listeners

import eu.miaplatform.crud.library.CRUDError

interface MultipleObjectsCallback<T> {
    fun onCompleted(result: ArrayList<T>?, error: CRUDError?)
}