package eu.miaplatform.customplugin;

import eu.miaplatform.crud.library.*;
import eu.miaplatform.crud.library.enums.State;
import eu.miaplatform.crud.support.Author;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class CRUDServiceClientTest {

    /**
     * To execute the tests you need to start the docker compose with the CRUD that you find in the CRUD-docker folder of this project.
     * When you are in the docker folder execute the command: docker-compose up
     * The CRUD works in localhost (http://localhost:8080) and it is not protected by a secret
     * You can see the related Swagger documentation at: http://localhost:8080/documentation/
     **/

    private String apiPath = "http://localhost:8080";

    private CRUD crud = new CRUD(apiPath);

    private CustomPluginHeadersPropagator headersPropagator;
    private ServiceClient crudClient;

    private String collectionName = "author";
    private Author author = new Author("Marco", "Marconi", 10);
    private ArrayList<Author> authors = new ArrayList<Author>();
    private ArrayList<String> authorsIds = null;

    private String attributeSurnameValue = "Luigione";
    private String attributeNameValue = "Luigi";
    private int attributeAgeValue = 20;

    @Before
    public void prepareData() {

        headersPropagator = new CustomPluginHeadersPropagatorImpl();
        headersPropagator.add("id", "1234");
        headersPropagator.add("token", "5678");

        crudClient = ServiceClientFactory.getCRUDServiceClient(apiPath, null, headersPropagator);

        ArrayList<State> stateList = new ArrayList<>();
        stateList.add(State.Pub);
        stateList.add(State.Draft);
        stateList.add(State.Deleted);
        stateList.add(State.Trash);

        authors.add(new Author(attributeNameValue, attributeSurnameValue, attributeAgeValue));
        authors.add(new Author(attributeNameValue, "Pieroni", 25));
        authors.add(new Author("Mario", "Marioni", 30));

        GET get = crud.get(collectionName, "");
        get.state(stateList);
        ArrayList<Author> result = get.sync(Author.class);
        if (result != null) {
            for (Author a : result) {
                DELETE delete = crud.delete(collectionName);
                delete.state(stateList);
                delete.sync(a.getId());
            }
        }
    }

    @After
    public void waitBeforeNext() {
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void retrieveAll() {
        POST post = crud.post(collectionName);
        authorsIds = post.sync(authors, null);

        List<Author> result = crudClient.retrieveAll(Author.class).collect(Collectors.toList());

        assertEquals(authors.size(), result.size());

        int i = 0;
        for (Author a : authors) {
            assertEquals(a.getName(), result.get(i).getName());
            i += 1;
        }
    }

    @Test
    public void retrieveById() {

        POST post = crud.post(collectionName);
        authorsIds = post.sync(authors, null);

        Author result0 = crudClient.retrieveById(authorsIds.get(0), Author.class);
        Author result1 = crudClient.retrieveById(authorsIds.get(1), Author.class);

        assertEquals(authorsIds.get(0), result0.getId());
        assertEquals(authorsIds.get(1), result1.getId());
    }

    @Test
    public void retrieveByAttribute() {

        POST post = crud.post(collectionName);
        authorsIds = post.sync(authors, null);

        List<Author> result = crudClient.retrieveByAttribute("surname", attributeSurnameValue, Author.class).collect(Collectors.toList());
        assertEquals(1, result.size());
        assertEquals(authors.get(0).getName(), result.get(0).getName());
    }

    @Test
    public void retrieveByQuery() {

        POST post = crud.post(collectionName);
        authorsIds = post.sync(authors, null);

        QueryBuilder queryBuilder = new QueryBuilder();
        queryBuilder.and(new QueryBuilder().equals("name", attributeNameValue));
        queryBuilder.and(new QueryBuilder().equals("age", attributeAgeValue));

        List<Author> result = crudClient.retrieveByQuery(queryBuilder, Author.class).collect(Collectors.toList());

        assertEquals(1, result.size());
        assertEquals(attributeSurnameValue, result.get(0).getSurname());
    }


    @Test
    public void storeSingle() {

        String id = crudClient.storeSingle(author, Author.class);

        List<Author> result = crudClient.retrieveAll(Author.class).collect(Collectors.toList());

        assertEquals(1, result.size());
        assertEquals(id, result.get(0).getId());
    }

    @Test
    public void storeMulti() {

        List<String> ids = crudClient.storeMulti(authors.stream(), Author.class).collect(Collectors.toList());

        List<Author> result = crudClient.retrieveAll(Author.class).collect(Collectors.toList());
        assertEquals(authors.size(), result.size());

        int i = 0;
        for (Author author :authors) {
            assertEquals(author.getName(), result.get(i).getName());
            assertEquals(ids.get(i), result.get(i).getId());
            i++;
        }
    }
}