package org.aicha.citronix.web.reset;

import org.aicha.citronix.dto.TreeDto;
import org.aicha.citronix.service.TreeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/trees")
public class TreeController {

    @Autowired
    private TreeService treeService;

    @GetMapping
    public ResponseEntity<List<TreeDto>> getAllTrees() {
        List<TreeDto> trees = treeService.getAllTrees();
        return ResponseEntity.ok(trees);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TreeDto> getTreeById(@PathVariable UUID id) {
        Optional<TreeDto> tree = treeService.getTreeById(id);
        return tree.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<TreeDto> createTree(@RequestBody TreeDto treeDto) {
        TreeDto createdTree = treeService.createTree(treeDto);
        return ResponseEntity.ok(createdTree);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TreeDto> updateTree(@PathVariable UUID id, @RequestBody TreeDto treeDto) {
        Optional<TreeDto> updatedTree = treeService.updateTree(id, treeDto);
        return updatedTree.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTree(@PathVariable UUID id) {
        treeService.deleteTree(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/age")
    public ResponseEntity<Integer> getTreeAge(@PathVariable UUID id) {
        try {
            int age = treeService.getTreeAge(id);
            return ResponseEntity.ok(age);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}/productivity")
    public ResponseEntity<Double> getTreeProductivity(@PathVariable UUID id) {
        try {
            double productivity = treeService.getTreeProductivity(id);
            return ResponseEntity.ok(productivity);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}