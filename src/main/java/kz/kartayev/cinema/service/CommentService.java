package kz.kartayev.cinema.service;

import kz.kartayev.cinema.model.Comment;
import kz.kartayev.cinema.repository.CommentRepository;
import net.bytebuddy.implementation.auxiliary.AuxiliaryType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommentService {
  private final CommentRepository commentRepository;

  @Autowired
  public CommentService(CommentRepository commentRepository) {
    this.commentRepository = commentRepository;
  }

  @Transactional
  public void save(Comment comment){
    commentRepository.save(comment);
  }
}
