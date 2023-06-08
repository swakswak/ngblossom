CREATE TABLE flower_price
(
    id             BIGSERIAL PRIMARY KEY,
    sale_date      DATE         NOT NULL,
    flower_type    VARCHAR(255) NOT NULL,
    item_name      VARCHAR(255) NOT NULL,
    cultivar_name  VARCHAR(255) NOT NULL,
    grade          INT  NOT NULL,
    max_amount     BIGINT      NOT NULL,
    min_amount     BIGINT      NOT NULL,
    average_amount BIGINT      NOT NULL,
    total_amount   BIGINT      NOT NULL,
    total_quantity BIGINT      NOT NULL,

    FOREIGN KEY (grade) REFERENCES flower_grade (grade)
);
CREATE INDEX flower_price_sale_date_idx ON flower_price (sale_date);
CREATE INDEX flower_price_flower_type_sale_date_grade_idx ON flower_price (flower_type, sale_date, grade);

CREATE TABLE flower_grade
(
    grade INT PRIMARY KEY,
    name  VARCHAR(5) NOT NULL
);
CREATE UNIQUE INDEX flower_grade_grade_idx ON flower_grade (grade);

INSERT INTO flower_grade (grade, name) VALUES (11, '보1');
INSERT INTO flower_grade (grade, name) VALUES (12, '보2');
INSERT INTO flower_grade (grade, name) VALUES (13, '보3');
INSERT INTO flower_grade (grade, name) VALUES (21, '상1');
INSERT INTO flower_grade (grade, name) VALUES (22, '상2');
INSERT INTO flower_grade (grade, name) VALUES (23, '상3');
INSERT INTO flower_grade (grade, name) VALUES (31, '특1');
INSERT INTO flower_grade (grade, name) VALUES (32, '특2');
INSERT INTO flower_grade (grade, name) VALUES (33, '특3');