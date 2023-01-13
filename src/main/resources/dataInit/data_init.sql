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
values (1, false, 'Java Programming Language', 'Java', 1),
       (2,false,'JavaScript Programming Language','JavaScript',1);
