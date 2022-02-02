package hotel.controllers;

import hotel.entities.Badge;
import hotel.entities.Region;
import hotel.mapper.RegionMapper;
import hotel.repositories.BadgeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/badges/")
public class BadgeController {
    private final BadgeRepository regionRepository;
    //private final RegionMapper regionMapper;

    @GetMapping("/{id}")
    public Badge getById(@PathVariable("id") int id) {
        return regionRepository.findById(id).get();
    }

    @GetMapping("/all")
    public Page<Badge> getById() {
        Sort sort = Sort.by("Id");
        Pageable pageable = PageRequest.of(0,10,sort);
        return regionRepository.findAll(pageable);
    }
}
