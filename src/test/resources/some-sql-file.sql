CREATE TABLE WardType (
  ward_type INT,
  charge_per_day FLOAT NOT NULL,

  PRIMARY KEY (ward_type)
);

CREATE TABLE Ward (
  ward_number INT AUTO_INCREMENT,
  ward_type INT NOT NULL,
  capacity INT NOT NULL,
  availability INT NOT NULL,

  PRIMARY KEY (ward_number),
  FOREIGN KEY (ward_type) REFERENCES WardType(ward_type)
);

DROP TABLE Staff;

DROP TABLE Doctor;

INSERT INTO Nurse (
  staff_id,
  ward_number,
) VALUES (
);


