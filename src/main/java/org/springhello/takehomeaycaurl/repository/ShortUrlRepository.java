package org.springhello.takehomeaycaurl.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springhello.takehomeaycaurl.model.ShortUrl;

public interface ShortUrlRepository extends JpaRepository<ShortUrl, String> {

    //communication with the database
    // <T, ID>  T: our model (entity)
    //no further methods needed (i think)


}
