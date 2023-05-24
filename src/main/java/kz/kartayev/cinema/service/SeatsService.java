package kz.kartayev.cinema.service;

import kz.kartayev.cinema.model.Seats;
import kz.kartayev.cinema.repository.SeatsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SeatsService {
  private final SeatsRepository seatsRepository;

  @Autowired
  public SeatsService(SeatsRepository seatsRepository) {
    this.seatsRepository = seatsRepository;
  }

  public Seats findById(int id){
    return seatsRepository.findById(id).get();
  }
  @Transactional
  public void save(Seats s){
    seatsRepository.save(s);
  }
}
