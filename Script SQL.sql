create table employee(
empID serial primary key,
fName varchar(30) not null,
lName varchar(30) not null,
emailID varchar(30) not null,
login varchar (15) not null,
password varchar(20) not null,
bankAccountNo char(9) not null,
managerID int,
foreign key(managerID) references manager(managerID) on delete set null
);

ALTER table employee DROP COLUMN bankAccountNo;

insert into employee(fName,lName,emailid,login,"password",bankaccountno,managerid) values('Ishan', 'Kishan', 'ik@india.com','ik111','ik111',1);
insert into employee(fName,lName,emailid,login,"password",bankaccountno,managerid) values('Sanju', 'Samson', 'ss@india.com','ss121','ss121',3);
insert into employee(fName,lName,emailid,login,"password",bankaccountno,managerid) values('Shreyas', 'Iyer', 'si@india.com','si999','si999',2);
insert into employee(fName,lName,emailid,login,"password",bankaccountno,managerid) values('Ravi', 'Jadeja', 'rj@india.com','rj111','rj111',1);


create table manager(
managerID serial primary key,
fName varchar(30) not null,
lName varchar(30) not null,
emailID varchar(30) not null,
login varchar(15) not null,
password varchar(20) not null);

insert into manager(fName, lName, emailid, login, "password" ) values ('MS','Dhoni','msd@india.com','msd007','msd007');
insert into manager(fName, lName, emailid, login, "password" ) values ('Virat','Kohli','vk@india.com','vk001','vk001');
insert into manager(fName, lName, emailid, login, "password" ) values ('Rohit','Sharma','rs@india.com','rs015','rs015');
insert into manager(fName, lName, emailid, login, "password" ) values ('Rishabh','Pant','rp@india.com','rp009','rp009');

create table bankAccount(
bankAccountNo char(9) primary key,
bankName varchar(20) not null,
routingNo char(9) not null,
accType varchar(10) not null,
empID int not null,
foreign key(empID) references employee(empID) on delete set null
);

insert into bankaccount(bankAccountNo, bankname, routingno, acctype, empid) values('000111123','chase bank','checking','454000454',1);
insert into bankaccount(bankAccountNo, bankname, routingno, acctype, empid) values('000111999','chase bank','checking','454000454',2);
insert into bankaccount(bankAccountNo, bankname, routingno, acctype, empid) values('000222111','bank of america','checking','787000787',3);
insert into bankaccount(bankAccountNo, bankname, routingno, acctype, empid) values('000222222','bank of america','checking','787000787',4);
insert into bankaccount(bankAccountNo, bankname, routingno, acctype, empid) values('000222123','bank of america','saving','787000787',1);

create table reimbursement(
requestID serial primary key,
empID int not null,
managerID int,
amount Double precision not null,
bankAccountNo char(9) not null,
status varchar(10),
foreign key (empID) references employee(empID) on delete set null,
foreign key (managerID) references manager(managerID) on delete set null,
foreign key (bankAccountNo) references bankAccount(bankAccountNo) on delete set null);
















