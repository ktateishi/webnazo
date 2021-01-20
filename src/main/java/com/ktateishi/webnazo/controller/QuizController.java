package com.ktateishi.webnazo.controller;

import com.ktateishi.webnazo.model.request.QuizRequest;
import com.ktateishi.webnazo.model.response.QuizLinkResponse;
import com.ktateishi.webnazo.model.response.QuizResponse;
import com.ktateishi.webnazo.service.QuizService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@AllArgsConstructor
public class QuizController {

    private final QuizService service;

    @RequestMapping(path = "/")
    public ResponseEntity<QuizLinkResponse> index() {
        return new ResponseEntity<>(
                new QuizLinkResponse("/quiz"),
                HttpStatus.OK);
    }

    @RequestMapping(path = "/quiz")
    public ResponseEntity<QuizResponse> quiz(@RequestBody(required = false) QuizRequest param,
                                             @RequestParam(required = false) String name,
                                             HttpServletRequest req) {
        // メソッドチェック
        if (!"POST".equals(req.getMethod())) {
            return new ResponseEntity<>(
                    new QuizResponse("POST method required."),
                    HttpStatus.METHOD_NOT_ALLOWED);
        }

        // パラメータチェック（クエリパラメータ）
        if (name != null && !name.isEmpty() && (param == null || param.getName().isEmpty())) {
            return new ResponseEntity<>(
                    new QuizResponse("parameter 'name' is required in request body. Not query parameter."),
                    HttpStatus.BAD_REQUEST);
        }

        // パラメータチェック（BODY）
        if (param == null || param.getName().isEmpty()) {
            return new ResponseEntity<>(
                    new QuizResponse("parameter 'name' is required."),
                    HttpStatus.BAD_REQUEST);
        }

        // 正しいリクエスト
        String paramName = param.getName();
        if (!service.confirmRegist(paramName)) {
            service.registUser(paramName);
        }
        return new ResponseEntity<>(
                new QuizResponse("Congratulations!! " + paramName + "さんゲームクリアです。おめでとうございます！"),
                HttpStatus.OK);
    }

    @DeleteMapping(path = "/admin/user")
    public ResponseEntity<QuizResponse> delete() {
        service.deleteUser();
        return new ResponseEntity<>(
                new QuizResponse("deleted."),
                HttpStatus.OK);
    }
}
