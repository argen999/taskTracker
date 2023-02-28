insert into users(id, name, surname, email, password, photo_link)
values (1, 'Kerezbek', 'Aibekov', 'kerezbek@gmail.com', '$2a$12$Kzwf20Tt.RqrbCjvljxSKeS0ajkjHmswyZPB0jJ1GDkcS3I4jhj1i', 'https://ru.pinterest.com/pin/907686499876044855/'),
       (2, 'Aruuke', 'Sartbaeva', 'aruuke@gmail.com', '$2a$12$axNSZ2ekesrc4cNDzxxlUuiElncsMs91KJd325HBREw5SNl0.Clxe', 'https://ru.pinterest.com/pin/907686499876044855/'),
       (3, 'Mahamatjan', 'Islamidinov', 'mahamatjan@gmail.com', '$2a$12$634QZhZUKhhIkaPexme/wOeRDnvZBLbqV.EAAh0Jy8RCPlDiWrX2K', 'https://ru.pinterest.com/pin/907686499876044855/'),
       (4, 'Argen', 'Abdymomunov', 'argen@gmail.com', '$2a$12$cVTaN/z9ZWlkhm3d.7Xemea11Og6MeooUWm8//PNuKQKJc6XxQ24y', 'https://ru.pinterest.com/pin/907686499876044855/'),
       (5, 'Nurislam', 'Bakytov' , 'nurislam@gmail.com', '$2a$12$xINOT3UG0ubHqO9RsSk5hOsc4ywAjJynkBWByhoWPIbbJ5T2Hxexu', 'https://ru.pinterest.com/pin/907686499876044855/');

insert into roles(id, name)
values (1, 'ADMIN'),
       (2, 'USER');

insert into users_roles(user_id, role_id)
values (1, 2),
       (2, 2),
       (3, 2),
       (4, 2),
       (5, 2);

insert into workspaces(id, archive, name, creator_id)
values (1, false, 'Peaksoft House', 1),
       (2, false, 'Makers', 2),
       (3, false, 'DevX', 3),
       (4, false, 'IT School', 4),
       (5, false, 'MegaCom', 5);

insert into boards(id, archive, background, name, workspace_id)
values (1, false,'https://i.pinimg.com/236x/e5/9a/52/e59a522e5010613ae986ede14b8916a3.jpg','Java',1),
       (2, false,'https://i.pinimg.com/564x/68/d9/1a/68d91a4a0c40857674fbd3a22b9f0b03.jpg','JavaScript', 2),
       (3, false,'https://i.pinimg.com/564x/90/07/3c/90073c3c05426d4e3576b7b147ca6ee1.jpg','Python', 3),
       (4, false,'https://i.pinimg.com/564x/90/07/3c/90073c3c05426d4e3576b7b147ca6ee1.jpg','C++', 4),
       (5, false,'https://i.pinimg.com/564x/90/07/3c/90073c3c05426d4e3576b7b147ca6ee1.jpg','Kotlin', 5);

insert into columns(id, name, board_id)
values (1, 'TO DO', 1),
       (2, 'IN PROGRESS', 2),
       (3, 'KICK BACK', 3),
       (4, 'FINAL REVIEW', 4),
       (5, 'COMPLETE', 5);

insert into user_workspace_roles(id, role_id, user_id, workspace_id)
values (1, 1, 1, 1),
       (2, 1, 2, 2),
       (3, 1, 3, 3),
       (4, 1, 4, 4),
       (5, 1, 5, 5);

insert into cards(id, archive, created, description, title, column_id, creator_id, workspace_id)
values (1, false, '2023-01-28', '', 'Variables', 1, 1, 1),
       (2, false, '2023-01-28', '', 'byte', 2, 2, 2),
       (3, false, '2023-01-28', '', 'short', 3, 3, 3),
       (4, false, '2023-01-28', '', 'int', 4, 4, 4),
       (5, false, '2023-01-28', '', 'long', 5, 5, 5);

insert into estimations(id, date_of_finish, date_of_start, card_id, creator_id, reminder)
values (1, '2023-01-15', '2023-01-17', 1, 1, 'None'),
       (2, '2023-01-08', '2023-01-16', 2, 2, '5 min. before'),
       (3, '2023-02-06', '2023-01-30', 3, 3, '15 min. before'),
       (4, '2023-02-09', '2023-02-13', 4, 4, '30 min. before'),
       (5, '2023-03-11', '2023-02-27', 5, 5, '1 hour before');

insert into comments(id, local_date_time, text, card_id, user_id)
values (1, '2023-01-09', 'I will do it only in a week,after the vocation', 1, 1),
       (2, '2023-01-10', 'I will do it only in a week,after the vocation', 2, 2),
       (3, '2023-01-13', 'I will do it only in a week,after the vocation', 3, 3),
       (4, '2023-01-05', 'I will do it only in a week,after the vocation', 4, 4),
       (5, '2023-01-06', 'I will do it only in a week,after the vocation', 5, 4);

insert into label(id, color, description, card_id)
values (1, 'Gray', 'TO DO', 1),
       (2, 'Red', 'IN PROGRESS', 2),
       (3, 'Orange', 'KICK BACK', 3),
       (4, 'Blue', 'FINAL REVIEW', 4),
       (5, 'Green', 'COMPLETE', 5);

insert into checklists(id, percent, title, card_id)
values (1, 0, 'Auth with GOOGLE', 1),
       (2, 0, 'DataBase Initialization', 2),
       (3, 0, 'Create Entities', 3),
       (4, 0, 'Swagger', 4),
       (5, 0, 'Sign in/Sign up', 5);

insert into attachment(id, attachment, date_of_start, card_id)
values (1, 'title.pdf', '2023-01-02', 1),
       (2, 'title.png', '2023-01-16', 2),
       (3, 'title.doc', '2023-01-30', 3),
       (4, 'title.zip', '2023-02-13', 4),
       (5, 'title.txt', '2023-02-27', 5);

insert into items(id, is_done, text, checklist_id)
values (1, false, 'Finish before deadline', 1),
       (2, false, 'Finish before deadline', 2),
       (3, false, 'Finish before deadline', 3),
       (4, false, 'Finish before deadline', 4),
       (5, false, 'Finish before deadline', 5);

insert into notifications(id, date_of_write, status, text, board_id, card_id, column_id, from_user_id, user_id)
values (1, '2023-01-09', false, 'Appointed you  as an admin to workspace Peaksoft House', 1, 1, 1, 1, 5),
       (2, '2023-01-12', false, 'Moved to list done', 2, 2, 2, 2, 4),
       (3, '2023-01-09', false, 'My God,let me not burn out on this task', 3, 3, 3, 3, 2),
       (4, '2023-01-09', false, 'Added you to the board C', 4, 4, 4, 4, 3),
       (5, '2023-02-25', false, 'Create card', 5, 5, 5, 5, 1);

insert into favourites(id, user_id, board_id, workspace_id,is_board)
values (1, 1, 1, null,false),
       (2, 2, null, 2,true),
       (3, 3, 3, null,false),
       (4, 4, null, 4,true),
       (5, 5, 5, null,false);



insert into cards_users(cards_id, users_id)
values (1, 1),
       (2, 2),
       (3, 3),
       (4, 4),
       (5, 5);



