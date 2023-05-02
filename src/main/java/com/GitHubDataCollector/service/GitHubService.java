package com.GitHubDataCollector.service;

import org.apache.commons.io.FileUtils;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import okhttp3.*;
import org.json.JSONArray;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.*;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.json.JSONObject;

@Service
public class GitHubService {
    private static String[] EXTENSIONS = new String[]{".java", ".py", ".js", ".php", ".rb", ".cpp", ".h", ".c", ".cs"}; // Define the file extensions to include
    public static final Pattern REPOSITORIES_PATTERN = Pattern.compile("codeRepository[^\\s]+[^\\S][^\\n][^\\S]+([^<]+)");
    public static final Pattern NUM_OF_REPOSITORIES_PATTERN = Pattern.compile("Repositories[^<]+<span title=\\\"([^\\\"]+)");
    public static final Pattern NAME_PATTERN = Pattern.compile("itemprop=\\\"name[^\\s]+[^\\S][^\\n][^\\S]+([^\\n]+)");
    public static final Pattern FOLLOW_PATTERN = Pattern.compile("<span class=\\\"text-bold color-fg-default\\\">([^<]+)[^g]+[^e]+[^>]+>([^<]+)");
    public static final Pattern FORKS_PATTERN = Pattern.compile("text-bold[^>]+>([^<]+)[^f]+forks");
    public static final Pattern COMMITS_PATTERN = Pattern.compile("<span class=\\\"d-none d-sm-inline\\\">[^\\S]+<strong>([^<]+)");
    public static final Pattern STARS_PATTERN = Pattern.compile("text-bold[^>]+>([^<]+)[^f]+stars");
    public static final Pattern PROGRAMMING_LANGUAGE_PATTERN = Pattern.compile("programmingLanguage\\\">([^<]+)");

    private static OkHttpClient client = new OkHttpClient.Builder()
            .callTimeout(Duration.ofSeconds(60))
            .readTimeout(Duration.ofSeconds(60))
            .connectTimeout(Duration.ofSeconds(60))
            .build();


