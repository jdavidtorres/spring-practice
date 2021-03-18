INSERT INTO subjects (`id`, `name`, `father_id`) VALUES
	(1, 'Matemáticas', NULL),
	(2, 'Lenguaje', NULL),
	(3, 'Inglés', NULL),
	(4, 'Ciencias Naturales', NULL),
	(5, 'Ciencias Sociales y Historia', NULL),
	(6, 'Música', NULL),
	(7, 'Artes', NULL),
	(8, 'Algebra', 1),
	(9, 'Aritmética', 1),
	(10, 'Trigonometría', 1),
	(11, 'Lectura y comprensión', 2),
	(12, 'Verbos', 2),
	(13, 'Gramática', 2),
	(14, 'Inglés', 3),
	(15, 'Gramática', 3),
	(16, 'Verbos', 3),
	(17, 'Ciencias Naturales', 4),
	(18, 'Biología', 4),
	(19, 'Física', 4),
	(20, 'Quimica', 4),
	(21, 'Historia', 5),
	(22, 'Ciencias Sociales', 5),
	(23, 'Filosofía', 5),
	(24, 'Música', 6),
	(25, 'Artes', 7);

insert into students (create_at, email, lastname, name, photo)
values  ('2021-03-08 13:38:49.166000', 'hjmcpato@mail.com', 'McPato', 'Hugo Jose', null),
        ('2021-03-08 13:39:11.692000', 'minombre@mail.com', 'McPerro', 'Paco', null),
        ('2021-03-08 13:39:12.295000', 'minombre@mail.com', 'Jumanji', 'Luis', null),
        ('2021-03-08 13:39:12.968000', 'minombre@mail.com', 'Fulano', 'Pedro', null),
        ('2021-03-08 13:39:13.609000', 'minombre@mail.com', 'Torres', 'Jesus', null),
        ('2021-03-15 09:43:50.640000', 'elfulano@mail.com', 'De Tal', 'Fulanito', null);

insert into courses (id, create_at, name)
values  ('2021-03-09 09:22:06.097000', '1A'),
        ('2021-03-09 09:27:55.387000', '2A'),
        ('2021-03-09 09:28:31.281000', '2B'),
        ('2021-03-09 09:28:37.533000', '3A'),
        ('2021-03-09 09:28:41.619000', '3B'),
        ('2021-03-09 09:31:25.122000', '1B');
