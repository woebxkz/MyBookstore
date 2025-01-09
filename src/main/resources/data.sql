INSERT INTO books (title,
                   author,
                   price,
                   stock,
                   category,
                   published_date,
                   publisher) VALUES
                                ('Pride and Prejudice', 'Jane Austen', 39.99, 25, 'Romance', '1813-01-28', 'Penguin Books'),
                                ('Anne of Green Gables', 'Lucy Maud Montgomery', 37.99, 50, 'Children’s Fiction', '1908-01-01', 'Hachette'),
                                ('Wuthering Heights', 'Emily Brontë', 39.99, 25, 'Gothic Fiction', '1847-12-01', 'Alfred A. Knopf'),
                                ('Beach Read', 'Emily Henry', 49.99, 40, 'Romance', '2020-05-19', 'Hachette'),
                                ('Daisy Jones & The Six', 'Taylor Jenkins Reid', 44.99, 30, 'Fiction', '2019-03-05', 'Ballantine Books'),
                                ('Malibu Rising', 'Taylor Jenkins Reid', 45.99, 35, 'Fiction', '2021-06-01', 'Penguin Books'),
                                ('The Seven Husbands of Evelyn Hugo', 'Taylor Jenkins Reid', 47.99, 40, 'Fiction', '2017-06-13', 'Atria Books'),
                                ('Six of Crows', 'Leigh Bardugo', 48.99, 45, 'Fantasy', '2015-09-29', 'Macmillan'),
                                ('Shadow and Bone', 'Leigh Bardugo', 42.99, 40, 'Fantasy', '2012-06-05', 'Macmillan'),
                                ('Crooked Kingdom', 'Leigh Bardugo', 51.99, 35, 'Fantasy', '2016-09-27', 'Macmillan');

INSERT INTO cart (book_id, quantity) VALUES (2, 1), (1,1), (6,2)