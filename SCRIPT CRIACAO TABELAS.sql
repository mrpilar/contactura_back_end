use contactura2;


    CREATE TABLE contactura
(
	`id` bigint unsigned auto_increment,
    `name` varchar(45) not null,
    `email` varchar(45) not null,
    `phone` varchar(45) not null,
    PRIMARY KEY (`id`));
    
    ALTER TABLE contactura
    CHANGE COLUMN `id` `id` INT(11) NOT NULL AUTO_INCREMENT,
    ADD UNIQUE INDEX `id_UNIQUE` (`id` ASC);
     
    SELECT * FROM  CONTACTURA;
    
    drop table contacturauser;
    
    CREATE TABLE contacturaUser (
    `id` INT NOT NULL,
    `username` varchar (255) NOT NULL,
    `password` varchar(255) not null,
    `name` varchar(45) not null,
    `admin` tinyint not null,
    primary key (`id`));
    
    ALTER TABLE `contactura2`.`contacturaUser` 
    CHANGE COLUMN `id` `id` INT(11) NOT NULL AUTO_INCREMENT,
    ADD UNIQUE INDEX `id_UNIQUE` (`id` ASC);
    
    
