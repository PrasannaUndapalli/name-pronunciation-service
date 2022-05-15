create table NamePronunciation
(
   id integer not null,
   uId varchar(10),
   legalFName varchar(100),
   legalLName varchar(100),
   prefName varchar(255),
   prefPronunciation blob,
   createdBy varchar(255),
   createdOn datetime,
   updatedBy varchar(255),
   updatedOn datetime,
   primary key(id)
);