insert into auth_infos(id, email, password)
values (1, 'kerezbek@gmail.com', 'kerezbek'),
       (2, 'aruuke@gmail.com', 'aruuke'),
       (3, 'mahamatjan@gmail.com', 'mahamatjan'),
       (4, 'argen@gmail.com', 'argen'),
       (5, 'nurislam@gmail.com', 'nurislam');

insert into users(id, name, surname, auth_info_id)
values (1, 'Kerezbek', 'Aibekov', 1),
       (2, 'Aruuke', 'Sartbaeva', 2),
       (3, 'Mahamatjan', 'Islamidinov', 3),
       (4, 'Argen', 'Abdymomunov', 4),
       (5, 'Nurislam', 'Bakytov', 5);

insert into roles(id, name)
values (1, 'ADMIN'),
       (2, 'USER');

insert into users_roles(user_id, role_id)
values (1, 2),
       (2, 2),
       (3, 2),
       (4, 2),
       (5, 2);

insert into workspaces(id, archive, is_favourite, name)
values (1, false, false, 'Peaksoft House'),
       (2, false, false, 'Makers'),
       (3, false, false, 'DevX'),
       (4, false, false, 'IT School'),
       (5, false, false, 'MegaCom');

insert into boards(id, archive, background, name, workspace_id)
values (1, false,'https://i.pinimg.com/236x/e5/9a/52/e59a522e5010613ae986ede14b8916a3.jpg','Java', 1),
       (2, false,'https://i.pinimg.com/564x/68/d9/1a/68d91a4a0c40857674fbd3a22b9f0b03.jpg','JavaScript', 1),
       (3, false,'https://i.pinimg.com/564x/90/07/3c/90073c3c05426d4e3576b7b147ca6ee1.jpg','Python', 2),
       (4, false,'https://i.pinimg.com/564x/90/07/3c/90073c3c05426d4e3576b7b147ca6ee1.jpg','C++', 2),
       (5, false,'https://i.pinimg.com/564x/90/07/3c/90073c3c05426d4e3576b7b147ca6ee1.jpg','Kotlin', 3),
       (6, false,'https://i.pinimg.com/564x/90/07/3c/90073c3c05426d4e3576b7b147ca6ee1.jpg','PHP', 3),
       (7, false,'https://i.pinimg.com/564x/90/07/3c/90073c3c05426d4e3576b7b147ca6ee1.jpg','Go', 4),
       (8, false,'https://i.pinimg.com/564x/90/07/3c/90073c3c05426d4e3576b7b147ca6ee1.jpg','Dart', 4),
       (9, false,'https://i.pinimg.com/236x/ca/a0/cc/caa0ccc4bd948e9b543e2193cd6592bc.jpg','C', 5),
       (10, false,'https://i.pinimg.com/236x/e5/9a/52/e59a522e5010613ae986ede14b8916a3.jpg','Scala', 5);

