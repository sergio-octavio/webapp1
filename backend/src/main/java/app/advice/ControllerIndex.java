package app.advice;


import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.engine.jdbc.BlobProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import app.entity.Comment;
import app.entity.User;
import app.entity.Film;
import app.entity.Genre;
import app.service.FilmService;
import app.service.UserService;


@Controller
public class ControllerIndex {

	@Autowired
	private UserService userService;
	
	@Autowired
	private FilmService filmService;


	@Autowired
	private SendMailController mailController;
	
	@GetMapping("/")
	public String adviceMe(Model model) {	
		model.addAttribute("trending", filmService.findAll(PageRequest.of(0,6)));
		
		model.addAttribute("action", filmService.findByGenre(Genre.ACTION, PageRequest.of(0,6)));
		model.addAttribute("adventure", filmService.findByGenre(Genre.ADVENTURE, PageRequest.of(0,6)));
		model.addAttribute("animation", filmService.findByGenre(Genre.ANIMATION, PageRequest.of(0,6)));
		model.addAttribute("comedy", filmService.findByGenre(Genre.COMEDY, PageRequest.of(0,6)));
		model.addAttribute("drama", filmService.findByGenre(Genre.DRAMA, PageRequest.of(0,6)));
		model.addAttribute("horror", filmService.findByGenre(Genre.HORROR, PageRequest.of(0,6)));
		model.addAttribute("scifi", filmService.findByGenre(Genre.SCIENCE_FICTION, PageRequest.of(0,6)));

		return "adviceMe";
	}
	
	@GetMapping("/more/{page}")
	public String getFilms(Model model, @PathVariable int page) {
		// Before returning a page it confirms that there are more left
		if (page <= (int)Math.ceil(filmService.count()/6)) {
			model.addAttribute("films", filmService.findAll(PageRequest.of(page,6)));
			return "movies";
		}
		return null;
	}
	
	@GetMapping("/moreGenre/{genre}/{page}")
	public String getFilmsGenre(Model model, @PathVariable String genre, @PathVariable int page) {
		// Before returning a page it confirms that there are more left
		Genre gen = Genre.valueOf(genre);
		if (page <= (int)Math.ceil(filmService.countByGenre(gen)/6)) {
			
			model.addAttribute("films", filmService.findByGenre(gen, PageRequest.of(page,6)));
			return "movies";
		}
		return null;
	}
	
