import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Headers;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;
import org.testng.TestNG;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;

public class Test {

    public static Response doGetRequest(String endpoint) {
        RestAssured.defaultParser = Parser.JSON;

        return
                given().headers("Content-Type", ContentType.JSON, "Accept", ContentType.JSON).
                        when()
                        .get(endpoint)
                        .then()
                        .contentType(ContentType.JSON)
                        .log()
                        .all()
                        .extract()
                        .response();
    }
        @org.testng.annotations.Test
    public void main() {
        Response response = doGetRequest("https://jsonplaceholder.typicode.com/users");

        List<String> jsonResponse = response.jsonPath().getList("$"); // "$" это обозначение каоторое означает корневой элемент
        System.out.println(jsonResponse.size());// здесь мы получаем количество пользователей в массиве


        String usernames = response.jsonPath()
                .getString("username");
        System.out.println(usernames); // здесь мы получаем их имина в массиве

        System.out.println();

        String usernames0 = response.jsonPath()
                .getString("username[0]");
        System.out.println(usernames0);// выведет только первое имя из массива

    }


    @org.testng.annotations.Test
    public void test() {

        Response response = doGetRequest("https://jsonplaceholder.typicode.com/users/1");
        Map<String, String> company = response.jsonPath().getMap("company"); //Здесь мы в стринговое значение company прсваиваем все что в него входит
        System.out.println(company.get("name"));


    }

    @org.testng.annotations.Test
    public void test1() {
        Response response = doGetRequest("https://jsonplaceholder.typicode.com/users/");
        Map<String, String> company = response.jsonPath().getMap("company[1]"); //собирает все компании из тела ответа и возвращает только имя из 2 компании
        System.out.println(company.get("name"));
    }

    @org.testng.annotations.Test
    public void test2() {       // Этот тест собирает впеременную заголовки все заголовки
        Response response =
                given().
                        when().get("https://jsonplaceholder.typicode.com/users/").then().extract().response();
        Headers allheaders = response.getHeaders();    // вот тут это и происходит
        //String heaferName = response.getHeader("HeaderName");
        System.out.println(allheaders);

    }

    @org.testng.annotations.Test
    public void test3() {       // Этот тест собирает впеременную только один заголовок
        Response response =
                given().
                        when().get("https://jsonplaceholder.typicode.com/users/").then().extract().response();
        // Headers allheaders = response.getHeaders();    // вот тут это и происходит
        String headerName = response.getHeader("Content-Type");
        System.out.println(headerName);

    }

    @org.testng.annotations.Test
    public void test4() {       //Получаем все куки в мапу
        Response response =
                given().
                        when().get("https://jsonplaceholder.typicode.com/users/").then().extract().response();
        Map<String, String> allCookies = response.getCookies();
        //String cookieValue = response.getCookie("");   // в этом примере конкретно какую нибудь куку))
        System.out.println(allCookies);
        given().cookies(allCookies).when().get("https://jsonplaceholder.typicode.com/users/").then().extract().response(); // тут мы передаем наши куки в запрос
        Map<String, String> allCookies1 = response.getCookies();
        System.out.println(allCookies1);

    }

  @org.testng.annotations.Test
    public void test5() {       //Получаем Line and Status
        Response response =
                given().
                        when().get("https://jsonplaceholder.typicode.com/users/").then().extract().response();

        String statusLine = response.getStatusLine();  // Получаем статус линию
        System.out.println(statusLine);
        int statusCode = response.getStatusCode();     // Получаем статус код
        System.out.println(statusCode);

    }

    @org.testng.annotations.Test
    public void test6() {       //Получаем Headers
        Response response =

                given().contentType(ContentType.JSON).// здесть могут быть хидоры и заголовки и контент тип

                        when().get("https://jsonplaceholder.typicode.com/users/").then().assertThat().cookie("__cfduid").extract().response();
        Map<String, String> allCookies1 = response.getCookies();
        long timeInMs = get("https://jsonplaceholder.typicode.com/users/").timeIn(TimeUnit.SECONDS); // прказывает измирение в секундах
        System.out.println(timeInMs);


    }

        }
