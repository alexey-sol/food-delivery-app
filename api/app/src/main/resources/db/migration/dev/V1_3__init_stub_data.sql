-- Util

create or replace function random_between(low int, high int)
    returns int as
$$
begin
    return floor(random() * (high - low + 1) + low);
end;
$$ language 'plpgsql' strict;

-- Init "place_address" data

do $$
begin
for i in 1..30 loop
    insert into public."place_address" (id, city_id, address_line)
        values (nextval('place_address_seq'), 1, 'This is a place address ' || currval('place_address_seq'));
    end loop;
end;
$$;

do $$
begin
for i in 1..20 loop
    insert into public."place_address" (id, city_id, address_line)
        values (nextval('place_address_seq'), 2, 'This is a place address ' || currval('place_address_seq'));
    end loop;
end;
$$;

do $$
begin
for i in 1..10 loop
    insert into public."place_address" (id, city_id, address_line)
        values (nextval('place_address_seq'), 3, 'This is a place address ' || currval('place_address_seq'));
    end loop;
end;
$$;

-- Init "place" and "product" data

do $$
begin
for i in 1..60 loop
    insert into public."place" (
        id,
        address_id,
        name,
        description,
        created_at,
        updated_at
    ) values (
        nextval('place_seq'),
        currval('place_seq'),
        'Place ' || currval('place_seq'),
        'This is a brief description of the place ' || currval('place_seq'),
        current_timestamp,
        current_timestamp
    );
        for i in 1..1000 loop
            insert into public."product" (
                id,
                place_id,
                price,
                name,
                description,
                calories,
                quantity,
                created_at,
                updated_at
            ) values (
                nextval('product_seq'),
                currval('place_seq'),
                random_between(10000, 100000),
                'Product ' || currval('product_seq'),
                'This is a brief description of the product ' || currval('product_seq'),
                random_between(10000, 100000),
                0,
                current_timestamp,
                current_timestamp
            );
        end loop;
    end loop;
end;
$$;
