package com.GitHubDataCollector.controller;

import com.GitHubDataCollector.service.GitHubService;
import com.GitHubDataCollector.service.JsonToExcelConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

import java.io.IOException;

@RestController
@RequestMapping("")
public class MainController {

    @Autowired
    GitHubService gitHubService;

    @Autowired
    JsonToExcelConverter jsonToExcelConverter;

    @RequestMapping(value = "/MultiplyUsersInfo", method = RequestMethod.GET)
    public ResponseEntity<?> MultiplyUsersInfo(@RequestParam("users") String users, @RequestParam(value = "keywords", required = false) String keywords) throws IOException, InterruptedException {
        List<String> usersList = new ArrayList<>();
        List<String> keywordsList = new ArrayList<>();

        String[] usersSplit = users.split(",");
        for (String s : usersSplit) {
            usersList.add(s.trim());
        }

        if (keywords != null && !keywords.isEmpty()) {
            String[] keywordsSplit = keywords.split(",");
            for (String s : keywordsSplit) {
                keywordsList.add(s.trim());
            }
        }

        String jsonString = gitHubService.getMultiplyUsersInfo(usersList, keywordsList);
        String res = jsonToExcelConverter.writeJsonToCsv(jsonString);
        System.out.println(res);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
