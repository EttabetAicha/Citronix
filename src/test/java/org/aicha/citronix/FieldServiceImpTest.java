package org.aicha.citronix;

import org.aicha.citronix.domain.Field;
import org.aicha.citronix.domain.Farm;
import org.aicha.citronix.repository.FieldRepository;
import org.aicha.citronix.service.imp.FieldServiceImp;
import org.aicha.citronix.web.errors.field.FieldNotFoundException;
import org.aicha.citronix.web.errors.field.InvalidFieldAreaException;
import org.aicha.citronix.web.errors.field.InvalidTreeDensityException;
import org.aicha.citronix.web.errors.field.MaxFieldsInFarmException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class FieldServiceImpTest {

    @Mock
    private FieldRepository fieldRepository;

    @InjectMocks
    private FieldServiceImp fieldService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void save_shouldThrowException_whenFieldAreaIsInvalid() {
        Field field = new Field();
        Farm farm = new Farm();
        farm.setArea(100);
        field.setFarm(farm);
        field.setArea(60);

        when(fieldRepository.findFieldByFarmId(any(UUID.class))).thenReturn(Collections.emptyList());

        assertThrows(InvalidFieldAreaException.class, () -> fieldService.save(field));
    }

    @Test
    void save_shouldThrowException_whenMaxFieldsInFarmExceeded() {
        Field field = new Field();
        Farm farm = new Farm();
        farm.setArea(100);
        farm.setFields(Collections.nCopies(10, new Field()));
        field.setFarm(farm);
        field.setArea(10);

        when(fieldRepository.findFieldByFarmId(any(UUID.class))).thenReturn(Collections.emptyList());

        assertThrows(MaxFieldsInFarmException.class, () -> fieldService.save(field));
    }

    @Test
    void save_shouldSaveField_whenValidField() {
        Field field = new Field();
        Farm farm = new Farm();
        farm.setArea(100);
        field.setFarm(farm);
        field.setArea(10);

        when(fieldRepository.findFieldByFarmId(any(UUID.class))).thenReturn(Collections.emptyList());
        when(fieldRepository.save(any(Field.class))).thenReturn(field);

        Field savedField = fieldService.save(field);

        assertNotNull(savedField);
        verify(fieldRepository, times(1)).save(field);
    }

    @Test
    void delete_shouldThrowException_whenFieldNotFound() {
        UUID fieldId = UUID.randomUUID();

        when(fieldRepository.existsById(fieldId)).thenReturn(false);

        assertThrows(FieldNotFoundException.class, () -> fieldService.delete(fieldId));
    }

    @Test
    void delete_shouldDeleteField_whenFieldExists() {
        UUID fieldId = UUID.randomUUID();

        when(fieldRepository.existsById(fieldId)).thenReturn(true);

        fieldService.delete(fieldId);

        verify(fieldRepository, times(1)).deleteById(fieldId);
    }

    @Test
    void findByFarmId_shouldReturnPageOfFields() {
        UUID farmId = UUID.randomUUID();
        Pageable pageable = PageRequest.of(0, 10);
        Page<Field> fieldPage = new PageImpl<>(Collections.singletonList(new Field()));

        when(fieldRepository.findByFarmId(farmId, pageable)).thenReturn(fieldPage);

        Page<Field> result = fieldService.findByFarmId(farmId, pageable);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
    }

    @Test
    void findById_shouldThrowException_whenFieldNotFound() {
        UUID fieldId = UUID.randomUUID();

        when(fieldRepository.findById(fieldId)).thenReturn(Optional.empty());

        assertThrows(FieldNotFoundException.class, () -> fieldService.findById(fieldId));
    }

    @Test
    void findById_shouldReturnField_whenFieldExists() {
        UUID fieldId = UUID.randomUUID();
        Field field = new Field();

        when(fieldRepository.findById(fieldId)).thenReturn(Optional.of(field));

        Optional<Field> result = fieldService.findById(fieldId);

        assertTrue(result.isPresent());
        assertEquals(field, result.get());
    }
}