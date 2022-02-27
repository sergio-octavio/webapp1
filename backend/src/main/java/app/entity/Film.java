package app.entity;


import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;



@Entity
public class Film {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	@Column
	private String title;
	@Column
	private String releaseDate;
	@Column
	private float averageStars;
	@Column
	private String minAge;
	@Column
	private String genre;
	@Column
	private String duration;
	@Column (name="gente")
	private String cast;
	@Column
	private String director;
	@Column
	private String plot;
	
	@OneToMany
	private List<Comment> comments = new ArrayList<>();

	@Lob
	@JsonIgnore
	private Blob imageFile;

	@Column
	private boolean image;
	
	public Film() {
		
	}
	
    public Film(String title, String releaseDate, String minAge, String genre, String duration, String cast, String director, String plot) {
        this.title = title;
        this.releaseDate = releaseDate;
        this.minAge = minAge;
        this.genre = genre;
        this.duration = duration;
        this.cast = cast;
        this.director = director;
        this.plot = plot;
    }
    /*
    // Comments
    public void addComment(Comment comment) {
    	this.comments.add(comment);
    	calculateAverage();
    }
    
    public void deleteComment(Comment comment) {
		this.comments.remove(comment);
		calculateAverage();
	}
    
    // Stars
    public void calculateAverage() {
    	int sum = 0;
    	for (Comment comment: comments) {
    		sum += comment.getStars();
    	}
    	this.averageStars = (sum/comments.size());
    }
    */
	// Getters
	public String getTitle() {
        return title;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getMinAge() {
        return minAge;
    }

    public String getGenre() {
        return genre;
    }

    public String getDuration() {
        return duration;
    }

    public String getCast() {
        return cast;
    }

    public String getDirector() {
        return director;
    }

    public String getPlot() {
        return plot;
    }
    
	public Blob getImageFile() {
		return imageFile;
	}
	
	public boolean getImage(){
		return this.image;
	}
	
	public long getId() {
		return id;
	}
	
	public List<Comment> getComments() {
		return comments;
	}
	
    // Setters
    public void setTitle(String title) {
        this.title = title;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void setMinAge(String minAge) {
        this.minAge = minAge;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public void setCast(String cast) {
        this.cast = cast;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

	public void setImageFile(Blob image) {
		this.imageFile = image;
	}
	
	public void setImage(boolean image){
		this.image = image;
	}

	public void setId(long newId) {
		this.id = newId;	
	}
	
	public void setComments(Comment comments) {
		this.comments.add(comments);
	}
	
}