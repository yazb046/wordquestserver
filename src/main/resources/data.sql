INSERT INTO public."T_GOAL_TYPE"  (description) VALUES ('Markdown'), ('Learn Language'), ('Video stream');

INSERT INTO public."T_DICT" (description) VALUES ('Deutsch');

INSERT INTO public."T_WORD" (dictionary_id, lang_level, word)
VALUES
(1, 'A1', 'antworten'),
(1, 'A1', 'arbeiten'),
(1, 'A1', 'arbeitslos'),
(1, 'A1', 'aufhören'),
(1, 'A1', 'aufstehen');

INSERT INTO public."T_TEXT"
(text, contextword, version, checked, lang_level)
VALUES
('Er antwortet nicht.', 'antworten', 1, true, 'A1'),
('Wo arbeiten Sie?', 'arbeiten', 1, true, 'A1'),
('Es gibt bei uns viele Leute, die schon lange arbeitslos sind', 'arbeitslos', 1, true, 'A1'),
('Der Kurs hört in einer Woche auf. Hier hört die Bahnhofstraße auf.', 'aufhören', 1 , true, 'A1'),
('Ich muss immer um vier Uhr aufstehen. Soll ich aufstehen?','aufstehen', 1 , true, 'A1'),
('Bitte antworten Sie sofort.','sofort', 1 , true, 'A1'),
('Ab morgen muss ich arbeiten.','ab', 1 , true, 'A1'),
('Viele meiner Verwandten, z. B. meine beiden Brüder, arbeiten auch hier.','Beispiel', 1 , true, 'A1'),
('Das ist Frau Becker. Guten Tag, Frau Schmitt! Hier arbeiten mehr Frauen als Männer.','Frau', 1 , true, 'A1'),
('Ich muss jeden Tag von 8 Uhr bis 18 Uhr arbeiten.','müssen', 1 , true, 'A1'),
('Es gibt bei uns viele Leute, die schon lange arbeitslos sind.','arbeitslos', 1 , true, 'A1'),
('Seit seiner Kündigung vor einem Monat ist er arbeitslos und sucht aktiv nach neuen beruflichen Möglichkeiten.','arbeitslos', 1 , true, 'A1'),
('Der Kurs hört in einer Woche auf. Hier hört die Bahnhofstraße auf.','aufhören', 1 , true, 'A1'),
('Ich muss immer um vier Uhr aufstehen. Soll ich aufstehen?','aufstehen', 1 , true, 'A1'),
('Sie sollte mit dem Rauchen aufhören, um ihre Gesundheit zu verbessern.','aufhören', 1 , true, 'A1'),
('Wenn du mit dem Lernen aufhörst, wirst du deine Ziele nicht erreichen.','aufhören', 1 , true, 'A1'),
('Er kann einfach nicht damit aufhören, Witze zu erzählen, selbst in ernsten Situationen.','aufhören', 1 , true, 'A1');
