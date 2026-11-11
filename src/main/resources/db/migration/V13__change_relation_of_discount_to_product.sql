ALTER TABLE public.discount_products
DROP CONSTRAINT fk_discount_product_product;

ALTER TABLE discount_products
ADD CONSTRAINT fk_discount_product_product
FOREIGN KEY (product_id)
REFERENCES food_items(id)
ON DELETE CASCADE;
