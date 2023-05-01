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


    public void writeJsonToExcel(String jsonData) throws IOException, JSONException {

        // Parse JSON data
        JSONArray jsonArray = new JSONArray(jsonData);

        Path tempFile = Files.createTempFile(UUID.randomUUID().toString(), ".csv");
        String tempFilePath = tempFile.toAbsolutePath().toString();
        String[] headers = { "Name", "Url", "Public Repos", "Followers", "Following", "Java Repositories",
                "Python Repositories", "Node.js Repositories", "Angular Repositories",
                "React Repositories", ".NET Repositories", "Forks", "Commits", "Stars",
                "Code Lines", "Tests", "Keywords" };
        try (ICsvBeanWriter csvWriter = new CsvBeanWriter(new FileWriter(tempFilePath, StandardCharsets.UTF_8, true), CsvPreference.STANDARD_PREFERENCE)) {
            String[] headerColumns = headers;
            csvWriter.writeHeader(headerColumns);
            String[] columnsMapping = {"name", "url", "public_repos","followers","following","java_repositories","python_repositories","node.js_repositories",
                    "angular_repositories","react_repositories",".net_repositories", "forks","commits","stars","code_lines","tests","keywords"};
            for (int i = 0 ; i < jsonArray.length(); i++) {
                csvWriter.write(jsonArray.getJSONObject(i), columnsMapping);
            }
        }

//        // Create Excel workbook and sheet
//        Workbook workbook = new XSSFWorkbook();
//        Sheet sheet = workbook.createSheet("Users Data");
//
//        // Write header row
//        Row headerRow = sheet.createRow(0);
//        for (int i = 0; i < headers.length; i++) {
//            Cell cell = headerRow.createCell(i);
//            cell.setCellValue(headers[i]);
//        }
//
//        // Write data rows
//        for (int i = 0; i < jsonArray.length(); i++) {
//            JSONObject jsonObject = jsonArray.getJSONObject(i);
//            Row dataRow = sheet.createRow(i + 1);
//
//            dataRow.createCell(0).setCellValue(jsonObject.getString("name"));
//            dataRow.createCell(1).setCellValue(jsonObject.getString("url"));
//            dataRow.createCell(2).setCellValue(jsonObject.getString("public_repos"));
//            dataRow.createCell(3).setCellValue(jsonObject.getString("followers"));
//            dataRow.createCell(4).setCellValue(jsonObject.getString("following"));
//            dataRow.createCell(5).setCellValue(jsonObject.getInt("java_repositories"));
//            dataRow.createCell(6).setCellValue(jsonObject.getInt("python_repositories"));
//            dataRow.createCell(7).setCellValue(jsonObject.getInt("node.js_repositories"));
//            dataRow.createCell(8).setCellValue(jsonObject.getInt("angular_repositories"));
//            dataRow.createCell(9).setCellValue(jsonObject.getInt("react_repositories"));
//            dataRow.createCell(10).setCellValue(jsonObject.getInt(".net_repositories"));
//            dataRow.createCell(11).setCellValue(jsonObject.getInt("forks"));
//            dataRow.createCell(12).setCellValue(jsonObject.getInt("commits"));
//            dataRow.createCell(13).setCellValue(jsonObject.getInt("stars"));
//            dataRow.createCell(14).setCellValue(jsonObject.getString("code_lines"));
//            dataRow.createCell(15).setCellValue(jsonObject.getInt("tests"));
//            dataRow.createCell(16).setCellValue(jsonObject.getString("keywords"));
//        }
//
//        // Write Excel file
//        FileOutputStream outputStream = new FileOutputStream("github_user.xlsx");
//        workbook.write(outputStream);
//        workbook.close();
//        outputStream.close();
        System.out.println("Excel file written successfully.");
    }

    public String writeJsonToCsv(String jsonData) throws IOException, JSONException {
        // Parse JSON data
        JSONArray jsonArray = new JSONArray(jsonData);

        // Define headers for CSV
        String[] headers = { "Name", "Url", "Public Repos", "Followers", "Following", "Java Repositories",
                "Python Repositories", "Node.js Repositories", "Angular Repositories",
                "React Repositories", ".NET Repositories", "Forks", "Commits", "Stars",
                "Code Lines", "Tests", "Keywords" };
        String[] columnsMapping = {"name", "url", "publicRepos","followers","following","javaRepositories","pythonRepositories","nodeJsRepositories",
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
        String res =  baos.toString(StandardCharsets.UTF_8).replaceAll("\r\n","\n");
        return res;
    }

}

