package guru.springframework.sfgpetclinic.services.jpa;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.repositories.OwnerRepository;
import guru.springframework.sfgpetclinic.repositories.PetRepository;
import guru.springframework.sfgpetclinic.repositories.PetTypeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OwnerJPAServiceTest {

  @Mock
  OwnerRepository ownerRepository;

  @Mock
  PetRepository petRepository;

  @Mock
  PetTypeRepository petTypeRepository;

  @InjectMocks
  OwnerJPAService service;

  String LAST_NAME = "Smith";

  @Test
  void findAll() {
    Set<Owner> returnOwnerSet = new HashSet<>();
    returnOwnerSet.add(Owner.builder().id(1L).build());
    returnOwnerSet.add(Owner.builder().id(2L).build());

    when(ownerRepository.findAll()).thenReturn(returnOwnerSet);

    Set<Owner> owners = service.findAll();
    assertNotNull(owners);
    assertEquals(2, owners.size());
  }

  @Test
  void findById() {
    Owner returnOwner = Owner.builder().id(1L).lastName(LAST_NAME).build();
    when(ownerRepository.findById(anyLong())).thenReturn(Optional.of(returnOwner));
    Owner owner = service.findById(1L);
    assertNotNull(owner);
  }

  @Test
  void findByIdNotFound() {
    when(ownerRepository.findById(anyLong())).thenReturn(Optional.empty());
    Owner owner = service.findById(1L);
    assertNull(owner);
  }

  @Test
  void save() {
    Owner returnOwner = Owner.builder().id(1L).lastName(LAST_NAME).build();
    Owner ownerToSave = Owner.builder().id(1L).build();
    when(ownerRepository.save(any())).thenReturn(returnOwner);
    Owner savedOwner = service.save(ownerToSave);
    assertNotNull(savedOwner);
    verify(ownerRepository).save(any());
  }

  @Test
  void delete() {
    Owner returnOwner = Owner.builder().id(1L).lastName(LAST_NAME).build();
    service.delete(returnOwner);
    verify(ownerRepository, times(1)).delete(any());
  }

  @Test
  void deleteById() {
    service.deleteById(1L);
    verify(ownerRepository, times(1)).deleteById(anyLong());
  }

  @Test
  void findByLastName() {
    Owner returnOwner = Owner.builder().id(1L).lastName(LAST_NAME).build();
    when(ownerRepository.findByLastName((any()))).thenReturn(returnOwner);
    Owner smith = service.findByLastName(LAST_NAME);
    assertEquals(LAST_NAME, smith.getLastName());
    verify(ownerRepository, times(1)).findByLastName(any());
  }
}