insert into columns(id, name, board_id)
values (1, 'TO DO', 1),
       (2, 'IN PROGRESS', 1),
       (3, 'KICK BACK', 1),
       (4, 'FINAL REVIEW', 1),
       (5, 'COMPLETE', 1),
       (6, 'TO DO', 2),
       (7, 'IN PROGRESS', 2),
       (8, 'KICK BACK', 2),
       (9, 'FINAL REVIEW', 2),
       (10, 'COMPLETE', 2),
       (11, 'TO DO', 3),
       (12, 'IN PROGRESS', 3),
       (13, 'KICK BACK', 3),
       (14, 'FINAL REVIEW', 3),
       (15, 'COMPLETE', 3),
       (16, 'TO DO', 4),
       (17, 'IN PROGRESS', 4),
       (18, 'KICK BACK', 4),
       (19, 'FINAL REVIEW', 4),
       (20, 'COMPLETE', 4),
       (21, 'TO DO', 5),
       (22, 'IN PROGRESS', 5),
       (23, 'KICK BACK', 5),
       (24, 'FINAL REVIEW', 5),
       (25, 'COMPLETE', 5),
       (26, 'TO DO', 6),
       (27, 'IN PROGRESS', 6),
       (28, 'KICK BACK', 6),
       (29, 'FINAL REVIEW', 6),
       (30, 'COMPLETE', 6),
       (31, 'TO DO', 7),
       (32, 'IN PROGRESS', 7),
       (33, 'KICK BACK', 7),
       (34, 'FINAL REVIEW', 7),
       (35, 'COMPLETE', 7),
       (36, 'TO DO', 8),
       (37, 'IN PROGRESS', 8),
       (38, 'KICK BACK', 8),
       (39, 'FINAL REVIEW', 8),
       (40, 'COMPLETE', 8),
       (41, 'TO DO', 9),
       (42, 'IN PROGRESS', 9),
       (43, 'KICK BACK', 9),
       (44, 'FINAL REVIEW', 9),
       (45, 'COMPLETE', 9),
       (46, 'TO DO', 10),
       (47, 'IN PROGRESS', 10),
       (48, 'KICK BACK', 10),
       (49, 'FINAL REVIEW', 10),
       (50, 'COMPLETE', 10);

insert into user_workspace_roles(id, role_id_id, user_id_id, workspace_id_id)
values (1, 1, 1, 1),
       (2, 1, 2, 2),
       (3, 1, 3, 3),
       (4, 1, 4, 4),
       (5, 1, 5, 5);

insert into estimations(id, date_of_finish, date_of_start)
values (1, '2023-01-15', '2023-01-02'),
       (2, '2023-01-29', '2023-01-16'),
       (3, '2023-02-12', '2023-01-30'),
       (4, '2023-02-26', '2023-02-13'),
       (5, '2023-03-12', '2023-02-27'),
       (6, '2023-03-26', '2023-03-13');

