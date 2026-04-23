CREATE TYPE food_category AS ENUM (
'NIGIRI',
'MAKI',
'URAMAKI',
'STARTERS',
'COMBO',
'OTHER'
);

ALTER TABLE public.food_items
ADD COLUMN category food_category,
ADD COLUMN baked BOOLEAN DEFAULT FALSE;