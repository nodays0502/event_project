drop table if exists review_event_log;
drop table if exists review_event;
drop table if exists image;
drop table if exists review;
drop table if exists user;
drop table if exists place;



create table user (
                      user_id varchar(255) not null,
                      total_score bigint default 0,
                      primary key (user_id)
) engine=InnoDB;

create table place (
                       place_id varchar(255) not null,
                       has_review bit default false not null,
                       primary key (place_id)
) engine=InnoDB;


create table review (
                        review_id varchar(255) not null,
                        content longtext,
                        place_id varchar(255) not null,
                        user_id varchar(255) not null,
                        primary key (review_id),
                        FOREIGN KEY (place_id) REFERENCES place (place_id),
                        FOREIGN KEY (user_id) REFERENCES user (user_id)
) engine=InnoDB;


create table image (
                       image_id varchar(255) not null,
                       review_id varchar(255) not null,
                       primary key (image_id),
                       FOREIGN KEY (review_id) REFERENCES review (review_id)
) engine=InnoDB;


create table review_event (
                              review_event_id varchar(255) not null,
                              place_id varchar(255) not null,
                              review_id varchar(255) UNIQUE not null,
                              first_review_score bigint,
                              photo_score bigint,
                              text_score bigint,
                              FOREIGN KEY (review_id) REFERENCES review (review_id),
                              FOREIGN KEY (place_id) REFERENCES place (place_id),
                              primary key (review_event_id)
) engine=InnoDB;

create table review_event_log (
                                  review_event_log_id bigint not null auto_increment,
                                  action varchar(255) ,
                                  gap bigint,
                                  user_id varchar(255) not null,
                                  FOREIGN KEY (user_id) REFERENCES user (user_id),
                                  primary key (review_event_log_id)
) engine=InnoDB;

ALTER TABLE review_event ADD INDEX idx_review_event (review_id);