	@GetMapping("/{id}/image")
	public ResponseEntity<Object> downloadImage(@PathVariable long id) throws SQLException {
		Optional<Film> film = filmService.findById(id);
		if (film.isPresent() && film.get().getImageFile() != null) {
			Resource file = new InputStreamResource(film.get().getImageFile().getBinaryStream());
			return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, "image/jpeg").contentLength(film.get().getImageFile().length()).body(file);
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
    @RequestMapping("/login")
	public String login() {
		return "login";
	}
	
	@RequestMapping("/loginerror")
	public String loginerror() {
		return "loginerror";
	}

	@GetMapping("/register")
	public String register() {
		return "register";
	}

	@GetMapping("/admin")
	public String admin() {
		return "admin";
	}
	
	@PostMapping("/registerProcess")
	public String registerProcess(Model model, User user) {
		userService.save(user);
		System.out.println(user.getRoles());
		return "redirect:/menuRegistered";
	}
	
	@GetMapping("/menuRegistered")
	public String menuRegistered(Model model, HttpServletRequest request) {

		model.addAttribute("username", request.getUserPrincipal().getName());

    	User user = userService.findByName(request.getUserPrincipal().getName()).orElseThrow();
    	
		if (request.isUserInRole("ADMIN")){
			return "redirect:/menuAdmin";
		}
		else{
			model.addAttribute("trending", filmService.findAll(PageRequest.of(0,6)));
			model.addAttribute("action", filmService.findByGenre(Genre.ACTION));
			model.addAttribute("adventure", filmService.findByGenre(Genre.ADVENTURE));
			model.addAttribute("animation", filmService.findByGenre(Genre.ANIMATION));
			model.addAttribute("comedy", filmService.findByGenre(Genre.COMEDY));
			model.addAttribute("drama", filmService.findByGenre(Genre.DRAMA));
			model.addAttribute("horror", filmService.findByGenre(Genre.HORROR));
			model.addAttribute("scifi", filmService.findByGenre(Genre.SCIENCE_FICTION));
			
			model.addAttribute("user", user);
			
			//model.addAttribute("recommendation", filmService.);
			//model.addAttribute("commented", filmService.);
			return "menuRegistered";
		}
	}
	
	@GetMapping("/menuFollowing/{id}")
	public String menuFollowing() {
		return "menuFollowing";
	}
	
	@GetMapping("/menuAdmin")
	public String menuAdmin(Model model) {
		model.addAttribute("trending", filmService.findAll(PageRequest.of(0,6)));
		
		model.addAttribute("action", filmService.findByGenre(Genre.ACTION, PageRequest.of(0,6)));
		model.addAttribute("adventure", filmService.findByGenre(Genre.ADVENTURE, PageRequest.of(0,6)));
		model.addAttribute("animation", filmService.findByGenre(Genre.ANIMATION, PageRequest.of(0,6)));
		model.addAttribute("comedy", filmService.findByGenre(Genre.COMEDY, PageRequest.of(0,6)));
		model.addAttribute("drama", filmService.findByGenre(Genre.DRAMA, PageRequest.of(0,6)));
		model.addAttribute("horror", filmService.findByGenre(Genre.HORROR, PageRequest.of(0,6)));
		model.addAttribute("scifi", filmService.findByGenre(Genre.SCIENCE_FICTION, PageRequest.of(0,6)));

		//model.addAttribute("recommendation", filmService.);
		//model.addAttribute("commented", filmService.);
		return "menuAdmin";
	}
	
	@GetMapping("/profile/{id}")
	public String profile(Model model, @PathVariable long id) {
		model.addAttribute("user", userService.findById(id).orElseThrow());
		return "profile";
	}
	
	@GetMapping("/editProfile/{id}")
	public String editProfile(Model model, @PathVariable long id) {
		model.addAttribute("user", userService.findById(id).orElseThrow());
		return "editProfile";
	}
	
	@PostMapping("/editProfile")
	public String editProfileProcess(Model model, User user)
			throws IOException, SQLException {
		
		userService.save(user);


		return "redirect:/profile/" + user.getId();
	}
	
	@GetMapping("/followers")
	public String followers(Model model) {
		return "followers";
	}
	
	@GetMapping("/following")
	public String following(Model model) {
		return "following";
	}
	
	@GetMapping("/watchProfile")
	public String watchProfile(Model model) {
		return "watchProfile";
	}
	
	@GetMapping("/filmUnregistered/{id}")
	public String filmUnregistered(Model model, @PathVariable long id) {
		Film film = filmService.findById(id).orElseThrow();
		model.addAttribute("film", film);
		Genre similar = film.getGenre();
		model.addAttribute("similar", filmService.findByGenre(similar));
		return "filmUnregistered";
	} 
	
	@GetMapping("/filmRegistered/{id}")
	public String filmRegistered(Model model, @PathVariable long id, HttpServletRequest request) {
		Film film = filmService.findById(id).orElseThrow();
		User user = userService.findByName(request.getUserPrincipal().getName()).orElseThrow();
		model.addAttribute("film", film);
		model.addAttribute("user", user);
		Genre similar = film.getGenre();
		model.addAttribute("similar", filmService.findByGenre(similar));
		return "filmRegistered";
	}
	
	
	@GetMapping("/addComment/{id}")
	public String addComent(Model model, @PathVariable long id, HttpServletRequest request) {
		Film film = filmService.findById(id).orElseThrow();
		User user = userService.findByName(request.getUserPrincipal().getName()).orElseThrow();
		model.addAttribute("film", film);
		model.addAttribute("user", user);
		return"addComment";
	}
	
	@PostMapping("/addComment/{id}")
	public String addComment(Model model, @PathVariable long id, Comment comment, HttpServletRequest request) {
		Film film = filmService.findById(id).orElseThrow();
		User user = userService.findByName(request.getUserPrincipal().getName()).orElseThrow();
		film.addComments(comment, user);
		filmService.save(film);
		return"redirect:/filmRegistered/" + film.getId();
	}
	
	@GetMapping("/filmAdmin/{id}")
	public String filmAdmin(Model model, @PathVariable long id) {
		Film film = filmService.findById(id).orElseThrow();
		model.addAttribute("film", film);
		Genre similar = film.getGenre();
		model.addAttribute("similar", filmService.findByGenre(similar));
		return "filmAdmin";
	}
	
	@GetMapping("/removeFilm/{id}")
	public String removeFilm(Model model, @PathVariable long id) {
		Optional<Film> film = filmService.findById(id);
		if (film.isPresent()) {
			filmService.delete(id);
		}
		return "redirect:/menuAdmin";
	}
	
	@GetMapping("/addFilm")
	public String addFilm(Model model) {
		return "addFilm";
	}
	
	@PostMapping("/addFilm")
	public String addFilmProcess(Model model, Film film, MultipartFile imageField) throws IOException {			
		if (!imageField.isEmpty()) {
			film.setImageFile(BlobProxy.generateProxy(imageField.getInputStream(), imageField.getSize()));
			film.setImage(true);
		}
		filmService.save(film);
		mailController.sendMail(film.getTitle(), "adviceme1111@gmail.com");
		
		return "redirect:/menuAdmin";
		//model.addAttribute("recommendation", filmService.);
		//model.addAttribute("commented", filmService.);
	}
	
	@GetMapping("/editFilm/{id}")
	public String editFilm(Model model, @PathVariable long id) {

		Optional<Film> film = filmService.findById(id);
		if (film.isPresent()) {
			model.addAttribute("film", film.get());
			return "editFilmPage";
		} else {
			return "redirect:/menuAdmin";
		}
	}
	
	@PostMapping("/editFilm")
	public String editFilmProcess(Model model, Film film, MultipartFile imageField)
			throws IOException, SQLException {
		
		updateImage(film, imageField);
		
		filmService.save(film);

		return "redirect:/menuAdmin";
	}
	
	private void updateImage(Film film, MultipartFile imageField) throws IOException, SQLException {
		
		if (!imageField.isEmpty()) {
			film.setImageFile(BlobProxy.generateProxy(imageField.getInputStream(), imageField.getSize()));
			film.setImage(true);
		} else {
			Film dbFilm = filmService.findById(film.getId()).orElseThrow();
			if (dbFilm.getImage()) {
				film.setImageFile(BlobProxy.generateProxy(dbFilm.getImageFile().getBinaryStream(),
						dbFilm.getImageFile().length()));
				film.setImage(true);
			}
		}
	}
	
}
