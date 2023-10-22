-- Створення таблиці "artist"
CREATE TABLE artist
(
    id            SERIAL PRIMARY KEY,
    name          VARCHAR(255) NOT NULL,
    surname       VARCHAR(255) NOT NULL,
    date_of_birth DATE
);

-- Створення таблиці "label"
CREATE TABLE label
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

-- Створення таблиці "album"
CREATE TABLE album
(
    id           SERIAL PRIMARY KEY,
    title        VARCHAR(255) NOT NULL,
    release_date DATE,
    artist_id    INTEGER      NOT NULL,
    label_id     INTEGER      NOT NULL,
    photo_path   VARCHAR(255),
    FOREIGN KEY (artist_id) REFERENCES artist (id),
    FOREIGN KEY (label_id) REFERENCES label (id)
);

-- Створення таблиці "song"
CREATE TABLE song
(
    id       SERIAL PRIMARY KEY,
    title    VARCHAR(255) NOT NULL,
    duration DECIMAL(5, 2),
    genre    VARCHAR(255),
    album_id INTEGER      NOT NULL,
    FOREIGN KEY (album_id) REFERENCES album (id)
);