insert into cards(id, archive, description, name, column_id, estimation_id)
values (1, false, '', 'Variables', 1, 1),
       (2, false, '', 'byte', 1, 1),
       (3, false, '', 'short', 2, 1),
       (4, false, '', 'int', 2, 1),
       (5, false, '', 'long', 3, 1),
       (6, false, '', 'float', 3, 1),
       (7, false, '', 'double', 4, 1),
       (8, false, '', 'char', 4, 1),
       (9, false, '', 'boolean', 5, 1),
       (10, false, '', 'reference types', 5, 1),
       (11, false, '', 'String', 6, 1),
       (12, false, '', 'Conditional statements', 6, 1),
       (13, false, '', 'If else', 7, 1),
       (14, false, '', 'Switch case', 7, 1),
       (15, false, '', 'Ternary operator', 8, 1),
       (16, false, '', 'Loops', 8, 2),
       (17, false, '', 'for', 9, 2),
       (18, false, '', 'while', 9, 2),
       (19, false, '', 'do while', 10, 2),
       (20, false, '', 'Arrays', 10, 2),
       (21, false, '', 'Object Oriented Programming', 11, 2),
       (22, false, '', 'Encapsulation', 11, 2),
       (23, false, '', 'Inheritance', 12, 2),
       (24, false, '', 'Polymorphism', 12, 2),
       (25, false, '', 'Abstraction', 13, 2),
       (26, false, '', 'Access modifiers', 13, 2),
       (27, false, '', 'StringBuilder', 14, 2),
       (28, false, '', 'StringBuffer', 14, 2),
       (29, false, '', 'Enum', 15, 2),
       (30, false, '', 'Exceptions', 15, 2),
       (31, false, '', 'Collection Framework', 16, 2),
       (32, false, '', 'List', 16, 2),
       (33, false, '', 'ArrayList', 17, 3),
       (34, false, '', 'LinkedList', 17, 3),
       (35, false, '', 'Vector', 18, 3),
       (36, false, '', 'Queue', 18, 3),
       (37, false, '', 'PriorityQueue', 19, 3),
       (38, false, '', 'Deque', 19, 3),
       (39, false, '', 'ArrayDeque', 20, 3),
       (40, false, '', 'Set', 20, 3),
       (41, false, '', 'HashSet', 21, 3),
       (42, false, '', 'LinkedHashSet', 21, 3),
       (43, false, '', 'SortedSet', 22, 3),
       (44, false, '', 'TreeSet', 22, 3),
       (45, false, '', 'Map', 23, 3),
       (46, false, '', 'HashMap', 23, 3),
       (47, false, '', 'LinkedHashMap', 24, 3),
       (48, false, '', 'Hashtable', 24, 3),
       (49, false, '', 'SortedMap', 25, 4),
       (50, false, '', 'Comparable,Comparator', 25, 4),
       (51, false, '', 'Equals,Hashcode', 26, 4),
       (52, false, '', 'Generics Wildcard', 26, 4),
       (53, false, '', 'Lambda', 27, 4),
       (54, false, '', 'Wrapper Class', 27, 4),
       (55, false, '', 'Byte', 28, 4),
       (56, false, '', 'Short', 28, 4),
       (57, false, '', 'Integer', 29, 4),
       (58, false, '', 'Long', 29, 4),
       (59, false, '', 'Float', 30, 4),
       (60, false, '', 'Double', 30, 4),
       (61, false, '', 'Character', 31, 4),
       (62, false, '', 'Boolean', 31, 4),
       (63, false, '', 'Git', 32, 4),
       (64, false, '', 'GitHub', 32, 4),
       (65, false, '', 'SQL', 33, 5),
       (66, false, '', 'PostgreSQL', 33, 5),
       (67, false, '', 'Java Database Connectivity', 34, 5),
       (68, false, '', 'Hibernate', 34, 5),
       (69, false, '', 'Cascade Types', 35, 5),
       (70, false, '', 'ALL', 35, 5),
       (71, false, '', 'DETACH', 36, 5),
       (72, false, '', 'MERGE', 36, 5),
       (73, false, '', 'REFRESH', 37, 5),
       (74, false, '', 'PERSIST', 37, 5),
       (75, false, '', 'REMOVE', 38, 5),
       (76, false, '', 'Fetch Types', 38, 5),
       (77, false, '', 'EAGER', 39, 5),
       (78, false, '', 'LAZY', 39, 5),
       (79, false, '', 'Generation Types', 40, 5),
       (80, false, '', 'AUTO', 40, 5),
       (81, false, '', 'TABLE', 41, 6),
       (82, false, '', 'SEQUENCE', 41, 6),
       (83, false, '', 'IDENTITY', 42, 6),
       (84, false, '', 'Spring Framework', 42, 6),
       (85, false, '', 'Bean', 43, 6),
       (86, false, '', 'HTML CSS', 43, 6),
       (87, false, '', 'Spring Model View Controller', 44, 6),
       (88, false, '', 'Spring Security In Memory', 44, 6),
       (89, false, '', 'Authentication', 45, 6),
       (90, false, '', 'Authorization', 45, 6),
       (91, false, '', 'Spring Security With DataBase', 46, 6),
       (92, false, '', 'Rest API', 46, 6),
       (93, false, '', 'Spring Security With JSON Web Token', 47, 6),
       (94, false, '', 'Annotations', 47, 6),
       (95, false, '', 'Create Entities', 48, 6),
       (96, false, '', 'Sign in/Sign up', 48, 6),
       (97, false, '', 'Sign up/Sign in auth with GOOGLE', 49, 6),
       (98, false, '', 'Swagger', 49, 6),
       (99, false, '', 'DataBase Initialization', 50, 6),
       (100, false, '', 'Test and interview', 50, 6);

insert into label(id, color, text)
values (1, 'Gray', 'TO DO'),
       (2, 'Red', 'IN PROGRESS'),
       (3, 'Orange', 'KICK BACK'),
       (4, 'Blue', 'FINAL REVIEW'),
       (5, 'Green', 'COMPLETE');

