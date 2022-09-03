CREATE TABLE receipts
(
    id    VARCHAR(128) PRIMARY KEY,
    store VARCHAR(32) NOT NULL,
    date  DATE        NOT NULL
);

CREATE TABLE items
(
    id         VARCHAR(128),
    receipt_id VARCHAR(128),
    category   VARCHAR(128) NOT NULL,
    name       VARCHAR(128) NOT NULL,
    amount     INT          NOT NULL,

    PRIMARY KEY (id, receipt_id),
    FOREIGN KEY (receipt_id) REFERENCES receipts (id)
);