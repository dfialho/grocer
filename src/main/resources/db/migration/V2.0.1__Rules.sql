CREATE TABLE categories
(
    id   UUID PRIMARY KEY,
    name VARCHAR(128) UNIQUE
);

CREATE TABLE subcategories
(
    id          UUID,
    category_id UUID,
    name        VARCHAR(128) UNIQUE,

    PRIMARY KEY (category_id, id),
    FOREIGN KEY (category_id) REFERENCES categories (id)
);

CREATE TABLE rules
(
    id   UUID PRIMARY KEY,
    spec JSONB NOT NULL
);


