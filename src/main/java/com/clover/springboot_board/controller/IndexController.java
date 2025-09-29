package com.clover.springboot_board.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j // 로깅용
public class IndexController {

    @GetMapping
    public String index() {
        log.info("index메서드 call");
        return "index"; // 타임리프가 templates을 자동으로 탐색해준다.
    }
}
