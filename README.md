# Advice.me 

# Phase 0

## Members 
| Name                      | e-mail                            | GitHub username |
| ------------------------- | --------------------------------- | --------------- |
| María Esteban Sánchez     | m.esteban.2019@alumnos.urjc.es    | Maria-ES        | 
| Miguel Regato Herrero     | m.regato.2019@alumnos.urjc.es     | MiguelRegato    | 
| Juan Carlos Moreno García | jc.moreno.2019@alumnos.urjc.es    | JuanCarlos-URJC |  
| Héctor Fernández Martínez | h.fernandezm.2019@alumnos.urjc.es | HectorURJC      | 
| Vanesa Reina Hernández    | v.reina.2019@alumnos.urjc.es      | vanesa-rh       | 

 

## Entities

### Users: 

* **Anonymous**: This type of users will be able to see the assessments about films of the web page. However, he/she will not be able to contribute adding his/her own assessments and will not receive recommendations. 

* **Registered**: They will be able to receive recommendations and assess/rate films of the web page.  

* **Admin**: They will be able to add/delete films and delete assessments of the web page. 

 

### Assessments:  

Users can assess and rate films. 

 

### Films:  

Users will be able to see the description and assessments of any film. 

 

### Recommendations:  

Users can receive recommendations of films and their assessments. 

 

## User permissions 

Users can write an assessment about a film, having also to rate it. They can modify their assessments at any moment. 

 

## Pictures

The entity ‘Users’ may have one profile picture and the entity ‘Films’ can have several pictures (at least one). 

 

## Charts

There will be a pie chart representing the most valued genders of films. 

 

## Complementary Technology

The web page will send emails to users notifying them about new recommendations. 

 

## Algorithm

Search by filter and sort by ratings from highest to lowest. Furthermore, the application will show films suggested based on user preferences. 

# Phase 1

## Web pages layout using HTML and CSS

### Unregistered users
**Initial menu:**
First screen of the webpage that contains a list of films splitted by genre and a pie chart that informs the most famous film genres of Advice.me.

![avatar](AdviseMe.png)

**Login:**
Screen that will allow registered users to log in giving their email and password. All users have access to this screen even though only registered users will be able to end this process.

![avatar](Login.png)

**Sign up:**
Screen that will allow new users to sing up in Advice.me and access exclusive content and services.

![avatar](Register.png)

**Film description:**
Screen that will display information, images and assessments about the film. Also, at the bottom of the page related films will appear.

![avatar](FilmUnregistered.png)

### Registered users
**Initial menu:**
First screen of the webpage that contains a list of films splitted by genre and a circle graph that informs the most famous film genres of Advice.me. Moreover, this screen will have personaliced recomendations for the user and access to their account.

![avatar](MenuRegistered.png)

**Profile:**
Screen that will display the users information and profile picture. Also they will be able to access the modification profile information and change their profile picture.

![avatar](Profile.png)

**Modify profile:**
Screen that will allow the user to  modify their information but not their profile picture.

![avatar](EditProfile.png)

**Film description:**
Screen that will display information, images and assessments about the film. Also, at the bottom of the page related films will appear. Registered users can add an assessment to the film if they did not do it before.

![avatar](FilmRegistered.png)

### Admin
**Initial menu:**
First screen of the webpage that contains a list of films splitted by genre and a circle graph that informs the most famous film genres of Advice.me. Admins will have access to the "Post film" button.

![avatar](MenuAdmin.png)

**Post film:**
Screen that will allow admins to add films to the database.

![avatar](PostFilm.png)

**Film description:**
Screen that will display information, images and assessments about the film. Admins can eliminate the film and its assesments.

![avatar](FilmAdmin.png)

## Navigation Diagram

![diagram](Diagrama.png)
