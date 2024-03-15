package Tempgroup.AWS.httpclient;

import Tempgroup.AWS.DTO.EmailDto;
import Tempgroup.AWS.DTO.LoginDto;
import Tempgroup.AWS.DTO.RegisterDto;
import Tempgroup.AWS.DTO.TokenDto;
import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class AdminController {


    private static final String TOKEN_TYPE = "Bearer ";
    private String accessToken;
    private String refreshToken;


    public void adminLogin(LoginDto loginDto) throws URISyntaxException, IOException, InterruptedException {
        Gson gson = new Gson();
        String json = gson.toJson(loginDto);


        HttpClient client = HttpClient.newHttpClient();
        HttpRequest post = HttpRequest.newBuilder()
                .uri(new URI("http://localhost:8080/api/v1/auth/authenticate"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        HttpResponse<String> response = client.send(post, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());

        if (response.statusCode() != 200) {
            throw new RuntimeException("Failed : HTTP error code : " + response.statusCode());
        }

        TokenDto tokenDto = gson.fromJson(response.body(), TokenDto.class);

        accessToken = tokenDto.getAccess_token();
        refreshToken = tokenDto.getRefresh_token();
    }

    //  http://localhost:8080/api/v1/auth/userRegister
    public void userRegister(RegisterDto registerDto) throws URISyntaxException, IOException, InterruptedException {
        Gson gson = new Gson();
        String json = gson.toJson(registerDto);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest post = HttpRequest.newBuilder()
                .uri(new URI("http://localhost:8080/api/v1/auth/userRegister"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        HttpResponse<String> response = client.send(post, HttpResponse.BodyHandlers.ofString());
        TokenDto tokenDto = gson.fromJson(response.body(), TokenDto.class);
        System.out.println(tokenDto);

    }

    // http://localhost:8080/api/v1/users/update/{userEmail}
    public void updateUserByEmail(LoginDto loginDto, String email, RegisterDto updatedUser) throws URISyntaxException, IOException, InterruptedException {
        adminLogin(loginDto);
        Gson gson = new Gson();
        String json = gson.toJson(updatedUser);
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest post = HttpRequest.newBuilder()
                .uri(new URI("http://localhost:8080/api/v1/users/update/" + email))
                .header("Content-Type", "application/json")
                .header("Authorization", TOKEN_TYPE + accessToken)
                .PUT(HttpRequest.BodyPublishers.ofString(json))
                .build();

        try {
            HttpResponse<String> response = client.send(post, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.body());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void deleteUserByEmail(LoginDto loginDto, String email) throws URISyntaxException, IOException, InterruptedException {
        adminLogin(loginDto);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest post = HttpRequest.newBuilder()
                .uri(new URI("http://localhost:8080/api/v1/users/delete/" + email))
                .header("Authorization", TOKEN_TYPE + accessToken)
                .DELETE()
                .build();

        try {
            HttpResponse<String> response = client.send(post, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.body());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void updateToAdminRole(LoginDto loginDto, EmailDto email) throws URISyntaxException, IOException, InterruptedException {

        adminLogin(loginDto);
        Gson gson = new Gson();
        String json = gson.toJson(email);
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest post = HttpRequest.newBuilder()
                .uri(new URI("http://localhost:8080/api/v1/users/promoteToAdmin"))
                .header("Content-Type", "application/json")
                .header("Authorization", TOKEN_TYPE + accessToken)
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        HttpResponse<String> response = client.send(post, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());
    }


}
