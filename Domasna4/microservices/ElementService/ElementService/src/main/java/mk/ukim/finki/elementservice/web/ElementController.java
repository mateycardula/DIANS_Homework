package mk.ukim.finki.elementservice.web;


import mk.ukim.finki.elementservice.model.Element;
import mk.ukim.finki.elementservice.service.ElementService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api")
public class ElementController {
    private final ElementService elementService;

    public ElementController(ElementService elementService) {
        this.elementService = elementService;
    }
    @GetMapping("/all")
    public ResponseEntity<List<Element>> findAll(){
        return new ResponseEntity<>(elementService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Element> findById(@PathVariable Long id){
        return new ResponseEntity<>(elementService.findById(id), HttpStatus.OK);
    }

    @GetMapping("/monasteries")
    public ResponseEntity<List<Element>> findMonasteries(){
        return new ResponseEntity<>(elementService.findMonasteries(), HttpStatus.OK);
    }

    @GetMapping("/archaeologicalSites")
    public ResponseEntity<List<Element>> findArchaeologicalSites(){
        return new ResponseEntity<>(elementService.findArchaeologicalSites(), HttpStatus.OK);
    }

    @GetMapping("/museums")
    public ResponseEntity<List<Element>> findMuseums(){
        return new ResponseEntity<>(elementService.findMuseums(), HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Element>> findSimilarTo(@RequestParam(required = false) String query){
        if(query == null){
            return new ResponseEntity<>(List.of(), HttpStatus.OK);
        }
        return new ResponseEntity<>(elementService.searchPlaces(query), HttpStatus.OK);
    }

    @GetMapping("/search/{name}")
    public ResponseEntity<List<Element>> findElement(@PathVariable String name){
        return new ResponseEntity<>(elementService.findByName(name), HttpStatus.OK);
    }

    @PutMapping("/add/user")
    public ResponseEntity<Element> addUserLocation(@RequestBody Map<String, Double> body){
        Double latitude = body.get("latitude");
        Double longitude = body.get("longitude");
        return new ResponseEntity<>(elementService.addUserLocation(latitude, longitude), HttpStatus.OK);
    }

    @PostMapping("/allByIds")
    public ResponseEntity<List<Element>> getElementsByIds(@RequestBody Long[] elementIds) {
        List<Element> elements = elementService.findAllById(List.of(elementIds));
        return ResponseEntity.ok(elements);
    }

}
