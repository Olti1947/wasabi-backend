ALTER TABLE public.discounts
ALTER COLUMN type TYPE VARCHAR(50)
USING type::text;
