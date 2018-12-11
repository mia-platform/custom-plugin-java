package eu.miaplatform.customplugin;

import eu.miaplatform.crud.library.CRUD;
import eu.miaplatform.crud.library.GET;
import eu.miaplatform.crud.library.POST;
import eu.miaplatform.crud.library.QueryBuilder;
import eu.miaplatform.crud.library.annotations.CollectionAnnotation;
import eu.miaplatform.crud.library.enums.State;
import okhttp3.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.*;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CRUDServiceClient implements ServiceClient {

    private CRUD crud;
    private Logger logger = LoggerFactory.getLogger(CRUDServiceClient.class);

    private CRUDServiceClient(String apiPath, String apiSecret) {

        this.crud = new CRUD(apiPath);

        if (apiSecret != null) {

            this.crud.getNetwork().addInterceptor(chain -> {

                Request request = chain.request();

                if (!request.headers().names().contains("secret")) {
                    request = request.newBuilder().
                            addHeader("secret", apiSecret).
                            build();
                }

                return chain.proceed(request);
            });
        }
    }

    public CRUDServiceClient(String apiPath, String apiSecret, HeadersPropagator headersPropagator) {
        this(apiPath, apiSecret);

        List<CustomPluginHeader> headers = headersPropagator.getHeaders();

        if (headers.size() > 0) {

            this.crud.getNetwork().addInterceptor(chain -> {

                Request request = chain.request();
                Request.Builder builder = request.newBuilder();

                headers.forEach((customPluginHeader) -> {

                    if (request.headers().names().contains(customPluginHeader.getName())) {
                        builder.removeHeader(customPluginHeader.getName());
                    }
                    builder.addHeader(customPluginHeader.getName(), customPluginHeader.getValue());
                });

                Request newRequest = builder.build();

                return chain.proceed(newRequest);
            });
        }
    }

    public <T extends Serializable> Stream<T> retrieveAll(Class<T> clazz) {
        GET get = crud.get(clazz.getAnnotation(CollectionAnnotation.class).collectionName(), (QueryBuilder) null);

        return Objects.requireNonNull(get.sync(clazz)).stream();
    }

    public <T extends Serializable> T retrieveById(String id, Class<T> clazz) {
        GET get = crud.get(clazz.getAnnotation(CollectionAnnotation.class).collectionName(), "");

        return get.sync(id, clazz);
    }

    public <T extends Serializable> Stream<T> retrieveByAttribute(String attributeName, String attribute, Class<T> clazz) {
        GET get = crud.get(clazz.getAnnotation(CollectionAnnotation.class).collectionName(),
                new QueryBuilder().equals(attributeName, attribute));

        return Objects.requireNonNull(get.sync(clazz)).stream();
    }


    public <T extends Serializable> Stream<T> retrieveByQuery(QueryBuilder queryBuilder, Class<T> clazz) {
        GET get = crud.get(clazz.getAnnotation(CollectionAnnotation.class).collectionName(), queryBuilder);

        return Objects.requireNonNull(get.sync(clazz)).stream();
    }

    public <T extends Serializable> String storeSingle(T obj, Class<T> clazz) {
        POST post = crud.post(clazz.getAnnotation(CollectionAnnotation.class).collectionName());

        return post.sync(obj, State.Pub);
    }

    public <T extends Serializable> Stream<String> storeMulti(Stream<T> objs, Class<T> clazz) {
        POST post = crud.post(clazz.getAnnotation(CollectionAnnotation.class).collectionName());
        ArrayList<T> objsList = objs.collect(Collectors.toCollection(ArrayList::new));

        return Objects.requireNonNull(post.sync(objsList, State.Pub)).stream();
    }
}
