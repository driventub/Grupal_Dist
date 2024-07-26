package com.distribuida.book.clients;

import com.distribuida.book.dtos.AutorDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "app-authors")
public interface AuthorRestClient {

    @GetMapping("/authors/{id}")
    AutorDto findById(@PathVariable("id") Integer id);
}
