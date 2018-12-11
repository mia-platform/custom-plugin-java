package eu.miaplatform.crud.library.listeners

import eu.miaplatform.crud.library.CRUDError

interface SingleObjectCallback<T> {
    fun onCompleted(result: T?, error: CRUDError?)
}