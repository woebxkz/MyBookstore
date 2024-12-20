CREATE TABLE books (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(255) NOT NULL,
    author VARCHAR(255) NOT NULL,
    price DOUBLE NOT NULL,
    stock INT NOT　NULL,
    category VARCHAR(255) NOT NULL,
    published_date DATE NOT NULL,
    publisher VARCHAR(255) NOT NULL
);

CREATE TABLE cart (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    book_id INT NOT NULL,
    quantity INT NOT NULL
);