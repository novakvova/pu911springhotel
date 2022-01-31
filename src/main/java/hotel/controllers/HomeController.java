package hotel.controllers;

import hotel.dto.regions.AddRegionDto;
import hotel.entities.Hotel;
import hotel.entities.Region;
import hotel.mapper.RegionMapper;
import hotel.repositories.RegionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequiredArgsConstructor
public class HomeController {
    private final RegionRepository regionRepository;
    private final RegionMapper regionMapper;

//    @Autowired
//    public HomeController(RegionRepository regionRepository) {
//        this.regionRepository = regionRepository;
//    }

    @GetMapping("/")
    public List<Region> index() {
        return regionRepository.findAll();
    }

    @PostMapping("/create")
    public Region create(AddRegionDto dto) {
        Region region = regionMapper.AddRegionToRegion(dto);
        regionRepository.save(region);
        return region;
    }
}
