
-- Schema creation

CREATE TABLE users (
    login_name VARCHAR(20) NOT NULL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    password VARCHAR(100) NOT NULL,
    created_on TIMESTAMP NOT NULL DEFAULT now()
);

CREATE TABLE categories (
    code VARCHAR(20) PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    created_on TIMESTAMP NOT NULL DEFAULT now()
);

CREATE TABLE tags (
    code VARCHAR(20) PRIMARY KEY,
    created_on TIMESTAMP NOT NULL DEFAULT now()
);

CREATE TABLE expenses (
    id BIGSERIAL PRIMARY KEY,
    category_code VARCHAR(20) NOT NULL REFERENCES categories(code),
    value REAL NOT NULL,
    date TIMESTAMP NOT NULL,
    created_on TIMESTAMP NOT NULL DEFAULT now(),
    updated_on TIMESTAMP NOT NULL DEFAULT now(),
    created_by VARCHAR(20) NOT NULL REFERENCES users(login_name),
    notes TEXT
);

CREATE TABLE expense_tags (
    expense_id BIGINT NOT NULL REFERENCES expenses(id),
    tag_code VARCHAR(20) NOT NULL REFERENCES tags(code),
    PRIMARY KEY (expense_id, tag_code)
);


