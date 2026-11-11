CREATE TYPE discount_type AS ENUM ('PERCENTAGE', 'FIXED');
CREATE TYPE user_discount_status AS ENUM ('ACTIVE', 'USED', 'EXPIRED');

CREATE TABLE discounts (
    id BIGSERIAL PRIMARY KEY,

    title VARCHAR(255) NOT NULL,
    description TEXT,

    type discount_type NOT NULL,
    value NUMERIC(10,2) NOT NULL,

    starts_at TIMESTAMP NOT NULL,
    ends_at TIMESTAMP NOT NULL,

    min_order_value NUMERIC(10,2) DEFAULT 0,
    stackable BOOLEAN DEFAULT FALSE,

    active BOOLEAN DEFAULT TRUE,

    created_at TIMESTAMP DEFAULT NOW()
);

CREATE TABLE user_discounts (
    id BIGSERIAL PRIMARY KEY,

    user_id BIGINT NOT NULL,
    discount_id BIGINT NOT NULL,

    activated_at TIMESTAMP NOT NULL,
    expires_at TIMESTAMP NOT NULL,

    status user_discount_status NOT NULL,
    used_at TIMESTAMP,

    CONSTRAINT fk_user_discount_user
        FOREIGN KEY (user_id) REFERENCES users(id),

    CONSTRAINT fk_user_discount_discount
        FOREIGN KEY (discount_id) REFERENCES discounts(id),

    CONSTRAINT uq_user_discount UNIQUE (user_id, discount_id)
);

CREATE INDEX idx_user_discounts_active
ON user_discounts (user_id, status, expires_at);

CREATE TABLE discount_products (
    discount_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,

    PRIMARY KEY (discount_id, product_id),

    CONSTRAINT fk_discount_product_discount
        FOREIGN KEY (discount_id) REFERENCES discounts(id) ON DELETE CASCADE,

    CONSTRAINT fk_discount_product_product
        FOREIGN KEY (product_id) REFERENCES food_items(id)
);


CREATE TABLE orders (
    id BIGSERIAL PRIMARY KEY,

    user_id BIGINT NOT NULL,

    subtotal NUMERIC(10,2) NOT NULL,
    discount_total NUMERIC(10,2) NOT NULL,
    total NUMERIC(10,2) NOT NULL,

    status VARCHAR(30) NOT NULL DEFAULT 'CREATED',

    created_at TIMESTAMP DEFAULT NOW(),

    CONSTRAINT fk_orders_user
        FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE INDEX idx_orders_user ON orders(user_id);
CREATE INDEX idx_orders_created_at ON orders(created_at);

CREATE TABLE order_items (
    id BIGSERIAL PRIMARY KEY,

    order_id BIGINT NOT NULL,
    food_item_id INTEGER NOT NULL,

    quantity INTEGER NOT NULL,
    unit_price NUMERIC(10,2) NOT NULL,
    total_price NUMERIC(10,2) NOT NULL,

    CONSTRAINT fk_order_items_order
        FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE,

    CONSTRAINT fk_order_items_food_item
        FOREIGN KEY (food_item_id) REFERENCES food_items(id)
);

CREATE INDEX idx_order_items_order ON order_items(order_id);

CREATE TABLE order_discounts (
    id BIGSERIAL PRIMARY KEY,

    order_id BIGINT NOT NULL,
    discount_id BIGINT NOT NULL,

    discount_amount NUMERIC(10,2) NOT NULL,

    CONSTRAINT fk_order_discount_order
        FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE,

    CONSTRAINT fk_order_discount_discount
        FOREIGN KEY (discount_id) REFERENCES discounts(id)
);

CREATE TABLE order_discount_items (
    id BIGSERIAL PRIMARY KEY,

    order_item_id BIGINT NOT NULL,
    discount_id BIGINT NOT NULL,

    discount_amount NUMERIC(10,2) NOT NULL,

    CONSTRAINT fk_odi_order_item
        FOREIGN KEY (order_item_id) REFERENCES order_items(id) ON DELETE CASCADE,

    CONSTRAINT fk_odi_discount
        FOREIGN KEY (discount_id) REFERENCES discounts(id)
);
