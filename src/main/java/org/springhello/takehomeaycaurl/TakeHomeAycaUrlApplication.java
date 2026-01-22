package org.springhello.takehomeaycaurl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TakeHomeAycaUrlApplication {

    public static void main(String[] args) {
        SpringApplication.run(TakeHomeAycaUrlApplication.class, args);

        //dependencies: spring web (for web apps, http, restful
        //              spring data jpa (for database functions)
        //              h2 database (a lightweight database)

         //      controller
         //      service
         //      repo
         //      model
//
//        {
//            "url": "https://www.youtube.com/",
//                "customId": "hello",
//                "ttl": 3600
//        }
//



// beispiel für eingabe:

//https://www.bahn.de


 //       curl -i -X POST "http://localhost:8084/shorten" -H "Content-Type: application/json" -d "{\"url\":\"https://www.bahn.de\",\"customId\":\"hey\",\"ttl\":600}"

//ins browser:
//   http://localhost:8084/hey


    }

}
