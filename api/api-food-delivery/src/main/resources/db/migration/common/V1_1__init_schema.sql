create sequence cart_item_seq start with 1 increment by 1;

create sequence cart_seq start with 1 increment by 1;

create sequence category_seq start with 1 increment by 1;

create sequence city_seq start with 1 increment by 1;

create sequence order_item_seq start with 1 increment by 1;

create sequence order_seq start with 1 increment by 1;

create sequence product_seq start with 1 increment by 1;

create sequence place_address_seq start with 1 increment by 1;

create sequence place_seq start with 1 increment by 1;

create sequence user_address_seq start with 1 increment by 1;

create sequence user_seq start with 1 increment by 1;

create table "order" (
    id bigint not null,
    user_id bigint,
    place_id bigint,
    total_price bigint not null,
    status varchar(255) not null check (status in ('PROCESSING','DELIVERING','COMPLETED','CANCELLED','FAILED')),
    created_at timestamp(6),
    updated_at timestamp(6),
    primary key (id)
);

create table "user" (
    id bigint not null,
    address_id bigint unique,
    phone varchar(255) not null,
    username varchar(255),
    password varchar(255),
    created_at timestamp(6),
    updated_at timestamp(6),
    primary key (id)
);

create table "user_roles" (
    "user_id" bigint not null,
    roles_id bigint not null,
    primary key (roles_id, "user_id")
);

create table cart (
    id bigint not null,
    user_id bigint,
    place_id bigint,
    total_price bigint not null,
    created_at timestamp(6),
    updated_at timestamp(6),
    primary key (id)
);

create table cart_item (
    id bigint not null,
    cart_id bigint not null,
    product_id bigint not null,
    quantity integer not null,
    created_at timestamp(6),
    updated_at timestamp(6),
    primary key (id)
);

create table category (
    id bigint not null,
    name varchar(255) not null,
    primary key (id)
);

create table city (
    id bigint not null,
    name varchar(255) not null,
    created_at timestamp(6),
    updated_at timestamp(6),
    primary key (id)
);

create table order_item (
    id bigint not null,
    order_id bigint not null,
    product_id bigint not null,
    quantity integer not null,
    created_at timestamp(6),
    updated_at timestamp(6),
    primary key (id)
);

create table product (
    id bigint not null,
    place_id bigint not null,
    price bigint not null,
    name varchar(255) not null,
    description varchar(255),
    calories integer not null,
    created_at timestamp(6),
    updated_at timestamp(6),
    primary key (id)
);

create table product_category (
    product_id bigint not null,
    category_id bigint not null,
    primary key (category_id, product_id)
);

create table role (
    id integer not null,
    name varchar(255) not null check (name in ('ADMIN','CUSTOMER')),
    primary key (id)
);

create table place (
    id bigint not null,
    address_id bigint not null unique,
    name varchar(255) not null,
    description varchar(255),
    created_at timestamp(6),
    updated_at timestamp(6),
    primary key (id)
);

create table place_address (
    id bigint not null,
    city_id bigint,
    address_line varchar(255) not null,
    primary key (id)
);

create table user_address (
    id bigint not null,
    city_id bigint,
    address_line varchar(255) not null,
    primary key (id)
);

create index IDXab2gqa6fgbndvi4s8xsexlu70 on "order" (user_id);

create index IDXj2p75fh8fffujkl53neyijiv0 on "order" (user_id, place_id, status);

create index IDX5c856itaihtmi69ni04cmpc4m on "user" (username);

create index IDXjo7caqjoviq8vdo3f967kcj75 on "user" (phone);

create index IDX9emlp6m95v5er2bcqkjsw48he on cart (user_id);

create index IDX7ot2xiq5gkyyvx8bu7lbcd0y2 on cart (user_id, place_id);

create index IDX2fypguoq8qc1uigt9i6em0t07 on cart_item (cart_id, product_id);

create index IDXlejodabacstkd042ij5ilx6tl on place_address (city_id);

create index IDXd3m1iper28s5bxu61usj4ein9 on product (place_id);

alter table if exists "order"
    add constraint FK90lxxrxlt4chf273vcm9pi8ak
    foreign key (place_id)
    references place;

alter table if exists "order"
    add constraint FKrcaf946w0bh6qj0ljiw3pwpnu
    foreign key (user_id)
    references "user";

alter table if exists "user"
    add constraint FKd48ubyfppqpgofburkmvj7gcs
    foreign key (address_id)
    references user_address;

alter table if exists "user_roles"
    add constraint FKsoyrbfa9510yyn3n9as9pfcsx
    foreign key (roles_id)
    references role;

alter table if exists "user_roles"
    add constraint FK40cm955hgg5oxf1oax8mqw0c4
    foreign key ("user_id")
    references "user";

alter table if exists cart
    add constraint FKd1x6ip1voqx475p471usgde93
    foreign key (place_id)
    references place;

alter table if exists cart
    add constraint FKaf0wt8hgkk8v5rfpwdwq7se0t
    foreign key (user_id)
    references "user";

alter table if exists cart_item
    add constraint FK1uobyhgl1wvgt1jpccia8xxs3
    foreign key (cart_id)
    references cart;

alter table if exists cart_item
    add constraint FKjcyd5wv4igqnw413rgxbfu4nv
    foreign key (product_id)
    references product;

alter table if exists order_item
    add constraint FKs234mi6jususbx4b37k44cipy
    foreign key (order_id)
    references "order";

alter table if exists order_item
    add constraint FK551losx9j75ss5d6bfsqvijna
    foreign key (product_id)
    references product;

alter table if exists product
    add constraint FKjlfidudl1gwqem0flrlomvlcl
    foreign key (place_id)
    references place;

alter table if exists product_category
    add constraint FKkud35ls1d40wpjb5htpp14q4e
    foreign key (category_id)
    references category;

alter table if exists product_category
    add constraint FK2k3smhbruedlcrvu6clued06x
    foreign key (product_id)
    references product;

alter table if exists place
    add constraint FKaclgjwpadibnrwyr3rdvwb45l
    foreign key (address_id)
    references place_address;

alter table if exists place_address
    add constraint FKryg0psm2gvueonpbakr91vyst
    foreign key (city_id)
    references city;

alter table if exists user_address
    add constraint FKn0wqubhqgnp048fnmagvg1q76
    foreign key (city_id)
    references city;
