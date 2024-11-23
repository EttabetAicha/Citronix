package org.aicha.citronix.web.rest;

import jakarta.validation.Valid;
import org.aicha.citronix.domain.Field;
import org.aicha.citronix.domain.Tree;
import org.aicha.citronix.service.TreeService;
import org.aicha.citronix.service.imp.FieldServiceImp;
import org.aicha.citronix.web.errors.tree.TreeNotFoundException;
import org.aicha.citronix.web.mapper.request.TreeMapper;
import org.aicha.citronix.web.vm.request.tree.TreeCreateVM;
import org.aicha.citronix.web.vm.response.tree.TreeResponseVM;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/trees")
public class TreeController {
    private final TreeService treeService;
    private final TreeMapper treeMapper;
    private final FieldServiceImp fieldServiceImp;

    public TreeController(TreeService treeService, TreeMapper treeMapper, FieldServiceImp fieldServiceImp) {
        this.treeService = treeService;
        this.treeMapper = treeMapper;
        this.fieldServiceImp = fieldServiceImp;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createTree(@Valid @RequestBody TreeCreateVM treeCreateVM) {
        try {
            Tree tree = treeMapper.toEntity(treeCreateVM);
            Optional<Field> field = fieldServiceImp.findById(treeCreateVM.getFieldId());
            tree.setField(field.orElseThrow());
            Tree savedTree = treeService.save(tree);
            return ResponseEntity.ok(treeMapper.toResponseVM(savedTree));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Tree creation failed: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("An unexpected error occurred: " + e.getMessage());
        }
    }


    @GetMapping("/findById/{id}")
    public ResponseEntity<TreeResponseVM> getTree(@PathVariable UUID id) {
        Tree tree = treeService.findById(id)
                .orElseThrow(() -> new TreeNotFoundException("Tree not found with ID: " + id));
        return ResponseEntity.ok(treeMapper.toResponseVM(tree));
    }

    @GetMapping("/field/{fieldId}")
    public ResponseEntity<Page<TreeResponseVM>> getTreesByField( @PathVariable UUID fieldId,@RequestParam(defaultValue = "0") int page,@RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Tree> trees = treeService.findByFieldId(fieldId, pageable);
        Page<TreeResponseVM> response = trees.map(treeMapper::toResponseVM);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}/productivity")
    public ResponseEntity<Double> getTreeProductivity(@PathVariable UUID id) {
        double productivity = treeService.calculateProductivity(id);
        return ResponseEntity.ok(productivity);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteTree(@PathVariable UUID id) {
        Tree tree = treeService.findById(id)
                .orElseThrow(() -> new TreeNotFoundException("Tree not found with ID: " + id));
        treeService.delete(tree);
        return ResponseEntity.ok("Tree deleted.");
    }
}
