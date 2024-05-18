package com.example.soloproject.services;

import com.example.soloproject.models.Chore;
import com.example.soloproject.repositories.ChoreRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ChoreService {

  private final ChoreRepository choreRepository;

  public ChoreService(ChoreRepository choreRepository) {
    this.choreRepository = choreRepository;
  }

  public List<Chore> getAllChores() {
    return (List<Chore>) choreRepository.findAll();
  }

  public Optional<Chore> getChoreById(Long id) {
    return choreRepository.findById(id);
  }

  public Chore saveChore(Chore chore) {
    return choreRepository.save(chore);
  }

  public void deleteChore(Long id) {
    choreRepository.deleteById(id);
  }

  public void removeFromAvailableChores(Long choreId) {
    Optional<Chore> chore = choreRepository.findById(choreId);
    if (chore.isPresent()) {
      chore.get().setAvailable(false);
      choreRepository.save(chore.get());
    }
  }
}
