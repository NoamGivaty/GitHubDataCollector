package com.GitHubDataCollector.service;

import java.io.*;
import java.nio.charset.StandardCharsets;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
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
                "EJS Repositories", "C# Repositories", "JavaScript Repositories", "Jupyter Notebook Repositories",
                "C++ Repositories", "CSS Repositories", "Python Repositories", "Node.js Repositories", "Angular Repositories",
                "React Repositories","HTML Repositories", "Kotlin Repositories","C Repositories","TypeScript Repositories","Dart Repositories", "Objective-C Repositories", "Forks", "Commits", "Stars", "Code Lines", "Tests", "Keywords" };
        String[] columnsMapping = {"name", "username", "url", "publicRepos","followers","following","javaRepositories","ejsRepositories",
                "cSharpRepositories", "javaScriptRepositories", "jupyterRepositories", "cppRepositories","cssRepositories","pythonRepositories","nodeJsRepositories",
                "angularRepositories","reactRepositories","htmlRepositories","kotlinRepositories","cRepositories","typeScriptRepositories","dartRepositories",
                "objectiveCRepositories", "forks","commits","stars","codeLines","tests","keywords"};

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
                data.setEjsRepositories(obj.getInt("ejs_repositories"));
                data.setcSharpRepositories(obj.getInt("cSharp_repositories"));
                data.setJavaScriptRepositories(obj.getInt("javaScript_repositories"));
                data.setJupyterRepositories(obj.getInt("jupyter_repositories"));
                data.setCppRepositories(obj.getInt("cpp_repositories"));
                data.setCssRepositories(obj.getInt("css_repositories"));
                data.setPythonRepositories(obj.getInt("python_repositories"));
                data.setNodeJsRepositories(obj.getInt("node.js_repositories"));
                data.setAngularRepositories(obj.getInt("angular_repositories"));
                data.setReactRepositories(obj.getInt("react_repositories"));
                data.setObjectiveCRepositories(obj.getInt("objectiveC_repositories"));
                data.setDartRepositories(obj.getInt("dart_repositories"));
                data.setTypeScriptRepositories(obj.getInt("typeScript_repositories"));
                data.setCRepositories(obj.getInt("c_repositories"));
                data.setKotlinRepositories(obj.getInt("kotlin_repositories"));
                data.setHtmlRepositories(obj.getInt("html_repositories"));
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

