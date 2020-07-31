package eu.miaplatform.crud.library.annotations

import java.lang.annotation.Inherited

@Target(AnnotationTarget.CLASS, AnnotationTarget.FILE)
@Inherited
@Retention(AnnotationRetention.RUNTIME)
annotation class CollectionAnnotation(val value: String = "", val collectionName: String = "")