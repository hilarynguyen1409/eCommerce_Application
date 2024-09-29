CREATE TABLE IF NOT EXISTS item (
                                    id INT AUTO_INCREMENT PRIMARY KEY,
                                    name VARCHAR(255),
    price DECIMAL(10, 2),
    description VARCHAR(255)
    );
CREATE TABLE IF NOT EXISTS cart (
                      id BIGINT AUTO_INCREMENT PRIMARY KEY,
                      total DECIMAL(19,2) NOT NULL
);