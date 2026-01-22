package org.springhello.takehomeaycaurl.service;

import org.springhello.takehomeaycaurl.model.ShortUrl;
import org.springhello.takehomeaycaurl.repository.ShortUrlRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;   //generating unique IDs

@Service  //business logic

public class ShortUrlService {

    // controller gets the http request, directs (calls) to the service
    // service does the job, goes to repository if needed, doesnt involve http
    //service makes the connection between controller and the repository

    private final ShortUrlRepository repository;

    public ShortUrlService(ShortUrlRepository repository) {
        this.repository=repository;
    }

    public ShortUrl createShortUrl(String originalUrl, String customId, Long ttlSeconds){
        // creates a short url with the custom id. if not possible, assigns a random id



        String id;

        if(customId==null || customId.isEmpty()){
            id=generateId();
        }
        else{
            id=customId;
        }
        if(repository.existsById(id)){
            throw new IllegalArgumentException(" id already exists: "+id);

        }
        ShortUrl shortUrl= new ShortUrl();
        shortUrl.setOriginalUrl(originalUrl);
        shortUrl.setId(id);
        shortUrl.setCreatedAt(LocalDateTime.now());

        if (ttlSeconds != null) {
            shortUrl.setExpiresAt(LocalDateTime.now().plusSeconds(ttlSeconds));
        }

        return repository.save(shortUrl);



    }


    public String generateId() {
        return UUID.randomUUID().toString().substring(0, 6);
    }

    public void deleteShortUrl(String id){
        repository.deleteById(id);
    }



    //should return the shorturl by id if its not expired

    public Optional<ShortUrl> getShortUrl(String id){
        Optional<ShortUrl> found = repository.findById(id);

        if(found.isEmpty() ){
            return Optional.empty();
        }

        ShortUrl shortUrl = found.get();

        if(shortUrl.getExpiresAt()==null || shortUrl.getExpiresAt().isAfter(LocalDateTime.now())){
            return Optional.of(shortUrl);
        }
        return Optional.empty();


    }


    public boolean existsById(String id){
        return repository.existsById(id);
    }




}
