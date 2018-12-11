package eu.miaplatform.crud.library.listeners

import eu.miaplatform.crud.library.CRUDError

interface NoObjectCallback {
    fun onCompleted(error: CRUDError?)
}