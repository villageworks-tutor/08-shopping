ALTER TABLE customer ADD COLUMN password TEXT DEFAULT 'himimtu' NOT NULL;
INSERT INTO customer (name, address, tel, email) VALUES ('リスキル太郎', '東京都新宿区', '03-1111-1111', 'reskill@aaa.com');