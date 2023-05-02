package com.GitHubDataCollector.controller;

import com.GitHubDataCollector.service.GitHubService;
import com.GitHubDataCollector.service.JsonToExcelConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("")
public class GitHubCollector {

    @Autowired
    GitHubService gitHubService;

    @Autowired
    JsonToExcelConverter jsonToExcelConverter;

    @PostMapping("/index")
    public ResponseEntity<?> collectGitHubData(@RequestBody Map<String, List<String>> requestData) throws IOException, InterruptedException {
        List<String> usernames = requestData.get("usernames");
        List<String> keywords = requestData.get("keywords");

        for (int i = 0; i < usernames.size(); i++) {
            String user = usernames.get(i);
            if (user.contains("https")) {
                int lastSlashIndex = user.lastIndexOf("/");
                if (lastSlashIndex >= 0 && lastSlashIndex < user.length() - 1) {
                    String lastPathComponent = user.substring(lastSlashIndex + 1);
                    usernames.set(i, lastPathComponent);
                }
            }
        }

        System.out.println("Usernames: " + usernames);
        System.out.println("Keywords: " + keywords);

        String jsonString = gitHubService.getMultiplyUsersInfo(usernames, keywords);

        String csvString = jsonToExcelConverter.writeJsonToCsv(jsonString);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=githubdata.csv");
        headers.add(HttpHeaders.CONTENT_TYPE, "text/csv");
        headers.add(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);
        System.out.println(csvString);
        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=githubdata.csv")
                .header("Access-Control-Expose-Headers", "*")
                .contentLength(csvString.length())
                .contentType(MediaType.parseMediaType("text/csv"))
                .body(csvString);
    }
}
