package de.neebs.indexing.controller;

import de.neebs.indexing.model.ultumus.UltumusFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ultumus")
public class UltumusController {
    @Autowired
    private UltumusFacade ultumusFacade;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<?> testUltumus() {
        ultumusFacade.retrieveSummary();
        return ResponseEntity.ok("success");
    }
}
