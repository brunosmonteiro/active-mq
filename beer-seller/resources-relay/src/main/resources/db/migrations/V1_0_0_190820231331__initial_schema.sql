CREATE TABLE Beer (
  id SERIAL PRIMARY KEY,
  external_id VARCHAR(255) NOT NULL,
  name VARCHAR(255) NOT NULL,
  description TEXT
);

CREATE TABLE Inventory (
  id SERIAL PRIMARY KEY,
  beer_id INT NOT NULL,
  quantity INT NOT NULL,
  FOREIGN KEY (beer_id) REFERENCES Beer(id)
);

CREATE TABLE Pricing (
  id SERIAL PRIMARY KEY,
  beer_id INT NOT NULL,
  price DECIMAL(10, 2) NOT NULL,
  FOREIGN KEY (beer_id) REFERENCES Beer(id)
);

CREATE TABLE Orders (
  id SERIAL PRIMARY KEY,
  total DECIMAL(10, 2) NOT NULL,
  status VARCHAR(255) NOT NULL,
  consumer_id VARCHAR(255) NOT NULL
);

CREATE TABLE Order_Beer (
  id SERIAL PRIMARY KEY,
  beer_id INT NOT NULL,
  order_id INT NOT NULL,
  quantity INT NOT NULL,
  status VARCHAR(255) NOT NULL,
  FOREIGN KEY (beer_id) REFERENCES Beer(id),
  FOREIGN KEY (order_id) REFERENCES Orders(id)
);

CREATE TABLE Notification (
  id SERIAL PRIMARY KEY,
  order_id INT NOT NULL,
  method VARCHAR(255) NOT NULL,
  status VARCHAR(255) NOT NULL,
  FOREIGN KEY (order_id) REFERENCES Orders(id)
);