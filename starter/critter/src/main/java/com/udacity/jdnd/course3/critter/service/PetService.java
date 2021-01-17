package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PetService {

    private PetRepository petRepository;
    private CustomerRepository customerRepository;
    private UserService userService;

    @Autowired
    public PetService(PetRepository petRepository, CustomerRepository customerRepository, UserService userService){
        this.customerRepository = customerRepository;
        this.petRepository = petRepository;
        this.userService = userService;
    }

    public Pet savePet(Pet pet){
        Pet returnedPet = petRepository.save(pet);
        Customer customer = returnedPet.getOwner();
        customer.addPet(returnedPet);
        customerRepository.save(customer);
        return returnedPet;

    }

    public Pet getPet(Long petId){
        return petRepository.getOne(petId);
    }

    public List<Pet> getAllPets(){
        return petRepository.findAll();
    }

    public List<Pet> getAllPetsByIds(List<Long> ids){
        return petRepository.findAllById(ids);
    }

    public List<Pet> getAllPetsByOwner(Long ownerId){
        return petRepository.findPetsByOwnerId(ownerId);
    }

    public Pet findPetById(Long id) {
        return petRepository.findById(id).orElse(null);
    }
}
