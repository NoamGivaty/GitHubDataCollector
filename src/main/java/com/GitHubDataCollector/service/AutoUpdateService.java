package com.GitHubDataCollector.service;

import com.GitHubDataCollector.model.User;
import com.GitHubDataCollector.util.Dates;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class AutoUpdateService {
    private final int DAY_TIME_IN_MILLISECONDS = 86400000; // 1 Day
    @Autowired
    GitHubService gitHubService;

    @Autowired
    UserService userService;

    public void startAutoUpdate() throws IOException, InterruptedException {
        Iterable<User> users;

        while(true) {
            users = userService.all();
            for (User user : users) {
                int daysDifference = Dates.getDaysDifference(user.getLastUpdated(), Dates.nowUTC());
                if (daysDifference > 3) {
                    String userString = gitHubService.getUserInfo(user.getUsername(), null);
                    User updatedUser = user.JSONObjectToUser(new JSONObject(userString));
                    userService.save(updatedUser);
                }
            }
            Thread.sleep(DAY_TIME_IN_MILLISECONDS);
        }
    }
}
