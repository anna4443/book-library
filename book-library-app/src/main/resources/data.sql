delete from loan;
delete from book;
delete from authorities;
delete from users;

insert into users(id, name, surname, username, email, password, enabled) values
    (1, 'Lana', 'Lanic', 'lana', 'lana@mail.com', '$2y$12$T0gJvBH55nHkw7qNFbHxmO2T6saXxJ9MPphiAuxNSPN6FwLmHeg7S', 1),
    (2, 'Maja', 'Majic', 'maja', 'maja@mail.com', '$2y$12$PeoUg5FxTa7iRJ/tJzk2lu9T6pe6OI2el1eE4wP5gT/IjXdI/qn.S', 1),
    (3, 'Pero', 'Peric', 'pero', 'pero@mail.com', '$2y$12$zOGJ77RmurM8resiC9F57.hK27H273QYAiQOchG.M1LiyARtCcWXG', 1);


insert into authorities (username, authority) values
    ('maja', 'ROLE_USER'),
    ('pero', 'ROLE_ADMIN');


insert into book(id, title, author, year_release, img) values
    (1, 'Harry Potter and the Philosopher''s Stone', 'J.K.Rowling', 1997, 'https://www.jkrowling.com/wp-content/uploads/2018/01/SorcerersStone_2017.png'),
    (2, 'Harry Potter and the Chamber of Secrets', 'J.K.Rowling', 1998, 'https://i.gr-assets.com/images/S/compressed.photo.goodreads.com/books/1521110614l/49525._SX318_.jpg'),
    (3, 'Harry Potter and the Prisoner of Azkaban', 'J.K.Rowling', 1999, 'https://i.gr-assets.com/images/S/compressed.photo.goodreads.com/books/1499277281l/5.jpg'),
    (4, 'Harry Potter and the Goblet of Fire', 'J.K.Rowling', 2000, 'https://i.gr-assets.com/images/S/compressed.photo.goodreads.com/books/1554006152l/6.jpg'),
    (5, 'Harry Potter and the Order of the Phoenix', 'J.K.Rowling', 2003, 'https://i.gr-assets.com/images/S/compressed.photo.goodreads.com/books/1546910265l/2.jpg'),
    (6, 'Harry Potter and the Half-Blood Prince', 'J.K.Rowling', 2005, 'https://i.gr-assets.com/images/S/compressed.photo.goodreads.com/books/1587697303l/1._SX318_.jpg'),
    (7, 'Harry Potter and the Deathly Hallows', 'J.K.Rowling', 2007, 'https://i.gr-assets.com/images/S/compressed.photo.goodreads.com/books/1432278568l/22844208._SY475_.jpg');


insert into loan(id, name, surname, username, title) values
    (1, 'Maja', 'Majic', 'maja', 'Harry Potter and the Goblet of Fire');