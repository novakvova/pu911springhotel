package hotel.controllers;

import hotel.dto.regions.AddRegionDto;
import hotel.dto.regions.UploadImageDto;
import hotel.entities.Hotel;
import hotel.entities.HotelImage;
import hotel.entities.Region;
import hotel.mapper.RegionMapper;
import hotel.repositories.HotelImageRepository;
import hotel.repositories.RegionRepository;
import hotel.storage.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;


@RestController
@RequiredArgsConstructor
public class HomeController {
    private final RegionRepository regionRepository;
    private final RegionMapper regionMapper;
    private final StorageService storageService;
    private final HotelImageRepository hotelImageRepository;

//    @Autowired
//    public HomeController(RegionRepository regionRepository) {
//        this.regionRepository = regionRepository;
//    }

    @GetMapping("/")
    public List<Region> index() {

        //storageService.save("data:image/png;base64,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");
        return regionRepository.findAll();
    }

    @PostMapping("/create")
    public Region create(@RequestBody AddRegionDto dto) {
        Region region = regionMapper.AddRegionToRegion(dto);
        regionRepository.save(region);
        return region;
    }

    @PostMapping("/upload")
    public String upload(@RequestBody UploadImageDto dto) {
        String fileName = storageService.save(dto.getBase64());
        HotelImage image = new HotelImage(fileName);
        hotelImageRepository.save(image);
        return fileName;
    }

    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) throws Exception {

        Resource file = storageService.loadAsResource(filename);
        String urlFileName =  URLEncoder.encode("????????.jpg", StandardCharsets.UTF_8.toString());
        return ResponseEntity.ok()
                //.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
                .contentType(MediaType.IMAGE_JPEG)

                .header(HttpHeaders.CONTENT_DISPOSITION,"filename=\""+urlFileName+"\"")
                .body(file);
    }
}
