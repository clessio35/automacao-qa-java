package br.com.qa.pages;

import java.util.HashMap;
import java.util.Random;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.hamcrest.Matchers;

public class TestApiHunterPage {
    
    private static TestApiHunterPage instance;
    private String baseURI = "https://api.hunter.io/v2";
    private String key = "6e62e4139ba00669b103536a51ff447797901209";
    private String head = "x-api-key";
    private String lead = "/leads";
    private String leadList = "/leads_lists";
    private String leadCustom = "/leads_custom_attributes";
    private String dominio = "/domain-search?domain=";
    private Integer ID;

    private TestApiHunterPage() {}

    public static TestApiHunterPage getInstance() {
        if (instance == null) {
            instance = new TestApiHunterPage();
        }
        return instance;
    }

    public RequestSpecification getRequestSpecification() {
        return RestAssured.given().log().all().header(head, key).contentType(ContentType.JSON);
    }

    public void realizandoComandoGet() {
        System.out.println("Executando método realizandoComandoGet");
        getRequestSpecification()
            .when().get(baseURI + lead)
            .then().log().body().statusCode(200);
    }

    public void recuperarTodosOsLeadsComGet() {
        System.out.println("Executando método recuperarTodosOsLeadsComGet");
        getRequestSpecification()
            .when().get(baseURI + dominio + "github.com")
            .then().log().body().statusCode(200);
    }

    public void recuperarLeadEspecifico() {
        System.out.println("Executando método recuperarLeadEspecifico");
        getRequestSpecification()
            .when().get(baseURI + leadCustom + "/label")
            .then().log().body().statusCode(404)
            .body(Matchers.containsString("not_found"))
            .body(Matchers.containsString("This leads custom attribute does not exist."));
        deveCriarNovoLead();
        getRequestSpecification()
            .when().get(baseURI + lead + "/" + ID)
            .then().log().body().statusCode(200)
            .body("data.id", Matchers.is(ID));
    }

    public void deveCriarNovoLead() {
        System.out.println("Executando método deveCriarNovoLead");
        Random ger = new Random();
        HashMap<String, Object> user = new HashMap<>();
        user.put("email", ger.nextInt(999) * 11 + "@" + ger.nextInt(999) * 11 + ".com");
        user.put("first_name", ger.nextInt(99) * 11);
        user.put("last_name", ger.nextInt(99) * 11);
        user.put("position", "Analista");
        user.put("company", "Amazon");
        user.put("company_industry", "Internet and Telecom");
        ID = getRequestSpecification()
            .body(user)
            .when().post(baseURI + lead)
            .then().log().body().statusCode(201)
            .extract().path("data.id");
        System.out.println("ID: >>> " + ID);
    }

    public void deveEditarRequisicaoDoLeadEspecifico() {
        System.out.println("Executando método deveEditarRequisicaoDoLeadEspecifico");
        deveCriarNovoLead();
        Random ger = new Random();
        HashMap<String, Object> user = new HashMap<>();
        user.put("email", ger.nextInt(999) * 11 + "@" + ger.nextInt(999) * 11 + ".com");
        user.put("first_name", ger.nextInt(99) * 11);
        user.put("last_name", ger.nextInt(99) * 11);
        user.put("position", "Gerente");
        user.put("company", "Google");
        user.put("company_industry", "Internet and Telecom");
        getRequestSpecification()
            .body(user)
            .when().put(baseURI + lead + "/" + ID)
            .then().log().status().log().body().statusCode(Matchers.anyOf(Matchers.is(200), Matchers.is(204)));
    }

    public void deveDeletarOLeadEspecifico() {
        System.out.println("Executando método deveDeletarOLeadEspecifico");
        deveCriarNovoLead();
        getRequestSpecification()
            .when().delete(baseURI + lead + "/" + ID)
            .then().log().status().statusCode(204);
    }

    public void deveEnviarRequestGetParaReceberLista() {
        System.out.println("Executando método deveEnviarRequestGetParaReceberLista");
        getRequestSpecification()
            .when().get(baseURI + leadList)
            .then().log().status().log().body().statusCode(200)
            .body("data.leads_lists.id", Matchers.hasItem(4337228))
            .body("data.leads_lists.name", Matchers.hasItem("Clessio's leads"))
            .body("data.leads_lists.leads_count", Matchers.hasItem(43))
            .body("data.leads_lists.created_at", Matchers.hasItem("2023-05-29 22:06:20 UTC"));
    }

    public void deveEnviarRequestGetParaReceberUmaListaEspecifica() {
        System.out.println("Executando método deveEnviarRequestGetParaReceberUmaListaEspecifica");
        Object path = getRequestSpecification()
            .when().get(baseURI + leadList)
            .then().log().status().log().body().statusCode(200)
            .extract().path("data.leads_lists.id");
        System.out.println("Path: " + path.toString().substring(1, 8));
        String subs = path.toString().substring(1, 8);
        getRequestSpecification()
            .when().get(baseURI + leadList + "/" + subs)
            .then().log().status().log().body().statusCode(200)
            .body("data.leads.id", Matchers.hasItem(127315783))
            .body("data.leads.id", Matchers.hasItem(127315782))
            .body("data.leads.id", Matchers.hasItem(127315780))
            .body("data.leads.id", Matchers.hasItem(127315779))
            .body("data.leads.id", Matchers.hasItem(127315778))
            .body("data.leads.id", Matchers.hasItem(127306182));
    }

    public void deveEnviarRequestPostParaCriarNovaLista() {
        System.out.println("Executando método deveEnviarRequestPostParaCriarNovaLista");
        HashMap<String, Object> list = new HashMap<>();
        list.put("name", "Clessio's New leads list");
        getRequestSpecification()
            .body(list)
            .when().post(baseURI + leadList)
            .then().log().status().log().body().statusCode(201)
            .body("data.name", Matchers.hasItem("Clessio's New leads list"));
    }

    public void deveAlterarRequestComPutParaEditarListaDeLead() {
        System.out.println("Executando método deveAlterarRequestComPutParaEditarListaDeLead");
        Object path = getRequestSpecification()
            .when().get(baseURI + leadList)
            .then().log().status().log().body().statusCode(200)
            .extract().path("data.leads_lists.id");
        System.out.println("Path: " + path.toString().substring(1, 8));
        String subs = path.toString().substring(1, 8);
        HashMap<String, Object> list = new HashMap<>();
        list.put("name", "Clessio's New leads list Edit");
        getRequestSpecification()
            .body(list)
            .when().put(baseURI + leadList + "/" + subs)
            .then().log().status().log().body().statusCode(204);
    }

    public void deveDeletarUmaListaDeLeadEspecifica() {
        System.out.println("Executando método deveDeletarUmaListaDeLeadEspecifica");
        Object path = getRequestSpecification()
            .when().get(baseURI + leadList)
            .then().log().status().log().body().statusCode(200)
            .extract().path("data.leads_lists.id");
        System.out.println("Path: " + path.toString().substring(1, 8));
        String subs = path.toString().substring(1, 8);
        getRequestSpecification()
            .when().delete(baseURI + leadList + "/" + subs)
            .then().log().status().log().body().statusCode(204);
    }
}
