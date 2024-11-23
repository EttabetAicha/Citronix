package org.aicha.citronix.service.imp;

import jakarta.validation.Valid;
import org.aicha.citronix.domain.Farm;
import org.aicha.citronix.domain.Field;
import org.aicha.citronix.domain.Tree;
import org.aicha.citronix.repository.TreeRepository;
import org.aicha.citronix.service.TreeService;
import org.aicha.citronix.web.errors.tree.TreeNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TreeServiceImpl implements TreeService {


    private final TreeRepository treeRepository;

    public TreeServiceImpl(TreeRepository treeRepository) {
        this.treeRepository = treeRepository;
    }

    @Override
    public Tree save(@Valid Tree tree) {
        validateTree(tree);
        tree.validatePlantingDate();
        return treeRepository.save(tree);
    }

    @Override
    public Optional<Tree> findById(UUID id) {
        return treeRepository.findById(id);
    }

    @Override
    public List<Tree> findAll() {
        return treeRepository.findAll();
    }

    @Override
    public Page<Tree> findByFieldId(UUID fieldId, Pageable pageable) {
        return treeRepository.findByFieldId(fieldId, pageable);
    }

    @Override
    public void delete(Tree tree) {
        treeRepository.delete(tree);
    }

    @Override
    public double calculateProductivity(UUID treeId) {
        Tree tree = treeRepository.findById(treeId)
                .orElseThrow(() -> new TreeNotFoundException("Tree not found with ID: " + treeId));
        return tree.calculateProductivity();
    }

    private void validateTree(Tree tree) {
        Field field = tree.getField();
        Farm farm = field.getFarm();

        List<Tree> existingTrees = treeRepository.findByFieldId(field.getId());

        double fieldAreaInHectares = field.getArea() / 10000.0;

        int maxAllowedTrees = (int) Math.floor(fieldAreaInHectares * 100);

        if (existingTrees.size() >= maxAllowedTrees) {
            throw new IllegalArgumentException("Tree density exceeds " + 100 +
                    " trees per hectare for field: " + field.getId() + ". Max allowed: " + maxAllowedTrees +
                    " for field area: " + field.getArea() + " m².");
        }
        if (tree.calculateAge() > 20) {
            throw new IllegalArgumentException("Tree is beyond its productive age (20 years) and cannot be added.");
        }
        if (tree.getPlantingDate().isBefore(farm.getCreationDate())) {
            throw new IllegalArgumentException("Tree planting date cannot be before the farm's creation date.");
        }
    }
}
