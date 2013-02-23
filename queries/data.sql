-- populates tables with data

use loldb;

insert into users_mst values ("raghav","Raghavendra","Belapure","raghav","1 JAN 1990","raghav@localhost","404-123-5467","gatech","none",3);
insert into users_mst values ("yatish","Yatish","Jain","yatish","1 JAN 1990","yatish@localhost","404-123-5467","gatech","symantec",3);
insert into users_mst values ("rizu","Prashasti","Baid","rizu","1 JAN 1990","rizu@localhost","404-123-5467","gatech","none",3);
insert into users_mst values ("abhi","Abhishek","Sinha","abhi","1 JAN 1990","abhi@localhost","404-123-5467","gatech","none",3);
insert into users_mst values ("cheema","Karanjit Singh","Cheema","cheema","1 JAN 1990","cheema@localhost","404-123-5467","gatech","cisco",3);
insert into users_mst values ("aakash","Aakash","Goel","aakash","1 JAN 1990","aakash@localhost","404-123-5467","gatech","oracle",3);
insert into users_mst values ("pallavi","Pallavi","Prasad","pallavi","1 JAN 1990","pallavi@localhost","404-123-5467","gatech","none",3);

insert into friends_mst values ("cheema", "rizu", "yes");
insert into friends_mst values ("raghav", "rizu", "yes");
insert into friends_mst values ("rizu", "yatish", "pending");
insert into friends_mst values ("yatish", "abhi", "pending");

insert into categories_mst values (null, "computer science");
insert into categories_mst values (null, "physics");
insert into categories_mst values (null, "sports");
insert into categories_mst values (null, "latest events");
insert into categories_mst values (null, "politics");
