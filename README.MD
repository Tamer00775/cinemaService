# Cinema Center Service 

# About 
This application for see movies schedule in cinema center. User can register in system
and buy ticket, write comment, add some money to wallet. Schedule visible only for 
today and tomorrow. This application run with one command. 

# Technologies
- Java 8, 11 
- Spring Framework (Security, Data JPA, REST)
- Spring Mail Sender
- JSON Web Token (JWT)
- Postgresql 11.6
- JUnit
- Mockito 
- Dockerfile 
- Model Mapper
- Checkstyle 

# ENTITY: 
- Person(person_id, username, age, card, role, password, wallet)
- Cinema(cinema_id, name)
- Movies(movie_id, cinema_id, movie_name, description, genre, startTime, endTime, available place, price, rating, duration)
- Comment(comment_id, person_id, movie_id, comment, rating, created_at)
- Transactions(transaction_id, user_id, movie_id, cinema_id, total_price, created_at)

# CODE
`docker pull postgres:11.6`

# Relations between entities 
- Comments -> Person (Many to One) 
- Comments -> Movie (Many to One) 
- Cinema -> Movie(One to Many)
- Sale - Movie (Many to Many)
