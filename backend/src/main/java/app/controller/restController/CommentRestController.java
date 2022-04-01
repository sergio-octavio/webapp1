package app.controller.restController;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import app.model.Comment;
import app.model.Film;
import app.model.User;
import app.service.CommentService;
import app.service.FilmService;
import app.service.UserService;

@RestController
@RequestMapping("/api/comments")
public class CommentRestController {

	@Autowired
	private UserService userService;

	@Autowired
	private FilmService filmService;

	@Autowired
	private CommentService commentService;

	
	@PostMapping("/addCom/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public Comment addComment(@PathVariable long id, HttpServletRequest request, @RequestBody Comment comment) {
		Film film = filmService.findById(id).orElseThrow();
		User user = userService.findByName(request.getUserPrincipal().getName()).orElseThrow();
		comment.setFilm(film);
		comment.setUser(user);
		commentService.save(comment);
		film.calculateAverage();
		filmService.save(film);
		// createRecommendation(id, film, user);
		return comment;
	}

	@PutMapping("/editCom")
	public ResponseEntity<Comment> editComment(Comment newComment){
		Comment comment = commentService.findById(newComment.getId()).orElseThrow();
		Film film = comment.getFilm();
		User user = comment.getUser();
		newComment.setUser(user);
		newComment.setFilm(film);
		commentService.save(newComment);
		film.calculateAverage();
		filmService.save(film);
		return new ResponseEntity<>(newComment, HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Comment> removeComment(@PathVariable long id, HttpServletRequest request) {
		Comment comment = commentService.findById(id).orElseThrow();
		User user = userService.findByName(request.getUserPrincipal().getName()).orElseThrow();
		User userComment = comment.getUser();
		
		if (userComment.getId().equals(user.getId()) || request.isUserInRole("ADMIN")) {
			Film film = comment.getFilm();
			commentService.delete(id);
			film.calculateAverage();
			filmService.save(film);
			return new ResponseEntity<>(HttpStatus.OK);
		}
		
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
}
