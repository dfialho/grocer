CREATE TABLE receipts
(
    id    UUID PRIMARY KEY,
    store VARCHAR(32) NOT NULL,
    date  DATE        NOT NULL
);

CREATE TABLE items
(
    id         UUID,
    receipt_id UUID,
    category   VARCHAR(128) NOT NULL,
    name       VARCHAR(128) NOT NULL,
    amount     INT          NOT NULL,

    PRIMARY KEY (id, receipt_id),
    FOREIGN KEY (receipt_id) REFERENCES receipts (id)
);