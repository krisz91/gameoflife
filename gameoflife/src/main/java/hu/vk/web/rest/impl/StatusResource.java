package hu.vk.web.rest.impl;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by vargakrisztian on 2017. 02. 01..
 */
@RestController
@RequestMapping("/")
public class StatusResource {

    @GetMapping
    public String ok() {
        return "OK";
    }
}
