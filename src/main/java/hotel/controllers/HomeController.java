package hotel.controllers;

import hotel.entities.Hotel;
import hotel.entities.Region;
import hotel.repositories.RegionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class HomeController {
    private final RegionRepository regionRepository;

    @Autowired
    public HomeController(RegionRepository regionRepository) {
        this.regionRepository = regionRepository;
    }

    @GetMapping("/")
    public List<Region> index() {
        return regionRepository.findAll();
    }
}
