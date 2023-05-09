package kz.kartayev.cinema.service;

import kz.kartayev.cinema.model.Comment;
import kz.kartayev.cinema.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

  public Comment findById(int id){
    return commentRepository.findById(id).get();
  }
  public void deleteById(int id){
    commentRepository.deleteById(id);
  }

  public List<Comment> findAll(){
    return commentRepository.findAll();
  }
}
