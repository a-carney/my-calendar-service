-- MySQL
-- creates a schema from scratch for backend service

CREATE DATABASE IF NOT EXISTS calendardb;
USE calendardb;

CREATE TABLE contacts (
        id INT PRIMARY KEY AUTO_INCREMENT,
        first_name VARCHAR(50) NOT NULL,
        last_name VARCHAR(50) NOT NULL,
        email VARCHAR(100),
        phone VARCHAR(20),
        organization VARCHAR(100),  
        notes TEXT,
        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE locations (
        id INT PRIMARY KEY AUTO_INCREMENT,
        name VARCHAR(100) NOT NULL,  
        address VARCHAR(255),
        city VARCHAR(100),
        postal_code VARCHAR(20)
);

CREATE TABLE events (
        id INT PRIMARY KEY AUTO_INCREMENT,
        title VARCHAR(100) NOT NULL,
        description TEXT,
        start_datetime DATETIME NOT NULL,
        end_datetime DATETIME NOT NULL,
        all_day BOOLEAN DEFAULT FALSE,
        status ENUM('scheduled', 'happening', 'completed', 'canceled') DEFAULT 'scheduled',
        location_id INT,
        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
        FOREIGN KEY (location_id) REFERENCES locations(id) ON DELETE SET NULL
);

CREATE TABLE attendees (
        event_id INT NOT NULL,
        contact_id INT NOT NULL,
        is_organizer BOOLEAN DEFAULT FALSE,
        PRIMARY KEY (event_id, contact_id),
        FOREIGN KEY (event_id) REFERENCES events(id) ON DELETE CASCADE,
        FOREIGN KEY (contact_id) REFERENCES contacts(id) ON DELETE CASCADE
);
