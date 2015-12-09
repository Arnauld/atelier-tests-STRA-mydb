DROP ALL OBJECTS;

CREATE TABLE clients (
  email          VARCHAR(255) PRIMARY KEY,
  nom            VARCHAR(100) NOT NULL,
  prenom         VARCHAR(100),
  accountType    VARCHAR(100) NOT NULL,
  activationDate DATETIME
);

INSERT INTO clients (email, nom, prenom, accountType, activationDate)
VALUES ('normal@monsite.fr', 'LeGrand', 'Paul', 'STANDART', '2015-11-30 11:14:16');
INSERT INTO clients (email, nom, prenom, accountType, activationDate)
VALUES ('platinium@monsite.fr', 'Lagare', 'Jean', 'PLATINIUM', '2015-12-04 11:14:16');
INSERT INTO clients (email, nom, prenom, accountType)
VALUES ('inactif@monsite.fr', 'Durand', 'Marc', 'STANDART');

CREATE TABLE stocks (
  id       VARCHAR(255) PRIMARY KEY,
  quantity INT NOT NULL
);

INSERT INTO stocks (id, quantity) VALUES
  ('71e8e7e5c52a45f682cd23021931d4ea', 0),
  ('dde361414fb2cd113346c7341fc6805f', 10),
  ('b8ec935decba42bfb4551b357ef04be1', 5),
  ('8e5cfaee57c942a8b8304bfdb68b6014', 9),
  ('1fc6846dde361645fb2cd05f1133c7d4', 2),
  ('fc7e3ef376054109ac34db53ec56961e', 6),
  ('113346dde361414fb2cdc7d41fc6805f', 2),
  ('2ed4a086cee94a8496816d11b9790916', 3),
  ('61645fb2113346dde3cdc7d41fc6805f', 5);


CREATE TABLE produit_catalogues (
  reference        VARCHAR(255) PRIMARY KEY,
  libelle          VARCHAR(255),
  prixAchat        VARCHAR(20),
  marge            VARCHAR(20),
  categorieProduit VARCHAR(255),
  actif            BOOLEAN,
);

INSERT INTO produit_catalogues (reference, libelle, prixAchat, marge, categorieProduit, actif)
VALUES
  ('71e8e7e5c52a45f682cd23021931d4ea', 'Durex feeling sensual x10', '10.45', '4', 'PRESERVATIF', TRUE),
  ('dde361414fb2cd113346c7341fc6805f', 'Colgate fraisheur Plus', '2.65', '1.32', 'HYGIENE_DENTAIRE', FALSE),
  ('b8ec935decba42bfb4551b357ef04be1', 'Durex Play', '7.5', '1.5', 'PRESERVATIF', TRUE),
  ('8e5cfaee57c942a8b8304bfdb68b6014', 'Pastilles vichy', '12.6', '7.05', 'CONFISERIE', TRUE),
  ('1fc6846dde361645fb2cd05f1133c7d4', 'Se soigner avec les plantes (des pieds)', '10.5', '4', 'LIVRE', TRUE),
  ('fc7e3ef376054109ac34db53ec56961e', 'Doliprane', '7.9', '1', 'MEDICAMENT_NON_REMBOURSABLE', TRUE),
  ('113346dde361414fb2cdc7d41fc6805f', 'Actiranox', '10.5', '3.21', 'MEDICAMENT_REMBOURSABLE', FALSE),
  ('2ed4a086cee94a8496816d11b9790916', 'Efferalgan', '8.6', '0.9', 'MEDICAMENT_NON_REMBOURSABLE', TRUE),
  ('61645fb2113346dde3cdc7d41fc6805f', 'Bequilles', '50.5', '30', 'EQUIPEMENT_PERSONNE_DEPENDANTE', FALSE);














