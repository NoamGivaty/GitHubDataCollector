package com.GitHubDataCollector.repo;

import com.GitHubDataCollector.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User,Long> {
}
