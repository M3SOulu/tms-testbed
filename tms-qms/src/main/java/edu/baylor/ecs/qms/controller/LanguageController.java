package edu.baylor.ecs.qms.controller;

import edu.baylor.ecs.qms.Exception.ResourceNotFoundException;
import edu.baylor.ecs.qms.model.Language;
import edu.baylor.ecs.qms.repository.LanguageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/language")
public class LanguageController {
    @Autowired
    private LanguageRepository languageRepository;

    @CrossOrigin
    @GetMapping("")
    public List<Language> findAllLanguages() {
        return languageRepository.findAll();
    }

    @CrossOrigin
    @GetMapping("/{languageId}")
    public Language findLanguageById(@PathVariable Long languageId) {
        return languageRepository.findById(languageId).orElse(null);
    }

    @CrossOrigin
    @PostMapping("")
    public Language createLanguage(@Valid @RequestBody Language language) {
        return languageRepository.save(language);
    }

    @CrossOrigin
    @PutMapping("/{languageId}")
    public Language updateLanguage(@PathVariable Long languageId, @Valid @RequestBody Language languageRequest) {
        return languageRepository.findById(languageId)
                .map(language -> {
                    language.setName(languageRequest.getName());
                    return languageRepository.save(language);
                }).orElseThrow(() -> new ResourceNotFoundException("Language not found with id " + languageId));
    }

    @CrossOrigin
    @DeleteMapping("/{languageId}")
    public ResponseEntity<?> deleteLanguage(@PathVariable Long languageId) {
        return languageRepository.findById(languageId)
                .map(language -> {
                    languageRepository.delete(language);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("Language not found with id " + languageId));
    }


}
