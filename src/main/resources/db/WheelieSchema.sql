create table if not exists account
(
    uuid                varchar(100) not null
    primary key,
    kakao_id            int          not null,
    kakao_nickname      varchar(100) not null,
    kakao_email         varchar(100) null,
    kakao_profile_image text         not null
    );

create table if not exists area
(
    id      int auto_increment
    primary key,
    sido    varchar(50) not null,
    sigungu varchar(50) not null,
    map_x   double      not null,
    map_y   double      not null
    );

create table if not exists bike_road
(
    uuid        varchar(100) not null
    primary key,
    name        varchar(100) not null,
    images      text         not null,
    address     varchar(100) not null,
    map_x       double       not null,
    map_y       double       not null,
    dong        varchar(100) not null,
    description text         not null,
    gpx         varchar(100) null
    );

create table if not exists bike_road_bookmark_relation
(
    id            int auto_increment
    primary key,
    bookmark      varchar(100) not null,
    bikeroad_uuid varchar(100) not null
    );

create table if not exists course
(
    uuid            varchar(100) not null
    primary key,
    name            varchar(100) not null,
    dong            varchar(100) not null,
    map_x           double       not null,
    map_y           double       not null,
    first_place_id  int          null,
    first_image     varchar(100) null,
    first_name     varchar(100) null,
    first_tag       varchar(100) null,
    first_map_x     double       null,
    first_map_y     double       null,
    second_place_id int          null,
    second_image    varchar(100) null,
    second_name    varchar(100) null,
    second_tag      varchar(100) null,
    second_map_x    double       null,
    second_map_y    double       null,
    third_place_id  int          null,
    third_image     varchar(100) null,
    third_name     varchar(100) null,
    third_tag       varchar(100) null,
    third_map_x     double       null,
    third_map_y     double       null,
    bikeroad        varchar(100) not null
    );

create table if not exists bookmark
(
    uuid  varchar(100) not null
    primary key,
    owner varchar(100) not null,
    name  varchar(100) not null,
    image text         null
    );

create table if not exists course_relation
(
    uuid   varchar(100) not null
    primary key,
    course varchar(100) not null,
    place  varchar(100) not null
    );

create table if not exists place_bookmark_relation
(
    id       int auto_increment
    primary key,
    bookmark varchar(100) not null,
    place_id int          not null,
    image    text         null,
    name     varchar(100) not null,
    map_x    double       not null,
    map_y    double       not null,
    tag      varchar(100) not null
    );

create table if not exists token
(
    uuid       varchar(100) not null
    primary key,
    account    varchar(100) not null,
    created_at mediumtext   not null
    );