insert into checklists(id, percent, title)
values (1, '20%', 'Auth with GOOGLE'),
       (2, '35%', 'DataBase Initialization'),
       (3, '50%', 'Create Entities'),
       (4, '45%', 'Swagger'),
       (5, '60%', 'Sign in/Sign up'),
       (6, '55%', 'CRUD Workspace'),
       (7, '85%', 'CRUD Board'),
       (8, '30%', 'CRUD Column'),
       (9, '70%', 'CRUD Label'),
       (10, '80%', 'CRUD Checklist');

insert into attachment(id, attachment, date_of_start)
values (1, 'title.pdf', '2023-01-02'),
       (2, 'title.png', '2023-01-16'),
       (3, 'title.doc', '2023-01-30'),
       (4, 'title.zip', '2023-02-13'),
       (5, 'title.txt', '2023-02-27'),
       (6, 'title.jpg', '2023-03-13'),
       (7, 'title.jpeg', '2023-01-16'),
       (8, 'title.xml', '2023-02-13'),
       (9, 'title.gif', '2023-02-13'),
       (10, 'title.html', '2023-01-02');

insert into comments(id, date_of_start, text, cards_id)
values (1, '2023-01-09', 'I will do it only in a week,after the vocation', 97),
       (2, '2023-01-10', 'I will do it only in a week,after the vocation', 95),
       (3, '2023-01-13', 'I will do it only in a week,after the vocation', 99),
       (4, '2023-01-05', 'I will do it only in a week,after the vocation', 96),
       (5, '2023-01-06', 'I will do it only in a week,after the vocation', 98);

insert into items(id, is_done, text, checklist_id)
values (1, false, 'Finish before deadline', 1),
       (2, false, 'Finish before deadline', 2),
       (3, false, 'Finish before deadline', 3),
       (4, false, 'Finish before deadline', 4),
       (5, false, 'Finish before deadline', 5),
       (6, false, 'Finish before deadline', 6),
       (7, false, 'Finish before deadline', 7),
       (8, false, 'Finish before deadline', 8),
       (9, false, 'Finish before deadline', 9),
       (10, false, 'Finish before deadline', 10);

insert into notifications(id, date_of_write, status, text, board_id, card_id, column_id, from_user_id, user_id_id)
values (1, '2023-01-09', false, 'Appointed you  as an admin to workspace Peaksoft House', 1, 99, 4, 1, 2),
       (2, '2023-01-12', false, 'Moved to list done', 5, 98, 25, 3, 2),
       (3, '2023-01-09', false, 'My God,let me not burn out on this task', 7, 97, 31, 4, 2),
       (4, '2023-01-09', false, 'Added you to the board C', 9, 95, 42, 5, 2);

insert into cards_attachments(card_id, attachments_id)
values (91, 1),
       (92, 2),
       (93, 3),
       (94, 4),
       (95, 5),
       (96, 6),
       (97, 7),
       (98, 8),
       (99, 9),
       (100, 10);

insert into cards_checklists(card_id, checklists_id)
values (97, 1),
       (99, 2),
       (95, 3),
       (98, 4),
       (96, 5),
       (94, 6),
       (92, 7),
       (93, 8),
       (89, 9),
       (90, 10);

insert into cards_labels(card_id, labels_id)
values (95, 1),
       (96, 5),
       (97, 3),
       (98, 4),
       (99, 2);

insert into comments_users(comment_id, users_id)
values (1, 4),
       (2, 5),
       (3, 1),
       (4, 2),
       (5, 3);

insert into users_cards(users_id, cards_id)
values (1, 99),
       (2, 96),
       (3, 98),
       (4, 97),
       (5, 95);

insert into users_notification(user_id, notification_id)
values (1, 1),
       (3, 2),
       (4, 3),
       (5, 4);

insert into favourites(id, board_id, user_id, workspace_id)
values (1, 1, 1, 1),
       (2, 3, 2, 2),
       (3, 5, 3, 3),
       (4, 7, 4, 4),
       (5, 9, 5, 5);
