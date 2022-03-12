package app.controller;

import java.security.Principal;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import app.model.Film;
import app.model.Genre;
import app.model.User;
import app.service.CommentService;
import app.service.FilmService;
import app.service.UserService;

@Controller
public class AjaxController {
	
	@Autowired
	private FilmService filmService;
	
	@Autowired
	private CommentService commentService;
	
	@Autowired
	private UserService userService;
	
	@ModelAttribute
	public void addAttributes(Model model, HttpServletRequest request) {
		Principal principal = request.getUserPrincipal();

		if (principal != null) {
			model.addAttribute("logged", true);
			model.addAttribute("admin", request.isUserInRole("ADMIN"));
		} else {
			model.addAttribute("logged", false);
		}
	}
	
	@GetMapping("/more/{page}")
	public String getFilms(Model model, @PathVariable int page) {
		// Before returning a page it confirms that there are more left
		if (page <= (int) Math.ceil(filmService.count()/6)) {
			model.addAttribute("films", filmService.findAll(PageRequest.of(page,6)));
			return "movies";
		}
		
		return null;
	}
	
	@GetMapping("/moreSearch/{name}/{page}")
	public String getFilmsSeach(Model model, @PathVariable String name, @PathVariable int page) {
		// Before returning a page it confirms that there are more left
		if (page <= (int) Math.ceil(filmService.countByName(name)/6)) {
			model.addAttribute("films", filmService.findLikeName(name, PageRequest.of(page,6)));
			return "movies";
		}
		
		return null;
	}
	
	@GetMapping("/moreGenre/{genre}/{page}")
	public String getFilmsGenre(Model model, @PathVariable String genre, @PathVariable int page) {
		// Before returning a page it confirms that there are more left
		Genre gen = Genre.valueOf(genre);
		
		if (page <= (int) Math.ceil(filmService.countByGenre(gen)/6)) {
			model.addAttribute("films", filmService.findByGenre(gen, PageRequest.of(page,6)));
			return "movies";
		}
		
		return null;
	}
	
	@GetMapping("/moreComments/{id}/{page}")
	public String getComments(Model model, @PathVariable long id, @PathVariable int page) {
		// Before returning a page it confirms that there are more left
		Optional<Film> film = filmService.findById(id);
		
		if (page <= (int) Math.ceil(commentService.countByFilm(film)/2)) {
			model.addAttribute("comments", commentService.findByFilm(film, PageRequest.of(page, 2)));
			return "comments";
		}
		
		return null;
	}

	@GetMapping("/moreCommentsProfile/{id}/{page}")
	public String getCommentsProfile(Model model, @PathVariable long id, @PathVariable int page) {
		// Before returning a page it confirms that there are more left
		Optional<User> user = userService.findById(id);
		
		if (page <= (int) Math.ceil(commentService.countByUser(user)/6)) {
			model.addAttribute("comments", commentService.findByUser(user, PageRequest.of(page, 5)));
			return "commentsProfile";
		}
		
		return null;
	}
	
	@GetMapping("/moreCommentsWatchProfile/{id}/{page}")
	public String getCommentsWatchProfile(Model model, @PathVariable long id, @PathVariable int page) {
		// Before returning a page it confirms that there are more left
		Optional<User> user = userService.findById(id);
		
		if (page <= (int) Math.ceil(commentService.countByUser(user)/6)) {
			model.addAttribute("comments", commentService.findByUser(user, PageRequest.of(page, 5)));
			return "commentsWatchProfile";
		}
		
		return null;
	}
}