-- Demo content for local development. Executed by DemoDataSeeder when SEED_DEMO_DATA=true and the menu is empty.
-- The demo customer (customer@wasabi.local) is created by the seeder before this script runs.

-- Menu
INSERT INTO food_items (name, description, price, image_url, popular, category, baked, ingredients) VALUES
('Salmon Nigiri', 'Two pieces of fresh Norwegian salmon over seasoned sushi rice.', 5.50, 'https://images.unsplash.com/photo-1562158074-d49fbeffcc91?w=800&q=80', TRUE, 'NIGIRI', FALSE, '["Salmon", "Sushi rice", "Wasabi"]'),
('Seared Salmon Nigiri', 'Torched salmon nigiri brushed with a light teriyaki glaze.', 6.00, 'https://images.unsplash.com/photo-1615361200141-f45040f367be?w=800&q=80', FALSE, 'NIGIRI', TRUE, '["Salmon", "Sushi rice", "Teriyaki glaze"]'),
('Nigiri Selection', 'Eight pieces of the chef''s choice: salmon, tuna, shrimp and sea bass.', 14.90, 'https://images.unsplash.com/photo-1617196034796-73dfa7b1fd56?w=800&q=80', FALSE, 'NIGIRI', FALSE, '["Salmon", "Tuna", "Shrimp", "Sea bass", "Sushi rice"]'),
('Avocado Cucumber Maki', 'Eight light vegetarian rolls with creamy avocado and crisp cucumber.', 6.50, 'https://images.unsplash.com/photo-1564489563601-c53cfc451e93?w=800&q=80', FALSE, 'MAKI', FALSE, '["Avocado", "Cucumber", "Nori", "Sushi rice"]'),
('Salmon Maki', 'Eight classic rolls filled with fresh salmon.', 7.50, 'https://images.unsplash.com/photo-1607301406259-dfb186e15de8?w=800&q=80', FALSE, 'MAKI', FALSE, '["Salmon", "Nori", "Sushi rice"]'),
('California Roll', 'Crab, avocado and cucumber, rolled inside out and topped with tobiko.', 10.90, 'https://images.unsplash.com/photo-1580822184713-fc5400e7fe10?w=800&q=80', TRUE, 'URAMAKI', FALSE, '["Crab", "Avocado", "Cucumber", "Tobiko", "Sesame"]'),
('Spicy Tuna Roll', 'Fresh tuna on top of a cucumber roll, finished with spicy mayo.', 12.50, 'https://images.unsplash.com/photo-1559410545-0bdcd187e0a6?w=800&q=80', TRUE, 'URAMAKI', FALSE, '["Tuna", "Spicy mayo", "Cucumber", "Spring onion"]'),
('Dragon Roll', 'Shrimp tempura and cucumber, covered in avocado and eel sauce.', 13.90, 'https://images.unsplash.com/photo-1582450871972-ab5ca641643d?w=800&q=80', TRUE, 'URAMAKI', FALSE, '["Shrimp tempura", "Avocado", "Cucumber", "Eel sauce"]'),
('Salmon Crunch Roll', 'Salmon and cream cheese with crunchy tempura flakes.', 11.90, 'https://images.unsplash.com/photo-1633478062482-790e3b5dd810?w=800&q=80', FALSE, 'URAMAKI', FALSE, '["Salmon", "Cream cheese", "Tempura flakes", "Spicy mayo"]'),
('Philadelphia Roll', 'Salmon, cream cheese and cucumber, wrapped in more salmon.', 11.50, 'https://images.unsplash.com/photo-1579871494447-9811cf80d66c?w=800&q=80', FALSE, 'URAMAKI', FALSE, '["Salmon", "Cream cheese", "Cucumber"]'),
('Baked Salmon Roll', 'Warm roll topped with salmon and oven-baked spicy mayo.', 12.90, 'https://images.unsplash.com/photo-1617196034183-421b4917c92d?w=800&q=80', FALSE, 'URAMAKI', TRUE, '["Salmon", "Cream cheese", "Baked spicy mayo", "Cucumber"]'),
('Volcano Roll', 'Crab and shrimp roll under a bubbling baked spicy sauce.', 13.50, 'https://images.unsplash.com/photo-1617196035154-1e7e6e28b0db?w=800&q=80', TRUE, 'URAMAKI', TRUE, '["Crab", "Shrimp", "Baked spicy sauce", "Sesame"]'),
('Pork Gyoza', 'Six pan-fried dumplings with a ponzu dipping sauce.', 7.90, 'https://images.unsplash.com/photo-1602273660127-a0000560a4c1?w=800&q=80', FALSE, 'STARTERS', FALSE, '["Pork", "Cabbage", "Ginger", "Ponzu"]'),
('Yakitori Skewers', 'Four grilled chicken skewers glazed with tare sauce.', 8.50, 'https://images.unsplash.com/photo-1600470944938-b301e41001c8?w=800&q=80', FALSE, 'STARTERS', FALSE, '["Chicken", "Spring onion", "Tare sauce"]'),
('Sushi Boat for Two', '40 pieces of maki, uramaki and nigiri served on a wooden boat.', 39.90, 'https://images.unsplash.com/photo-1553621042-f6e147245754?w=800&q=80', TRUE, 'COMBO', FALSE, '["Salmon", "Tuna", "Crab", "Avocado", "Cucumber"]'),
('Party Platter', '64 pieces for sharing, with a bit of everything from the menu.', 59.90, 'https://images.unsplash.com/photo-1611143669185-af224c5e3252?w=800&q=80', FALSE, 'COMBO', FALSE, '["Salmon", "Tuna", "Shrimp", "Avocado", "Cucumber"]'),
('Salmon Lovers Set', 'Salmon nigiri, salmon maki and a sesame salmon roll.', 22.90, 'https://images.unsplash.com/photo-1583623025817-d180a2221d0a?w=800&q=80', FALSE, 'COMBO', FALSE, '["Salmon", "Sesame", "Sushi rice"]'),
('Chef''s Mixed Board', 'Sixteen pieces of nigiri and maki chosen by the chef.', 27.50, 'https://images.unsplash.com/photo-1563612116625-3012372fccce?w=800&q=80', FALSE, 'COMBO', FALSE, '["Salmon", "Tuna", "Tamago", "Shrimp"]'),
('Chirashi Bowl', 'Sushi rice topped with assorted sashimi, salmon roe and vegetables.', 16.90, 'https://images.unsplash.com/photo-1534482421-64566f976cfa?w=800&q=80', FALSE, 'OTHER', FALSE, '["Salmon", "Tuna", "Shrimp", "Salmon roe", "Sushi rice"]'),
('Tonkotsu Ramen', 'Rich pork broth with chashu, soft egg and spring onion.', 13.90, 'https://images.unsplash.com/photo-1632709810780-b5a4343cebec?w=800&q=80', TRUE, 'OTHER', FALSE, '["Pork broth", "Chashu", "Egg", "Noodles", "Spring onion"]'),
('Shoyu Ramen', 'Soy-based broth with roast pork, egg and bamboo shoots.', 12.90, 'https://images.unsplash.com/photo-1618889482923-38250401a84e?w=800&q=80', FALSE, 'OTHER', FALSE, '["Soy broth", "Pork", "Egg", "Noodles", "Bamboo shoots"]'),
('Yakisoba', 'Stir-fried noodles with vegetables and yakisoba sauce.', 11.50, 'https://images.unsplash.com/photo-1612929633738-8fe44f7ec841?w=800&q=80', FALSE, 'OTHER', FALSE, '["Noodles", "Cabbage", "Carrot", "Yakisoba sauce"]');

