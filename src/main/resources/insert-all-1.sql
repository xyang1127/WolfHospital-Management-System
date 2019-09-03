INSERT INTO WardType (
  ward_type,
  charge_per_day
) VALUES (
  1,
  200
);

INSERT INTO WardType (
  ward_type,
  charge_per_day
) VALUES (
  2,
  100
);

INSERT INTO WardType (
  ward_type,
  charge_per_day
) VALUES (
  4,
  50
);

INSERT INTO Ward (
  ward_number,
  ward_type,
  capacity,
  availability
) VALUES (
  1,
  4,
  4,
  2
);

INSERT INTO Ward (
  ward_number,
  ward_type,
  capacity,
  availability
) VALUES (
  2,
  4,
  4,
  3
);

INSERT INTO Ward (
  ward_number,
  ward_type,
  capacity,
  availability
) VALUES (
  3,
  2,
  2,
  2
);

INSERT INTO Ward (
  ward_number,
  ward_type,
  capacity,
  availability
) VALUES (
  4,
  2,
  2,
  2
);

INSERT INTO Staff (
  staff_id,
  last_name,
  first_name,
  DOB,
  gender,
  job_title,
  phone_number,
  professional_title,
  department,
  street_address,
  city,
  state,
  zip_code
) VALUES (
  100,
  'Smith',
  'Mary',
  '1979-01-01',
  'F',
  'Doctor',
  '654',
  'senior',
  'Neurology',
  '90 ABC St',
  'Raleigh',
  'NC',
  '27'
);

INSERT INTO Staff (
  staff_id,
  last_name,
  first_name,
  DOB,
  gender,
  job_title,
  phone_number,
  professional_title,
  department,
  street_address,
  city,
  state,
  zip_code
) VALUES (
  101,
  'Doe',
  'John',
  '1974-02-05',
  'M',
  'Billing staff',
  '564',
  null,
  'Office',
  '798 XYZ St',
  'Rochester',
  'NY',
  '54'
);

INSERT INTO Staff (
  staff_id,
  last_name,
  first_name,
  DOB,
  gender,
  job_title,
  phone_number,
  professional_title,
  department,
  street_address,
  city,
  state,
  zip_code
) VALUES (
  102,
  'Jones',
  'Carol',
  '1964-01-05',
  'F',
  'Nurse',
  '911',
  null,
  'ER',
  '351 MH St',
  'Greensboro',
  'NC',
  '27'
);

INSERT INTO Staff (
  staff_id,
  last_name,
  first_name,
  DOB,
  gender,
  job_title,
  phone_number,
  professional_title,
  department,
  street_address,
  city,
  state,
  zip_code
) VALUES (
  103,
  'Sams',
  'Emma',
  '1964-03-25',
  'F',
  'Doctor',
  '546',
  'Senior surgeon',
  'Oncological Surgery',
  '49 ABC St',
  'Raleigh',
  'NC',
  '27'
);

INSERT INTO Staff (
  staff_id,
  last_name,
  first_name,
  DOB,
  gender,
  job_title,
  phone_number,
  professional_title,
  department,
  street_address,
  city,
  state,
  zip_code
) VALUES (
  104,
  'Hale',
  'Ava',
  '1964-01-15',
  'F',
  'Front Desk Staff',
  '777',
  null,
  'Office',
  '425 RG St',
  'Raleigh',
  'NC',
  '27'
);

INSERT INTO Staff (
  staff_id,
  last_name,
  first_name,
  DOB,
  gender,
  job_title,
  phone_number,
  professional_title,
  department,
  street_address,
  city,
  state,
  zip_code
) VALUES (
  105,
  'Brown',
  'Peter',
  '1967-03-05',
  'M',
  'Doctor',
  '724',
  'Anesthetist',
  'Oncological Surgery',
  '475 RG St',
  'Raleigh',
  'NC',
  '27'
);

