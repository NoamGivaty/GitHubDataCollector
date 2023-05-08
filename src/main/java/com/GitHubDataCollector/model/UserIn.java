package com.GitHubDataCollector.model;

import java.io.Serializable;
import static com.GitHubDataCollector.model.User.UserBuilder.anUser;

public class UserIn implements Serializable {

    private String name;
    private String username;
    private String url;
    private int publicRepos;
    private int forkedRepos;
    private int emptyRepos;
    private int followers;
    private int following;
    private int scssRepositories;
    private int assemblyRepositories;
    private int pawnRepositories;
    private int javaRepositories;
    private int ejsRepositories;
    private int cSharpRepositories;
    private int javaScriptRepositories;
    private int jupyterRepositories;
    private int cppRepositories;
    private int cssRepositories;
    private int pythonRepositories;
    private int nodeJsRepositories;
    private int angularRepositories;
    private int reactRepositories;
    private int objectiveCRepositories;
    private int dartRepositories;
    private int typeScriptRepositories;
    private int CRepositories;
    private int kotlinRepositories;
    private int htmlRepositories;
    private int swiftRepositories;
    private int goRepositories;
    private int rustRepositories;
    private int rubyRepositories;
    private int scalaRepositories;
    private int phpRepositories;
    private int rRepositories;
    private int forks;
    private int commits;
    private int stars;
    private int codeLines;
    private int tests;
    private String keywords;


    public User toUser() {
        return anUser().name(name).username(username)
                .url(url).publicRepos(publicRepos).forkedRepos(forkedRepos).emptyRepos(emptyRepos)
                .followers(followers).following(following)
                .scssRepositories(scssRepositories).assemblyRepositories(assemblyRepositories)
                .pawnRepositories(pawnRepositories).javaRepositories(javaRepositories)
                .ejsRepositories(ejsRepositories).cSharpRepositories(cSharpRepositories)
                .javaScriptRepositories(javaScriptRepositories).jupyterRepositories(jupyterRepositories)
                .cppRepositories(cppRepositories).cssRepositories(cssRepositories)
                .pythonRepositories(pythonRepositories).nodeJsRepositories(nodeJsRepositories)
                .angularRepositories(angularRepositories).reactRepositories(reactRepositories)
                .objectiveCRepositories(objectiveCRepositories).kotlinRepositories(kotlinRepositories)
                .htmlRepositories(htmlRepositories).swiftRepositories(swiftRepositories)
                .goRepositories(goRepositories).rustRepositories(rustRepositories)
                .rubyRepositories(rubyRepositories).scalaRepositories(scalaRepositories)
                .phpRepositories(phpRepositories).rRepositories(rRepositories)
                .forks(forks).commits(commits).stars(stars).codeLines(codeLines)
                .dartRepositories(dartRepositories)
                .tests(tests).keywords(keywords).build();
    }