-- Home screen banners
INSERT INTO banner_images (image_url, description, is_active) VALUES
('https://images.unsplash.com/photo-1611143669185-af224c5e3252?w=1200&h=600&fit=crop&q=80', 'Fresh sushi, made every day', TRUE),
('https://images.unsplash.com/photo-1553621042-f6e147245754?w=1200&h=600&fit=crop&q=80', 'Share a Sushi Boat with someone', TRUE),
('https://images.unsplash.com/photo-1621871908119-295c8ce5cee4?w=1200&h=600&fit=crop&q=80', 'Visit us for dinner tonight', TRUE);

-- Discounts (each one only applies to the products linked below)
INSERT INTO discounts (title, description, type, value, imageurl, starts_at, ends_at, min_order_value, stackable, active) VALUES
('20% off Uramaki', 'Save 20% on every inside-out roll.', 'PERCENTAGE', 20, 'https://images.unsplash.com/photo-1582450871972-ab5ca641643d?w=800&q=80', NOW() - INTERVAL '1 day', NOW() + INTERVAL '60 days', 0, FALSE, TRUE),
('$5 off Combos', '$5 off any combo when you spend $25 or more.', 'FIXED', 5, 'https://images.unsplash.com/photo-1553621042-f6e147245754?w=800&q=80', NOW() - INTERVAL '1 day', NOW() + INTERVAL '60 days', 25, FALSE, TRUE),
('Ramen Week', '15% off all ramen bowls this month.', 'PERCENTAGE', 15, 'https://images.unsplash.com/photo-1632709810780-b5a4343cebec?w=800&q=80', NOW() - INTERVAL '1 day', NOW() + INTERVAL '30 days', 0, FALSE, TRUE);

