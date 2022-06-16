CREATE TABLE IF NOT EXISTS bookings (
    id          VARCHAR(60) DEFAULT RANDOM_UUID() PRIMARY KEY,
    user        VARCHAR NOT NULL,
    date        VARCHAR NOT NULL,
    timeslot    VARCHAR NOT NULL,
    laundryroom VARCHAR NOT NULL
);