package mk.ukim.finki.elementservice.service.implementation;


import mk.ukim.finki.elementservice.model.Element;
import mk.ukim.finki.elementservice.model.exceptions.ElementNotFoundException;
import mk.ukim.finki.elementservice.repository.ElementRepository;
import mk.ukim.finki.elementservice.service.ElementService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ElementServiceImplementation implements ElementService {
    ElementRepository elementRepository;

    public ElementServiceImplementation(ElementRepository elementRepository) {
        this.elementRepository = elementRepository;
    }

    @Override
    public List<Element> findAll() {
        return elementRepository.findAll();
    }

    @Override
    public Element findById(Long id) {
        return elementRepository.findById(id).orElseThrow(() -> new ElementNotFoundException(id));
    }

    @Override
    public Element findByStringId(String id) {
        return elementRepository.getElementById(id);
    }

    @Override
    public List<Element> findMuseums() {
        return elementRepository.findAll().stream().filter(element -> element.getType().strip().equalsIgnoreCase("музеј")).collect(Collectors.toList());
    }

    @Override
    public List<Element> findArchaeologicalSites() {
        return elementRepository.findAll().stream().filter(element -> element.getType().strip().equalsIgnoreCase("археолошки локалитет")).collect(Collectors.toList());
    }

    @Override
    public List<Element> findMonasteries() {
        return elementRepository.findAll().stream().filter(element -> element.getType().strip().equalsIgnoreCase("манастир")).collect(Collectors.toList());
    }

    @Override
    public List<Element> searchPlaces(String query) {
        Set<Element> suggestions = new LinkedHashSet<>(elementRepository.findAll().stream()
                .filter(element -> element.getName().strip().toLowerCase().startsWith(query.toLowerCase()))
                .toList());

        suggestions.addAll(elementRepository.findAll().stream()
                .filter(element -> element.getName().strip().toLowerCase().contains(query.toLowerCase()))
                .toList());

        return new ArrayList<>(suggestions);
       // return elementRepository.findAll().stream().filter(element -> element.getName().toLowerCase().contains(query.toLowerCase())).collect(Collectors.toList());
    }

    @Override
    public List<Element> findByName(String name) {
        return elementRepository.findAll().stream().filter(element -> element.getName().strip().equalsIgnoreCase(name)).collect(Collectors.toList());
    }

    @Override
    public Element addUserLocation(Double latitude, Double longitude) {
        Element userLocation = new Element("0", "User", "User", latitude.toString(), longitude.toString());
        return elementRepository.save(userLocation);
    }

    @Override
    public List<Element> findAllById(List<Long> ids) {
        return elementRepository.findAllById(ids);
    }
}