INSERT INTO Staff (
  staff_id,
  last_name,
  first_name,
  DOB,
  gender,
  job_title,
  phone_number,
  professional_title,
  department,
  street_address,
  city,
  state,
  zip_code
) VALUES (
  106,
  'Strange',
  'Olivia',
  '1992-04-01',
  'F',
  'Nurse',
  '799',
  null,
  'Neurology',
  '325 PD St',
  'Raleigh',
  'NC',
  '27'
);

INSERT INTO Doctor (
  staff_id,
  specialty
) VALUES (
  100,
  NULL
);

INSERT INTO Doctor (
  staff_id,
  specialty
) VALUES (
  103,
  NULL
);

INSERT INTO Doctor (
  staff_id,
  specialty
) VALUES (
  105,
  NULL
);

INSERT INTO NurseAssignedWard (
  staff_id,
  ward_number
) VALUES (
  102,
  1
);

INSERT INTO NurseAssignedWard (
  staff_id,
  ward_number
) VALUES (
  102,
  2
);

INSERT INTO NurseAssignedWard (
  staff_id,
  ward_number
) VALUES (
  106,
  3
);

INSERT INTO NurseAssignedWard (
  staff_id,
  ward_number
) VALUES (
  106,
  4
);

INSERT INTO Patient (
  patient_id,
  first_name,
  last_name,
  ssn,
  gender,
  DOB,
  phone_number,
  street_address,
  city,
  state,
  zip_code,
  status
) VALUES (
  1001,
  'David',
  'Aldridge',
  '000-01-1234',
  'M',
  '1980-01-30',
  '919-123-3324',
  '69 ABC St',
  'Raleigh',
  'NC',
  '27730',
  'processing treatment plan 20'
);

INSERT INTO Patient (
  patient_id,
  first_name,
  last_name,
  ssn,
  gender,
  DOB,
  phone_number,
  street_address,
  city,
  state,
  zip_code,
  status
) VALUES (
  1002,
  'Sarah',
  'Belford',
  '000-02-1234',
  'F',
  '1971-01-30',
  '919-563-3478',
  '81 DEF St ',
  'Cary',
  'NC',
  '27519',
  'processing treatment plan 20'
);

INSERT INTO Patient (
  patient_id,
  first_name,
  last_name,
  ssn,
  gender,
  DOB,
  phone_number,
  street_address,
  city,
  state,
  zip_code,
  status
) VALUES (
  1003,
  'Joseph',
  'Crawford',
  '000-03-1234',
  'M',
  '1987-01-30',
  '919-957-2199',
  '31 OPG St',
  'Cary',
  'NC',
  '27519',
  'processing treatment plan 10'
);

INSERT INTO Patient (
  patient_id,
  first_name,
  last_name,
  ssn,
  gender,
  DOB,
  phone_number,
  street_address,
  city,
  state,
  zip_code,
  status
) VALUES (
  1004,
  'Lucy',
  'Dun',
  '000-04-1234',
  'F',
  '1985-01-30',
  '919-838-7123',
  '10 TBC St',
  'Raleigh',
  'NC',
  '27730',
  'processing treatment plan 5'
);

INSERT INTO BillingAccount (
  account_number,
  patient_id,
  SSN_responsible_party,
  payment_method,
  card_number,
  insurance_provider,
  billing_street,
  billing_city,
  billing_state,
  billing_zip_code,
  check_in_date
) VALUES (
  1,
  1004,
  '000-04-1234',
  'Credit card',
  '4044-9876-1234-9123',
  NULL,
  '10 TBC St',
  'Raleigh',
  'NC',
  '27730',
  '2019-03-17'
);

INSERT INTO BillingAccount (
  account_number,
  patient_id,
  SSN_responsible_party,
  payment_method,
  card_number,
  insurance_provider,
  billing_street,
  billing_city,
  billing_state,
  billing_zip_code,
  check_in_date
) VALUES (
  2,
  1001,
  '000-01-1234',
  'Credit card',
  '4044-8754-0961-3234',
  NULL,
  '69 ABC St',
  'Raleigh',
  'NC',
  '27730',
  '2019-03-01'
);

