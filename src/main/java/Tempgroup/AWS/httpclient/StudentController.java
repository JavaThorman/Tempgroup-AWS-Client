package Tempgroup.AWS.httpclient;

import Tempgroup.AWS.DTO.StudentDTO;
import Tempgroup.AWS.utils.InputScanner;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StudentController {

    InputScanner scanner = new InputScanner();

    public void createStudent(String studentName, int age, String dept) throws IOException, InterruptedException {
        String newStudentUri = "http://localhost:8080/student/save";

        var student = new StudentDTO();
        student.setName(studentName);
        student.setAge(age);
        student.setDept(dept);

        Gson gson = new Gson();
        String jsonStudent = gson.toJson(student);

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest addStudentRequest = HttpRequest.newBuilder()
                .uri(URI.create(newStudentUri))
                .header("Content-Type", "application/json")
                //.header("Authorization", "Bearer " + authToken)
                .POST(HttpRequest.BodyPublishers.ofString(jsonStudent))
                .build();

        HttpResponse<String> response = client.send(addStudentRequest, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            System.out.println("Student added!");

            String responseBody = response.body();
            var convertedBody = gson.fromJson(responseBody, StudentDTO.class);

            System.out.println("New student: " + convertedBody);
        } else {
            System.out.println("Error adding student. Status: " + response.statusCode());
        }
    }

    public void getOneStudent(StudentDTO studentDTO) {

    }

    public void getAllStudents() throws URISyntaxException, IOException, InterruptedException {
        String studentUrl = "http://localhost:8080/student/getStudents";

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest getRequest = HttpRequest.newBuilder()
                .uri(new URI(studentUrl))
                .header("Content-Type", "application/json")
                .build();

        HttpResponse<String> response = client.send(getRequest, HttpResponse.BodyHandlers.ofString());
        int statusCode = response.statusCode();

        if (statusCode == 200) {
            Gson gson = new Gson();
            Type studentType = new TypeToken<ArrayList<StudentDTO>>() {
            }.getType();
            List<StudentDTO> students = gson.fromJson(response.body(), studentType);
            System.out.println("Students: ");
            for (StudentDTO student : students) {
                System.out.println(student.toString());
            }
        } else {
            System.out.println("Error fetching students. Status: " + statusCode);
        }
    }

    public List<StudentDTO> getAllStudents2() throws URISyntaxException, IOException, InterruptedException {
        String studentUri = "http://localhost:8080/student/getStudents";

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest getRequest = HttpRequest.newBuilder()
                .uri(new URI(studentUri))
                .header("Content-Type", "application/json")
                .build();

        HttpResponse<String> response = client.send(getRequest, HttpResponse.BodyHandlers.ofString());

        int statusCode = response.statusCode();

        if (statusCode == 200) {
            Gson gson = new Gson();
            Type studentsType = new TypeToken<ArrayList<StudentDTO>>() {
            }.getType();
            return gson.fromJson(response.body(), studentsType);
        } else {
            System.out.println("Error fetching products. Status code: " + statusCode);
            return Collections.emptyList();
        }
    }

    public void updateStudent (long id) throws IOException, InterruptedException {
        String name = scanner.stringPut("Enter updated student name: ");
        int age = scanner.intPut("Inter updated student age: ");
        String dept = scanner.stringPut("Enter updated dept: ");

        var updatedStudent = new StudentDTO();
        updatedStudent.setId(id);
        updatedStudent.setName(name);
        updatedStudent.setAge(age);
        updatedStudent.setDept(dept);

        Gson gson = new Gson();
        String jsonUpdatedStudent = gson.toJson(updatedStudent);

        String updatedStudentUri = "http://localhost:8080/student/update/" + id;

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest updatedStudentrequest = HttpRequest.newBuilder()
                .uri(new URI(updatedStudentUri))
                .header("Content-Type", "application/json")
                //.header("Authorization", "Bearer " + authToken)
                .PUT(HttpRequest.BodyPublishers.ofString(jsonUpdatedStudent))
                .build();

        HttpResponse<String> response = client.send(updatedStudentrequest, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            System.out.println("Student updated!");
        } else {
            System.out.println("Error updating student. Status: " + response.statusCode());
        }
    }

    public void deleteStudent(long id) throws URISyntaxException, IOException, InterruptedException {
        List<StudentDTO> allStudents = getAllStudents2();

        boolean studentExists = allStudents.stream().anyMatch(student -> student.getId() == id);

        if(!studentExists) {
            System.out.println("Student does not exist");
            System.out.flush();
            return;
        }

        String deleteStudentUri = "http://localhost:8080/student/delete/" + id;

        if(authToken == null || authToken.isEmpty()) {
            System.out.println("Please log in first");
            return;
        }

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest deleteStudentRequest = HttpRequest.newBuilder()
                .uri(new URI(deleteStudentUri))
                .header("Content-Type", "application/json")
                //.header("Authorization", "Bearer " + authToken)
                .DELETE()
                .build();

        try {
            HttpResponse<String> response = client.send(deleteStudentRequest, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                System.out.println("Student deleted");
            } else {
                System.out.println("Error deleting student. Status: " + response.statusCode());
            }
        } catch (Exception e) {
            System.out.println("Error occurred " + e.getMessage());
        }
    }
}
