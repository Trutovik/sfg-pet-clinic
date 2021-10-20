package guru.springframework.sfgpetclinic.services.map;

import guru.springframework.sfgpetclinic.model.Owner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class OwnerMapServiceTest {

  OwnerMapService ownerMapService;

  final Long ownerId = 1L;
  final String lastName = "Smith";

  @BeforeEach
  void setUp() {
    ownerMapService = new OwnerMapService(new PetTypeMapService(), new PetMapService());
    ownerMapService.save(Owner.builder().id(ownerId).lastName(lastName).build());
  }

  @Test
  void findAll() {
    Set<Owner> ownerSet = ownerMapService.findAll();
    assertEquals(1L, ownerSet.size());
  }

  @Test
  void findById() {
    Owner owner = ownerMapService.findById(ownerId);
    assertEquals(1L, owner.getId());
  }

  @Test
  void saveExisitngId() {
    final Long existingId = 2L;
    Owner owner2 = Owner.builder().id(existingId).build();
    Owner savedOwner = ownerMapService.save(owner2);
    assertEquals(existingId, savedOwner.getId());
  }

  @Test
  void saveNoId() {
    Owner savedOwner = ownerMapService.save(Owner.builder().build());
    assertNotNull(savedOwner);
    assertNotNull(savedOwner.getId());
  }

  @Test
  void delete() {
    ownerMapService.delete(ownerMapService.findById(ownerId));
    assertEquals(0, ownerMapService.findAll().size());
  }

  @Test
  void deleteById() {
    ownerMapService.deleteById(ownerId);
    assertEquals(0, ownerMapService.findAll().size());
  }

  @Test
  void findByLastName() {
    Owner smith = ownerMapService.findByLastName("Smith");
    assertNotNull(smith);
    assertEquals(ownerId, smith.getId());
    Owner foo = ownerMapService.findByLastName("Foo");
    assertNull(foo);
  }
}