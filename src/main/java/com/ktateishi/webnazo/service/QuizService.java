package com.ktateishi.webnazo.service;

import com.ktateishi.webnazo.dao.QuizDao;
import com.ktateishi.webnazo.model.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class QuizService {

    private final QuizDao dao;

    public boolean confirmRegist(String name) {
        User user = dao.fetchUser(name);
        return user != null;
    }

    public void registUser(String name) {
        dao.insertUser(name);
    }

    public List<User> getUserList() {
        return dao.fetchUserList();
    }

    public void deleteUser() {
        dao.deleteUser();
    }
}
