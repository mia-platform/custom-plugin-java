package eu.miaplatform.customplugin;

import eu.miaplatform.crud.library.QueryBuilder;

import java.io.Serializable;
import java.util.stream.Stream;

public interface ServiceClient {

    <T extends Serializable> Stream<T> retrieveAll(Class<T> clazz);
    <T extends Serializable> T retrieveById(String id, Class<T> clazz);
    <T extends Serializable> Stream<T> retrieveByAttribute(String attributeName, String attribute, Class<T> clazz);
    <T extends Serializable> Stream<T> retrieveByQuery(QueryBuilder queryBuilder, Class<T> clazz);
    <T extends Serializable> String storeSingle(T obj, Class<T> clazz);
    <T extends Serializable> Stream<String> storeMulti(Stream<T> objs, Class<T> clazz);
}
