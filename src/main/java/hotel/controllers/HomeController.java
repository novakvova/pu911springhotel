package hotel.controllers;

import hotel.entities.Hotel;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class HomeController {

    @GetMapping("/")
    public Hotel index() {
        Hotel hotel =new Hotel();
        hotel.setName("Баня");
        return hotel;
    }
}
