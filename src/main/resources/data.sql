INSERT INTO artist (id, name, surname, date_of_birth)
VALUES (1, 'John', 'Doe', '1990-05-15'),
       (2, 'Jane', 'Doe', '1985-02-20'),
       (3, 'Michael', 'Jackson', '1958-08-29'),
       (4, 'Madonna', 'Ciccone', '1958-08-16');
ALTER SEQUENCE artist_id_seq restart with 5;

INSERT INTO label (id, name)
VALUES (1, 'Universal Music Group'),
       (2, 'Sony Music Entertainment'),
       (3, 'Warner Music Group');
ALTER SEQUENCE label_id_seq restart with 4;

INSERT INTO album (id, title, release_date, photo_path, artist_id, label_id)
VALUES (1, 'Album 1', '2022-01-10', '', 1, 1),
       (2, 'Album 2', '2022-03-05', '', 2, 2),
       (3, 'Thriller', '1982-11-30', '', 3, 1),
       (4, 'Like a Virgin', '1984-11-12', '', 4, 2),
       (5, 'Pink Friday', '2023-11-12', '', 4, 2);
ALTER SEQUENCE album_id_seq restart with 6;


INSERT INTO song (id, title, duration, genre, album_id)
VALUES (1, 'Song 1', 3.5, 'POP', 1),
       (2, 'Song 2', 4.2, 'ROCK', 1),
       (3, 'Song 3', 2.8, 'HIP_HOP', 2),
       (4, 'Song 4', 5.1, 'ELECTRONIC', 2),
       (5, 'Billie Jean', 4.8, 'POP', 3),
       (6, 'Thriller', 5.6, 'POP', 3),
       (7, 'Like a Virgin', 4.0, 'POP', 4),
       (8, 'Material Girl', 3.7, 'POP', 4);
ALTER SEQUENCE song_id_seq restart with 9;