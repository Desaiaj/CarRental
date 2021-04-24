insert into car (id, name, model, price,releaseyear)
values (1, 'bmw', 'BMW',16000, 2020),(2, 'Aqe', 'Audi',1000, 2002);

insert into customer (id, first_name, last_name) values (1, 'Jeff', 'Heisenberg');
insert into customer (id, first_name, last_name) values (2, 'John', 'Doe');
insert into customer (id, first_name, last_name) values (3, 'Richard', 'Stallman');
INSERT INTO User(email, encrypted_Password, enabled) VALUES 
				('simon.hood@sheridancollege.ca', '$2a$04$c2tNEflQkJeW4DCCAyxcBeQPkdoOgv3dXrdtiflCcXMNrJeemaDBW', 1), 
				('jonathan.penava@sheridancollege.ca', '$2a$04$c2tNEflQkJeW4DCCAyxcBeQPkdoOgv3dXrdtiflCcXMNrJeemaDBW', 1);
insert into rental (id, rental_Date, return_Date,km,custid) values (1, '2020-01-10', '2020-05-11',20,1);
				
INSERT INTO Role(rolename) VALUES ('ROLE_USER'), ('ROLE_ADMIN');
INSERT INTO User_Role_List VALUES (1, 1),(1, 2),(2, 2);