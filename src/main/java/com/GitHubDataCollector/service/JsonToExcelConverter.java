package com.GitHubDataCollector.service;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

@Service
public class JsonToExcelConverter {

    public String writeJsonToCsv(String jsonData) throws IOException, JSONException {
        // Parse JSON data
        JSONArray jsonArray = new JSONArray(jsonData);

        // Define headers for CSV
        String[] headers = { "Name", "Username", "Url", "Public Repos", "Followers", "Following", "Java Repositories",
                "Python Repositories", "Node.js Repositories", "Angular Repositories",
                "React Repositories", ".NET Repositories", "Forks", "Commits", "Stars",
                "Code Lines", "Tests", "Keywords" };
        String[] columnsMapping = {"name", "username", "url", "publicRepos","followers","following","javaRepositories","pythonRepositories","nodeJsRepositories",
                "angularRepositories","reactRepositories","netRepositories", "forks","commits","stars","codeLines","tests","keywords"};

        // Write data to CSV file
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintWriter pw = new PrintWriter(baos);
        try (ICsvBeanWriter csvWriter = new CsvBeanWriter(pw, CsvPreference.STANDARD_PREFERENCE)) {
            csvWriter.writeHeader(headers);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                GithubData data = new GithubData();
                data.setName(obj.getString("name"));
                data.setUsername(obj.getString("username"));
                data.setUrl(obj.getString("url"));
                data.setPublicRepos(obj.getString("public_repos"));
                data.setFollowers(obj.getString("followers"));
                data.setFollowing(obj.getString("following"));
                data.setJavaRepositories(obj.getInt("java_repositories"));
                data.setPythonRepositories(obj.getInt("python_repositories"));
                data.setNodeJsRepositories(obj.getInt("node.js_repositories"));
                data.setAngularRepositories(obj.getInt("angular_repositories"));
                data.setReactRepositories(obj.getInt("react_repositories"));
                data.setNetRepositories(obj.getInt(".net_repositories"));
                data.setForks(obj.getInt("forks"));
                data.setCommits(obj.getInt("commits"));
                data.setStars(obj.getInt("stars"));
                data.setCodeLines(obj.getString("code_lines"));
                data.setTests(obj.getInt("tests"));
                data.setKeywords(obj.getString("keywords"));
                csvWriter.write(data, columnsMapping);
            }
        }
        System.out.println("Excel file written successfully.");
        String res = baos.toString(StandardCharsets.UTF_8)
                .replaceAll("\r\n          ","")
                .replaceAll("\"\r\n","\"\n");

        return res;
    }
}

