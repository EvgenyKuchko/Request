create table hunting_licenses
(
    id         bigint not null,
    issue_date date,
    number     int4   not null,
    series     varchar(255),
    primary key (id)
);
create table hunting_order_resources
(
    id               bigint not null,
    amount           int4   not null,
    district_id      int8,
    resource_id      int8,
    hunting_order_id int8,
    status_id        int8   not null,
    primary key (id)
);
create table hunting_order_types
(
    id   bigint not null,
    type varchar(255),
    primary key (id)
);
create table hunting_orders
(
    id                    bigint not null,
    date                  date,
    hunting_order_type_id int8,
    person_id             int8   not null,
    status_id             int8   not null,
    primary key (id)
);
create table persons
(
    id                 bigint not null,
    full_name          varchar(255),
    hunting_license_id int8,
    primary key (id)
);
create table resources
(
    id                    bigint not null,
    name                  varchar(255),
    hunting_order_type_id int8   not null,
    amount int4   not null,
    finish date,
    start  date,
    primary key (id)
);
create table statuses
(
    id   bigint not null,
    name varchar(255),
    primary key (id)
);
create table districts
(
    id   bigint not null,
    name varchar(255),
    primary key (id)
);

alter table hunting_order_resources
    add constraint hunting_order_resources_resources_fk foreign key (resource_id) references resources;
alter table hunting_order_resources
    add constraint hunting_order_resources_statuses_fk foreign key (status_id) references statuses;
alter table hunting_order_resources
    add constraint hunting_order_resources_hunting_orders_fk foreign key (hunting_order_id) references hunting_orders;
alter table hunting_orders
    add constraint hunting_order_resources_districts_fk foreign key (status_id) references districts;
alter table hunting_orders
    add constraint hunting_orders_hunting_order_type_fk foreign key (hunting_order_type_id) references hunting_order_types;
alter table hunting_orders
    add constraint hunting_orders_persons_fk foreign key (person_id) references persons;
alter table hunting_orders
    add constraint hunting_orders_statuses_fk foreign key (status_id) references statuses;
alter table persons
    add constraint persons_hunting_licenses foreign key (hunting_license_id) references hunting_licenses;
alter table resources
    add constraint resources_hunting_type_orders foreign key (hunting_order_type_id) references hunting_order_types;