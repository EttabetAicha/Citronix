package org.aicha.citronix.service;

import org.aicha.citronix.domain.Tree;
import org.aicha.citronix.dto.TreeDto;
import org.aicha.citronix.mapper.TreeMapper;
import org.aicha.citronix.repository.TreeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TreeService {

    @Autowired
    private TreeRepository treeRepository;

    @Autowired
    private TreeMapper treeMapper;

    public List<TreeDto> getAllTrees() {
        return treeRepository.findAll().stream()
                .map(treeMapper::toDto)
                .collect(Collectors.toList());
    }

    public Optional<TreeDto> getTreeById(UUID id) {
        return treeRepository.findById(id)
                .map(treeMapper::toDto);
    }

    public TreeDto createTree(TreeDto treeDto) {
        Tree tree = treeMapper.toEntity(treeDto);
        Tree savedTree = treeRepository.save(tree);
        return treeMapper.toDto(savedTree);
    }

    public Optional<TreeDto> updateTree(UUID id, TreeDto treeDto) {
        return treeRepository.findById(id)
                .map(existingTree -> {
                    Tree updatedTree = treeMapper.toEntity(treeDto);
                    updatedTree.setId(existingTree.getId());
                    return treeMapper.toDto(treeRepository.save(updatedTree));
                });
    }

    public void deleteTree(UUID id) {
        treeRepository.deleteById(id);
    }

    public int getTreeAge(UUID id) {
        return treeRepository.findById(id)
                .map(Tree::getAge)
                .orElseThrow(() -> new IllegalArgumentException("Tree not found"));
    }

    public double getTreeProductivity(UUID id) {
        return treeRepository.findById(id)
                .map(Tree::getProductivity)
                .orElseThrow(() -> new IllegalArgumentException("Tree not found"));
    }
}