INSERT INTO BillingAccount (
  account_number,
  patient_id,
  SSN_responsible_party,
  payment_method,
  card_number,
  insurance_provider,
  billing_street,
  billing_city,
  billing_state,
  billing_zip_code,
  check_in_date
) VALUES (
  3,
  1002,
  '000-02-1234',
  'Credit card',
  '4401-9823-9854-1143',
  NULL,
  '81 DEF St',
  'Cary',
  'NC',
  '27519',
  '2019-03-10'
);

INSERT INTO BillingAccount (
  account_number,
  patient_id,
  SSN_responsible_party,
  payment_method,
  card_number,
  insurance_provider,
  billing_street,
  billing_city,
  billing_state,
  billing_zip_code,
  check_in_date
) VALUES (
  4,
  1003,
  '000-02-1234',
  'Check',
  NULL,
  NULL,
  '31 OPG St',
  'Cary',
  'NC',
  '27519',
  '2019-03-15'
);

INSERT INTO BillingRecord (
  account_number,
  billing_record_number,
  fee_description,
  fee
) VALUES (
  1,
  1,
  'registration fee',
  100
);

INSERT INTO BillingRecord (
    account_number,
    billing_record_number,
    fee_description,
    fee
  ) VALUES (
    1,
    2,
    'accommodation fee',
    400
  );

INSERT INTO BillingRecord (
  account_number,
  billing_record_number,
  fee_description,
  fee
) VALUES (
  1,
  3,
  'medication',
  50
);


INSERT INTO BillingRecord (
  account_number,
  billing_record_number,
  fee_description,
  fee
) VALUES (
  2,
  1,
  'registration fee',
  100
);

INSERT INTO BillingRecord (
  account_number,
  billing_record_number,
  fee_description,
  fee
) VALUES (
  2,
  2,
  'medication',
  100
);

INSERT INTO BillingRecord (
  account_number,
  billing_record_number,
  fee_description,
  fee
) VALUES (
  3,
  1,
  'registration fee',
  100
);

INSERT INTO BillingRecord (
  account_number,
  billing_record_number,
  fee_description,
  fee
) VALUES (
  3,
  2,
  'medication',
  25
);

INSERT INTO BillingRecord (
  account_number,
  billing_record_number,
  fee_description,
  fee
) VALUES (
  4,
  1,
  'registration fee',
  100
);

INSERT INTO BillingRecord (
  account_number,
  billing_record_number,
  fee_description,
  fee
) VALUES (
  4,
  2,
  'medication',
  125
);

INSERT INTO MedicalRecord (
  record_id,
  record_type,
  patient_id,
  start_date,
  end_date,
  diagnosis_details,
  prescription,
  specialist,
  test_status,
  test_result,
  doctor
) VALUES (
  1,
  'checkin',
  1001,
  '2019-03-01',
  NULL,
  NULL,
  NULL,
  NULL,
  NULL,
  NULL,
  100
);

INSERT INTO MedicalRecord (
  record_id,
  record_type,
  patient_id,
  start_date,
  end_date,
  diagnosis_details,
  prescription,
  specialist,
  test_status,
  test_result,
  doctor
) VALUES (
  2,
  'treatment',
  1001,
  '2019-03-01',
  NULL,
  'Hospitalization',
  'nervine',
  NULL,
  NULL,
  NULL,
  100
);

INSERT INTO MedicalRecord (
  record_id,
  record_type,
  patient_id,
  start_date,
  end_date,
  diagnosis_details,
  prescription,
  specialist,
  test_status,
  test_result,
  doctor
) VALUES (
  1,
  'checkin',
  1002,
  '2019-03-10',
  NULL,
  NULL,
  NULL,
  NULL,
  NULL,
  NULL,
  100
);

INSERT INTO MedicalRecord (
  record_id,
  record_type,
  patient_id,
  start_date,
  end_date,
  diagnosis_details,
  prescription,
  specialist,
  test_status,
  test_result,
  doctor
) VALUES (
  2,
  'treatment',
  1002,
  '2019-03-10',
  NULL,
  'Hospitalization',
  'nervine',
  NULL,
  NULL,
  NULL,
  100
);

