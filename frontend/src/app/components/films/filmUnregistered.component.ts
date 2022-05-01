import { Component, OnInit } from "@angular/core";
import { Comment } from "src/app/models/comment.model";
import { Film } from "src/app/models/film.model";
import { FilmComments } from "src/app/models/rest/filmComments.model";
import { FilmsService } from "src/app/services/film.service";
import { Router, ActivatedRoute } from '@angular/router';
import { LoginService } from "src/app/services/login.service";
import { User } from "src/app/models/user.model";

@Component({
    templateUrl: './filmUnregistered.component.html',
    styleUrls: ['../../../assets/css/appFilm.component.css', '../../../assets/css/themeFilm.component.css',
        '../../../assets/css/jquery.rateyo.component.css', '../../../assets/css/loadingButton.component.css',]
})
export class FilmUnregisteredComponent implements OnInit {

    filmComments!: FilmComments;
    film!: Film;
    comments!: Comment[];
    similar!: Film[];
    user: User | undefined;

    admin: boolean = false;
    registered: boolean = false;
    unregistered: boolean = false;

    constructor(private router: Router, private activatedRouter: ActivatedRoute, private service: FilmsService, private loginService: LoginService) {
        if (this.loginService.isLogged()){
            if (this.loginService.isAdmin()) {
                this.admin = true;
            } else {
                this.registered = true;
                this.user = this.loginService.currentUser();
            }
        } else {
            this.unregistered = true;
        }   
    }

    ngOnInit(): void {
        const id = this.activatedRouter.snapshot.params['id'];
        this.update(id);
    }

    update(id: number) {
        this.service.getFilm(id).subscribe(
            response => {
                this.filmComments = response;
                this.film = this.filmComments.film;
                this.comments = this.filmComments.comments.content;
                this.similar = this.film.similar;
            },
            error => console.log(error)
        );

    }

    deleteFilm(film: Film) {
        this.service.deleteFilm(film.id);
    }

    filmImage(film: Film) {
        return this.service.downloadImage(film);
    }

    account(){
        this.router.navigate(['/profile/', this.user?.id]);
    }

}