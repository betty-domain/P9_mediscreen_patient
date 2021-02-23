use `patient_p9_test`;
truncate `patient`;

INSERT INTO `patient` ( `address`, `birth_date`, `firstname`, `lastname`, `phone`, `sex`)
VALUES ('Address 1', '2000-05-15', 'Bob', 'Dylan', '111-222-3333', 'M');

INSERT INTO `patient` ( `address`, `birth_date`, `firstname`, `lastname`, `phone`, `sex`)
VALUES ('Address 2', '1950-11-28', 'Marylin', 'Monroe', '222-111-4444', 'F');

