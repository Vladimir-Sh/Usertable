create table test.User (
  id int(8) not null auto_increment,
  name varchar(25) not null,
  age int not null,
  is_admin bit(1) not null,
  created_date date not null,
  primary key (id));
