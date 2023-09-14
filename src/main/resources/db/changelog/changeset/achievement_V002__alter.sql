ALTER TABLE user_achievement ADD CONSTRAINT unique_achievement_id_and_user_id UNIQUE(user_id, achievement_id);

INSERT INTO achievement (title, description, rarity, points)
VALUES ('Сенсей', 'Взять на менторство 30 человек', 3, 30);