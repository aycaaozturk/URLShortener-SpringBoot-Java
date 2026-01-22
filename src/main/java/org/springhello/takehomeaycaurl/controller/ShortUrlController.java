package org.springhello.takehomeaycaurl.controller;


import org.springframework.web.bind.annotation.RestController;
import org.springhello.takehomeaycaurl.service.ShortUrlService;
import org.springhello.takehomeaycaurl.model.ShortUrl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.HttpStatus;      //represents http status like 404, used with response entity
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;  //to create uri objects
import java.util.Map;
import java.util.Optional;


@RestController    // framework will know this class is related to http
public class ShortUrlController {

    //controller receives the http requests (get, post, put, delete)
    //calls the related functions (directing to the service)
    //returns a json object

    //http functions match with rest functions

    //get    gets from the server, through URL             ->read
    //post   sends the server a new one, with json         ->create
    //put    updates                                       ->update
    //delete                                               ->delete


    //Roles

    //localhost: my pc, with the ip address...
    //http://localhost:8080 : i want to connect to the web service on my pc which works at port 8080
    //http://localhost:8080/...  : endpoints, the functionality defined by the program

    //Server-> when the program runs, tomcat (server) starts running at the address http://localhost:8080
    //Client-> when i send a request with postman/curl with a json body
    //         when i visit the short url

    private final ShortUrlService service;


    //logger: to write log messages

    private static final Logger logger = LoggerFactory.getLogger(ShortUrlController.class);

    public ShortUrlController(ShortUrlService service) {
        this.service = service;
    }


    //will get the request for /shorten
    // from the json body, itll create a short url, then itll save and return it

    @PostMapping("/shorten")
    public ResponseEntity<?> createShortUrl(@RequestBody Map<String, Object> body) {
        //can return any type, the json input will be in a map form

        try {
            String url = (String) body.get("url");
            String customId = (String) body.get("customId");
            Object firstTTL = body.get("ttl");
            Long ttl = null;

            if (firstTTL != null) {
                ttl = Long.valueOf(firstTTL.toString());
            }
            ShortUrl shortUrl = service.createShortUrl(url, customId, ttl);
            logger.info("new short URL created:" + shortUrl.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(shortUrl);


        } catch (IllegalArgumentException e) {
            logger.error("Error");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }


    }

    @GetMapping("/{id}")   //should find the original url from the short one
    public ResponseEntity<?> redirect(@PathVariable String id) {  //id redirected as argument String id

        Optional<ShortUrl> url = service.getShortUrl(id);
        if (url.isPresent()) {
            ShortUrl shortUrl = url.get();
            logger.info("Redirected: {} -> {}", id, shortUrl.getOriginalUrl());


            return ResponseEntity.status(HttpStatus.FOUND)
                    .location(URI.create(shortUrl.getOriginalUrl()))
                    .build();

        } else {
            logger.warn("not found or expired:  {}", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Short URL not found or expired");
        }


    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {

        if (!service.existsById(id)) {
            logger.warn("non-existent short url: {}", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Short URL not found");
        }

        service.deleteShortUrl(id);
        logger.info("short url deleted: {}", id);
        return ResponseEntity.noContent().build();
    }

}
