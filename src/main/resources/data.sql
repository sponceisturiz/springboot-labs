INSERT INTO PRODUCTS (id, name, description, imageFileName, priceUSD, category) VALUES
(1, 'Toy Car', 'Great toy for boys 1-10 years old. Fun and educational', 'toy-car.jpeg', 1, 'toys'),
(2, 'Teddy Bear', 'Great toy for kids 2-12 years old. Perfect falling asleep with', 'teddy-bear.jpg', 15, 'toys'),
(3, 'Apple Laptop', 'Perfect computer for programming in checking emails', 'laptop_640x426.jpeg', 700, 'electronics'),
(4, 'Desktop Monitor', 'Computer monitor for gaming and watching movies', 'computer-monitor_640x426.jpeg', 299, 'electronics'),
(5, 'Audio Speakers', 'Stereo speakers for listening to music at home', 'speakers.jpeg', 89, 'electronics'),

(6, 'Color Markers', 'Four high quality markers for any art project', 'markers_640x426.jpeg', 14.99, 'art'),
(7, 'CD Collection', 'Best of the 80s music collection', 'music_640x426.jpeg', 49.99, 'music'),

(8, 'Gray T-Shirt', 'Unisex gray shirt, small size, cotton', 'gray-shirt_640x426.jpeg', 9.99, 'apparel'),
(9, 'Jeans', 'Fashionable and comfortable jeans', 'pants.jpg', 40, 'apparel'),
(10, 'Diamond Necklace', 'Beautiful green necklace with  diamonds', 'necklace_640x426.jpeg', 800, 'jewelry');

INSERT INTO PRODUCT_CATEGORIES (id, category) VALUES
(1, 'toys'),
(2, 'electronics'),
(3, 'art'),
(4, 'music'),
(5, 'apparel'),
(6, 'jewelry');