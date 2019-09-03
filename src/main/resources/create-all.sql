CREATE TABLE WardType (
  ward_type INT(11) DEFAULT 0,
  charge_per_day FLOAT NOT NULL,

  PRIMARY KEY (ward_type)
);

CREATE TABLE Ward (
  ward_number INT(11) DEFAULT 0,
  ward_type INT(11) NOT NULL,
  capacity INT(11) NOT NULL,
  availability INT(11) NOT NULL,

  PRIMARY KEY (ward_number),
  FOREIGN KEY (ward_type) REFERENCES WardType(ward_type)
);

CREATE TABLE Staff (

  staff_id INT(11) AUTO_INCREMENT,
  first_name VARCHAR(255) NOT NULL,
  last_name VARCHAR(255) NOT NULL,
  DOB DATE NOT NULL,
  gender CHAR(1) NOT NULL,
  job_title VARCHAR(255) NOT NULL,
  phone_number VARCHAR(31),
  professional_title VARCHAR(255),
  department VARCHAR(255) NOT NULL,
  street_address VARCHAR(255) NOT NULL,
  city VARCHAR(255) NOT NULL,
  state CHAR(2) NOT NULL,
  zip_code CHAR(5) NOT NULL,

  PRIMARY KEY (staff_id)
);

CREATE TABLE Doctor (
  staff_id INT(11) DEFAULT 0,
  specialty VARCHAR(255),

  PRIMARY KEY (staff_id),
  FOREIGN KEY (staff_id) REFERENCES Staff(staff_id) ON DELETE CASCADE
);

CREATE TABLE NurseAssignedWard (
  staff_id INT(11) DEFAULT 0,
  ward_number INT(11),

  PRIMARY KEY (staff_id, ward_number),
  FOREIGN KEY (staff_id) REFERENCES Staff(staff_id) ON DELETE CASCADE,
  FOREIGN KEY (ward_number) REFERENCES Ward(ward_number) ON DELETE CASCADE
);

CREATE TABLE Patient (
  patient_id INT AUTO_INCREMENT,
  first_name VARCHAR(255) NOT NULL,
  last_name VARCHAR(255) NOT NULL,
  ssn CHAR(11),
  gender CHAR(1) NOT NULL,
  DOB DATE NOT NULL,
  phone_number VARCHAR(31),
  street_address VARCHAR(255),
  city VARCHAR(255),
  state CHAR(2),
  zip_code CHAR(5),
  status VARCHAR(255) NOT NULL,

  PRIMARY KEY (patient_id)
);

CREATE TABLE BillingAccount (
  account_number INT(11) AUTO_INCREMENT,
  patient_id INT(11) NOT NULL,
  SSN_responsible_party CHAR(11) NOT NULL,
  payment_method VARCHAR(255) NOT NULL,
  card_number CHAR(19),
  insurance_provider VARCHAR(255),
  billing_street VARCHAR(255) NOT NULL,
  billing_city VARCHAR(255) NOT NULL,
  billing_state VARCHAR(255) NOT NULL,
  billing_zip_code CHAR(5) NOT NULL,
  check_in_date DATE NOT NULL,

  PRIMARY KEY (account_number),
  FOREIGN KEY (patient_id) REFERENCES Patient(patient_id) ON DELETE CASCADE
);

CREATE TABLE BillingRecord (
  account_number INT(11) DEFAULT 0,
  billing_record_number INT(11) DEFAULT 0,
  fee_description VARCHAR(255) NOT NULL,
  fee FLOAT NOT NULL,

  PRIMARY KEY (account_number, billing_record_number),
  FOREIGN KEY (account_number) REFERENCES BillingAccount(account_number) ON DELETE CASCADE
);


CREATE TABLE MedicalRecord (
  record_id INT(11),
  record_type VARCHAR(255),
  patient_id INT(11),
  start_date DATE NOT NULL,
  end_date DATE DEFAULT NULL,
  diagnosis_details VARCHAR(1024) DEFAULT NULL,
  prescription VARCHAR(255) DEFAULT NULL,
  specialist INT(11),
  test_status VARCHAR(255) DEFAULT NULL,
  test_result VARCHAR(255) DEFAULT NULL,
  doctor INT(11),

  PRIMARY KEY (record_id, patient_id),
  FOREIGN KEY (patient_id) REFERENCES Patient(patient_id) ON DELETE CASCADE,
  FOREIGN KEY (specialist) REFERENCES Doctor(staff_id) ON DELETE SET NULL,
  FOREIGN KEY (doctor) REFERENCES Doctor(staff_id) ON DELETE SET NULL
);

CREATE TABLE Bed (
  bed_number INT(11) DEFAULT 0,
  ward_number INT(11) DEFAULT 0,
  availability BOOLEAN NOT NULL,

  PRIMARY KEY (bed_number, ward_number),
  FOREIGN KEY (ward_number) REFERENCES Ward(ward_number) ON DELETE CASCADE
);

CREATE TABLE PatientAssignedBed (
  assignment_id INT(11) AUTO_INCREMENT,
  patient_id INT(11) NOT NULL,
  bed_number INT(11) NOT NULL,
  ward_number INT(11) NOT NULL,
  start_date DATE NOT NULL,
  end_date DATE,
  status VARCHAR(255) NOT NULL,

  PRIMARY KEY (assignment_id),
  FOREIGN KEY (patient_id) REFERENCES Patient(patient_id) ON DELETE CASCADE,
  FOREIGN KEY (ward_number, bed_number) REFERENCES Bed(ward_number, bed_number) ON DELETE CASCADE
);


