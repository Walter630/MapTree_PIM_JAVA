package com.pim.MapTree.modules.tree.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/trees")
public class TreeController {

    @GetMapping
    public ResponseEntity<List<Object>> getTrees() {
        return ResponseEntity.ok(List.of());
    }
}