INSERT INTO MedicalRecord (
  record_id,
  record_type,
  patient_id,
  start_date,
  end_date,
  diagnosis_details,
  prescription,
  specialist,
  test_status,
  test_result,
  doctor
) VALUES (
  1,
  'checkin',
  1003,
  '2019-03-15',
  NULL,
  NULL,
  NULL,
  NULL,
  NULL,
  NULL,
  100
);

INSERT INTO MedicalRecord (
  record_id,
  record_type,
  patient_id,
  start_date,
  end_date,
  diagnosis_details,
  prescription,
  specialist,
  test_status,
  test_result,
  doctor
) VALUES (
  2,
  'treatment',
  1003,
  '2019-03-15',
  NULL,
  'Hospitalization',
  'nervine',
  NULL,
  NULL,
  NULL,
  100
);

INSERT INTO MedicalRecord (
  record_id,
  record_type,
  patient_id,
  start_date,
  end_date,
  diagnosis_details,
  prescription,
  specialist,
  test_status,
  test_result,
  doctor
) VALUES (
  1,
  'checkin',
  1004,
  '2019-03-17',
  '2019-03-21',
  NULL,
  NULL,
  NULL,
  NULL,
  NULL,
  103
);

INSERT INTO MedicalRecord (
  record_id,
  record_type,
  patient_id,
  start_date,
  end_date,
  diagnosis_details,
  prescription,
  specialist,
  test_status,
  test_result,
  doctor
) VALUES (
  2,
  'treatment',
  1004,
  '2019-03-17',
  '2019-03-21',
  'Surgeon, Hospitalization',
  'analgesic',
  NULL,
  NULL,
  NULL,
  103
);

INSERT INTO Bed (
  bed_number,
  ward_number,
  availability
) VALUES (
  1,
  1,
  0
);

INSERT INTO Bed (
  bed_number,
  ward_number,
  availability
) VALUES (
  2,
  1,
  0
);

INSERT INTO Bed (
  bed_number,
  ward_number,
  availability
) VALUES (
  3,
  1,
  1
);

INSERT INTO Bed (
  bed_number,
  ward_number,
  availability
) VALUES (
  4,
  1,
  1
);

INSERT INTO Bed (
  bed_number,
  ward_number,
  availability
) VALUES (
  1,
  2,
  0
);

INSERT INTO Bed (
  bed_number,
  ward_number,
  availability
) VALUES (
  2,
  2,
  1
);

INSERT INTO Bed (
  bed_number,
  ward_number,
  availability
) VALUES (
  3,
  2,
  1
);

INSERT INTO Bed (
  bed_number,
  ward_number,
  availability
) VALUES (
  4,
  2,
  1
);

INSERT INTO Bed (
  bed_number,
  ward_number,
  availability
) VALUES (
  1,
  3,
  1
);

INSERT INTO Bed (
  bed_number,
  ward_number,
  availability
) VALUES (
  2,
  3,
  1
);

INSERT INTO Bed (
  bed_number,
  ward_number,
  availability
) VALUES (
  1,
  4,
  1
);

INSERT INTO Bed (
  bed_number,
  ward_number,
  availability
) VALUES (
  2,
  4,
  1
);

INSERT INTO PatientAssignedBed (
  patient_id,
  bed_number,
  ward_number,
  start_date,
  end_date,
  status
) VALUES (
  1001,
  1,
  1,
  '2019-03-01',
  NULL,
  'Assigned'
);

INSERT INTO PatientAssignedBed (
  patient_id,
  bed_number,
  ward_number,
  start_date,
  end_date,
  status
) VALUES (
  1002,
  1,
  2,
  '2019-03-10',
  NULL,
  'Assigned'
);

INSERT INTO PatientAssignedBed (
  patient_id,
  bed_number,
  ward_number,
  start_date,
  end_date,
  status
) VALUES (
  1003,
  2,
  1,
  '2019-03-15',
  NULL,
  'Assigned'
);

INSERT INTO PatientAssignedBed (
  patient_id,
  bed_number,
  ward_number,
  start_date,
  end_date,
  status
) VALUES (
  1004,
  1,
  3,
  '2019-03-17',
  '2019-03-21',
  'Assigned'
);