    public void updateUser(User user) {
        user.setName(name);
        user.setUsername(username);
        user.setUrl(url);
        user.setPublicRepos(publicRepos);
        user.setForkedRepos(forkedRepos);
        user.setEmptyRepos(emptyRepos);
        user.setFollowers(followers);
        user.setFollowing(following);
        user.setScssRepositories(scssRepositories);
        user.setAssemblyRepositories(assemblyRepositories);
        user.setPawnRepositories(pawnRepositories);
        user.setJavaRepositories(javaRepositories);
        user.setEjsRepositories(ejsRepositories);
        user.setcSharpRepositories(cSharpRepositories);
        user.setJavaScriptRepositories(javaScriptRepositories);
        user.setJupyterRepositories(jupyterRepositories);
        user.setCppRepositories(cppRepositories);
        user.setCssRepositories(cssRepositories);
        user.setPythonRepositories(pythonRepositories);
        user.setNodeJsRepositories(nodeJsRepositories);
        user.setAngularRepositories(angularRepositories);
        user.setReactRepositories(reactRepositories);
        user.setObjectiveCRepositories(objectiveCRepositories);
        user.setDartRepositories(dartRepositories);
        user.setTypeScriptRepositories(typeScriptRepositories);
        user.setCRepositories(CRepositories);
        user.setKotlinRepositories(kotlinRepositories);
        user.setHtmlRepositories(htmlRepositories);
        user.setSwiftRepositories(swiftRepositories);
        user.setGoRepositories(goRepositories);
        user.setRustRepositories(rustRepositories);
        user.setRubyRepositories(rubyRepositories);
        user.setScalaRepositories(scalaRepositories);
        user.setPhpRepositories(phpRepositories);
        user.setrRepositories(rRepositories);
        user.setForks(forks);
        user.setCommits(commits);
        user.setStars(stars);
        user.setCodeLines(codeLines);
        user.setTests(tests);
        user.setKeywords(keywords);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getPublicRepos() {
        return publicRepos;
    }

    public void setPublicRepos(int publicRepos) {
        this.publicRepos = publicRepos;
    }

    public int getForkedRepos() {
        return forkedRepos;
    }

    public void setForkedRepos(int forkedRepos) {
        this.forkedRepos = forkedRepos;
    }

    public int getEmptyRepos() {
        return emptyRepos;
    }

    public void setEmptyRepos(int emptyRepos) {
        this.emptyRepos = emptyRepos;
    }

    public int getFollowers() {
        return followers;
    }

    public void setFollowers(int followers) {
        this.followers = followers;
    }

    public int getFollowing() {
        return following;
    }

    public void setFollowing(int following) {
        this.following = following;
    }

    public int getScssRepositories() {
        return scssRepositories;
    }

    public void setScssRepositories(int scssRepositories) {
        this.scssRepositories = scssRepositories;
    }

    public int getAssemblyRepositories() {
        return assemblyRepositories;
    }

    public void setAssemblyRepositories(int assemblyRepositories) {
        this.assemblyRepositories = assemblyRepositories;
    }

    public int getPawnRepositories() {
        return pawnRepositories;
    }

    public void setPawnRepositories(int pawnRepositories) {
        this.pawnRepositories = pawnRepositories;
    }

    public int getJavaRepositories() {
        return javaRepositories;
    }

    public void setJavaRepositories(int javaRepositories) {
        this.javaRepositories = javaRepositories;
    }

    public int getEjsRepositories() {
        return ejsRepositories;
    }

    public void setEjsRepositories(int ejsRepositories) {
        this.ejsRepositories = ejsRepositories;
    }

    public int getcSharpRepositories() {
        return cSharpRepositories;
    }

    public void setcSharpRepositories(int cSharpRepositories) {
        this.cSharpRepositories = cSharpRepositories;
    }

    public int getJavaScriptRepositories() {
        return javaScriptRepositories;
    }

    public void setJavaScriptRepositories(int javaScriptRepositories) {
        this.javaScriptRepositories = javaScriptRepositories;
    }

    public int getJupyterRepositories() {
        return jupyterRepositories;
    }

    public void setJupyterRepositories(int jupyterRepositories) {
        this.jupyterRepositories = jupyterRepositories;
    }

    public int getCppRepositories() {
        return cppRepositories;
    }

    public void setCppRepositories(int cppRepositories) {
        this.cppRepositories = cppRepositories;
    }

    public int getCssRepositories() {
        return cssRepositories;
    }

    public void setCssRepositories(int cssRepositories) {
        this.cssRepositories = cssRepositories;
    }

    public int getPythonRepositories() {
        return pythonRepositories;
    }

    public void setPythonRepositories(int pythonRepositories) {
        this.pythonRepositories = pythonRepositories;
    }

    public int getNodeJsRepositories() {
        return nodeJsRepositories;
    }

    public void setNodeJsRepositories(int nodeJsRepositories) {
        this.nodeJsRepositories = nodeJsRepositories;
    }

    public int getAngularRepositories() {
        return angularRepositories;
    }

    public void setAngularRepositories(int angularRepositories) {
        this.angularRepositories = angularRepositories;
    }

    public int getReactRepositories() {
        return reactRepositories;
    }

    public void setReactRepositories(int reactRepositories) {
        this.reactRepositories = reactRepositories;
    }

    public int getObjectiveCRepositories() {
        return objectiveCRepositories;
    }

    public void setObjectiveCRepositories(int objectiveCRepositories) {
        this.objectiveCRepositories = objectiveCRepositories;
    }

    public int getDartRepositories() {
        return dartRepositories;
    }

    public void setDartRepositories(int dartRepositories) {
        this.dartRepositories = dartRepositories;
    }

    public int getTypeScriptRepositories() {
        return typeScriptRepositories;
    }

    public void setTypeScriptRepositories(int typeScriptRepositories) {
        this.typeScriptRepositories = typeScriptRepositories;
    }

    public int getCRepositories() {
        return CRepositories;
    }

    public void setCRepositories(int CRepositories) {
        this.CRepositories = CRepositories;
    }

    public int getKotlinRepositories() {
        return kotlinRepositories;
    }

    public void setKotlinRepositories(int kotlinRepositories) {
        this.kotlinRepositories = kotlinRepositories;
    }

    public int getHtmlRepositories() {
        return htmlRepositories;
    }

    public void setHtmlRepositories(int htmlRepositories) {
        this.htmlRepositories = htmlRepositories;
    }

    public int getSwiftRepositories() {
        return swiftRepositories;
    }

    public void setSwiftRepositories(int swiftRepositories) {
        this.swiftRepositories = swiftRepositories;
    }

    public int getGoRepositories() {
        return goRepositories;
    }

    public void setGoRepositories(int goRepositories) {
        this.goRepositories = goRepositories;
    }

    public int getRustRepositories() {
        return rustRepositories;
    }

    public void setRustRepositories(int rustRepositories) {
        this.rustRepositories = rustRepositories;
    }

    public int getRubyRepositories() {
        return rubyRepositories;
    }

    public void setRubyRepositories(int rubyRepositories) {
        this.rubyRepositories = rubyRepositories;
    }

    public int getScalaRepositories() {
        return scalaRepositories;
    }

    public void setScalaRepositories(int scalaRepositories) {
        this.scalaRepositories = scalaRepositories;
    }

    public int getPhpRepositories() {
        return phpRepositories;
    }

    public void setPhpRepositories(int phpRepositories) {
        this.phpRepositories = phpRepositories;
    }

    public int getrRepositories() {
        return rRepositories;
    }

    public void setrRepositories(int rRepositories) {
        this.rRepositories = rRepositories;
    }

    public int getForks() {
        return forks;
    }

    public void setForks(int forks) {
        this.forks = forks;
    }

    public int getCommits() {
        return commits;
    }

    public void setCommits(int commits) {
        this.commits = commits;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    public int getCodeLines() {
        return codeLines;
    }

    public void setCodeLines(int codeLines) {
        this.codeLines = codeLines;
    }

    public int getTests() {
        return tests;
    }

    public void setTests(int tests) {
        this.tests = tests;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }
}