    //----main-method------------
    public String getMultiplyUsersInfo(List<String> users, List<String> keywords) throws IOException, InterruptedException {

        JSONArray jsonArray = new JSONArray();
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        for (String user : users) {
            executorService.execute(new Thread(()->{
                JSONObject jsonObject;
                try {
                    jsonObject = new JSONObject(getUserInfo(user, keywords));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                jsonArray.put(jsonObject);

            }));
        }
        executorService.shutdown();
        executorService.awaitTermination(1000000,TimeUnit.HOURS);

        return jsonArray.toString();
    }
    public String getUserInfo(String username, List<String> keywords) throws IOException, InterruptedException {

        JsonNodeFactory factory = JsonNodeFactory.instance;
        ObjectNode objectNode = factory.objectNode();

        objectNode.setAll(rawData(username));
        objectNode.setAll(programmingLanguageUsed(username));
        objectNode.setAll(repositoriesData(username, keywords));

        return  objectNode.toString();
    } //main method
    //----data-collectors------------
    public static ObjectNode rawData (String username) throws IOException {
        JsonNodeFactory factory = JsonNodeFactory.instance;
        ObjectNode objectNode = factory.objectNode();

        String name = getRegexGroup(NAME_PATTERN,username,1,1);
        if (name.equals("</span>"))
            name = "N/A";
        objectNode.put("name",name);
        objectNode.put("username",username);
        objectNode.put("url","https://github.com/" + username);
        objectNode.put("public_repos",getRegexGroup(NUM_OF_REPOSITORIES_PATTERN,username,1,1));
        objectNode.put("followers",getRegexGroup(FOLLOW_PATTERN,username,1,1));
        objectNode.put("following",getRegexGroup(FOLLOW_PATTERN,username,1,2));


        return objectNode;
    } //"dry" data from the user
    public static ObjectNode repositoriesData(String username, List<String> keywords) throws IOException, InterruptedException {
        JsonNodeFactory factory = JsonNodeFactory.instance;
        ObjectNode objectNode = factory.objectNode();
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        List<String> repositoryUrls = scrapingUrls(username); //Creating List (String) of Repositories URL

        List<Thread> threads = new ArrayList<>();

        AtomicInteger forks = new AtomicInteger();
        AtomicInteger commits = new AtomicInteger();
        AtomicInteger stars = new AtomicInteger();

        for (String repositoryUrl : repositoryUrls){
            Thread thread = new Thread(() -> {

                try {
                    forks.addAndGet(Integer.parseInt(getGitHubRepositoryHtml(FORKS_PATTERN, repositoryUrl)));
                    commits.addAndGet(Integer.parseInt(getGitHubRepositoryHtml(COMMITS_PATTERN, repositoryUrl)));
                    stars.addAndGet(Integer.parseInt(getGitHubRepositoryHtml(STARS_PATTERN, repositoryUrl)));
                    System.out.println("Read repository: " + repositoryUrl);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            threads.add(thread);
            executorService.execute(thread);
        }

        executorService.shutdown();
        executorService.awaitTermination(10000,TimeUnit.HOURS);

        objectNode.put("forks", forks.get());
        objectNode.put("commits", commits.get());
        objectNode.put("stars", stars.get());

        String path = username + "Repositories" + generateRandomString();
        cloneRepositories(repositoryUrls,username, path); //Cloning repositories
        objectNode.setAll(getFilesData(keywords, path));
        deleteRepositoriesFolder(path); //Deleting cloned repositories

        return objectNode;
    } //data that fetches from each repository
    public static ObjectNode programmingLanguageUsed(String username) throws IOException {
        JsonNodeFactory factory = JsonNodeFactory.instance;
        ObjectNode objectNode = factory.objectNode();

        float numOfRepositoriesPages = Float.parseFloat(getRegexGroup(NUM_OF_REPOSITORIES_PATTERN,username,1,1)) / 30;
        int java = 0, python = 0, node = 0, angular = 0, react = 0, net = 0;

        for(int i=1 ; numOfRepositoriesPages > 0 ; i++, numOfRepositoriesPages--){
            String html = getGitHubHtml(username,i);
            Matcher matcher = PROGRAMMING_LANGUAGE_PATTERN.matcher(html);

            while(matcher.find()){
                switch (matcher.group(1)){
                    case "Java":
                        java++;
                        break;
                    case "Python":
                        python++;
                        break;
                    case "Node.js":
                        node++;
                        break;
                    case "Angular":
                        angular++;
                        break;
                    case "React":
                        react++;
                        break;
                    case ".NET":
                        net++;
                        break;
                }
            }
        }

        objectNode.put("java_repositories",java);
        objectNode.put("python_repositories",python);
        objectNode.put("node.js_repositories",node);
        objectNode.put("angular_repositories",angular);
        objectNode.put("react_repositories",react);
        objectNode.put(".net_repositories",net);

        return objectNode;
    } //data on programming language
    //----clonde-count-delete-(from files)------------
    public static void cloneRepositories(List<String> repositoryUrls, String username, String path) throws IOException, InterruptedException {
        File repositoriesFolder = new File(path);
        repositoriesFolder.mkdir();
        ExecutorService executorService = Executors.newFixedThreadPool(10);


        for (String repositoryUrl : repositoryUrls) {
            executorService.execute(new Thread(() -> {
                String[] command = {"git", "clone", repositoryUrl};
                ProcessBuilder processBuilder = new ProcessBuilder(command);
                processBuilder.directory(repositoriesFolder);
                int exitCode = 0;
                try {
                    Process process = processBuilder.start();
                    exitCode = process.waitFor();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                if (exitCode == 0) {
                    System.out.println("Cloned repository: " + repositoryUrl);
                } else {
                    System.err.println("Failed to clone repository: " + repositoryUrl);
                }
            }));
        }

        executorService.shutdown();

        executorService.awaitTermination(1000,TimeUnit.HOURS);
    }
    public static ObjectNode getFilesData(List<String> keys, String path) throws IOException {
        List<String> importantKeywords = importantKeywords();
        List<String> keywords = new ArrayList<>();
        keywords.addAll(importantKeywords);
        if(keys!=null)
            keywords.addAll(keys);

        JsonNodeFactory factory = JsonNodeFactory.instance;
        ObjectNode objectNode = factory.objectNode();
        Map<String, Integer> linesOfCodePerRepository = new HashMap<>();
        Map<String, Integer> keywordCount = new HashMap<>();
        AtomicInteger totalLinesOfCode = new AtomicInteger();

        System.out.println("Start reading lines");

        Path repositoriesFolder = Path.of(path);
        Files.walk(repositoriesFolder)
                .filter(Files::isRegularFile)
                .filter(x-> !(x.getFileName().toAbsolutePath().toString().contains(".git")))
                .forEach(file -> {
                    if (!file.toString().contains(".git")) {
                        try {
                            List<String> lines = Files.readAllLines(file);
                            int linesOfCode = lines.size();
                            String fileName = file.getFileName().toString();
                            for (String extension : EXTENSIONS) {
                                if (fileName.endsWith(extension)) {
                                    totalLinesOfCode.addAndGet(linesOfCode);
                                }
                            }

                            Path repositoryPath = repositoriesFolder.relativize(file.getParent());
                            String repositoryName = repositoryPath.toString();

                            linesOfCodePerRepository.merge(repositoryName, linesOfCode, Integer::sum);

                            for (String keyword : keywords) {
                                int count = 0;
                                String regex = Pattern.quote(keyword.toLowerCase());
                                Pattern pattern = Pattern.compile(regex);
                                for (String line : lines) {
                                    Matcher matcher = pattern.matcher(line.toLowerCase());
                                    while (matcher.find()) {
                                        count++;
                                    }
                                }
                                keywordCount.merge(keyword, count, Integer::sum);
                            }


                            System.out.println("Read file: " + file);
                        } catch (IOException e) {
                            System.err.println("Failed to read file: " + file);
                        }
                    }
                });

        System.out.println("Total lines of code: " + totalLinesOfCode);
        System.out.println("Lines of code per repository: " + linesOfCodePerRepository);
        System.out.println("Keyword count: " + keywordCount);

        int count = 0;
        Map<String, Integer> words = new HashMap<>();

        for (String keyword : keywordCount.keySet()) {
            if(importantKeywords.contains(keyword))
                count += keywordCount.get(keyword);
            else
                words.put(keyword,keywordCount.get(keyword));
        }

        objectNode.put("code_lines",totalLinesOfCode.toString());
        objectNode.put("tests",count);
        if(words.toString().contains("{="))
            objectNode.put("keywords","-");
        else
            objectNode.put("keywords",words.toString());

        return objectNode;
    }
    public static List<String> importantKeywords(){
        List<String> keywords = new ArrayList<>();
        keywords.add("@Test"); //Java
        keywords.add("@def test_"); //Python
        keywords.add("[TestMethod]"); //C#
        keywords.add("[Test]"); //C#
        keywords.add("def test_"); //Ruby
        keywords.add("public function tes"); //PHP
        keywords.add("@func Test"); //Go
        keywords.add("@Test");

        return keywords;
    }
    public static void deleteRepositoriesFolder(String path){
        File repositoriesFolder = new File(path);
        try {
            FileUtils.deleteDirectory(repositoriesFolder);
        } catch (IOException e) {
            System.err.println("Failed to delete directory: " + repositoriesFolder);
        }
    }
    //----different------------
    public static String getRegexGroup(Pattern pattern, String username, int pageNum, int groupNum) throws IOException {
        Matcher matcher = pattern.matcher(getGitHubHtml(username,pageNum));
        matcher.find();
        return matcher.group(groupNum);
    }
    public static List<String> scrapingUrls(String username) throws IOException {
        float numOfRepositoriesPages = Float.parseFloat(getRegexGroup(NUM_OF_REPOSITORIES_PATTERN,username,1,1)) / 30;
        List<String> repositoryUrls = new ArrayList<>();

        for(int i=1 ; numOfRepositoriesPages > 0 ; i++, numOfRepositoriesPages--){
            String html = getGitHubHtml(username,i);
            Matcher matcher = REPOSITORIES_PATTERN.matcher(html);

            while(matcher.find()){
                repositoryUrls.add("https://github.com/" + username + "/" + matcher.group(1) + ".git");
            }
        }

        return repositoryUrls;
    }
    //----html-requests------------
    public static String getGitHubHtml(String username, int pageNumber) throws IOException {
        Request request = new Request.Builder()
                .url("https://github.com/" + username + "?page=" + pageNumber +"&tab=repositories")
                .method("GET", null)
                .addHeader("Cookie", "_gh_sess=2JiGkVKEY5BoIbdkJWQK3%2FTkaLpkHNukTMef66cA1KE1svwxvc5i%2BrN5UuS37YffadVIDw0%2B%2BlCmxPgURYHNlvBKf2VmtLKrKcNW7fEHUb3NB%2FHoKwFv8qxKJdXxok9iZQ8rkHWM9eZenH4nlqEpmDTTqQRZjSxdTKuPa8bLCI%2BhEZaBUSsOLMENCXzHsktek5gUEQBQLvPAoXl%2FQ08f7142H3RA2qIZGZBVzRdRubuf3DNuJy1dAqwgV4JgDnTmvRC2Sv7QubV%2F4NHgL4i5pg%3D%3D--r2kOjbmSWWw%2BRZow--LbSQCxsKeZVQROecVe8SdA%3D%3D; _octo=GH1.1.1338388705.1682315579; logged_in=no")
                .build();

        Response response = client.newCall(request).execute();

        return response.body().string();
    }
    public static String getGitHubRepositoryHtml(Pattern pattern ,String repositoryUrl) throws IOException {
        Request request = new Request.Builder()
                .url(repositoryUrl)
                .method("GET", null)
                .addHeader("Cookie", "_gh_sess=2JiGkVKEY5BoIbdkJWQK3%2FTkaLpkHNukTMef66cA1KE1svwxvc5i%2BrN5UuS37YffadVIDw0%2B%2BlCmxPgURYHNlvBKf2VmtLKrKcNW7fEHUb3NB%2FHoKwFv8qxKJdXxok9iZQ8rkHWM9eZenH4nlqEpmDTTqQRZjSxdTKuPa8bLCI%2BhEZaBUSsOLMENCXzHsktek5gUEQBQLvPAoXl%2FQ08f7142H3RA2qIZGZBVzRdRubuf3DNuJy1dAqwgV4JgDnTmvRC2Sv7QubV%2F4NHgL4i5pg%3D%3D--r2kOjbmSWWw%2BRZow--LbSQCxsKeZVQROecVe8SdA%3D%3D; _octo=GH1.1.1338388705.1682315579; logged_in=no")
                .build();

        Response response = client.newCall(request).execute();

        Matcher matcher = pattern.matcher(response.body().string());
        matcher.find();
        return matcher.group(1);
    }
    public static String generateRandomString() {
        final int LENGTH = 5;
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuilder sb = new StringBuilder(LENGTH);

        for (int i = 0; i < LENGTH; i++) {
            sb.append(characters.charAt(random.nextInt(characters.length())));
        }

        return sb.toString();
    }
}