INSERT INTO discount_products (discount_id, product_id)
SELECT d.id, f.id FROM discounts d JOIN food_items f ON f.category = 'URAMAKI' WHERE d.title = '20% off Uramaki';

INSERT INTO discount_products (discount_id, product_id)
SELECT d.id, f.id FROM discounts d JOIN food_items f ON f.category = 'COMBO' WHERE d.title = '$5 off Combos';

INSERT INTO discount_products (discount_id, product_id)
SELECT d.id, f.id FROM discounts d JOIN food_items f ON f.name LIKE '%Ramen' WHERE d.title = 'Ramen Week';

-- The demo customer has already activated the uramaki discount
INSERT INTO user_discounts (user_id, discount_id, activated_at, expires_at, status)
SELECT u.id, d.id, NOW(), d.ends_at, 'ACTIVE' FROM users u, discounts d
WHERE u.email = 'customer@wasabi.local' AND d.title = '20% off Uramaki';

-- Saved address
INSERT INTO addresses (user_id, label, street, city, postal_code, is_default)
SELECT id, 'Home', '12 Harbor Street', 'Springfield', '10000', TRUE FROM users WHERE email = 'customer@wasabi.local';

-- Orders in different states, so the admin order list has something to manage
INSERT INTO orders (user_id, subtotal, discount_total, total, status, created_at, comment, phone_number, address)
SELECT u.id, 0, 0, 0, o.status, NOW() - o.age, o.comment, '+1 555 0100', '12 Harbor Street, Springfield'
FROM users u, (VALUES
    ('CREATED', INTERVAL '10 minutes', 'Extra ginger, please'),
    ('IN_PROGRESS', INTERVAL '35 minutes', NULL),
    ('DELIVERY', INTERVAL '1 hour', 'Ring the bell twice'),
    ('COMPLETED', INTERVAL '2 days', NULL),
    ('CANCELLED', INTERVAL '5 days', 'Ordered by mistake')
) AS o(status, age, comment)
WHERE u.email = 'customer@wasabi.local';

INSERT INTO order_items (order_id, food_item_id, quantity, unit_price, total_price)
SELECT o.id, f.id, i.quantity, f.price, f.price * i.quantity
FROM (VALUES
    ('CREATED', 'California Roll', 2),
    ('CREATED', 'Pork Gyoza', 1),
    ('IN_PROGRESS', 'Dragon Roll', 1),
    ('IN_PROGRESS', 'Spicy Tuna Roll', 1),
    ('DELIVERY', 'Sushi Boat for Two', 1),
    ('COMPLETED', 'Tonkotsu Ramen', 2),
    ('COMPLETED', 'Salmon Nigiri', 2),
    ('CANCELLED', 'Party Platter', 1)
) AS i(status, food_name, quantity)
JOIN orders o ON o.status = i.status AND o.user_id = (SELECT id FROM users WHERE email = 'customer@wasabi.local')
JOIN food_items f ON f.name = i.food_name;

UPDATE orders o SET subtotal = t.sum, total = t.sum
FROM (SELECT order_id, SUM(total_price) AS sum FROM order_items GROUP BY order_id) t
WHERE t.order_id = o.id;

-- Loyalty spending reflects the completed order
UPDATE users SET spending = (
    SELECT COALESCE(SUM(total), 0) FROM orders WHERE user_id = users.id AND status = 'COMPLETED'
) WHERE email = 'customer@wasabi.local';
