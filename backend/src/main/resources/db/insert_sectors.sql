create procedure insert_child_sector(new_name varchar, parent_name varchar)
language sql
as $$
insert into sector (name, parent_id)
select new_name, s.id
from sector as s
where s."name" = parent_name;
$$;

create procedure insert_child_other(new_name varchar)
language sql
as $$
insert into sector (name, parent_id)
select new_name, s.id
from sector as s
where s."name" = 'Other' and parent_id is null;
$$;

insert into sector (name) values ('Manufacturing');
insert into sector (name) values ('Other');
insert into sector (name) values ('Service');

call insert_child_sector('Construction materials', 'Manufacturing');
call insert_child_sector('Electronics and Optics', 'Manufacturing');
call insert_child_sector('Food and Beverage', 'Manufacturing');
call insert_child_sector('Bakery & confectionery products', 'Food and Beverage');
call insert_child_sector('Beverages', 'Food and Beverage');
call insert_child_sector('Fish & fish products', 'Food and Beverage');
call insert_child_sector('Meat & meat products', 'Food and Beverage');
call insert_child_sector('Milk & dairy products', 'Food and Beverage');
call insert_child_sector('Other', 'Food and Beverage');
call insert_child_sector('Sweets & snack food', 'Food and Beverage');
call insert_child_sector('Furniture', 'Manufacturing');
call insert_child_sector('Bathroom/sauna', 'Furniture');
call insert_child_sector('Bedroom', 'Furniture');
call insert_child_sector('Children''s room', 'Furniture');
call insert_child_sector('Kitchen', 'Furniture');
call insert_child_sector('Living room', 'Furniture');
call insert_child_sector('Office', 'Furniture');
call insert_child_sector('Other (Furniture)', 'Furniture');
call insert_child_sector('Outdoor', 'Furniture');
call insert_child_sector('Project furniture', 'Furniture');
call insert_child_sector('Machinery', 'Manufacturing');
call insert_child_sector('Machinery components', 'Machinery');
call insert_child_sector('Machinery equipment/tools', 'Machinery');
call insert_child_sector('Manufacture of machinery', 'Machinery');
call insert_child_sector('Maritime', 'Machinery');
call insert_child_sector('Aluminium and steel workboats', 'Maritime');
call insert_child_sector('Boat/Yacht building', 'Maritime');
call insert_child_sector('Ship repair and conversion', 'Maritime');
call insert_child_sector('Metal structures', 'Machinery');
call insert_child_sector('Other', 'Machinery');
call insert_child_sector('Repair and maintenance service', 'Machinery');
call insert_child_sector('Metalworking', 'Manufacturing');
call insert_child_sector('Construction of metal structures', 'Metalworking');
call insert_child_sector('Houses and buildings', 'Metalworking');
call insert_child_sector('Metal products', 'Metalworking');
call insert_child_sector('Metal works', 'Metalworking');
call insert_child_sector('CNC-machining', 'Metal works');
call insert_child_sector('Forgings, Fasteners', 'Metal works');
call insert_child_sector('Gas, Plasma, Laser cutting', 'Metal works');
call insert_child_sector('MIG, TIG, Aluminium welding', 'Metal works');
call insert_child_sector('Plastic and Rubber', 'Manufacturing');
call insert_child_sector('Packaging', 'Plastic and Rubber');
call insert_child_sector('Plastic goods', 'Plastic and Rubber');
call insert_child_sector('Plastic processing technology', 'Plastic and Rubber');
call insert_child_sector('Blowing', 'Plastic processing technology');
call insert_child_sector('Moulding', 'Plastic processing technology');
call insert_child_sector('Plastics welding and processing', 'Plastic processing technology');
call insert_child_sector('Plastic profiles', 'Plastic and Rubber');
call insert_child_sector('Printing', 'Manufacturing');
call insert_child_sector('Advertising', 'Printing');
call insert_child_sector('Book/Periodicals printing', 'Printing');
call insert_child_sector('Labelling and packaging printing', 'Printing');
call insert_child_sector('Textile and Clothing', 'Manufacturing');
call insert_child_sector('Clothing', 'Textile and Clothing');
call insert_child_sector('Textile', 'Textile and Clothing');
call insert_child_sector('Wood', 'Manufacturing');
call insert_child_sector('Other (Wood)', 'Wood');
call insert_child_sector('Wooden building materials', 'Wood');
call insert_child_sector('Wooden houses', 'Wood');

call insert_child_other('Creative industries');
call insert_child_other('Energy technology');
call insert_child_other('Environment');

call insert_child_sector('Business services', 'Service');
call insert_child_sector('Engineering', 'Service');
call insert_child_sector('Information Technology and Telecommunications', 'Service');
call insert_child_sector('Data processing, Web portals, E-marketing', 'Information Technology and Telecommunications');
call insert_child_sector('Programming, Consultancy', 'Information Technology and Telecommunications');
call insert_child_sector('Software, Hardware', 'Information Technology and Telecommunications');
call insert_child_sector('Telecommunications', 'Information Technology and Telecommunications');
call insert_child_sector('Tourism', 'Service');
call insert_child_sector('Translation services', 'Service');
call insert_child_sector('Transport and Logistics', 'Service');
call insert_child_sector('Air', 'Transport and Logistics');
call insert_child_sector('Rail', 'Transport and Logistics');
call insert_child_sector('Road', 'Transport and Logistics');
call insert_child_sector('Water', 'Transport and Logistics');

drop procedure insert_child_sector;
drop procedure insert_child_other;