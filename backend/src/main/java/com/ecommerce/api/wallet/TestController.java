package com.ecommerce.api.wallet;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RestController
public class TestController {
    @RequestMapping(value = "/test1", method = RequestMethod.GET)
    public ResponseEntity test1() {
        return new ResponseEntity<String>("This is test1", HttpStatus.OK);
    }

    @RequestMapping(value = "/test2", method = RequestMethod.GET)
    public ResponseEntity test2() {
        return new ResponseEntity<String>("This is test2", HttpStatus.OK);
    }

    @RequestMapping(value = "/test3", method = RequestMethod.GET)
    public ResponseEntity test3() {
        return new ResponseEntity<String>("This is test3", HttpStatus.OK);
    }
}
